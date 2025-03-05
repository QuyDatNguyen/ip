package danchat.storage;

import danchat.exception.DanException;
import danchat.task.Task;
import danchat.task.TaskList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static danchat.task.TaskList.innerList;

public class Storage {
    public static final String DEFAULT_FILE_PATH = "tasks.txt";
    private static File file = new File(DEFAULT_FILE_PATH);
    public static Path filePath = Paths.get(DEFAULT_FILE_PATH);

    private static void initTaskListFile() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private static void writeToFile(String taskToAppend) throws IOException {
        FileWriter fw = new FileWriter(Storage.DEFAULT_FILE_PATH, false); // create a FileWriter in append mode
        fw.write(taskToAppend);
        fw.close();
    }

    private static String taskListText() {
        String taskListText = "";
        for (Task task:
                innerList) {
            taskListText = taskListText + task + System.lineSeparator();
        }
        return taskListText;
    }

    private static void saveChangeToFile() {
        String taskListText = taskListText();
        try {
            writeToFile(taskListText);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

//    public TaskList load() throws StorageOperationException {
//        try {
//            List<String> encodedTaskList = Files.readAllLines(filePath);
//        } catch (IOException e) {
//            throw new StorageOperationException("Error writing to file: " + filePath);
//        }
//
//    }
//
//    public static class StorageOperationException extends DanException {
//        public StorageOperationException(String message) {
//            super(message);
//        }
//    }
}
