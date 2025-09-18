package momo;

import momo.exceptions.InvalidTaskException;
import momo.exceptions.MomoException;
import momo.task.Task;

import java.util.ArrayList;

/**
 * Represents a list of tasks managed by the application.
 * Provides methods for adding, retrieving, deleting, marking, and unmarking tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates a TaskList with the specified tasks.
     *
     * @param tasks Initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {

        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        assert task != null : "task can't be null";
        tasks.add(task);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index Index of the task.
     * @return Task at the specified index.
     * @throws MomoException If the index is invalid.
     */
    public Task getTask(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskException();
        }
        return tasks.get(index);
    }

    /**
     * Returns the string representation of the task at the given index.
     *
     * @param index Index of the task.
     * @return String representation of the task.
     * @throws MomoException If the index is invalid.
     */
    public String printTask(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskException();
        }
        return tasks.get(index).toString();
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index Index of the task to delete.
     * @return The deleted task.
     * @throws MomoException If the index is invalid.
     */
    public Task delete(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskException();
        }
        return tasks.remove(index);
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index Index of the task.
     * @return The updated task.
     * @throws MomoException If the index is invalid.
     */
    public Task markTask(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskException();
        }
        Task targetTask = tasks.get(index);
        targetTask.markAsDone();
        return targetTask;
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index Index of the task.
     * @return The updated task.
     * @throws MomoException If the index is invalid.
     */
    public Task unmarkTask(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskException();
        }
        Task targetTask = tasks.get(index);
        targetTask.markAsNotDone();
        return targetTask;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Size of the task list.
     */
    public Integer size() {
        return tasks.size();
    }

}
