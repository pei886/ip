import java.time.format.DateTimeParseException;

public class AddCommand {
    public static Task handleTaskCreation(String input) throws MomoException {
        Task newTask;
        if (input.startsWith("todo ")) {
            String desc = input.substring(5).trim();
            if (desc.isEmpty()) throw new MomoException("The description of a todo cannot be empty!");
            newTask = new ToDo(desc);
        } else if (input.startsWith("deadline ")) {
            String[] parts = input.substring(9).split(" /by ");
            if (parts.length < 2) throw new MomoException("Deadline must include a description and /by time!");
            try {
                newTask = new Deadline(parts[0].trim(), parts[1].trim());
            } catch (DateTimeParseException e) {
                throw new MomoException("Invalid date format for deadline: " + parts[1]
                        + "\n Please use the following format: MM/dd/yyyy HH:mm");
            }
        } else if (input.startsWith("event ")) {
            String[] parts = input.substring(6).split(" /from ");
            if (parts.length < 2) throw new MomoException("Event must include /from and /to!");
            String desc = parts[0].trim();
            String[] period = parts[1].split(" /to ");
            if (period.length < 2) throw new MomoException("Event must include both start and end times!");
            try {
                newTask = new Events(desc, period[0].trim(), period[1].trim());
            } catch (DateTimeParseException e) {
                throw new MomoException("Invalid date format for event: " + period[0] + " or " + period[1]
                        + "\n Please use the following format: MM/dd/yyyy HH:mm");
            }
        } else {
            throw new InvalidCommandException();
        }
        return newTask;
    }

}
