package momo.task;

/**
 * Represents a to-do task with only a description.
 */
public class ToDo extends Task {
    /**
     * Creates a {@code ToDo} task with the given description.
     *
     * @param description Task description.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}