package momo;

import momo.exceptions.InvalidTaskException;
import momo.task.ToDo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    TaskList tasks;

    @BeforeEach
    void setup() {
        tasks = new TaskList(new ArrayList<>());
    }

    @Test
    void add_increasesSize() {
        tasks.add(new ToDo("Read book"));
        assertEquals(1, tasks.size());
    }

    @Test
    void getTask_validIndex_returnsCorrectTask() throws Exception {
        ToDo todo = new ToDo("Read book");
        tasks.add(todo);
        assertEquals(todo, tasks.getTask(0));
    }

    @Test
    void getTask_invalidIndex_throwsException() {
        assertThrows(InvalidTaskException.class, () -> tasks.getTask(0));
    }

    @Test
    void delete_removesCorrectTask() throws Exception {
        ToDo todo = new ToDo("Stay alive");
        tasks.add(todo);
        assertEquals(todo, tasks.delete(0));
        assertEquals(0, tasks.size());
    }

    @Test
    void markTask_marksAsDone() throws Exception {
        ToDo todo = new ToDo("Sleep");
        tasks.add(todo);
        tasks.markTask(0);
        assertTrue(tasks.getTask(0).isDone());
    }

    @Test
    void unmarkTask_marksAsNotDone() throws Exception {
        ToDo todo = new ToDo("Eat");
        tasks.add(todo);
        tasks.markTask(0);
        tasks.unmarkTask(0);
        assertFalse(tasks.getTask(0).isDone());
    }
}
