package danchat.command;

import danchat.exception.DanException;
import danchat.storage.Storage;
import danchat.task.TaskList;
import danchat.ui.Ui;

/**
 * Represent an executable command
 */
public class Command {
//    protected static final String LINE = "=============================";
    protected String command;
    protected String detail;
    protected String commandMessage;
    protected boolean isExit = false;

    public Command() {}

    public Command(String command, String detail) {
        this.command = command;
        this.detail = detail;
    }

    /**
     * Execute the command, save the changes and return the result to user
     *
     * @param taskList the task list that user executes command to
     * @param storage storage file to save the result
     * @param ui user interface to display the result
     */
    public void execute(TaskList taskList, Storage storage, Ui ui) {
        try {
            executeCommand(taskList);
            ui.showCommandMessage(this.getCommandMessage());
            storage.saveChangeToFile();
            if (this.isExit()) {
                System.exit(0);
            }
        } catch (DanException e) {
            ui.showError(e.getMessage());
//            throw new RuntimeException(e);
        }
    }

    /**
     * Execute the user command
     *
     * @param taskList the task list that user executes command to
     * @throws DanException if the method is not called by specific command child classes
     */

    public void executeCommand(TaskList taskList) throws DanException {
        throw new DanException("This method is operated by child classes");
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }

    public boolean isExit() {
        return isExit;
    }

    public String getCommandMessage() {
        return commandMessage;
    }

    public void setCommandMessage(String commandMessage) {
        this.commandMessage = commandMessage;
    }
}
