package danchat.command;
import danchat.task.TaskList;

public class ByeCommand extends Command{
    private static final String LINE = "=============================";
    private static final String COMMAND_BYE_WORD = "bye";
    private static final String COMMAND_BYE_MESSAGE = "Bye. Hope to see you again soon!";
    public ByeCommand(String command, String detail) {
        super(command, detail);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        this.setExit(true);
        this.setCommandMessage(COMMAND_BYE_MESSAGE);
//        System.exit(0);
    }
}
