import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Events extends Task{
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm");

    public Events(String description, String start, String end) throws DateTimeParseException{
        super(description);
        this.start = parseDateTimeInput(start);
        this.end = parseDateTimeInput(end);
    }

    public String getFormattedStart() {
        return start.format(formatter);
    }

    public String getFormattedEnd() {
        return end.format(formatter);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.format(formatter) + " to: " + end.format(formatter) + ")";
    }
}
