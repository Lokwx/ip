package lokwx;

import java.util.Scanner;

/**
 * Starts the Lokwx chatbot and accepts commands from the user.
 */
public class Lokwx {
    /**
     * Starts a Lokwx chatbot session.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        System.out.println("_____________________________________");
        String banner = """
                 _           _                   \s
                | |         | |                  \s
                | |     ___ | | ____      ____  __
                | |    / _ \\| |/ /\\ \\ /\\ / /\\ \\/ /
                | |___| (_) |   <  \\ V  V /  >  <\s
                \\_____/\\___/|_|\\_\\  \\_/\\_/  /_/\\_\\
                """;
        System.out.println(banner);
        System.out.println("Hello! I'm Lokwx.");
        System.out.println("What can I do for you?");
        System.out.println("______________________________________");

        Scanner inputScanner = new Scanner(System.in);
        Echo.echoCommands(inputScanner);
    }
}
