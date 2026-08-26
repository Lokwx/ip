import java.util.Scanner;

public class Echo {

    static final String divider = "_____________________________________";
    static final String chatbotTag = "[Lokwx]";
    
    public static void echoCommands(Scanner in) {
        String line;

        do {
            line = in.nextLine();
            System.out.println(divider);

            //Displays the closing message if bye is inputted
            if (line.equals("bye")) {
                System.out.println(chatbotTag + " " + "Bye. Hope to see you again soon!");
            }
            else {
                System.out.println(chatbotTag + " " + line);
            }

            System.out.println(divider);
        } while (!line.equals("bye"));
    }
}
