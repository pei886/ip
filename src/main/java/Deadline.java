import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task{
    protected LocalDateTime deadline;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm");

    public Deadline(String description, String deadline) throws DateTimeParseException {
        super(description);
        this.description = description;
        this.deadline = parseDateTimeInput(deadline);
    }
    public String getFormattedDeadline() {
        return deadline.format(formatter);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(formatter) + ")";
    }
}
