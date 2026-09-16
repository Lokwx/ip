package lokwx.command;

import lokwx.exception.LokwxException;
import lokwx.task.*;
import lokwx.ui.Echo;

/**
 * Parses user input and routes to the appropriate task and echo actions.
 */
public final class InputCommandHandler {

    private static final String DELIMITER_BY = "/by";
    private static final String DELIMITER_FROM = "/from";
    private static final String DELIMITER_TO = "/to";
    private static final int INDEX_NOT_FOUND = -1;
    private static final int MINIMUM_INDEX = 0;
    private static final int MIN_ARGUMENT_COUNT = 2;
    public static final int ZERO_BASED = 1;

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
            throws LokwxException, IllegalArgumentException, IndexOutOfBoundsException {
        if (input.trim().isEmpty()) {
            throw new LokwxException("Input cannot be empty.");
        }

        String[] inputCommands = input.trim().split(" ");

        switch (inputCommands[0].trim().toLowerCase()) {
            case "bye" -> Echo.endChatbot();
            case "list" -> {
                if (taskHandler.getNumberOfTasks() == 0) {
                    throw new LokwxException("Oops! list is empty, please add a task!");
                }
                taskHandler.printAllTasks();
            }
            case "mark" -> {
                if (inputCommands.length < MIN_ARGUMENT_COUNT || inputCommands[1].isEmpty()) {
                    throw new IllegalArgumentException("Oops! Please specify a task number to mark.");
                }

                int itemIndex;
                try {
                    itemIndex = Integer.parseInt(inputCommands[1]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Oops! Task number must be a valid integer.");
                }

                // Handle wrong index
                if (itemIndex <= MINIMUM_INDEX || itemIndex > taskHandler.getNumberOfTasks()) {
                    throw new IndexOutOfBoundsException("Oops! The number you typed in is not on the list!");
                }

                taskHandler.markTask(itemIndex - 1);
            }
            case "unmark" -> {
                if (inputCommands.length < MIN_ARGUMENT_COUNT || inputCommands[1].isEmpty()) {
                    throw new IllegalArgumentException("Oops! Please specify a task number to unmark.");
                }

                int itemIndex;
                try {
                    itemIndex = Integer.parseInt(inputCommands[1]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Oops! Task number must be a valid integer.");
                }

                // Handle wrong index
                if (itemIndex <= MINIMUM_INDEX || itemIndex > taskHandler.getNumberOfTasks()) {
                    throw new IndexOutOfBoundsException("Oops! The number you typed in is not on the list!");
                }

                taskHandler.unmarkTask(itemIndex - 1);
            }
            case "todo" -> {
                String description = input.trim().substring("todo".length()).trim();

                // Handle empty todo
                if (description.isEmpty()) {
                    throw new IllegalArgumentException("Oops! The description of a todo cannot be empty.");
                }

                Todo todo = new Todo(description, Task.TaskType.TODO);
                taskHandler.addTask(todo);
            }
            case "deadline" -> {
                String trimmedInput = input.trim();
                int byIndex = trimmedInput.indexOf(DELIMITER_BY);

                if (byIndex == INDEX_NOT_FOUND) {
                    throw new IndexOutOfBoundsException("Try: deadline <description> " + DELIMITER_BY + " <time>!");
                }

                String description = trimmedInput.substring("deadline".length(), byIndex).trim();

                // Handle empty deadline
                if (description.isEmpty()) {
                    throw new IllegalArgumentException("Oops! The description of a deadline cannot be empty!");
                }

                String deadlineBy = trimmedInput.substring(byIndex + DELIMITER_BY.length()).trim();

                // Handle empty deadline date
                if (deadlineBy.isEmpty()) {
                    throw new IllegalArgumentException("Oops! You need to set a deadline!");
                }

                Deadline deadline = new Deadline(description, deadlineBy, Task.TaskType.DEADLINE);
                taskHandler.addTask(deadline);
            }
            case "event" -> {
                String trimmedInput = input.trim();
                int fromIndex = trimmedInput.indexOf(DELIMITER_FROM);
                int toIndex = trimmedInput.indexOf(DELIMITER_TO);

                if (fromIndex == INDEX_NOT_FOUND || toIndex == INDEX_NOT_FOUND || fromIndex > toIndex) {
                    throw new IndexOutOfBoundsException("Try: event <description> "
                            + DELIMITER_FROM + " <time> " + DELIMITER_TO + " <time>");
                }

                String description = trimmedInput.substring("event".length(), fromIndex).trim();

                // Handle empty description
                if (description.isEmpty()) {
                    throw new IllegalArgumentException("Oops! The description of an event cannot be empty!");
                }

                String eventFrom = trimmedInput.substring(fromIndex + DELIMITER_FROM.length(), toIndex).trim();

                if (eventFrom.isEmpty()) {
                    throw new IllegalArgumentException("Oops! You need to set a start time!");
                }

                String eventTo = trimmedInput.substring(toIndex + DELIMITER_TO.length()).trim();

                if (eventTo.isEmpty()) {
                    throw new IllegalArgumentException("Oops! You need to set an end time!");
                }

                Event event = new Event(description, eventFrom, eventTo, Task.TaskType.EVENT);
                taskHandler.addTask(event);
            }
            case "delete" -> {
                if (taskHandler.getNumberOfTasks() == 0) {
                    throw new LokwxException("Oops! please add a task before deleting!");
                }

                int indexToDelete;

                try {
                    indexToDelete = Integer.parseInt(inputCommands[1]);
                } catch(IndexOutOfBoundsException e) {
                    throw new IndexOutOfBoundsException(String.format("Oops! you need to enter a valid number from [1-%d]",taskHandler.getNumberOfTasks()));
                }

                if (indexToDelete < 0 || indexToDelete > taskHandler.getNumberOfTasks()) {
                    throw new LokwxException(String.format("Oops! you need to enter a valid number from [1-%d]",taskHandler.getNumberOfTasks()));
                }

                 taskHandler.removeTask(indexToDelete- ZERO_BASED);
            }
            default -> throw new LokwxException("Oops! I'm sorry, but I don't understand what you mean.");
        }
    }
}
