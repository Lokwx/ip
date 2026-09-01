package lokwx;

/**
 * Represents a todo task without any date or time attached.
 */
public class Todo extends Task {
    public Todo(String description, TaskType taskType) {
        super(description, taskType);
    }

    @Override
    public String taskAddedString() {
        return String.format(
                "Got it. I've added this Todo:\n" +
                "%s\n",displayTask()
        );
    }
}
