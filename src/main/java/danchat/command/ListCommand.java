package danchat.command;
import danchat.task.TaskList;

/**
 * Show all events inside the task list to user
 */
public class ListCommand  extends Command{
    public ListCommand(String command, String detail) {
        super(command, detail);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        this.setCommandMessage(taskList.toString());
    }
}
