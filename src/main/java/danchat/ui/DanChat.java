package danchat.ui;

import danchat.exception.*;
import danchat.task.Deadline;
import danchat.task.Event;
import danchat.task.Task;
import danchat.task.Todo;

import java.util.Scanner;

public class DanChat {

    private static final String LINE = "=============================";
    private static final int MAX_TASK_CAPACITY = 100;

    private static final String COMMAND_BYE_WORD = "bye";
    private static final String COMMAND_BYE_MESSAGE = "Bye. Hope to see you again soon!";

    private static final String COMMAND_LIST_WORD = "list";

    private static final String ERROR_NOT_FOUND_TASK_MESSAGE = "Unfounded task with index ";
    private static final String COMMAND_UNMARK_WORD = "unmark";
    private static final String COMMAND_UNMARK_MESSAGE = "Ok, I have marked this task as not done yet";

    private static final String COMMAND_MARK_WORD = "mark";
    private static final String COMMAND_MARK_MESSAGE = "Nice! I have marked this task as done";

    private static final String COMMAND_TODO_WORD = "todo";
    private static final String COMMAND_TODO_MESSAGE = "Add new todo in your list: ";

    private static final String DEADLINE_SEPERATOR = " /by ";
    private static final String ERROR_MISSING_DEADLINE_DATE = "Missing deadline date. Please provide date after /by";
    private static final String COMMAND_DEADLINE_WORD = "deadline";
    private static final String COMMAND_DEADLINE_MESSAGE = "Add new deadline in your list: ";

    private static final String EVENT_BEGIN_DATE_SEPERATOR = " /from ";
    private static final String EVENT_END_DATE_SEPERATOR = " /to ";
    private static final String ERROR_MISSING_BEGIN_DATE = "Missing starting date. Please provide starting date after /from";
    private static final String ERROR_MISSING_END_DATE = "Missing ending date. Please provide ending date after /to";
    private static final String COMMAND_EVENT_WORD = "event";
    private static final String COMMAND_EVENT_MESSAGE = "Add new event in your list: ";

    private static final String COMMAND_ADD_MESSAGE = "added: ";
    public static final String ERROR_EMPTY_DETAIL = "Details must not be blank";
    public static final String ERROR_INVALID_INDEX_FORMAT = "Wrong index format. Index must be a positive integer";

    private static Task[] tasks;
    private static int taskCount;

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        showWelcomeMessage();
        initTaskList();
        while (true) {
            String userInput = getUserInput();
            processUserInput(userInput);
        }
    }

    private static void processUserInput(String userInput) {
        String[] splitInput = splitCommandAndDetail(userInput);
        String command = splitInput[0];
        String detail = splitInput[1];

        executeUserCommandAndDetail(command, detail);
    }

    private static String[] splitCommandAndDetail(String userInput) {
        String[] commandAndDetail = userInput.split(" ", 2);
        if (commandAndDetail.length == 2) {
            return commandAndDetail;
        }
        return new String[] {commandAndDetail[0], null};
    }

    private static void executeUserCommandAndDetail(String command, String detail) {
        try {
            if (isValidByeCommand(command, detail)) {
                executeByeCommand();
            } else if (isValidListCommand(command, detail)) {
                executeListCommand(tasks);
            } else if (isValidUnmarkCommand(command, detail)) {
                executeUnmarkCommand(detail);
            } else if (isValidMarkCommand(command, detail)) {
                executeMarkCommand(detail);
            } else if (isValidTodoCommand(command, detail)) {
                executeTodoCommand(detail);
            } else if (isValidDeadlineCommand(command, detail)) {
                executeDeadlineCommand(detail);
            } else if (isValidEventCommand(command, detail)) {
                executeEventCommand(detail);
            } else {
                throw new IllegalCommandException("Unknown Command: " + command + " " + detail);
            }
        } catch (DanException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
    }

    private static String getUserInput() {
        String userInput = SCANNER.nextLine();
        while (userInput.trim().isEmpty()) {
            userInput = SCANNER.nextLine();
        }
        return userInput;
    }

    private static void executeEventCommand(String detail) throws MissingDateException {
        String[] splitDetails = detail.split(EVENT_BEGIN_DATE_SEPERATOR, 2);
        if (splitDetails.length < 2) {
            throw new MissingDateException(ERROR_MISSING_BEGIN_DATE);
        }
        String description = splitDetails[0].trim();

        String duration = splitDetails[1].trim();
        String[] splitDurations = duration.split(EVENT_END_DATE_SEPERATOR, 2);
        if (splitDurations.length < 2) {
            throw new MissingDateException(ERROR_MISSING_END_DATE);
        }
        String from = splitDurations[0].trim();
        String to = splitDurations[1].trim();

        Event event = new Event(description, from, to);
        tasks[taskCount] = event;
        taskCount++;
        printMessage(COMMAND_EVENT_MESSAGE + event);
    }

    private static void executeDeadlineCommand(String detail) throws MissingDateException {
        String[] splitDetails = detail.split(DEADLINE_SEPERATOR, 2);
        if (splitDetails.length < 2) {
            throw new MissingDateException(ERROR_MISSING_DEADLINE_DATE);
        }
        String description = splitDetails[0].trim();
        String by = splitDetails[1].trim();

        Deadline deadline = new Deadline(description, by);
        tasks[taskCount] = deadline;
        taskCount++;
        printMessage(COMMAND_DEADLINE_MESSAGE + deadline);
    }

    private static void executeAddTask(String command, String detail) {

        String newTask = command;
        if (detail != null) {
            newTask = newTask + " " + detail;
        }

        Task task = new Task(newTask);
        tasks[taskCount] = task;
        taskCount++;
        printMessage(COMMAND_ADD_MESSAGE + newTask);
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

    private static void executeMarkCommand(String detail) throws IllegalTaskException {
        int taskNumber = Integer.parseInt(detail);
        if (taskNumber > taskCount) {
            throw new IllegalTaskException(ERROR_NOT_FOUND_TASK_MESSAGE + taskNumber);
        } else {
            Task changeTask = tasks[taskNumber - 1];
            changeTask.setDone(true);
            printMessage(COMMAND_MARK_MESSAGE + System.lineSeparator() +  "\t"
                    + "[" + changeTask.getStatusIcon() + "] " + changeTask.getDescription());
        }
    }

    private static void executeUnmarkCommand(String detail) throws IllegalTaskException {
        int taskNumber = Integer.parseInt(detail);
        if (taskNumber > taskCount) {
            throw new IllegalTaskException(ERROR_NOT_FOUND_TASK_MESSAGE + taskNumber);
        } else {
            Task changeTask = tasks[taskNumber - 1];
            changeTask.setDone(false);
            printMessage(COMMAND_UNMARK_MESSAGE + System.lineSeparator() + "\t"
                    + "[" + changeTask.getStatusIcon() + "] " + changeTask.getDescription());
        }
    }

    private static boolean isValidEventCommand(String command, String detail) throws EmptyTaskDetailException {
        if (!command.equals(COMMAND_EVENT_WORD)) {
            return false;
        }
        if (detail == null || detail.trim().isEmpty()) {
            throw new EmptyTaskDetailException(ERROR_EMPTY_DETAIL);
        }
        return true;
    }

    private static boolean isValidDeadlineCommand(String command, String detail) throws EmptyTaskDetailException {
        if (!command.equals(COMMAND_DEADLINE_WORD)) {
            return false;
        }
        if (detail == null || detail.trim().isEmpty()) {
            throw new EmptyTaskDetailException(ERROR_EMPTY_DETAIL);
        }
        return true;
    }

    private static boolean isValidTodoCommand(String command, String detail) throws EmptyTaskDetailException {
        if (!command.equals(COMMAND_TODO_WORD)) {
            return false;
        }
        if (detail == null || detail.trim().isEmpty()) {
            throw new EmptyTaskDetailException(ERROR_EMPTY_DETAIL);
        }
        return true;
    }

    private static boolean isValidListCommand(String command, String detail) {
        return command.trim().equals(COMMAND_LIST_WORD) && detail == null;
    }

    private static boolean isValidByeCommand(String command, String detail) {
        return command.trim().equals(COMMAND_BYE_WORD) && detail == null;
    }

    private static boolean isValidMarkCommand(String command, String detail) throws InvalidIndexException {
        return command.equals(COMMAND_MARK_WORD) && isValidIndex(detail);
    }

    private static boolean isValidUnmarkCommand(String command, String detail) throws InvalidIndexException {
        return command.equals(COMMAND_UNMARK_WORD) && isValidIndex(detail);
    }

    private static void initTaskList() {
        tasks = new Task[MAX_TASK_CAPACITY];
        taskCount = 0;
    }

    private static void showWelcomeMessage() {
        System.out.println(LINE);
        System.out.println("Hello! I'm DanChat");
        System.out.println("What can I do for you?");
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

    private static boolean isValidIndex (String str) throws InvalidIndexException {
        if (!isValidInteger(str) || Integer.parseInt(str) <= 0) {
            throw new InvalidIndexException(ERROR_INVALID_INDEX_FORMAT);
        }
        return true;
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