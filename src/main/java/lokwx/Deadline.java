package lokwx;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private String deadlineBy;

    public Deadline(String description, String deadlineBy, TaskType taskType) {
        super(description, taskType);
        this.deadlineBy = deadlineBy;
    }

    public String getDeadlineBy() {
        return deadlineBy;
    }

    public void setDeadlineBy(String deadlineBy) {
        this.deadlineBy = deadlineBy;
    }

    @Override
    public String taskAddedString() {
        return String.format(
                "Got it. I've added this deadline:\n" +
                "%s\n",displayTask()
        );
    }

    @Override
    public String displayTask() {
        return String.format("[D]%s %s (by: %s)",displayCheckbox(), description, deadlineBy);
    }

}
