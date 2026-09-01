package lokwx;

/**
 * Handles formatting and printing chatbot output to the console.
 */
public class Echo {
    public static final String DIVIDER = "_____________________________________";
    public static final String CHATBOT_TAG = "[Lokwx]";
    public static final String CLOSING_MESSAGE = CHATBOT_TAG + " Bye. Hope to see you again soon!";

    /**
     * Prints all tasks in the list.
     *
     * @param tasks Array of tasks.
     * @param numberOfTasks Number of tasks currently recorded.
     */
    public static void printList(Task[] tasks, int numberOfTasks) {
        for (int i = 0; i < numberOfTasks; i++) {
            System.out.println((i + 1) + ". " + tasks[i].displayCheckbox() + " " + tasks[i].getDescription());
        }
        System.out.println(DIVIDER);
    }

    /**
     * Ends the chatbot session with a farewell message.
     */
    public static void endChatbot() {
        System.out.println(CLOSING_MESSAGE);
        System.out.println(DIVIDER);
    }

    /**
     * Prints confirmation that a task was added.
     *
     * @param description Description of the added task.
     */
    public static void printAddedTask(Task task) {

    }

    /**
     * Prints confirmation that a task was marked as completed.
     *
     * @param task The task that was marked done.
     */
    public static void printMarkedTask(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.displayCheckbox() + " " + task.getDescription());
        System.out.println(DIVIDER);
    }

    /**
     * Prints confirmation that a task was marked as incomplete.
     *
     * @param task The task that was marked not done.
     */
    public static void printUnmarkedTask(Task task) {
        System.out.println("Okay, I've marked this task as not done yet:");
        System.out.println(task.displayCheckbox() + " " + task.getDescription());
        System.out.println(DIVIDER);
    }
}
