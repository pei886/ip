package momo.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a generic task with a description and completion status.
 * Provides methods for marking, unmarking, and parsing date-time inputs.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm"),
            DateTimeFormatter.ofPattern("M/d/yyyy H:mm"),
            DateTimeFormatter.ofPattern("M/d/yyyy")
    };

    /**
     * Creates a task with the specified description.
     *
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if done, " " if not done.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the description of the task.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns whether the task is marked as done.
     *
     * @return {@code true} if done, {@code false} otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Parses a date-time string into a {@link LocalDateTime}.
     * Supports multiple formats.
     *
     * @param input Date-time string to parse.
     * @return Parsed {@link LocalDateTime}.
     * @throws DateTimeParseException If parsing fails for all supported formats.
     */
    public static LocalDateTime parseDateTimeInput(String input) throws DateTimeParseException {
        input = input.trim();

        for (DateTimeFormatter formatter : FORMATTERS) {
            // Try LocalDateTime
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException ignored) {}

            // Try LocalDate
            try {
                LocalDate date = LocalDate.parse(input, formatter);
                return date.atStartOfDay();
            } catch (DateTimeParseException ignored) {}
        }

        throw new DateTimeParseException("Invalid date format: " + input, input, 0);
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }
}