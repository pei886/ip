package momo;

import momo.command.*;
import momo.exceptions.MomoException;
import momo.exceptions.InvalidCommandException;
import momo.exceptions.InvalidTaskException;
import momo.task.Deadline;

import java.time.format.DateTimeParseException;

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

    // ========== NEW COMPREHENSIVE TESTS ==========

    // ========== TODO COMMAND TESTS ==========
    @Test
    void parseCommand_todo_emptyDescription_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("todo"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("todo   "));
    }

    @Test
    void parseCommand_todo_withSpecialCharacters_success() throws Exception {
        Command c = Parser.parseCommand("todo read @#$%^&*()!");
        assertInstanceOf(AddCommand.class, c);
    }

    @Test
    void parseCommand_todo_multipleSpaces_success() throws Exception {
        Command c = Parser.parseCommand("todo     read    multiple    books");
        assertInstanceOf(AddCommand.class, c);
    }

    // ========== DEADLINE COMMAND TESTS ==========
    @Test
    void parseCommand_deadline_emptyDescription_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("deadline /by 12/31/2025"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("deadline    /by 12/31/2025"));
    }

    @Test
    void parseCommand_deadline_emptyDate_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("deadline finish work /by"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("deadline finish work /by   "));
    }

    @Test
    void parseCommand_deadline_noByKeyword_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("deadline finish work 12/31/2025"));
    }

    @Test
    void parseCommand_deadline_withTrailingSpaces_success() throws Exception {
        Command c = Parser.parseCommand("   deadline   finish work   /by   12/31/2025   ");
        assertInstanceOf(AddCommand.class, c);
    }

    @Test
    void parseCommand_deadline_withSpecialCharactersInDescription_success() throws Exception {
        Command c = Parser.parseCommand("deadline finish @work #project /by 12/31/2025");
        assertInstanceOf(AddCommand.class, c);
    }

    // ========== EVENT COMMAND TESTS ==========
    @Test
    void parseCommand_event_missingFrom_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("event party /to 01/01/2026"));
    }

    @Test
    void parseCommand_event_emptyDescription_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("event /from 12/31/2025 /to 01/01/2026"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("event    /from 12/31/2025 /to 01/01/2026"));
    }

    @Test
    void parseCommand_event_emptyStartDate_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("event party /from /to 01/01/2026"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("event party /from    /to 01/01/2026"));
    }

    @Test
    void parseCommand_event_emptyEndDate_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("event party /from 12/31/2025 /to"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("event party /from 12/31/2025 /to   "));
    }

    @Test
    void parseCommand_event_wrongOrder_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("event party /to 01/01/2026 /from 12/31/2025"));
    }

    @Test
    void parseCommand_event_withTrailingSpaces_success() throws Exception {
        Command c = Parser.parseCommand("   event   party   /from   12/31/2025   /to   01/01/2026   ");
        assertInstanceOf(AddCommand.class, c);
    }

    // ========== DUE COMMAND TESTS ==========
    @Test
    void parseCommand_due_invalidDateFormat_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("due 2025-12-31"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("due 31/12/2025"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("due Dec 31 2025"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("due 12-31-2025"));
    }

    // Test the underlying parsing logic
    @Test
    void deadline_constructor_invalidDate_throws() {
        assertThrows(DateTimeParseException.class, () ->
                new Deadline("test task", "02/30/2025"));
        assertThrows(DateTimeParseException.class, () ->
                new Deadline("test task", "13/01/2025"));
    }

    @Test
    void parseCommand_due_emptyDate_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("due"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("due   "));
    }

    @Test
    void parseCommand_due_invalidCharacters_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("due abc/def/ghij"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("due 12/ab/2025"));
    }

    @Test
    void parseCommand_due_leapYear_success() throws Exception {
        Command c = Parser.parseCommand("due 02/29/2024"); // 2024 is a leap year
        assertInstanceOf(DueCommand.class, c);
    }

    // ========== DELETE COMMAND TESTS ==========
    @Test
    void parseCommand_delete_validIndex_returnsDeleteCommand() throws Exception {
        Command c = Parser.parseCommand("delete 1");
        assertInstanceOf(DeleteCommand.class, c);
    }

    @Test
    void parseCommand_delete_noIndex_throws() {
        assertThrows(InvalidTaskException.class, () -> Parser.parseCommand("delete"));
        assertThrows(InvalidTaskException.class, () -> Parser.parseCommand("delete   "));
    }

    @Test
    void parseCommand_delete_zeroIndex_returnsDeleteCommand() throws Exception {
        // Note: zero gets converted to -1, which should be handled by the command execution
        Command c = Parser.parseCommand("delete 0");
        assertInstanceOf(DeleteCommand.class, c);
    }

    @Test
    void parseCommand_delete_withSpaces_success() throws Exception {
        Command c = Parser.parseCommand("   delete   5   ");
        assertInstanceOf(DeleteCommand.class, c);
    }

    // ========== MARK/UNMARK COMMAND TESTS ==========
    @Test
    void parseCommand_mark_validIndex_returnsMarkCommand() throws Exception {
        Command c = Parser.parseCommand("mark 1");
        assertInstanceOf(MarkCommand.class, c);
    }

    @Test
    void parseCommand_mark_noIndex_throws() {
        assertThrows(InvalidTaskException.class, () -> Parser.parseCommand("mark"));
        assertThrows(InvalidTaskException.class, () -> Parser.parseCommand("mark   "));
    }

    @Test
    void parseCommand_mark_invalidIndex_throws() {
        assertThrows(InvalidTaskException.class, () -> Parser.parseCommand("mark abc"));
        assertThrows(InvalidTaskException.class, () -> Parser.parseCommand("mark 1.5"));
    }

    @Test
    void parseCommand_unmark_validIndex_returnsUnmarkCommand() throws Exception {
        Command c = Parser.parseCommand("unmark 1");
        assertInstanceOf(UnmarkCommand.class, c);
    }

    @Test
    void parseCommand_unmark_noIndex_throws() {
        assertThrows(InvalidTaskException.class, () -> Parser.parseCommand("unmark"));
        assertThrows(InvalidTaskException.class, () -> Parser.parseCommand("unmark   "));
    }

    @Test
    void parseCommand_unmark_invalidIndex_throws() {
        assertThrows(InvalidTaskException.class, () -> Parser.parseCommand("unmark abc"));
    }

    // ========== FIND COMMAND TESTS ==========
    @Test
    void parseCommand_find_validKeyword_returnsFindCommand() throws Exception {
        Command c = Parser.parseCommand("find book");
        assertInstanceOf(FindCommand.class, c);
    }

    @Test
    void parseCommand_find_noKeyword_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("find"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("find   "));
    }

    @Test
    void parseCommand_find_multipleWords_success() throws Exception {
        Command c = Parser.parseCommand("find read multiple books");
        assertInstanceOf(FindCommand.class, c);
    }

    @Test
    void parseCommand_find_specialCharacters_success() throws Exception {
        Command c = Parser.parseCommand("find @#$%^&*()");
        assertInstanceOf(FindCommand.class, c);
    }

    @Test
    void parseCommand_find_withSpaces_success() throws Exception {
        Command c = Parser.parseCommand("   find   book   ");
        assertInstanceOf(FindCommand.class, c);
    }

    // ========== SORT COMMAND TESTS ==========
    @Test
    void parseCommand_sort_noParameter_returnsSortCommand() throws Exception {
        Command c = Parser.parseCommand("sort");
        assertInstanceOf(SortCommand.class, c);
    }

    @Test
    void parseCommand_sort_asc_returnsSortCommand() throws Exception {
        Command c = Parser.parseCommand("sort asc");
        assertInstanceOf(SortCommand.class, c);
    }

    @Test
    void parseCommand_sort_desc_returnsSortCommand() throws Exception {
        Command c = Parser.parseCommand("sort desc");
        assertInstanceOf(SortCommand.class, c);
    }

    @Test
    void parseCommand_sort_invalidParameter_throws() {
        assertThrows(MomoException.class, () -> Parser.parseCommand("sort invalid"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("sort ascending"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("sort descending"));
        assertThrows(MomoException.class, () -> Parser.parseCommand("sort 123"));
    }

    @Test
    void parseCommand_sort_withSpaces_success() throws Exception {
        Command c = Parser.parseCommand("   sort   asc   ");
        assertInstanceOf(SortCommand.class, c);
    }

    // ========== GENERAL EDGE CASES ==========

    @Test
    void parseCommand_unknownCommand_throws() {
        assertThrows(InvalidCommandException.class, () -> Parser.parseCommand("unknown"));
        assertThrows(InvalidCommandException.class, () -> Parser.parseCommand("xyz"));
        assertThrows(InvalidCommandException.class, () -> Parser.parseCommand("123"));
        assertThrows(InvalidCommandException.class, () -> Parser.parseCommand("@#$%"));
    }

    @Test
    void parseCommand_caseMatters_throws() {
        assertThrows(InvalidCommandException.class, () -> Parser.parseCommand("TODO read"));
        assertThrows(InvalidCommandException.class, () -> Parser.parseCommand("Deadline work /by 12/31/2025"));
        assertThrows(InvalidCommandException.class, () -> Parser.parseCommand("LIST"));
        assertThrows(InvalidCommandException.class, () -> Parser.parseCommand("BYE"));
    }

    @Test
    void parseCommand_commandWithExtraParameters_success() throws Exception {
        // Commands that don't expect parameters should still work if extra text is provided
        Command c = Parser.parseCommand("list extra parameters that should be ignored");
        assertInstanceOf(ListCommand.class, c);
    }

    @Test
    void parseCommand_commandWithExtraParameters_bye_success() throws Exception {
        Command c = Parser.parseCommand("bye extra text");
        assertInstanceOf(ByeCommand.class, c);
    }

    // ========== BOUNDARY CONDITIONS ==========
    @Test
    void parseCommand_veryLongDescription_success() throws Exception {
        String longDesc = "a".repeat(1000);
        Command c = Parser.parseCommand("todo " + longDesc);
        assertInstanceOf(AddCommand.class, c);
    }

    @Test
    void parseCommand_unicodeCharacters_success() throws Exception {
        Command c = Parser.parseCommand("todo 读书 📚 学习");
        assertInstanceOf(AddCommand.class, c);
    }

    @Test
    void parseCommand_deadline_dateAtYearBoundary_success() throws Exception {
        Command c = Parser.parseCommand("deadline work /by 01/01/2025");
        assertInstanceOf(AddCommand.class, c);

        Command c2 = Parser.parseCommand("deadline work /by 12/31/2025");
        assertInstanceOf(AddCommand.class, c2);
    }

    @Test
    void parseCommand_maxIntegerIndex_success() throws Exception {
        Command c = Parser.parseCommand("delete 2147483647"); // Integer.MAX_VALUE
        assertInstanceOf(DeleteCommand.class, c);
    }

    @Test
    void parseCommand_indexOverflow_throws() {
        assertThrows(InvalidTaskException.class, () -> Parser.parseCommand("delete 2147483648")); // Integer.MAX_VALUE + 1
    }
}