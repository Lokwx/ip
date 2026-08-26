/**
 * Entry point for the Lokwx chatbot.
 */

import java.util.Scanner;

public class Lokwx {
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

        //Create the new scanner to read inputs
        Scanner in = new Scanner(System.in);
        
        //Level-1 implementation
        Echo.echoCommands(in);
    }
}
