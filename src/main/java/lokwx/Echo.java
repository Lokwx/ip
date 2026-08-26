package lokwx;

import java.util.Scanner;

/**
 * Processes chatbot commands and displays responses to the user.
 */
public class Echo {
    private static final String DIVIDER = "_____________________________________";
    private static final String CHATBOT_TAG = "[Lokwx]";
    private static final String CLOSING_MESSAGE = CHATBOT_TAG + " Bye. Hope to see you again soon!";

    private static final Task[] tasks = new Task[100];
    private static int numberOfTasks = 0;

    /**
     * Adds a task with the specified description and displays a confirmation.
     *
     * @param description Description of the task to add.
     */
    public static void addTask(String description) {
        if (!description.equalsIgnoreCase("bye") && !description.equalsIgnoreCase("list")) {
            tasks[numberOfTasks++] = new Task(description);

            System.out.println(CHATBOT_TAG + " added: " + description);
            System.out.println(DIVIDER);
        }
    }

    /**
     * Prints all tasks in the order they were added.
     */
    public static void printList() {
        for (int i = 0; i < numberOfTasks; i++) {
            System.out.println(i + 1 + ". " + tasks[i].displayCheckbox() + " " + tasks[i].getDescription());
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
     * Marks the task at the specified index as done.
     *
     * @param itemIndex Zero-based index of the task to mark.
     */
    public static void markTask(int itemIndex) {
        tasks[itemIndex].setDone(true);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(tasks[itemIndex].displayCheckbox() + " " + tasks[itemIndex].getDescription());
        System.out.println(DIVIDER);
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param itemIndex Zero-based index of the task to unmark.
     */
    public static void unmarkTask(int itemIndex) {
        tasks[itemIndex].setDone(false);
        System.out.println("Okay, I've marked this task as not done yet:");
        System.out.println(tasks[itemIndex].displayCheckbox() + " " + tasks[itemIndex].getDescription());
        System.out.println(DIVIDER);
    }

    /**
     * Processes one line of user input as a chatbot command.
     *
     * @param input User input to process.
     */
    public static void handleInputCommands(String input) {
        String[] inputCommands = input.split(" ");

        switch (inputCommands[0].trim().toLowerCase()) {
            case "bye" -> endChatbot();
            case "list" -> printList();
            case "mark" -> {
                int itemIndex = Integer.parseInt(inputCommands[1]) - 1;
                markTask(itemIndex);
            }
            case "unmark" -> {
                int itemIndex = Integer.parseInt(inputCommands[1]) - 1;
                unmarkTask(itemIndex);
            }
            default -> addTask(input);
        }
    }

    /**
     * Reads and processes user commands until the user enters bye.
     *
     * @param inputScanner Scanner that provides user input.
     */
    public static void echoCommands(Scanner inputScanner) {
        String line;

        do {
            line = inputScanner.nextLine();
            handleInputCommands(line);
        } while (!line.equals("bye"));
    }
}
