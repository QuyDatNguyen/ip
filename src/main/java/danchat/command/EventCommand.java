package danchat.command;

import danchat.task.Event;
import danchat.task.TaskList;

public class EventCommand extends Command {
    public Event event;
    public EventCommand(String command, String description, String from, String to) {
        super();
        this.command = command;
        this.event = new Event(description, from, to);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        taskList.addTask(event);
    }
}
