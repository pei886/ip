import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;
    public TaskList(ArrayList<Task> tasks) {

        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public String printTask(int index) {
        return tasks.get(index).toString();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public Task markTask(int index) {
        Task targetTask = tasks.get(index);
        targetTask.markAsDone();
        return targetTask;
    }

    public Task unmarkTask(int index) {
        Task targetTask = tasks.get(index);
        targetTask.markAsNotDone();
        return targetTask;
    }

    public Integer size() {
        return tasks.size();
    }

}
