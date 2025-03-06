package danchat.storage;
import danchat.exception.DanException;
import danchat.exception.LoadException;
import danchat.exception.MissingDateException;
import danchat.task.Deadline;
import danchat.task.Task;
import danchat.task.TaskList;
import danchat.task.Todo;
import danchat.task.Event;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Storage {
    private static final String DEFAULT_FILE_PATH = "tasks.txt";
    public static final String DEADLINE_BY_DATE = " by ";
    public static final String ERROR_MISSING_BY_DATE = "Missing by date";
    public static final String ERROR_LOADING_TASK = "Error loading task: ";
    public static final String ERROR_LOADING_DEADLINE_TASK = "Error loading deadline task: ";
    public static final String ERROR_LOADING_EVENT_TASK = "Error loading event task: ";
    public static final String ERROR_LOADING_TASKS = "Error loading tasks: ";
    private static File file;
    private String filePath;

    public Storage () {
        this(DEFAULT_FILE_PATH);
    }

    public Storage (String filePath) {
        this.filePath = filePath;
        file = new File(this.filePath);
    }

    /**
     * Check for file at given filePath and create new storage file if no file exists.
     */

    public void initTaskListFile() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private void writeToFile(String taskToAppend) throws IOException {
        FileWriter fw = new FileWriter(Storage.DEFAULT_FILE_PATH, false); // create a FileWriter in append mode
        fw.write(taskToAppend);
        fw.close();
    }

    private String taskListText(TaskList taskList) {
        String taskListText = "";
        for (Task task: taskList.getInnerList()) {
            taskListText = taskListText + task + System.lineSeparator();
        }
        return taskListText;
    }

     /**
     * Overwriting the old file with new text containing changes to taskList
     *
     * @param taskList the content of task list with changes to be saved
     */
    public void saveChangeToFile(TaskList taskList) {
        String taskListText = taskListText(taskList);
        try {
            writeToFile(taskListText);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Loading all tasks from storage file into program's task list
     * @return extracted task list from storage
     * @throws LoadException if loading process failed
     */
    public TaskList loadTasks() throws LoadException {
        TaskList taskList = new TaskList();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath)); // Read all lines from file
            for (String line : lines) {
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    taskList.addTask(task);
                }
            }
        } catch (IOException e) {
            throw new LoadException(ERROR_LOADING_TASKS + e.getMessage());
        }
        return taskList;
    }

    /**
     * Parsing each line from storage file into task list
     * @param line: line describing the task inside storage file
     * @return decoded task
     * @throws LoadException if the processing of line fails
     */
    private Task parseTaskFromLine(String line) throws LoadException {
        try {
            char taskType = line.charAt(1); // 'D' for Deadline, 'T' for Todo, 'E' for Event
            boolean isDone = line.charAt(4) == 'X';
            String content = line.substring(7).trim(); // Extract the task details

            switch (taskType) {
                case 'T': return new Todo(content, isDone);
                case 'D': return decodeDeadlineTask(content, isDone);
                case 'E': return decodeEventTask(content, isDone);
                default: return null;
            }
        } catch (DanException e) {
            throw new LoadException(ERROR_LOADING_TASK + e.getMessage());
        }
    }

    /**
     * Parsing deadline lines into deadline tasks inside task list
     *
     * @param content description and by date of deadline
     * @param isDone done status of deadline
     * @return deadline task object
     * @throws LoadException if processing of deadline from storage fails
     */
    private Deadline decodeDeadlineTask(String content, boolean isDone) throws LoadException {
        try {
            String[] contentParts = splitContent(content, DEADLINE_BY_DATE, ERROR_MISSING_BY_DATE);
            String description = contentParts[0].trim();
            String by = contentParts[1].trim();
            LocalDate byDate = LocalDate.parse(by, DateTimeFormatter.ofPattern("MMM d yyyy"));
            return new Deadline(description, isDone, byDate.toString());
        } catch (Exception e) {
            throw new LoadException(ERROR_LOADING_DEADLINE_TASK + e.getMessage());
        }
    }

    private static String[] splitContent(String content, String separator, String errorMessage) throws MissingDateException {
        String[] splitParts = content.split(separator, 2);
        if (splitParts.length < 2) {
            throw new MissingDateException(errorMessage);
        }
        return splitParts;
    }

    /**
     * Parsing event lines into deadline tasks inside task list
     *
     * @param content description, start and end date of event
     * @param isDone done status of the event
     * @return event object
     * @throws LoadException if processing of event from storage fails
     */
    private Event decodeEventTask(String content, boolean isDone) throws LoadException {
        try {
            String[] contentParts = splitContent(content, " from ", "Missing start date");
            String description = contentParts[0].trim();
            String eventPeriod = contentParts[1].trim();
            String[] durationParts = splitContent(eventPeriod, " to ", "Missing end date");
            String from = durationParts[0].trim();
            String to = durationParts[1].trim();
            LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("MMM d yyyy"));
            LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern("MMM d yyyy"));
            return new Event(description, isDone, fromDate.toString(), toDate.toString());
        } catch (Exception e) {
            throw new LoadException(ERROR_LOADING_EVENT_TASK + e.getMessage());
        }
    }

}
