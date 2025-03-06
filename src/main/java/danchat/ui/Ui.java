package danchat.ui;

import java.util.Scanner;

public class Ui {
    private static final String LINE = "=============================";
    private static final String LINE_DIVIDER = "___________________________";
    private static final Scanner SCANNER = new Scanner(System.in);
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println("Hello! I'm DanChat");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }
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
    public void showError (String error) {
        System.out.println(error);
    }
    public void showCommandMessage (String message) {
        System.out.println(message);
    }
}
