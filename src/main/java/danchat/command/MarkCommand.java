package danchat.command;
import danchat.exception.IllegalTaskException;
import danchat.task.Task;
import danchat.task.TaskList;

/**
 * Mark a task as done
 */
public class MarkCommand extends Command{
    private static final String ERROR_NOT_FOUND_TASK_MESSAGE = "Unfounded task with index ";
    private static final String COMMAND_MARK_MESSAGE = "Ok, I have marked this task as done";
    private int taskNumber;
    public MarkCommand(String command, String detail) {
        super(command, detail);
        this.taskNumber = Integer.parseInt(detail);
    }
    @Override
    public void executeCommand(TaskList taskList) throws IllegalTaskException {
        int taskListSize = taskList.getSize();
        if (taskNumber > taskListSize) {
            throw new IllegalTaskException(ERROR_NOT_FOUND_TASK_MESSAGE + taskNumber);
        } else {
            Task changeTask = taskList.getTask(taskNumber - 1);
            changeTask.setDone(true);
            this.setCommandMessage(COMMAND_MARK_MESSAGE + System.lineSeparator() + "\t"
                    + "[" + changeTask.getStatusIcon() + "] " + changeTask.getDescription());
        }
    }
}
