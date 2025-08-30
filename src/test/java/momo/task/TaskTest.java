package momo.task;

import momo.task.Task;
import momo.task.ToDo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void markAsDone_changesStatusCorrectly() {
        Task task = new ToDo("Read book");
        task.markAsDone();
        assertTrue(task.isDone());
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    void markAsNotDone_resetsStatusCorrectly() {
        Task task = new ToDo("Read book");
        task.markAsDone();
        task.markAsNotDone();
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    void parseDateTimeInput_invalidFormat_shouldThrow() {
        assertThrows(Exception.class, () -> Task.parseDateTimeInput("not a date"));
    }
}