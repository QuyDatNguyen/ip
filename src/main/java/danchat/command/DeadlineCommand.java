package danchat.command;

import danchat.task.Deadline;
import danchat.task.TaskList;

public class DeadlineCommand extends Command{
    public Deadline deadline;
    public DeadlineCommand(String command, String description, String by) {
        super();
        this.command = command;
        this.deadline = new Deadline(description, by);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        taskList.addTask(deadline);
    }
}
