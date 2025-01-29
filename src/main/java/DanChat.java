import java.util.Scanner;

public class DanChat {
    public static void printMessage(String message) {
        System.out.println("=============================");
        System.out.println("\t" + message);
        System.out.println("=============================");
    }
    public static void printList(String[] items) {
        int itemIndex = 1; //Output index for list items, starting from 1.
        for (String item: items) {
            if (item == null) {
                break;
            }
            System.out.println("\t" + itemIndex + ". " + item);
            itemIndex++;
        }
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
        String[] textList = new String[100];
        int textCount = 0;
        Scanner in = new Scanner(System.in);
        text = in.nextLine();
        while (!text.equals("bye")) {
            if (text.equals("list")) {
                System.out.println(line);
                printList(textList);
                System.out.println(line);
            }
            else {
                textList[textCount] = text;
                textCount++;
                printMessage("added: " + text);
            }
            Scanner nextIn = new Scanner(System.in);
            text = nextIn.nextLine();
        }
        printMessage("Bye. Hope to see you again soon!");
    }
}