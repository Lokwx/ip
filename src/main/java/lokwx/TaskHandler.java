package lokwx;

/**
 * Manages the collection of tasks and performs operations on them.
 */
public class TaskHandler {
    private final Task[] tasks = new Task[100];
    private int numberOfTasks = 0;

    /**
     * Adds a task with the specified description and displays a confirmation.
     *
     * @param task Task to add.
     */
    public void addTask(Task task) {
        tasks[numberOfTasks++] = task;
        Echo.taskAdded(task, numberOfTasks);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param itemIndex Zero-based index of the task to mark.
     */
    public void markTask(int itemIndex) {
        tasks[itemIndex].setDone(true);
        Echo.printMarkedTask(tasks[itemIndex]);
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param itemIndex Zero-based index of the task to unmark.
     */
    public void unmarkTask(int itemIndex) {
        tasks[itemIndex].setDone(false);
        Echo.printUnmarkedTask(tasks[itemIndex]);
    }

    /**
     * Prints all recorded tasks.
     */
    public void printAllTasks() {
        Echo.printList(tasks, numberOfTasks);
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }
}
