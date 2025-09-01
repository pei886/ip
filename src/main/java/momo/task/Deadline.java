package momo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 * Extends {@link Task}.
 */
public class Deadline extends Task {
    protected LocalDateTime deadline;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm");

    /**
     * Constructs a {@code Deadline} task with the given description and deadline.
     *
     * @param description Description of the task.
     * @param deadline Deadline date-time string.
     * @throws DateTimeParseException If the date-time format is invalid.
     */
    public Deadline(String description, String deadline) throws DateTimeParseException {
        super(description);
        this.description = description;
        this.deadline = parseDateTimeInput(deadline);
    }

    /**
     * Returns the formatted deadline of this task.
     *
     * @return Formatted deadline string.
     */
    public String getFormattedDeadline() {
        return deadline.format(formatter);
    }

    /**
     * Returns the deadline of this task.
     *
     * @return Deadline as a {@link LocalDateTime}.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(formatter) + ")";
    }
}
