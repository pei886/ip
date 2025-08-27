import java.time.LocalDate;
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

    public String getStart() {
        return start.format(formatter);
    }

    public String getEnd() {
        return end.format(formatter);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.format(formatter) + " to: " + end.format(formatter) + ")";
    }
}
