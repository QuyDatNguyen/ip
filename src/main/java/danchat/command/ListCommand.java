package danchat.command;
import danchat.task.TaskList;

public class ListCommand  extends Command{
    public ListCommand(String command, String detail) {
        super(command, detail);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        this.setCommandMessage(taskList.toString());
    }
}
