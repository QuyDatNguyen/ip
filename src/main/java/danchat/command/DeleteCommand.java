package danchat.command;
import danchat.exception.IllegalTaskException;
import danchat.task.Task;
import danchat.task.TaskList;

/**
 * Delete a task from task list
 */
public class DeleteCommand extends Command {
    private static final String ERROR_NOT_FOUND_TASK_MESSAGE = "Unfounded task with index ";
    private static final String COMMAND_DELETE_MESSAGE = "I have deleted this task for you: ";

    private int taskNumber;
    public DeleteCommand(String command, String detail) {
        super(command, detail);
        this.taskNumber = Integer.parseInt(detail);
    }
    @Override
    public void executeCommand(TaskList taskList) throws IllegalTaskException {
        int taskListSize = taskList.getSize();
        if (taskNumber > taskListSize) {
            throw new IllegalTaskException(ERROR_NOT_FOUND_TASK_MESSAGE + taskNumber);
        } else {
            Task deleteTask = taskList.getTask(taskNumber - 1);
            taskList.deleteTask(taskNumber - 1);
            this.setCommandMessage(COMMAND_DELETE_MESSAGE + deleteTask);
        }
    }
}
