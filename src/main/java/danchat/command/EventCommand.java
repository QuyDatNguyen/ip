package danchat.command;

import danchat.task.Event;
import danchat.task.TaskList;

public class EventCommand extends Command {
    public Event event;
    private static final String COMMAND_EVENT_MESSAGE = "Add new event in your list: ";
    public EventCommand(String command, String description, String from, String to) {
        super();
        this.command = command;
        this.event = new Event(description, from, to);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        taskList.addTask(event);
        this.setCommandMessage(COMMAND_EVENT_MESSAGE + event);
    }
}
