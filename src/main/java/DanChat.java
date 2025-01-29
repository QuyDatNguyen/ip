import java.util.Scanner;

public class DanChat {
    public static void printMessage(String message) {
        String line = "=============================";
        System.out.println(line + "\n" + "\t" + message + "\n" + line);
    }
    public static void main(String[] args) {
        String line = "=============================";
//        System.out.println(line);
//        System.out.println("Hello! I'm DanChat\n"+
//                "What can I do for you?");
//        System.out.println(line);
        System.out.println(line);
        System.out.println(" /\\_/\\  ");
        System.out.println("( o.o ) " + " " + "Hello! I'm DanChat");
        System.out.println(" > ^ <  " + " " + "What can I do for you?");
        System.out.println(line);
        String text;
        Scanner in = new Scanner(System.in);
        text = in.nextLine();
        while (!text.equals("bye")) {
            printMessage("added: " + text);
            Scanner nextIn = new Scanner(System.in);
            text = nextIn.nextLine();
        }

        printMessage("Bye. Hope to see you again soon!");
    }
}