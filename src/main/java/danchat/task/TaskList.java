package danchat.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TaskList {
    public static final String EMPTY_TASK_LIST_MESSAGE = "Task list is currently empty";
    protected ArrayList<Task> innerList = new ArrayList<>();

    /**
     * Construct empty task list
     */
    public TaskList() {}

    public TaskList(Task... tasks) {
        final List<Task> initialTags = Arrays.asList(tasks);
        innerList.addAll(initialTags);
    }
    public TaskList(Collection<Task> tasks) {
        innerList.addAll(tasks);
    }
    /**
     * @return size of task list
     */
    public int getSize() {
        return innerList.size();
    }

    /**
     * @param task to be added into the list
     */

    public void addTask (Task task) {
        innerList.add(task);
    }

    public void deleteTask (Task task) {
        innerList.remove(task);
    }

    public void deleteTask (int index) {
        innerList.remove(index);
    }

    public Task getTask (int index) {
        try {
            return innerList.get(index);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Task> getInnerList() {
        return innerList;
    }

    @Override
    public String toString() {
        if (innerList.isEmpty()) {
            return EMPTY_TASK_LIST_MESSAGE;
        }
        String outString = "";
        for (Task task: innerList) {
            if (task == null) {
                break;
            }
            int taskNumber = innerList.indexOf(task) + 1;
            outString = outString + "\t" + taskNumber + ". " + task + System.lineSeparator();
        }
        return outString;
    }
}
