package lokwx;

/**
 * Handles formatting and printing chatbot output to the console.
 */
public final class Echo {
    public static final String DIVIDER = "__________________________________________________";

    private Echo() {
    }

    /**
     * Prints all tasks in the list.
     *
     * @param tasks Array of tasks.
     * @param numberOfTasks Number of tasks currently recorded.
     */
    public static void printList(Task[] tasks, int numberOfTasks) {
        System.out.println();
        for (int i = 0; i < numberOfTasks; i++) {
            System.out.printf("%d. %s\n", i + 1, tasks[i].displayTask());
        }
        printRobot(Robot.ROBOT_LIST);
    }

    /**
     * Prints a robot illustration followed by the output divider.
     *
     * @param robot Robot illustration to print.
     */
    public static void printRobot(String robot) {
        System.out.println();
        System.out.println(robot);
        System.out.println(DIVIDER);
    }

    /**
     * Prints the chatbot greeting.
     */
    public static void startChatbot() {
        System.out.println("Hello! I'm Lokwx.");
        System.out.println("What can I do for you?");
        printRobot(Robot.ROBOT_HAPPY);
    }

    /**
     * Ends the chatbot session with a farewell message.
     */
    public static void endChatbot() {
        System.out.println();
        System.out.println("Bye. Hope to see you again soon!");
        printRobot(Robot.ROBOT_BYE);
    }

    /**
     * Prints confirmation that a task was added.
     *
     * @param task Task that was added.
     * @param numberOfTasks Number of tasks currently recorded.
     */
    public static void printTaskAddedConfirmation(Task task, int numberOfTasks) {
        System.out.println();
        System.out.print(task.getTaskAddedMessage());
        System.out.printf("Now you have %d %s in this list.\n",
                numberOfTasks, numberOfTasks > 1 ? "tasks" : "task");
        printRobot(Robot.ROBOT_EXCITED);
    }

    /**
     * Prints confirmation that a task was marked as completed.
     *
     * @param task The task that was marked done.
     */
    public static void printMarkedTask(Task task) {
        System.out.println();
        System.out.println("Nice! I've marked this task as done:");
        System.out.printf("%s %s\n", task.displayCheckbox(), task.getDescription());
        printRobot(Robot.ROBOT_DONE);
    }

    /**
     * Prints confirmation that a task was marked as incomplete.
     *
     * @param task The task that was marked not done.
     */
    public static void printUnmarkedTask(Task task) {
        System.out.println();
        System.out.println("Okay, I've marked this task as not done yet:");
        System.out.printf("%s %s\n", task.displayCheckbox(), task.getDescription());
        printRobot(Robot.ROBOT_SAD);
    }
}
