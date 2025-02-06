import java.util.Scanner;

public class DanChat {

    private static final String LINE = "=============================";
    private static final int MAX_TASK_CAPACITY = 100;

    private static final String COMMAND_BYE_WORD = "bye";
    private static final String COMMAND_BYE_MESSAGE = "Bye. Hope to see you again soon!";

    private static final String COMMAND_LIST_WORD = "list";

    private static final String ERROR_NOT_FOUND_TASK_MESSAGE = "Sorry, there is no task with index ";
    private static final String COMMAND_UNMARK_WORD = "unmark";
    private static final String COMMAND_UNMARK_MESSAGE = "Ok, I have marked this task as not done yet";

    private static final String COMMAND_MARK_WORD = "mark";
    private static final String COMMAND_MARK_MESSAGE = "Nice! I have marked this task as done";

    private static final String COMMAND_TODO_WORD = "todo";
    private static final String COMMAND_TODO_MESSAGE = "Add new todo in your list: ";

    private static final String DEADLINE_SEPERATOR = " /by ";
    private static final String ERROR_MISSING_DEADLINE_DATE = "Please provide date after /by";
    private static final String COMMAND_DEADLINE_WORD = "deadline";
    private static final String COMMAND_DEADLINE_MESSAGE = "Add new deadline in your list: ";

    private static final String EVENT_BEGIN_DATE_SEPERATOR = " /from ";
    private static final String EVENT_END_DATE_SEPERATOR = " /to ";
    private static final String ERROR_MISSING_BEGIN_DATE = "Please provide starting date after /from";
    private static final String ERROR_MISSING_END_DATE = "Please provide ending date after /to";
    private static final String COMMAND_EVENT_WORD = "event";
    private static final String COMMAND_EVENT_MESSAGE = "Add new event in your list: ";

    private static final String COMMAND_ADD_MESSAGE = "added: ";

    private static Task[] tasks;
    private static int taskCount;

//    private static String command;
//    private static String detail;
    

    public static void main(String[] args) {
        showWelcomeMessage();
        initTaskList();



        while (true) {
            String text;
            Scanner SCANNER = new Scanner(System.in);
            text = SCANNER.nextLine();

            String[] words = text.split(" ", 2);
            String command = words[0];
            String detail;
            if (words.length == 2) {
                detail = words[1];
            } else {
                detail = null;
            }

            if (isValidByeCommand(text)) {
                executeByeCommand();
            }
            else if (isValidListCommand(text)) {
                executeListCommand(tasks);
            }
            else if (isValidUnmarkCommand(command, detail)) {
                executeUnmarkCommand(detail);
            }
            else if (isValidMarkCommand(command, detail)) {
                executeMarkCommand(detail);
            }
            else if (isValidTodoCommand(command)) {
                executeTodoCommand(detail);
            }
            else if (isValidDeadlineCommand(command, detail)) {
                executeDeadlineCommand(detail);
            }
            else if (isValidEventCommand(command, detail)) {
                executeEventCommand(detail);
            }
            else {
                executeAddTask(text);
            }
        }
    }

    private static void executeEventCommand(String detail) {
        String[] detailParts = detail.split(EVENT_BEGIN_DATE_SEPERATOR, 2);
        if (detailParts.length < 2) {
            printMessage(ERROR_MISSING_BEGIN_DATE);
            return;
        }
        String description = detailParts[0];

        String duration = detailParts[1];
        String[] durationParts = duration.split(EVENT_END_DATE_SEPERATOR, 2);
        if (durationParts.length < 2) {
            printMessage(ERROR_MISSING_END_DATE);
            return;
        }
        String from = durationParts[0];
        String to = durationParts[1];

        Event event = new Event(description, from, to);
        tasks[taskCount] = event;
        taskCount++;
        printMessage(COMMAND_EVENT_MESSAGE + event);
    }

    private static void executeDeadlineCommand(String detail) {
        String[] detailParts = detail.split(DEADLINE_SEPERATOR, 2);
        if (detailParts.length < 2) {
            printMessage(ERROR_MISSING_DEADLINE_DATE);
            return;
        }

        String description = detailParts[0];
        String by = detailParts[1];
        Deadline deadline = new Deadline(description, by);
        tasks[taskCount] = deadline;
        taskCount++;
        printMessage(COMMAND_DEADLINE_MESSAGE + deadline);
    }

    private static void executeAddTask(String text) {
        Task task = new Task(text);
        tasks[taskCount] = task;
        taskCount++;
        printMessage(COMMAND_ADD_MESSAGE + text);
    }

    private static void executeTodoCommand(String detail) {
        Todo todo = new Todo(detail);
        tasks[taskCount] = todo;
        taskCount++;
        printMessage(COMMAND_TODO_MESSAGE + todo);
    }

    private static void executeByeCommand() {
        printMessage(COMMAND_BYE_MESSAGE);
        System.exit(0);
    }

    private static void executeMarkCommand(String detail) {
        int taskNumber = Integer.parseInt(detail);
        if (taskNumber > taskCount) {
            printMessage(ERROR_NOT_FOUND_TASK_MESSAGE + taskNumber);
        } else {
            Task changeTask = tasks[taskNumber - 1];
            changeTask.setDone(true);
            printMessage(COMMAND_MARK_MESSAGE + System.lineSeparator() +  "\t"
                    + "[" + changeTask.getStatusIcon() + "] " + changeTask.getDescription());
        }
    }

    private static void executeUnmarkCommand(String detail) {
        int taskNumber = Integer.parseInt(detail);
        if (taskNumber > taskCount) {
            printMessage(ERROR_NOT_FOUND_TASK_MESSAGE + taskNumber);
        } else {
            Task changeTask = tasks[taskNumber - 1];
            changeTask.setDone(false);
            printMessage(COMMAND_UNMARK_MESSAGE + System.lineSeparator() + "\t"
                    + "[" + changeTask.getStatusIcon() + "] " + changeTask.getDescription());
        }
    }

    private static boolean isValidEventCommand(String command, String detail) {
        return command.equals(COMMAND_EVENT_WORD) && detail != null;
    }

    private static boolean isValidDeadlineCommand(String command, String detail) {
        return command.equals(COMMAND_DEADLINE_WORD) && detail != null;
    }

    private static boolean isValidTodoCommand(String command) {
        return command.equals(COMMAND_TODO_WORD);
    }

    private static boolean isValidListCommand(String text) {
        return text.trim().equals(COMMAND_LIST_WORD);
    }

    private static boolean isValidByeCommand(String text) {
        return text.trim().equals(COMMAND_BYE_WORD);
    }

    private static boolean isValidMarkCommand(String command, String detail) {
        return command.equals(COMMAND_MARK_WORD) && isValidInteger(detail);
    }

    private static boolean isValidUnmarkCommand(String command, String detail) {
        return command.equals(COMMAND_UNMARK_WORD) && isValidInteger(detail);
    }

    private static void initTaskList() {
        tasks = new Task[MAX_TASK_CAPACITY];
        taskCount = 0;
    }

    private static void showWelcomeMessage() {
        System.out.println(LINE);
        System.out.println(" /\\_/\\  ");
        System.out.println("( o.o ) " + " " + "Hello! I'm DanChat");
        System.out.println(" > ^ <  " + " " + "What can I do for you?");
        System.out.println(LINE);
    }

    public static void printMessage(String message) {
        System.out.println(LINE);
        System.out.println("\t" + message);
        System.out.println(LINE);
    }

    public static void executeListCommand(Task[] tasks) {
        System.out.println(LINE);
        printTaskList(tasks);
        System.out.println(LINE);
    }

    private static void printTaskList(Task[] tasks) {
        int taskNumber = 1; //Output index for list items, starting from 1.
        for (Task task: tasks) {
            if (task == null) {
                break;
            }
            System.out.println("\t" + taskNumber + ". " + task);
            taskNumber++;
        }
    }

    private static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}