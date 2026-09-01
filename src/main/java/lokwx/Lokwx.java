package lokwx;

import java.util.Scanner;

/**
 * Starts the Lokwx chatbot and accepts commands from the user.
 */
public final class Lokwx {
    private Lokwx() {
    }

    /**
     * Starts a Lokwx chatbot session.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        System.out.println(Echo.DIVIDER);
        String banner = """
               \n
               .^_^_^_^.    _           _                  \s
               | [^_^] |   | |         | |                  \s
             o---| |*| |--c| |     ___ | | ____      ____  __
               |  ===  |   | |    / _ \\| |/ /\\ \\ /\\ / /\\ \\/ /
               | |   | |   | |___| (_) |   <  \\ V  V /  >  <\s
              /_|     |_\\  \\_____/\\___/|_|\\_\\  \\_/\\_/  /_/\\_\\
            """;
        System.out.println(banner);
        System.out.println(Echo.DIVIDER);
        Echo.startChatbot();

        TaskHandler taskHandler = new TaskHandler();
        Scanner inputScanner = new Scanner(System.in);
        String line;

        do {
            line = inputScanner.nextLine();
            InputCommandHandler.handleInputCommand(line, taskHandler);
        } while (!line.trim().equalsIgnoreCase("bye"));
    }
}
