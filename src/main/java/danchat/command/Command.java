package danchat.command;

import danchat.exception.DanException;
import danchat.task.TaskList;

public class Command {
    protected static final String LINE = "=============================";
    protected String command;
    protected String detail;

    /**
     * Create an empty command as a placeholder
     *
     */
    public Command() {}

    public Command(String command, String detail) {
        this.command = command;
        this.detail = detail;
    }

    public void executeCommand(TaskList taskList) throws DanException {
        throw new UnsupportedOperationException("This method is operated by child classes");
    }

    public static void printMessage(String message) {
        System.out.println(LINE);
        System.out.println("\t" + message);
        System.out.println(LINE);
    }
}
