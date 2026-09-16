package lokwx.task;

import lokwx.data.FileHandler;
import lokwx.ui.Echo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the collection of tasks and performs operations on them.
 */
public class TaskHandler {
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Adds a task, saves the updated list, and displays a confirmation.
     *
     * @param task Task to add.
     * @throws IOException If the updated task list cannot be saved.
     */
    public void addTask(Task task) throws IOException {
        tasks.add(task);
        saveAllTasks();
        Echo.printTaskAddedConfirmation(task, tasks.size());
    }

    /**
     * Adds a task loaded from disk without saving it or displaying a confirmation.
     *
     * @param task Task loaded from the data file.
     */
    public void addLoadedTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task, saves the updated list, and displays a confirmation.
     *
     * @param taskNumber Zero-based index of the task to remove.
     * @throws IOException If the updated task list cannot be saved.
     */
    public void removeTask(int taskNumber) throws IOException {
        Task taskToDelete = tasks.remove(taskNumber);
        saveAllTasks();
        Echo.printTaskRemovedConfirmation(taskToDelete, tasks.size());
    }

    /**
     * Marks the task at the specified index as done and saves the updated list.
     *
     * @param itemIndex Zero-based index of the task to mark.
     * @throws IOException If the updated task list cannot be saved.
     */
    public void markTask(int itemIndex) throws IOException {
        tasks.get(itemIndex).setDone(true);
        saveAllTasks();
        Echo.printMarkedTask(tasks.get(itemIndex));
    }

    /**
     * Marks the task at the specified index as not done and saves the updated list.
     *
     * @param itemIndex Zero-based index of the task to unmark.
     * @throws IOException If the updated task list cannot be saved.
     */
    public void unmarkTask(int itemIndex) throws IOException {
        tasks.get(itemIndex).setDone(false);
        saveAllTasks();
        Echo.printUnmarkedTask(tasks.get(itemIndex));
    }

    /**
     * Prints all recorded tasks.
     */
    public void printAllTasks() {
        Echo.printList(new ArrayList<>(tasks), tasks.size());
    }

    /**
     * Saves all current tasks to the data file.
     *
     * @throws IOException If the task list cannot be saved.
     */
    public void saveAllTasks() throws IOException {
        FileHandler.saveData(tasks);
    }

    /**
     * Returns the number of recorded tasks.
     *
     * @return Number of recorded tasks.
     */
    public int getNumberOfTasks() {
        return tasks.size();
    }
}
