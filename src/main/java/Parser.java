import java.time.format.DateTimeParseException;

public class Parser {
    public static void formatOutput(String output) {
        System.out.println("____________________________________________________________");
        String[] lines = output.split("\n");
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("____________________________________________________________");
    }

    public static Command parseCommand(String input) throws MomoException {
        String[] parts = input.split(" ", 2);
        String commandWord = parts[0];
        String rest = parts[1];

        return switch (commandWord) {
            case "todo" -> parseTodo(rest);
            case "deadline" -> parseDeadline(rest);
            case "event" -> parseEvent(rest);
//            case "list" -> new ListCommand();
            default -> throw new InvalidCommandException();
        };
    }

    private static Command parseTodo(String rest) throws MomoException {
        if (rest.isBlank()) throw new MomoException("The description of a todo cannot be empty!");
        return new AddCommand("todo", rest);
    }

    private static Command parseDeadline(String rest) throws MomoException {
        String[] parts = rest.split("/by", 2);
        if (parts.length < 2) {
            throw new MomoException("Please use this format: deadline <description> /by <date time>");
        }
        String desc = parts[0].trim();
        String by   = parts[1].trim();
        if (desc.isEmpty() || by.isEmpty()) {
            throw new MomoException("Deadline needs both description and /by <date time>.");
        }
        return new AddCommand("deadline", desc, by);
    }

    private static Command parseEvent(String rest) throws MomoException {
        String[] first = rest.split("/from", 2);
        if (first.length < 2) throw new MomoException("Please use this format: event <description> /from <start> /to <end>");

        String desc = first[0].trim();
        String[] second = first[1].split("/to", 2);
        if (second.length < 2) throw new MomoException("Event must include both /from and /to.");

        String start = second[0].trim();
        String end   = second[1].trim();

        if (desc.isEmpty() || start.isEmpty() || end.isEmpty()) {
            throw new MomoException("Event needs <description>, <start>, and <end>.");
        }
        return new AddCommand("event", desc, start, end);
    }

}
