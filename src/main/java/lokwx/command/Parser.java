package lokwx.command;

import lokwx.exception.LokwxException;

import java.util.Locale;

/**
 * Interprets user input to identify the requested command.
 */
public final class Parser {

    /**
     * Identifies the commands supported by Lokwx.
     */
    public enum CommandType {
        BYE,
        LIST,
        FIND,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE
    }

    private Parser() {
    }

    /**
     * Returns the command type represented by the first word of the input.
     *
     * @param input User input to interpret.
     * @return Type of command requested by the user.
     * @throws LokwxException If the input is empty or starts with an unknown command.
     */
    public static CommandType parseCommandType(String input) throws LokwxException {
        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            throw new LokwxException("Input cannot be empty.");
        }

        String commandWord = trimmedInput.split("\\s+", 2)[0].toUpperCase(Locale.ROOT);

        try {
            return CommandType.valueOf(commandWord);
        } catch (IllegalArgumentException e) {
            throw new LokwxException("Oops! I'm sorry, but I don't understand what you mean.");
        }
    }
}
