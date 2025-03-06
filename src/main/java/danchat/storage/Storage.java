package danchat.storage;
import danchat.task.Task;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static danchat.task.TaskList.innerList;

public class Storage {
    private static final String DEFAULT_FILE_PATH = "tasks.txt";
    private static File file;
    private String filePath;

    public Storage () {
        this(DEFAULT_FILE_PATH);
    }

    public Storage (String filePath) {
        this.filePath = filePath;
        file = new File(this.filePath);
    }

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

    private String taskListText() {
        String taskListText = "";
        for (Task task: innerList) {
            taskListText = taskListText + task + System.lineSeparator();
        }
        return taskListText;
    }

    public void saveChangeToFile() {
        String taskListText = taskListText();
        try {
            writeToFile(taskListText);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

//    public TaskList load() throws StorageOperationException {
//        try {
//            List<String> encodedTask = Files.readAllLines(Paths.get(filePath));
//
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
