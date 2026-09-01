package lokwx;

/**
 * Parses user input and routes to the appropriate task and echo actions.
 */
public class InputCommandHandler {

    /**
     * Processes one line of user input as a chatbot command.
     *
     * @param input User input to process.
     * @param taskHandler Task handler that holds the current tasks.
     */
    public static void handleInputCommands(String input, TaskHandler taskHandler) {
        String[] inputCommands = input.split(" ");

        switch (inputCommands[0].trim().toLowerCase()) {
        case "bye" -> Echo.endChatbot();
        case "list" -> taskHandler.printAllTasks();
        case "mark" -> {
            int itemIndex = Integer.parseInt(inputCommands[1]) - 1;
            taskHandler.markTask(itemIndex);
        }
        case "unmark" -> {
            int itemIndex = Integer.parseInt(inputCommands[1]) - 1;
            taskHandler.unmarkTask(itemIndex);
        }
        default -> taskHandler.addTask(input);
        }
    }
}
