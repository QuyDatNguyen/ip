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
            System.out.println("\t" + taskNumber + ". " + task);
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

            String[] words = text.split(" ", 2);
            String command = words[0];
//            String description;
            String detail;
            if (words.length == 2) {
                detail = words[1];
            } else {
                detail = null;
            }

            if (text.trim().equals("bye")) {
                printMessage("Bye. Hope to see you again soon!");
                break;
            }
            else if (text.trim().equals("list")) {
                System.out.println(line);
                printTaskList(tasks);
                System.out.println(line);
            }
            else if (command.equals("unmark") && isValidInteger(detail)) {
//                String[] words = text.split(" ");
                int taskNumber = Integer.parseInt(words[1]);
                if (taskNumber > taskCount) {
                    printMessage("Sorry, there is no task with index " + taskNumber);
                } else {
                    Task changeTask = tasks[taskNumber - 1];
                    changeTask.setDone(false);
                    printMessage("Ok, I have marked this task as not done yet" + System.lineSeparator() + "\t"
                            + "[" + changeTask.getStatusIcon() + "] " + changeTask.getDescription());
                }
            }
            else if (command.equals("mark") && isValidInteger(detail)) {
//                String[] words = text.split(" ");
                int taskNumber = Integer.parseInt(words[1]);
                if (taskNumber > taskCount) {
                    printMessage("Sorry, there is no task with index " + taskNumber);
                } else {
                    Task changeTask = tasks[taskNumber - 1];
                    changeTask.setDone(true);
                    printMessage("Nice! I have marked this task as done" + System.lineSeparator() +  "\t"
                            + "[" + changeTask.getStatusIcon() + "] " + changeTask.getDescription());
                }
            }
            else if (command.equals("todo")) {
                Todo todo = new Todo(detail);
                tasks[taskCount] = todo;
                taskCount++;
                printMessage("Add new todo in your list: " + todo);
            }
            else if (command.equals("deadline") && detail != null) {
                String[] detailParts = detail.split(" /by ", 2);
                if (detailParts.length < 2) {
                    printMessage("Please provide date after /by");
                    text = in.nextLine();
                    continue;
                }
                String description = detailParts[0];
                String by = detailParts[1];
                Deadline deadline = new Deadline(description, by);
                tasks[taskCount] = deadline;
                taskCount++;
                printMessage("Add new deadline in your list: " + deadline);
            }
            else if (command.equals("event") && detail != null) {
                String[] detailParts = detail.split(" /from ", 2);
                if (detailParts.length < 2) {
                    printMessage("Please provide starting date after /from");
                    text = in.nextLine();
                    continue;
                }
                String description = detailParts[0];

                String duration = detailParts[1];
                String[] durationParts = duration.split(" /to ", 2);
                if (durationParts.length < 2) {
                    printMessage("Please provide ending date after /to");
                    text = in.nextLine();
                    continue;
                }
                String from = durationParts[0];
                String to = durationParts[1];

                Event event = new Event(description, from, to);
                tasks[taskCount] = event;
                taskCount++;
                printMessage("Add new event in your list: " + event);
            }
            else {
                Task task = new Task(text);
                tasks[taskCount] = task;
                taskCount++;
                printMessage("added: " + text);
            }
            text = in.nextLine();
        }
    }
}