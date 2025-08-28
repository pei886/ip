import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;
    public TaskList(ArrayList<Task> tasks) {

        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void delete(int index) {
        tasks.remove(index - 1);
    }

    public void markTask(int index) {
        Task targetTask = tasks.get(index - 1);
        targetTask.markAsDone();
    }

    public void unmarkTask(int index) {
        Task targetTask = tasks.get(index - 1);
        targetTask.markAsNotDone();
    }

}
