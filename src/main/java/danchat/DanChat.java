package danchat;

import danchat.command.Command;
import danchat.parser.Parser;
import danchat.exception.DanException;
import danchat.exception.EmptyTaskDetailException;
import danchat.exception.IllegalTaskException;
import danchat.exception.IllegalCommandException;
import danchat.exception.InvalidIndexException;
import danchat.exception.MissingDateException;
import danchat.storage.Storage;
import danchat.task.*;
import danchat.ui.Ui;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DanChat {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public DanChat(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        storage.initTaskListFile();
        tasks = new TaskList();
//        try {
////            tasks = new TaskList(storage.load());
//            storage.initTaskListFile();
//        } catch (DanException e) {
//            ui.showError(e.getMessage());
//            tasks = new TaskList();
//        }
    }

    public void run() {
        //...
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
//            try {
//                String fullCommand = ui.readCommand();
//                ui.showLine(); // show the divider line ("_______")
//                Command c = Parser.processUserInput(fullCommand);
//                c.execute(tasks, storage);
//                isExit = c.isExit();
//            } catch (DanException e) {
//                ui.showError(e.getMessage());
//            } finally {
//                ui.showLine();
//            }
                String fullCommand = ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.processUserInput(fullCommand);
                c.execute(tasks, storage, ui); //Error when c is null
                isExit = c.isExit();
                ui.showLine();
        }
    }

    public static void main(String[] args) {
        new DanChat("tasks.txt").run();
    }


/**
    private static final String LINE = "=============================";

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

    private static final String ERROR_EMPTY_DETAIL = "Details must not be blank";
    private static final String ERROR_INVALID_INDEX_FORMAT = "Wrong index format. Index must be a positive integer";
    private static final String COMMAND_DELETE_MESSAGE = "I have deleted this task for you: ";
    public static final String COMMAND_DELETE_WORD = "delete";

    private static ArrayList<Task> taskList = new ArrayList<>();
    private static int taskCount;

    public static final String FILE_DIRECTORY = "tasks.txt";
    private static File file = new File(FILE_DIRECTORY);

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        showWelcomeMessage();

        initTaskListFile();

        while (true) {
            String userInput = getUserInput();
            processUserInput(userInput);
        }
    }

    private static void initTaskListFile() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
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
                executeListCommand(taskList);
            } else if (isValidUnmarkCommand(command, detail)) {
                executeUnmarkCommand(detail);
                saveChangeToFile();
            } else if (isValidMarkCommand(command, detail)) {
                executeMarkCommand(detail);
                saveChangeToFile();
            } else if (isValidTodoCommand(command, detail)) {
                executeTodoCommand(detail);
                saveChangeToFile();
            } else if (isValidDeadlineCommand(command, detail)) {
                executeDeadlineCommand(detail);
                saveChangeToFile();
            } else if (isValidEventCommand(command, detail)) {
                executeEventCommand(detail);
                saveChangeToFile();
            } else if (isValidDeleteCommand(command, detail)) {
                executeDeleteCommand(detail);
                saveChangeToFile();
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
        String[] splitDetails = splitDetail(detail, EVENT_BEGIN_DATE_SEPERATOR, ERROR_MISSING_BEGIN_DATE);
        String description = splitDetails[0].trim();
        String eventPeriod = splitDetails[1].trim();

        String[] splitDurations = splitDetail(eventPeriod, EVENT_END_DATE_SEPERATOR, ERROR_MISSING_END_DATE);
        String from = splitDurations[0].trim();
        String to = splitDurations[1].trim();

        addEventToTaskList(description, from, to);
    }

    private static String[] splitDetail(String detail, String separator, String errorMessage) throws MissingDateException {
        String[] splitParts = detail.split(separator, 2);
        if (splitParts.length < 2) {
            throw new MissingDateException(errorMessage);
        }
        return splitParts;
    }

    private static void addEventToTaskList(String description, String from, String to) {
        Event event = new Event(description, from, to);
        taskList.add(event);
        taskCount++;
        printMessage(COMMAND_EVENT_MESSAGE + event);
    }

    private static void executeDeadlineCommand(String detail) throws MissingDateException {
        String[] splitDetails = splitDetail(detail, DEADLINE_SEPERATOR, ERROR_MISSING_DEADLINE_DATE);
        String description = splitDetails[0].trim();
        String by = splitDetails[1].trim();
        addDeadlineToTaskList(description, by);
    }

    private static void addDeadlineToTaskList(String description, String by) {
        Deadline deadline = new Deadline(description, by);
        taskList.add(deadline);
        taskCount++;
        printMessage(COMMAND_DEADLINE_MESSAGE + deadline);
    }

    private static void executeTodoCommand(String detail) {
        addTodoToTaskList(detail);
    }

    private static void addTodoToTaskList(String detail) {
        Todo todo = new Todo(detail);
        taskList.add(todo);
        taskCount++;
        printMessage(COMMAND_TODO_MESSAGE + todo);
    }

    private static void executeDeleteCommand(String detail) throws IllegalTaskException {
        int taskNumber = Integer.parseInt(detail);
        if (taskNumber > taskCount) {
            throw new IllegalTaskException(ERROR_NOT_FOUND_TASK_MESSAGE + taskNumber);
        } else {
            Task deleteTask = taskList.get(taskNumber - 1);
            taskList.remove(taskNumber - 1);
            taskCount--;
            printMessage(COMMAND_DELETE_MESSAGE + deleteTask);
        }
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
            Task changeTask = taskList.get(taskNumber - 1);
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
            Task changeTask = taskList.get(taskNumber - 1);
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

    private static boolean isValidDeleteCommand(String command, String detail) throws InvalidIndexException {
        return command.equals(COMMAND_DELETE_WORD) && isValidIndex(detail);
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

    public static void executeListCommand(ArrayList<Task> taskList) {
        System.out.println(LINE);
        printTaskList(taskList);
        System.out.println(LINE);
    }

    private static void printTaskList(ArrayList<Task> tasks) {
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

    private static void writeToFile(String filePath, String taskToAppend) throws IOException {
        FileWriter fw = new FileWriter(filePath, false); // create a FileWriter in append mode
        fw.write(taskToAppend);
        fw.close();
    }

    private static String taskListText() {
        String taskListText = "";
        for (Task task: taskList) {
            taskListText = taskListText + task + System.lineSeparator();
        }
        return taskListText;
    }

    private static void saveChangeToFile() {
        String taskListText = taskListText();
        try {
            writeToFile(FILE_DIRECTORY, taskListText);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
 */
}