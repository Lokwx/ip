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
        for (String line : Files.readAllLines(DATA_FILE_PATH)) {
            if (!line.isBlank()) {
                taskHandler.addLoadedTask(createTask(line));
            }
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

    private static Task createTask(String line) throws IOException {
        String[] fields = line.split("\\|", -1);

        if (fields.length < DESCRIPTION_INDEX + 1) {
            throw new IOException("Saved task has an invalid format.");
        }

        boolean isDone = parseDoneStatus(fields[IS_DONE_INDEX]);
        String description = fields[DESCRIPTION_INDEX];

        return switch (fields[TASK_TYPE_INDEX]) {
        case "T" -> new Todo(description, Task.TaskType.TODO, isDone);
        case "D" -> new Deadline(description, getField(fields, DEADLINE_BY_INDEX),
                Task.TaskType.DEADLINE, isDone);
        case "E" -> new Event(description, getField(fields, EVENT_FROM_INDEX),
                getField(fields, EVENT_TO_INDEX), Task.TaskType.EVENT, isDone);
        default -> throw new IOException("Saved task has an unknown type.");
        };
    }

    private static boolean parseDoneStatus(String status) throws IOException {
        if (status.equals("0")) {
            return false;
        }

        if (status.equals("1")) {
            return true;
        }

        throw new IOException("Saved task has an invalid completion status.");
    }

    private static String getField(String[] fields, int index) throws IOException {
        if (fields.length <= index) {
            throw new IOException("Saved task has an invalid format.");
        }

        return fields[index];
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
