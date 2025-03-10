package danchat.command;
import danchat.task.TaskList;

/**
 * Represent an error or incorrect command. Upon execution, provide some feedback to user
 */
public class ErrorCommand extends Command{
    private static final String COMMAND_ERROR_MESSAGE = "Caught exception: ";

    public ErrorCommand(String errorMessage) {
        super();
        this.command = "error";
        this.detail = errorMessage;
    }
    @Override
    public void executeCommand(TaskList taskList) {
        this.setCommandMessage(COMMAND_ERROR_MESSAGE + detail);
    }
}
