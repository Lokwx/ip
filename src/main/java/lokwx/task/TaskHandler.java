package lokwx.task;

import lokwx.ui.Echo;

import java.util.ArrayList;

/**
 * Manages the collection of tasks and performs operations on them.
 */
public class TaskHandler {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds the specified task and displays a confirmation.
     *
     * @param task Task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
        Echo.printTaskAddedConfirmation(task, tasks.size());
    }

    public void removeTask(int taskNumber) {
        Task toDelete = tasks.get(taskNumber);
        tasks.remove(taskNumber);
        Echo.printTaskRemovedConfirmation(toDelete, tasks.size());
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param itemIndex Zero-based index of the task to mark.
     */
    public void markTask(int itemIndex) {
        tasks.get(itemIndex).setDone(true);
        Echo.printMarkedTask(tasks.get(itemIndex));
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param itemIndex Zero-based index of the task to unmark.
     */
    public void unmarkTask(int itemIndex) {
        tasks.get(itemIndex).setDone(false);
        Echo.printUnmarkedTask(tasks.get(itemIndex));
    }

    /**
     * Prints all recorded tasks.
     */
    public void printAllTasks() {
        Echo.printList(tasks, tasks.size());
    }

    public int getNumberOfTasks() {
        return tasks.size();
    }
}
