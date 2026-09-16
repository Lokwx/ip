package lokwx.task;

/**
 * Represents a todo task without any date or time attached.
 */
public class Todo extends Task {
    /**
     * Creates an incomplete todo task with the specified description.
     *
     * @param description Description of the todo task.
     * @param taskType Type used to identify the task.
     */
    public Todo(String description, TaskType taskType, boolean isDone) {
        super(description, taskType, isDone);
    }

    @Override
    public String getTaskAddedMessage() {
        return String.format("Got it. I've added this Todo:\n%s\n", displayTask());
    }

    @Override
    public String getTaskRemovedMessage() {
        return String.format("Got it. I've removed this Todo:\n%s\n", displayTask());
    }
}
