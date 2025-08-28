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
        return tasks.remove(index - 1);
    }

    public void markTask(int index) {
        Task targetTask = tasks.get(index - 1);
        targetTask.markAsDone();
    }

    public void unmarkTask(int index) {
        Task targetTask = tasks.get(index - 1);
        targetTask.markAsNotDone();
    }

    public int size() {
        return tasks.size();
    }

}
