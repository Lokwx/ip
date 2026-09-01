package lokwx;

/**
 * Parses user input and routes to the appropriate task and echo actions.
 */
public final class InputCommandHandler {

    private static final String DELIMITER_BY = "/by";
    private static final String DELIMITER_FROM = "/from";
    private static final String DELIMITER_TO = "/to";

    private InputCommandHandler() {
    }

    /**
     * Processes one line of user input as a chatbot command.
     *
     * @param input User input to process.
     * @param taskHandler Task handler that holds the current tasks.
     */
    public static void handleInputCommand(String input, TaskHandler taskHandler) {
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
            case "todo" -> {
                String description = input.substring("todo".length()).trim();
                Todo todo = new Todo(description, Task.TaskType.TODO);
                taskHandler.addTask(todo);
            }
            case "deadline" -> {
                int byIndex = input.indexOf(DELIMITER_BY);

                String description = input.substring("deadline".length(), byIndex).trim();
                String deadlineBy = input.substring(byIndex + DELIMITER_BY.length()).trim();

                Deadline deadline = new Deadline(description, deadlineBy, Task.TaskType.DEADLINE);
                taskHandler.addTask(deadline);
            }
            case "event" -> {
                int fromIndex = input.indexOf(DELIMITER_FROM);
                int toIndex = input.indexOf(DELIMITER_TO);

                String description = input.substring("event".length(), fromIndex).trim();
                String eventFrom = input.substring(fromIndex + DELIMITER_FROM.length(), toIndex).trim();
                String eventTo = input.substring(toIndex + DELIMITER_TO.length()).trim();

                Event event = new Event(description, eventFrom, eventTo, Task.TaskType.EVENT);
                taskHandler.addTask(event);
            }
            default -> {
                Task task = new Task(input, Task.TaskType.TASK);
                taskHandler.addTask(task);
            }
        }
    }
}
