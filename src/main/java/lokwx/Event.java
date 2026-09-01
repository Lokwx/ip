package lokwx;

/**
 * Represents an event task occurring over a time period.
 */
public class Event extends Task {
    private String eventFrom;
    private String eventTo;

    /**
     * Creates an incomplete event task with the specified time period.
     *
     * @param description Description of the event.
     * @param eventFrom Start of the event period.
     * @param eventTo End of the event period.
     * @param taskType Type used to identify the task.
     */
    public Event(String description, String eventFrom, String eventTo, TaskType taskType) {
        super(description, taskType);
        this.eventFrom = eventFrom;
        this.eventTo = eventTo;
    }

    public String getEventFrom() {
        return eventFrom;
    }

    public void setEventFrom(String eventFrom) {
        this.eventFrom = eventFrom;
    }

    public String getEventTo() {
        return eventTo;
    }

    public void setEventTo(String eventTo) {
        this.eventTo = eventTo;
    }

    @Override
    public String getTaskAddedMessage() {
        return String.format("Got it. I've added this event:\n%s\n", displayTask());
    }

    @Override
    public String displayTask() {
        return String.format("[E]%s %s (from: %s to: %s)",
                displayCheckbox(), description, eventFrom, eventTo);
    }
}
