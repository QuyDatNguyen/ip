package danchat.command;

import danchat.task.Task;
import danchat.task.TaskList;

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
