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
            System.out.println(i + 1 + ". " + tasks[i].displayCheckbox() + " " + tasks[i].getDescription());
        }
        System.out.println(divider);
    }

    public static void endChatbot() {
        System.out.println(closingMessage);
        System.out.println(divider);
    }

    public static void markTask(int itemIndex) {
        tasks[itemIndex].setIsDone(true);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(tasks[itemIndex].displayCheckbox() + " " + tasks[itemIndex].getDescription());
        System.out.println(divider);
    }

    public static void unmarkTask(int itemIndex) {
        tasks[itemIndex].setIsDone(false);
        System.out.println("Okay, I've marked this task as not done yet:");
        System.out.println(tasks[itemIndex].displayCheckbox() + " " + tasks[itemIndex].getDescription());
        System.out.println(divider);
    }

    public static void handleInputCommands(String input) {
        String[] inputCommands = input.split(" ");
        int itemIndex;

        switch (inputCommands[0].trim().toLowerCase()) {
            case "bye":
                endChatbot();
                break;
            case "list":
                printList();
                break;
            case "mark":
                itemIndex = Integer.parseInt(inputCommands[1]) - 1;
                markTask(itemIndex);
                break;
            case "unmark":
                itemIndex = Integer.parseInt(inputCommands[1]) - 1;
                unmarkTask(itemIndex);
                break;
            default:
                addTask(input);
        }
    }

    public static void echoCommands(Scanner in) {
        String line;

        do {
            // Read input
            line = in.nextLine();

            handleInputCommands(line);

        } while (!line.equals("bye"));
    }
}
