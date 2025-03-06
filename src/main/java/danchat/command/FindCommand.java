package danchat.command;
import danchat.task.Task;
import danchat.task.TaskList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindCommand extends Command{
    public FindCommand(String command, String detail) {
        super(command, detail);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        ArrayList<Task> tasks = taskList.getInnerList();
        HashMap<Integer, Task> findResult = getSearchResult(tasks);

        if (findResult.isEmpty()) {
            this.setCommandMessage("Sorry, we cannot found task contains " + detail);
        } else {
            String message = convertResultToString(findResult);
            this.setCommandMessage(message);
        }
    }

    private static String convertResultToString(HashMap<Integer, Task> findResult) {
        String message = "";
        for (Map.Entry<Integer, Task> entry: findResult.entrySet()) {
            if (entry.getValue() == null) {
                break;
            }
            message = message + "\t" + entry.getKey() + ". "
                    + entry.getValue() + System.lineSeparator();
        }
        return message;
    }

    private HashMap<Integer, Task> getSearchResult(ArrayList<Task> tasks) {
        HashMap<Integer, Task> findResult = new HashMap<>();

        for (Task task: tasks) {
            String description = task.getDescription();
            if (description.contains(detail)) {
                findResult.put(tasks.indexOf(task) + 1, task);
            }
        }
        return findResult;
    }
}
