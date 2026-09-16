package lokwx.data;

import lokwx.task.Deadline;
import lokwx.task.Event;
import lokwx.task.Task;
import lokwx.task.TaskHandler;
import lokwx.task.Todo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores tasks in and loads tasks from the application's data file.
 */
public final class FileHandler {
    private static final Path DATA_FILE_PATH = Path.of("data", "lokwx.txt");
    private static final String FIELD_DELIMITER = "|";
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
     * Loads all saved tasks into the task handler.
     *
     * @param taskHandler Task handler that receives the loaded tasks.
     * @throws IOException If the data file cannot be read.
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
     * Saves all tasks by replacing the contents of the data file.
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

    private static Task createTask(String line, int lineNumber) throws IOException {
        String[] fields = line.split("\\|", -1);

        if (fields.length < DESCRIPTION_INDEX + 1) {
            throw corruptedFileException(lineNumber, "an invalid format");
        }

        boolean isDone = parseDoneStatus(fields[IS_DONE_INDEX], lineNumber);
        String description = fields[DESCRIPTION_INDEX];

        return switch (fields[TASK_TYPE_INDEX]) {
        case "T" -> new Todo(description, Task.TaskType.TODO, isDone);
        case "D" -> new Deadline(description, getField(fields, DEADLINE_BY_INDEX, lineNumber),
                Task.TaskType.DEADLINE, isDone);
        case "E" -> new Event(description, getField(fields, EVENT_FROM_INDEX, lineNumber),
                getField(fields, EVENT_TO_INDEX, lineNumber), Task.TaskType.EVENT, isDone);
        default -> throw corruptedFileException(lineNumber, "an unknown task type");
        };
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

    private static String getField(String[] fields, int index, int lineNumber) throws IOException {
        if (fields.length <= index) {
            throw corruptedFileException(lineNumber, "an invalid format");
        }

        return fields[index];
    }

    private static IOException corruptedFileException(int lineNumber, String problem) {
        return new IOException("Data file is corrupted at line " + lineNumber + ": " + problem + ".");
    }

    private static String formatTask(Task task) {
        String isDone = task.getIsDone() ? "1" : "0";

        if (task instanceof Todo) {
            return String.join(FIELD_DELIMITER, "T", isDone, task.getDescription());
        }

        if (task instanceof Deadline deadline) {
            return String.join(FIELD_DELIMITER, "D", isDone, task.getDescription(), deadline.getDeadlineBy());
        }

        if (task instanceof Event event) {
            return String.join(FIELD_DELIMITER, "E", isDone, task.getDescription(),
                    event.getEventFrom(), event.getEventTo());
        }

        throw new IllegalArgumentException("Unsupported task type.");
    }
}
