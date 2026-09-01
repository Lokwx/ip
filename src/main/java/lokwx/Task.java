package lokwx;

/**
 * Represents a task tracked by the Lokwx chatbot.
 */
public class Task {

    public enum TaskType {
        TASK,
        TODO,
        DEADLINE,
        EVENT
    }

    private TaskType taskType;
    private String description;
    private boolean isDone;

    /**
     * Creates an incomplete task with the specified description.
     *
     * @param description Description of the task.
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


}
