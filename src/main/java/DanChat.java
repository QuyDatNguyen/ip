import java.util.Scanner;

public class DanChat {
    public static void printMessage(String message) {
        String line = "=============================";
        System.out.println(line + "\n" + "\t" + message + "\n" + line);
    }
    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo);
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
        String command;
        Scanner in = new Scanner(System.in);
        command = in.nextLine();
        while (!command.equals("bye")) {
//            System.out.println(line + "\n" + "\t" + command + "\n" + line);
            printMessage(command);
            Scanner nextIn = new Scanner(System.in);
            command = nextIn.nextLine();
        }
//        System.out.println(line + "\n" + "\t" + "Bye. Hope to see you again soon!" + "\n" + line);
        printMessage("Bye. Hope to see you again soon!");
    }
}