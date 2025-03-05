package danchat.command;

import danchat.task.Task;
import danchat.task.TaskList;

public class ListCommand  extends Command{
    private static final String LINE = "=============================";
    public ListCommand(String command, String detail) {
        super(command, detail);
    }
    @Override
    public void executeCommand(TaskList taskList) {
        System.out.println(LINE);

        int taskNumber = 1; //Output index for list items, starting from 1.
        for (Task task: TaskList.innerList) {
            if (task == null) {
                break;
            }
            System.out.println("\t" + taskNumber + ". " + task);
            taskNumber++;
        }

        System.out.println(LINE);
    }
}
