import java.util.Scanner;

public class DanChat {
    public static void printMessage(String message) {
        System.out.println("=============================");
        System.out.println("\t" + message);
        System.out.println("=============================");
    }
    public static void printTaskList(Task[] tasks) {
        int taskNumber = 1; //Output index for list items, starting from 1.
        for (Task task: tasks) {
            if (task == null) {
                break;
            }
            String taskName = task.getDescription();
            String taskStatusIcon = task.getStatusIcon();
            System.out.println("\t" + taskNumber + ". " + "[" + taskStatusIcon + "] " + taskName);
            taskNumber++;
        }
    }
    public static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void main(String[] args) {
        String line = "=============================";
        System.out.println(line);
        System.out.println(" /\\_/\\  ");
        System.out.println("( o.o ) " + " " + "Hello! I'm DanChat");
        System.out.println(" > ^ <  " + " " + "What can I do for you?");
        System.out.println(line);

        String text;
        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner in = new Scanner(System.in);
        text = in.nextLine();

        while (true) {
            String[] words = text.split(" ");
            if (text.trim().equals("bye")) {
                printMessage("Bye. Hope to see you again soon!");
                break;
            }
            else if (text.trim().equals("list")) {
                System.out.println(line);
                printTaskList(tasks);
                System.out.println(line);
            } else if (words[0].equals("unmark") && words.length == 2 && isValidInteger(words[1])) {
//                String[] words = text.split(" ");
                int taskNumber = Integer.parseInt(words[1]);
                if (taskNumber > taskCount) {
                    printMessage("Sorry, there is no task with index " + taskNumber);
                } else {
                    Task changeTask = tasks[taskNumber - 1];
                    changeTask.setDone(false);
                    printMessage("Ok, I have marked this task as not done yet\n \t"
                            + "[" + changeTask.getStatusIcon() + "] " + changeTask.getDescription());
                }
            } else if (words[0].equals("mark") && words.length == 2 && isValidInteger(words[1])) {
//                String[] words = text.split(" ");
                int taskNumber = Integer.parseInt(words[1]);
                if (taskNumber > taskCount) {
                    printMessage("Sorry, there is no task with index " + taskNumber);
                } else {
                    Task changeTask = tasks[taskNumber - 1];
                    changeTask.setDone(true);
                    printMessage("Nice! I have marked this task as done\n \t"
                            + "[" + changeTask.getStatusIcon() + "] " + changeTask.getDescription());
                }
            }
            else {
                Task t = new Task(text);
                tasks[taskCount] = t;
                taskCount++;
                printMessage("added: " + text);
            }
            Scanner nextIn = new Scanner(System.in);
            text = nextIn.nextLine();
        }
    }
}