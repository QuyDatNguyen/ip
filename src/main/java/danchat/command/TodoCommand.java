package danchat.command;

import danchat.task.TaskList;
import danchat.task.Todo;

public class TodoCommand extends Command{
    public Todo todoTask;
    private static final String COMMAND_TODO_MESSAGE = "Add new todo in your list: ";
    public TodoCommand(String command, String detail) {
        super();
        this.todoTask = new Todo(detail);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        taskList.addTask(todoTask);
        this.setCommandMessage(COMMAND_TODO_MESSAGE + todoTask);
    }
}
