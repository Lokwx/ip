package lokwx;

/**
 * Represents an event task occurring over a time period.
 */
public class Event extends Task {
    private String eventFrom, eventTo;

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
    public String taskAddedString() {
        return String.format(
                "Got it. I've added this event:\n" +
                "%s\n",displayTask()
        );
    }

    @Override
    public String displayTask() {
        return String.format("[E]%s %s (from: %s to: %s)",displayCheckbox(), description, eventFrom, eventTo);
    }
}
