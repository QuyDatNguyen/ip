package danchat.command;

import danchat.exception.DanException;
import danchat.storage.Storage;
import danchat.task.TaskList;
import danchat.ui.Ui;

public class Command {
//    protected static final String LINE = "=============================";
    protected String command;
    protected String detail;
    protected String commandMessage;
    protected boolean isExit = false;


    /**
     * Create an empty command as a placeholder
     *
     */
    public Command() {}

    public Command(String command, String detail) {
        this.command = command;
        this.detail = detail;
    }

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
