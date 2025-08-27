import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task{
    protected LocalDateTime deadline;

    public Deadline(String description, String deadline) throws DateTimeParseException {
        super(description);
        this.description = description;
        this.deadline = parseDateTimeInput(deadline);
    }
    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, hh:mma");
        return "[D]" + super.toString() + " (by: " + deadline.format(formatter) + ")";
    }
}
