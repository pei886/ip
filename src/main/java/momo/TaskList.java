package momo;

import momo.exceptions.InvalidTaskException;
import momo.exceptions.MomoException;
import momo.task.Task;

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

    public Task getTask(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskException();
        }
        return tasks.get(index);
    }

    public String printTask(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskException();
        }
        return tasks.get(index).toString();
    }

    public Task delete(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskException();
        }
        return tasks.remove(index);
    }

    public Task markTask(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskException();
        }
        Task targetTask = tasks.get(index);
        targetTask.markAsDone();
        return targetTask;
    }

    public Task unmarkTask(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskException();
        }
        Task targetTask = tasks.get(index);
        targetTask.markAsNotDone();
        return targetTask;
    }

    public Integer size() {
        return tasks.size();
    }

}
