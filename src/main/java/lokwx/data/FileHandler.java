package lokwx.data;

import lokwx.task.Deadline;
import lokwx.task.Event;
import lokwx.task.Task;
import lokwx.task.TaskHandler;
import lokwx.task.Todo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * Stores tasks in a versioned data file and loads both versioned and older records.
 */
public final class FileHandler {
    private static final Path DATA_FILE_PATH = Path.of("data", "lokwx.txt");
    private static final String FIELD_DELIMITER = "|";
    /**
     * Identifies records whose text fields are encoded to preserve delimiter characters.
     */
    private static final String FORMAT_VERSION = "v2";
    private static final int TASK_TYPE_INDEX = 0;
    private static final int IS_DONE_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int DEADLINE_BY_INDEX = 3;
    private static final int EVENT_FROM_INDEX = 3;
    private static final int EVENT_TO_INDEX = 4;

    private FileHandler() {
    }

    /**
     * Creates the data directory and task file when they do not already exist.
     *
     * @throws IOException If the data directory or file cannot be created.
     */
    public static void createFile() throws IOException {
        Files.createDirectories(DATA_FILE_PATH.getParent());

        if (Files.notExists(DATA_FILE_PATH)) {
            Files.createFile(DATA_FILE_PATH);
        }
    }

    /**
     * Loads saved tasks from versioned or older records into the task handler.
     * Rejoins pipe-separated description fields in older todo and deadline records.
     *
     * @param taskHandler Task handler that receives the loaded tasks.
     * @throws IOException If the data file cannot be read or a record is malformed.
     */
    public static void readData(TaskHandler taskHandler) throws IOException {
        List<Task> loadedTasks = new ArrayList<>();
        List<String> lines = Files.readAllLines(DATA_FILE_PATH);

        for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            String line = lines.get(lineIndex);

            if (!line.isBlank()) {
                loadedTasks.add(createTask(line, lineIndex + 1));
            }
        }

        for (Task task : loadedTasks) {
            taskHandler.addLoadedTask(task);
        }
    }

    /**
     * Saves all tasks in the versioned format by replacing the contents of the data file.
     * Encoded text fields preserve pipe characters in descriptions and event times.
     *
     * @param tasks Tasks to save.
     * @throws IOException If the data file cannot be written.
     */
    public static void saveData(List<Task> tasks) throws IOException {
        createFile();

        try (BufferedWriter writer = Files.newBufferedWriter(DATA_FILE_PATH)) {
            for (Task task : tasks) {
                writer.write(formatTask(task));
                writer.newLine();
            }
        }
    }

    /**
     * Creates a task from either the versioned format or an older unencoded record.
     *
     * @param line One record from the data file.
     * @param lineNumber One-based line number used in error messages.
     * @return Task represented by the record.
     * @throws IOException If the record contains invalid fields.
     */
    private static Task createTask(String line, int lineNumber) throws IOException {
        String[] storedFields = line.split("\\|", -1);
        // Older records have no version prefix; new records encode text to keep separators intact.
        boolean isEncoded = storedFields[0].equals(FORMAT_VERSION);
        String[] fields = isEncoded ? Arrays.copyOfRange(storedFields, 1, storedFields.length) : storedFields;

        if (fields.length < DESCRIPTION_INDEX + 1) {
            throw corruptedFileException(lineNumber, "an invalid format");
        }

        boolean isDone = parseDoneStatus(fields[IS_DONE_INDEX], lineNumber);

        return switch (fields[TASK_TYPE_INDEX]) {
            case "T" -> {
                if (isEncoded && fields.length != DESCRIPTION_INDEX + 1) {
                    throw corruptedFileException(lineNumber, "an invalid format");
                }
                String description = isEncoded
                        ? decodeText(fields[DESCRIPTION_INDEX], lineNumber)
                        : joinDescription(fields, fields.length);
                yield new Todo(description, Task.TaskType.TODO, isDone);
            }
            case "D" -> {
                if (fields.length < DEADLINE_BY_INDEX + 1
                        || (isEncoded && fields.length != DEADLINE_BY_INDEX + 1)) {
                    throw corruptedFileException(lineNumber, "an invalid format");
                }
                int deadlineIndex = fields.length - 1;
                String description = isEncoded
                        ? decodeText(fields[DESCRIPTION_INDEX], lineNumber)
                        : joinDescription(fields, deadlineIndex);
                yield new Deadline(description, parseDeadline(fields[deadlineIndex], lineNumber),
                        Task.TaskType.DEADLINE, isDone);
            }
            case "E" -> {
                if (fields.length != EVENT_TO_INDEX + 1) {
                    throw corruptedFileException(lineNumber, "an invalid format");
                }
                String description = decodeTextIfNeeded(fields[DESCRIPTION_INDEX], isEncoded, lineNumber);
                String eventFrom = decodeTextIfNeeded(fields[EVENT_FROM_INDEX], isEncoded, lineNumber);
                String eventTo = decodeTextIfNeeded(fields[EVENT_TO_INDEX], isEncoded, lineNumber);
                yield new Event(description, eventFrom, eventTo, Task.TaskType.EVENT, isDone);
            }
            default -> throw corruptedFileException(lineNumber, "an unknown task type");
        };
    }

    private static LocalDateTime parseDeadline(String deadlineText, int lineNumber) throws IOException {
        try {
            return LocalDateTime.parse(deadlineText);
        } catch (DateTimeParseException e) {
            throw corruptedFileException(lineNumber, "an invalid deadline date");
        }
    }

    private static boolean parseDoneStatus(String status, int lineNumber) throws IOException {
        if (status.equals("0")) {
            return false;
        }

        if (status.equals("1")) {
            return true;
        }

        throw corruptedFileException(lineNumber, "an invalid completion status");
    }

    /**
     * Rejoins description pieces split by pipe characters in an older record.
     *
     * @param fields Fields from an older record.
     * @param toIndex Exclusive index after the final description piece.
     * @return Complete task description.
     */
    private static String joinDescription(String[] fields, int toIndex) {
        return String.join(FIELD_DELIMITER, Arrays.copyOfRange(fields, DESCRIPTION_INDEX, toIndex));
    }

    private static String decodeTextIfNeeded(String text, boolean isEncoded, int lineNumber) throws IOException {
        return isEncoded ? decodeText(text, lineNumber) : text;
    }

    /**
     * Decodes a versioned record's text field as UTF-8.
     *
     * @param text URL-safe Base64 text to decode.
     * @param lineNumber One-based line number used in error messages.
     * @return Decoded text field.
     * @throws IOException If the field is not valid Base64 text.
     */
    private static String decodeText(String text, int lineNumber) throws IOException {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(text);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw corruptedFileException(lineNumber, "an invalid encoded text field");
        }
    }

    /**
     * Encodes a text field without pipe characters for the versioned format.
     *
     * @param text Text field to store.
     * @return URL-safe Base64 representation of the text.
     */
    private static String encodeText(String text) {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    private static IOException corruptedFileException(int lineNumber, String problem) {
        return new IOException("Data file is corrupted at line " + lineNumber + ": " + problem + ".");
    }

    /**
     * Formats a task as a versioned record with encoded text fields.
     *
     * @param task Task to store.
     * @return One data-file record.
     */
    private static String formatTask(Task task) {
        String isDone = task.getIsDone() ? "1" : "0";

        if (task instanceof Todo) {
            return String.join(FIELD_DELIMITER, FORMAT_VERSION, "T", isDone,
                    encodeText(task.getDescription()));
        }

        if (task instanceof Deadline deadline) {
            return String.join(FIELD_DELIMITER, FORMAT_VERSION, "D", isDone, encodeText(task.getDescription()),
                    deadline.getDeadlineBy().toString());
        }

        if (task instanceof Event event) {
            return String.join(FIELD_DELIMITER, FORMAT_VERSION, "E", isDone, encodeText(task.getDescription()),
                    encodeText(event.getEventFrom()), encodeText(event.getEventTo()));
        }

        throw new IllegalArgumentException("Unsupported task type.");
    }
}
