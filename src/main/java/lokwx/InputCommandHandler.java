package lokwx;

/**
 * Parses user input and routes to the appropriate task and echo actions.
 */
public final class InputCommandHandler {

    private static final String DELIMITER_BY = "/by";
    private static final String DELIMITER_FROM = "/from";
    private static final String DELIMITER_TO = "/to";
    private static final int INDEX_NOT_FOUND = -1;

    private InputCommandHandler() {
    }

    /**
     * Processes one line of user input as a chatbot command.
     *
     * @param input User input to process.
     * @param taskHandler Task handler that holds the current tasks.
     * @throws IllegalArgumentException If command syntax, format, or argument values are invalid.
     * @throws IndexOutOfBoundsException If a referenced task number is outside the valid range.
     */
    public static void handleInputCommand(String input, TaskHandler taskHandler)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }

        String[] inputCommands = input.trim().split(" ");

        switch (inputCommands[0].trim().toLowerCase()) {
        case "bye" -> Echo.endChatbot();
        case "list" -> taskHandler.printAllTasks();
        case "mark" -> {
            if (inputCommands.length < 2) {
                throw new IllegalArgumentException("Oops! Please specify a task number to mark.");
            }

            int itemIndex;
            try {
                itemIndex = Integer.parseInt(inputCommands[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Oops! Task number must be a valid integer.");
            }

            // Handle wrong index
            if (itemIndex <= 0 || itemIndex > taskHandler.getNumberOfTasks()) {
                throw new IndexOutOfBoundsException("Oops! The number you typed in is not on the list!");
            }

            taskHandler.markTask(itemIndex - 1);
        }
        case "unmark" -> {
            if (inputCommands.length < 2) {
                throw new IllegalArgumentException("Oops! Please specify a task number to unmark.");
            }

            int itemIndex;
            try {
                itemIndex = Integer.parseInt(inputCommands[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Oops! Task number must be a valid integer.");
            }

            // Handle wrong index
            if (itemIndex <= 0 || itemIndex > taskHandler.getNumberOfTasks()) {
                throw new IndexOutOfBoundsException("Oops! The number you typed in is not on the list!");
            }

            taskHandler.unmarkTask(itemIndex - 1);
        }
        case "todo" -> {
            String description = input.substring("todo".length()).trim();

            // Handle empty todo
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Oops! The description of a todo cannot be empty.");
            }

            Todo todo = new Todo(description, Task.TaskType.TODO);
            taskHandler.addTask(todo);
        }
        case "deadline" -> {
            int byIndex = input.indexOf(DELIMITER_BY);

            if (byIndex == INDEX_NOT_FOUND) {
                throw new IndexOutOfBoundsException("Try: deadline <description> " + DELIMITER_BY + " <time>!");
            }

            String description = input.substring("deadline".length(), byIndex).trim();

            // Handle empty deadline
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Oops! The description of a deadline cannot be empty!");
            }

            String deadlineBy = input.substring(byIndex + DELIMITER_BY.length()).trim();

            // Handle empty deadline date
            if (deadlineBy.isEmpty()) {
                throw new IllegalArgumentException("Oops! You need to set a deadline!");
            }

            Deadline deadline = new Deadline(description, deadlineBy, Task.TaskType.DEADLINE);
            taskHandler.addTask(deadline);
        }
        case "event" -> {
            int fromIndex = input.indexOf(DELIMITER_FROM);
            int toIndex = input.indexOf(DELIMITER_TO);

            if (fromIndex == INDEX_NOT_FOUND || toIndex == INDEX_NOT_FOUND || fromIndex > toIndex) {
                throw new IndexOutOfBoundsException("Try: event <description> "
                        + DELIMITER_FROM + " <time> " + DELIMITER_TO + " <time>");
            }

            String description = input.substring("event".length(), fromIndex).trim();

            // Handle empty description
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Oops! The description of an event cannot be empty!");
            }

            String eventFrom = input.substring(fromIndex + DELIMITER_FROM.length(), toIndex).trim();

            if (eventFrom.isEmpty()) {
                throw new IllegalArgumentException("Oops! You need to set a start time!");
            }

            String eventTo = input.substring(toIndex + DELIMITER_TO.length()).trim();

            if (eventTo.isEmpty()) {
                throw new IllegalArgumentException("Oops! You need to set an end time!");
            }

            Event event = new Event(description, eventFrom, eventTo, Task.TaskType.EVENT);
            taskHandler.addTask(event);
        }
        default -> throw new IllegalArgumentException("Oops! I'm sorry, but I don't understand what you mean.");
        }
    }
}
