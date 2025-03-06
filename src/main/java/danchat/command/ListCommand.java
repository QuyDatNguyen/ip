package danchat.command;

import danchat.task.Task;
import danchat.task.TaskList;

/**
 * Show all events inside the task list to user
 */
public class ListCommand  extends Command{
//    private static final String LINE = "=============================";
    public ListCommand(String command, String detail) {
        super(command, detail);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        String message = "";
        int taskNumber = 1; //Output index for list items, starting from 1.
        for (Task task: TaskList.innerList) {
            if (task == null) {
                break;
            }
            message = message + "\t" + taskNumber + ". " + task;
            taskNumber++;
        }
        this.setCommandMessage(message);
    }
}
