package momo;

import momo.command.*;
import momo.exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Handles parsing of user input into executable objects.
 * The class converts raw command strings into their corresponding command objects.
 */
public class Parser {

    /**
     * Parses the given user input into a Command.
     * @param input User input string.
     * @return Corresponding Command object.
     * @throws MomoException If the input is invalid or incomplete.
     */
    public static Command parseCommand(String input) throws MomoException {
        String[] parts = input.trim().split(" ", 2);
        String commandWord = parts[0];
        String rest = (parts.length > 1) ? parts[1].trim() : "";

        return switch (commandWord) {
            case "bye" -> new ByeCommand();
            case "todo" -> parseTodo(rest);
            case "deadline" -> parseDeadline(rest);
            case "event" -> parseEvent(rest);
            case "list" -> new ListCommand();
            case "delete" -> parseDelete(rest);
            case "mark" -> parseMark(rest);
            case "unmark" -> parseUnmark(rest);
            case "due" -> parseDue(rest);
            case "find" -> parseFind(rest);
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

    private static Command parseDue(String rest) throws MomoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dueDate;
        String date = rest.trim();
        try {
            dueDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new MomoException("Invalid date format for deadline: " + date
                    + "\n Please use the following format: MM/dd/yyyy");
        }
        return new DueCommand(dueDate);
    }

    private static Command parseDelete(String rest) throws MomoException {
        return new DeleteCommand(parseIndex(rest));
    }

    private static Command parseMark(String rest) throws MomoException {
        return new MarkCommand(parseIndex(rest));
    }

    private static Command parseUnmark(String rest) throws MomoException {
        return new UnmarkCommand(parseIndex(rest));
    }

    private static Command parseFind(String rest) throws MomoException {
        if (rest.isBlank()) {
            throw new MomoException("Please enter a keyword to find");
        }
        return new FindCommand(rest.trim());
    }


    private static Integer parseIndex(String rest) throws MomoException {
        try {
            return Integer.parseInt(rest.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidTaskException();
        }
    }

}
