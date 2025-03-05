package danchat.command;

import danchat.task.TaskList;
import danchat.task.Todo;

public class TodoCommand extends Command{
    public Todo todoTask;
    public TodoCommand(String command, String detail) {
        super();
        this.todoTask = new Todo(detail);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        taskList.addTask(todoTask);
    }
}
