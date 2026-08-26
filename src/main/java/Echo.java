import java.util.Scanner;

public class Echo {

    static final String divider = "_____________________________________";
    static final String chatbotTag = "[Lokwx]";
    static final String closingMessage = chatbotTag + " " + "Bye. Hope to see you again soon!";

    private static Task[] tasks = new Task[100];
    private static int numberOfTasks = 0;

    public static void addTask(String description) {
        // Create an array of Task objects
        if (!description.equalsIgnoreCase("bye") && !description.equalsIgnoreCase("list")) {
            tasks[numberOfTasks++] = new Task(description);

            System.out.println(chatbotTag + " " + "added: " + description);
            System.out.println(divider);
        }

    }

    public static void printList() {
        // Display all the contents inside the list
        for (int i = 0; i < numberOfTasks; ++i) {
            System.out.println(i + 1 + ". " + tasks[i].getDescription());
        }
        System.out.println(divider);
    }

    public static void endChatbot() {
        System.out.println(closingMessage);
        System.out.println(divider);
    }

    public static void handleInputCommands(String input) {
        String inputCommand = input.trim().toLowerCase();

        switch (inputCommand) {
            case "bye":
                endChatbot();
                break;
            case "list":
                printList();
                break;
        }
    }

    public static void echoCommands(Scanner in) {
        String line;

        do {
            // Read input
            line = in.nextLine();

            // Add task based on input description
            addTask(line);
            handleInputCommands(line);

        } while (!line.equals("bye"));
    }
}
