package lokwx;

/**
 * Parses user input and routes to the appropriate task and echo actions.
 */
public class InputCommandHandler {

    public static final String BY_DELIMITER = "/by";
    public static final String FROM_DELIMITER = "/from";
    public static final String TO_DELIMITER = "/to";

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
            case "todo" -> {
                String description = input.substring("todo".length()).trim();
                Todo todo = new Todo(description, Task.TaskType.TODO);
                taskHandler.addTask(todo);
            }
            case "deadline" -> {
                int byIndex = input.indexOf(BY_DELIMITER);

                String description = input.substring("deadline".length(), byIndex).trim();
                String deadlineBy = input.substring(byIndex + BY_DELIMITER.length()).trim();

                Deadline deadline = new Deadline(description, deadlineBy, Task.TaskType.DEADLINE);
                taskHandler.addTask(deadline);
            }
            case "event" -> {
                int fromIndex = input.indexOf(FROM_DELIMITER);
                int toIndex = input.indexOf(TO_DELIMITER);

                String description = input.substring("event".length(), fromIndex).trim();
                String eventFrom = input.substring(fromIndex + FROM_DELIMITER.length(), toIndex).trim();
                String eventTo = input.substring(toIndex + TO_DELIMITER.length()).trim();

                Event event = new Event(description, eventFrom, eventTo, Task.TaskType.EVENT);
                taskHandler.addTask(event);
            }
            default -> {
                String description = input;
                Task task = new Task(input, Task.TaskType.TASK);
                taskHandler.addTask(task);
            }
        }
    }
}
