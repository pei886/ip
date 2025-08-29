package momo.exceptions;

public class TaskOutOfRangeException extends MomoException{
    public TaskOutOfRangeException(int maxTasks){
        super("Task number out of range! You only have " + maxTasks + " task(s) in your list.");
    }
}
