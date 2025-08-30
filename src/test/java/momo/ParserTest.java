package momo;

import momo.command.*;
import momo.exceptions.MomoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parseCommand_todo_returnsAddCommand() throws Exception {
        Command c = Parser.parseCommand("todo read");
        assertInstanceOf(AddCommand.class, c);
    }

    @Test
    void parseCommand_deadline_returnsAddCommand() throws Exception {
        Command c = Parser.parseCommand("deadline finish work /by 12/31/2025");
        assertInstanceOf(AddCommand.class, c);
    }

    @Test
    void parseCommand_event_returnsAddCommand() throws Exception {
        Command c = Parser.parseCommand("event party /from 12/31/2025 /to 01/01/2026");
        assertInstanceOf(AddCommand.class, c);
    }

    @Test
    void parseCommand_due_returnsDueCommand() throws Exception {
        Command c = Parser.parseCommand("due 12/31/2025");
        assertInstanceOf(DueCommand.class, c);
    }

    @Test
    void parseCommand_invalidCommand_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("blah blah"));
    }

    @Test
    void parseCommand_badTodo_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("todo "));
    }

    @Test
    void parseCommand_todo_withSpaces_returnsAddCommand() throws Exception {
        Command c = Parser.parseCommand("  todo    read book  ");
        assertInstanceOf(AddCommand.class, c);
    }

    @Test
    void parseCommand_deadline_missingBy_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("deadline finish work"));
    }

    @Test
    void parseCommand_event_missingTo_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("event party /from 12/31/2025"));
    }

    @Test
    void parseCommand_list_returnsListCommand() throws Exception {
        Command c = Parser.parseCommand("list");
        assertInstanceOf(ListCommand.class, c);
    }

    @Test
    void parseCommand_bye_returnsByeCommand() throws Exception {
        Command c = Parser.parseCommand("bye");
        assertInstanceOf(ByeCommand.class, c);
    }
}
