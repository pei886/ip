package momo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start and end time.
 */
public class Events extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm");

    /**
     * Creates an {@code Events} task with the specified description and period.
     *
     * @param description Event description.
     * @param start       Start date-time as string.
     * @param end         End date-time as string.
     * @throws DateTimeParseException If the date-time strings cannot be parsed.
     */
    public Events(String description, String start, String end) throws DateTimeParseException {
        super(description);
        this.start = parseDateTimeInput(start);
        this.end = parseDateTimeInput(end);
    }

    /**
     * Returns the formatted start time.
     *
     * @return Formatted start time.
     */
    public String getFormattedStart() {
        return start.format(formatter);
    }

    /**
     * Returns the formatted end time.
     *
     * @return Formatted end time.
     */
    public String getFormattedEnd() {
        return end.format(formatter);
    }

    /**
     * Returns the start time.
     *
     * @return Start date-time.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Returns the end time.
     *
     * @return End date-time.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + start.format(formatter) +
                " to: " + end.format(formatter) + ")";
    }
}
