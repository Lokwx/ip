import java.util.Scanner;

public class Echo {

    static final String divider = "_____________________________________";
    static final String chatbotTag = "[Lokwx]";

    private static String[] inputList = new String[100];
    private static int inputListIndex = 0;
    
    
    public static void echoCommands(Scanner in) {
        String line;

        do {
            line = in.nextLine();

            if (!line.equalsIgnoreCase("bye") && !line.equalsIgnoreCase("list")) {
                inputList[inputListIndex++] = line;
            }

            System.out.println(divider);

            //Displays the closing message if bye is inputted
            if (line.equalsIgnoreCase("bye")) {
                System.out.println(chatbotTag + " " + "Bye. Hope to see you again soon!");
            }
            else if (line.equalsIgnoreCase("list")) {
             
                //Display all the contents inside the list
                for (int i = 0; i < inputListIndex; ++i) {
                    System.out.println(i+1 + ". " + inputList[i]);
                }
            }
            else {
                System.out.println(chatbotTag + " " + "added: " + line);
            }

            System.out.println(divider);
        } while (!line.equals("bye"));
    }
}
