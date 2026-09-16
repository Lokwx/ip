package lokwx;

import lokwx.command.InputCommandHandler;
import lokwx.data.FileHandler;
import lokwx.exception.LokwxException;
import lokwx.task.TaskHandler;
import lokwx.ui.Echo;
import lokwx.ui.Robot;

import java.io.IOException;
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

        try {
            FileHandler.createFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Echo.printRobot(Robot.ROBOT_SAD);
        }

        try {
            FileHandler.readData(taskHandler);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Echo.printRobot(Robot.ROBOT_SAD);
        }

        do {
            line = inputScanner.nextLine();
            try {
                InputCommandHandler.handleInputCommand(line, taskHandler);
            } catch (IllegalArgumentException | IndexOutOfBoundsException | LokwxException | IOException e) {
                System.out.println(e.getMessage());
                Echo.printRobot(Robot.ROBOT_SAD);
            }
        } while (!line.trim().equalsIgnoreCase("bye"));
    }
}
