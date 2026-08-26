public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setIsDone(boolean newStatus) {
        this.isDone = newStatus;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public String displayCheckbox() {
        return "[" + (isDone ? "X" : " ") +  "]";
    }
}
