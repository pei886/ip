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

        assert !commandWord.isBlank() : "commandWord should not be blank";

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
            case "sort" -> parseSort(rest);
            default -> throw new InvalidCommandException();
        };
    }

    /**
     * Parses a "todo" command from the remaining input string.
     *
     * @param rest The input string after the command word.
     * @return {@link AddCommand} representing the todo task.
     * @throws MomoException If the description is empty.
     */
    private static Command parseTodo(String rest) throws MomoException {
        if (rest.isBlank()) throw new MomoException("The description of a todo cannot be empty!");
        return new AddCommand("todo", rest);
    }

    /**
     * Parses a "deadline" command from the remaining input string.
     *
     * Expected format: "<description> /by <date time>"
     *
     * @param rest The input string after the command word.
     * @return {@link AddCommand} representing the deadline task.
     * @throws MomoException If the input does not contain a description or valid /by date.
     */
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

    /**
     * Parses an "event" command from the remaining input string.
     *
     * Expected format: "<description> /from <start> /to <end>"
     *
     * @param rest The input string after the command word.
     * @return {@link AddCommand} representing the event task.
     * @throws MomoException If the input does not include a description, start, or end date.
     */
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

    /**
     * Parses a "due" command to find tasks due on a specific date.
     *
     * Expected format: "MM/dd/yyyy"
     *
     * @param rest The input string containing the date.
     * @return {@link DueCommand} representing the tasks due on the specified date.
     * @throws MomoException If the date format is invalid.
     */
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


    /**
     * Parses a "delete" command to remove a task by its index.
     *
     * @param rest The input string containing the task index.
     * @return {@link DeleteCommand} for the specified task.
     * @throws MomoException If the index is invalid.
     */
    private static Command parseDelete(String rest) throws MomoException {
        return new DeleteCommand(parseIndex(rest));
    }

    /**
     * Parses a "mark" command to mark a task as completed.
     *
     * @param rest The input string containing the task index.
     * @return {@link MarkCommand} for the specified task.
     * @throws MomoException If the index is invalid.
     */
    private static Command parseMark(String rest) throws MomoException {
        return new MarkCommand(parseIndex(rest));
    }

    /**
     * Parses an "unmark" command to mark a task as incomplete.
     *
     * @param rest The input string containing the task index.
     * @return {@link UnmarkCommand} for the specified task.
     * @throws MomoException If the index is invalid.
     */
    private static Command parseUnmark(String rest) throws MomoException {
        return new UnmarkCommand(parseIndex(rest));
    }

    /**
     * Parses a "find" command to search tasks containing a keyword.
     *
     * @param rest The input string containing the keyword.
     * @return {@link FindCommand} representing the search.
     * @throws MomoException If the keyword is blank.
     */
    private static Command parseFind(String rest) throws MomoException {
        if (rest.isBlank()) {
            throw new MomoException("Please enter a keyword to find");
        }
        return new FindCommand(rest.trim());
    }

    /**
     * Parses a "sort" command to sort tasks in ascending or descending order.
     *
     * @param rest The input string specifying the sort order ("asc" or "desc").
     * @return {@link SortCommand} configured with the sort order.
     * @throws MomoException If an invalid sort keyword is provided.
     */
    private static Command parseSort(String rest) throws MomoException {
        if (rest.isBlank() || rest.equals("asc")) {
            return new SortCommand(true);
        } else if (rest.equals("desc")) {
            return new SortCommand(false);
        } else {
            throw new MomoException("Invalid sort keyword: " + rest + ". Try asc or desc.");
        }
    }

    /**
     * Converts a string input to a zero-based task index.
     *
     * @param rest The input string representing the task index.
     * @return Zero-based index of the task.
     * @throws InvalidTaskException If the input cannot be parsed as an integer.
     */
    private static Integer parseIndex(String rest) throws MomoException {
        try {
            return Integer.parseInt(rest.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidTaskException();
        }
    }

}
