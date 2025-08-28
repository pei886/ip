import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public static LocalDateTime parseDateTimeInput(String input) throws DateTimeParseException {
        String[] patterns = {
                "MMM d yyyy, hh:mm",
                "M/d/yyyy H:mm",
                "M/d/yyyy",
        };

        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                try {
                    return LocalDateTime.parse(input.trim(), formatter);
                } catch (DateTimeParseException e) {
                    LocalDate date = LocalDate.parse(input.trim(), formatter);
                    return date.atStartOfDay();
                }
            } catch (DateTimeParseException ignored) {}
        }
        throw new DateTimeParseException("Invalid date format: " + input, input, 0);
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

}
