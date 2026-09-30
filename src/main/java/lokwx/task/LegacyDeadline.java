package lokwx.task;

/**
 * Represents a deadline saved by an older Lokwx version with free-form time text.
 */
public class LegacyDeadline extends Task {
    private final String deadlineText;

    /**
     * Creates a deadline from the original saved description and time text.
     *
     * @param description Description of the deadline task.
     * @param deadlineText Original deadline text to preserve.
     * @param isDone Whether the task has been completed.
     */
    public LegacyDeadline(String description, String deadlineText, boolean isDone) {
        super(description, TaskType.DEADLINE, isDone);
        this.deadlineText = deadlineText;
    }

    public String getDeadlineText() {
        return deadlineText;
    }

    @Override
    public String getTaskAddedMessage() {
        return String.format("Got it. I've added this deadline:\n%s\n", displayTask());
    }

    @Override
    public String getTaskRemovedMessage() {
        return String.format("Got it. I've removed this deadline:\n%s\n", displayTask());
    }

    @Override
    public String displayTask() {
        return String.format("[D]%s %s (by: %s)", displayCheckbox(), description, deadlineText);
    }
}
