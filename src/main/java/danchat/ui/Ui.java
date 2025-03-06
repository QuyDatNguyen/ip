package danchat.ui;

import java.util.Scanner;

public class Ui {
    private static final String LINE = "=============================";
    private static final String LINE_DIVIDER = "___________________________";
    private static final Scanner SCANNER = new Scanner(System.in);

    /** Generates and print out the program message at the beginning of application */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println("Hello! I'm DanChat");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    /** Read the text entered by user, ignoring all leading and trailing whitespace */
    public String readCommand() {
        String userInput = SCANNER.nextLine();
        while (userInput.trim().isEmpty()) {
            userInput = SCANNER.nextLine();
        }
        return userInput;
    }

    public void showLine() {
        System.out.println(LINE_DIVIDER);
    }

    /** Show error to user after failing to execute the command*/
    public void showError (String error) {
        System.out.println(error);
    }

    /** Print out the result/ message to use after successfully executing the command */
    public void showCommandMessage (String message) {
        System.out.println(message);
    }
}
