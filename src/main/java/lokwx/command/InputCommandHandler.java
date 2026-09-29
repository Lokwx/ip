package lokwx.command;

import lokwx.exception.LokwxException;
import lokwx.task.Deadline;
import lokwx.task.Event;
import lokwx.task.Task;
import lokwx.task.TaskHandler;
import lokwx.task.Todo;
import lokwx.ui.Echo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

/**
 * Parses user input and routes to the appropriate task and echo actions.
 */
public final class InputCommandHandler {

    private static final String DELIMITER_BY = "/by";
    private static final String DELIMITER_FROM = "/from";
    private static final String DELIMITER_TO = "/to";
    private static final int INDEX_NOT_FOUND = -1;
    public static final int INVALID_SIZE = 0;
    private static final int MINIMUM_INDEX = 0;
    private static final int MIN_ARGUMENT_COUNT = 2;
    private static final List<DateTimeFormatter> DEADLINE_DATE_TIME_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("d/M/uuuu HHmm").withResolverStyle(ResolverStyle.STRICT),
            DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm").withResolverStyle(ResolverStyle.STRICT));
    private static final DateTimeFormatter DEADLINE_DATE_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE.withResolverStyle(ResolverStyle.STRICT);
    /**
     * Defines the offset subtracted from a displayed task number to obtain a zero-based list index.
     */
    public static final int ZERO_BASED = 1;

    /**
     * Prevents instantiation of this utility class.
     */
    private InputCommandHandler() {
    }

    /**
     * Processes one line of user input as a chatbot command.
     * Validates command arguments before delegating task updates and console output.
     *
     * @param input User input to process.
     * @param taskHandler Task handler that holds the current tasks.
     * @throws LokwxException If input is empty or unknown, an operation requires a nonempty list,
     *         or a delete task number fails the range check.
     * @throws IllegalArgumentException If command syntax, format, or argument values are invalid.
     * @throws IndexOutOfBoundsException If a referenced task number is outside the valid range,
     *         a delete task number is missing, or required time delimiters are missing or out of order.
     */
    public static void handleInputCommand(String input, TaskHandler taskHandler)
            throws LokwxException, IllegalArgumentException, IndexOutOfBoundsException, IOException {
        if (input.trim().isEmpty()) {
            throw new LokwxException("Input cannot be empty.");
        }

        String[] inputCommands = input.trim().split(" ");

        // Normalize only the command word so task descriptions retain their original capitalization.
        switch (inputCommands[0].trim().toLowerCase()) {
            case "bye" -> {
                Echo.endChatbot();
            }
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

                // Displayed task numbers start at 1, whereas the task list uses zero-based indices.
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

                // Validate the displayed task number before converting it to a list index.
                if (itemIndex <= MINIMUM_INDEX || itemIndex > taskHandler.getNumberOfTasks()) {
                    throw new IndexOutOfBoundsException("Oops! The number you typed in is not on the list!");
                }

                taskHandler.unmarkTask(itemIndex - 1);
            }
            case "todo" -> {
                String description = input.trim().substring("todo".length()).trim();

                if (description.isEmpty()) {
                    throw new IllegalArgumentException("Oops! The description of a todo cannot be empty.");
                }

                Todo todo = new Todo(description, Task.TaskType.TODO, false);
                taskHandler.addTask(todo);

            }
            case "deadline" -> {
                String trimmedInput = input.trim();
                // The first /by separates the description from the deadline text.
                int byIndex = trimmedInput.indexOf(DELIMITER_BY);

                if (byIndex == INDEX_NOT_FOUND) {
                    throw new IndexOutOfBoundsException("Try: deadline <description> " + DELIMITER_BY + " <time>!");
                }

                String description = trimmedInput.substring("deadline".length(), byIndex).trim();

                if (description.isEmpty()) {
                    throw new IllegalArgumentException("Oops! The description of a deadline cannot be empty!");
                }

                String deadlineText = trimmedInput.substring(byIndex + DELIMITER_BY.length()).trim();

                if (deadlineText.isEmpty()) {
                    throw new IllegalArgumentException("Oops! You need to set a deadline!");
                }

                LocalDateTime deadlineBy = parseDeadline(deadlineText);
                Deadline deadline = new Deadline(description, deadlineBy, Task.TaskType.DEADLINE, false);
                taskHandler.addTask(deadline);
            }
            case "event" -> {
                String trimmedInput = input.trim();
                int fromIndex = trimmedInput.indexOf(DELIMITER_FROM);
                int toIndex = trimmedInput.indexOf(DELIMITER_TO);

                // Check delimiter order before extracting the description and the two time fields.
                if (fromIndex == INDEX_NOT_FOUND || toIndex == INDEX_NOT_FOUND || fromIndex > toIndex) {
                    throw new IndexOutOfBoundsException("Try: event <description> "
                            + DELIMITER_FROM + " <time> " + DELIMITER_TO + " <time>");
                }

                String description = trimmedInput.substring("event".length(), fromIndex).trim();

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

                Event event = new Event(description, eventFrom, eventTo, Task.TaskType.EVENT, false);
                taskHandler.addTask(event);
            }
            case "delete" -> {
                if (taskHandler.getNumberOfTasks() == INVALID_SIZE) {
                    throw new LokwxException("Oops! please add a task before deleting!");
                }

                int indexToDelete;

                try {
                    indexToDelete = Integer.parseInt(inputCommands[1]);
                } catch (IndexOutOfBoundsException e) {
                    throw new IndexOutOfBoundsException("Oops! you need to enter a valid number!");
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Oops! you need to enter a valid number!");
                }

                if (indexToDelete <= MINIMUM_INDEX || indexToDelete > taskHandler.getNumberOfTasks()) {
                    throw new LokwxException("Oops! you need to enter a valid number!");
                }

                // Convert the displayed task number to the index expected by TaskHandler.
                taskHandler.removeTask(indexToDelete - ZERO_BASED);
            }
            default -> throw new LokwxException("Oops! I'm sorry, but I don't understand what you mean.");
        }
    }

    /**
     * Parses a deadline in one of the date formats supported by the chatbot.
     * A date without a time is stored as midnight so every deadline has one consistent Java type.
     *
     * @param deadlineText Date and optional time entered after the {@code /by} delimiter.
     * @return Parsed deadline date and time.
     * @throws IllegalArgumentException If the text does not represent a valid supported date.
     */
    private static LocalDateTime parseDeadline(String deadlineText) {
        for (DateTimeFormatter formatter : DEADLINE_DATE_TIME_FORMATTERS) {
            try {
                return LocalDateTime.parse(deadlineText, formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next supported format.
            }
        }

        try {
            return LocalDate.parse(deadlineText, DEADLINE_DATE_FORMATTER).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Oops! Enter the deadline as d/M/yyyy HHmm, yyyy-MM-dd HHmm, or yyyy-MM-dd.");
        }
    }
}
