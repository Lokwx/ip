package lokwx;

/**
 * Represents a task tracked by the Lokwx chatbot.
 */
public class Task {

    /**
     * Identifies the supported task categories.
     */
    public enum TaskType {
        TASK,
        TODO,
        DEADLINE,
        EVENT
    }

    protected TaskType taskType;
    protected String description;
    protected boolean isDone;

    /**
     * Creates an incomplete task with the specified description.
     *
     * @param description Description of the task.
     * @param taskType Type used to identify the task.
     */
    public Task(String description, TaskType taskType) {
        this.description = description;
        this.taskType = taskType;
        this.isDone = false;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    /**
     * Returns a checkbox that indicates whether this task is done.
     *
     * @return Checkbox containing an X when the task is done.
     */
    public String displayCheckbox() {
        return "[" + (isDone ? "X" : " ") + "]";
    }

    /**
     * Returns the confirmation message shown when this task is added.
     *
     * @return Task-added confirmation message.
     */
    public String getTaskAddedMessage() {
        return String.format("Got it. I've added this task\n%s\n", displayTask());
    }

    /**
     * Returns this task formatted for display in the task list.
     *
     * @return Formatted task description and completion status.
     */
    public String displayTask() {
        return String.format("[T]%s %s", displayCheckbox(), description);
    }
}
