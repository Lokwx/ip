package lokwx.task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd uuuu", Locale.ENGLISH);
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd uuuu, h:mm a", Locale.ENGLISH);

    private final LocalDateTime deadlineBy;

    /**
     * Creates a deadline task with the specified due date and completion status.
     *
     * @param description Description of the deadline task.
     * @param deadlineBy Due date and time of the task.
     * @param taskType Type used to identify the task.
     * @param isDone Whether the task has been completed.
     */
    public Deadline(String description, LocalDateTime deadlineBy, TaskType taskType, boolean isDone) {
        super(description, taskType, isDone);
        this.deadlineBy = deadlineBy;
    }

    public LocalDateTime getDeadlineBy() {
        return deadlineBy;
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
        return String.format("[D]%s %s (by: %s)", displayCheckbox(), description, formatDeadline());
    }

    private String formatDeadline() {
        if (deadlineBy.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return deadlineBy.format(DATE_FORMATTER);
        }

        return deadlineBy.format(DATE_TIME_FORMATTER);
    }
}
