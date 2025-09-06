package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;
import momo.task.*;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents a command that lists tasks due on a specific date.
 */
public class DueCommand extends Command {
    private final LocalDate dueDate;
    private final String DUE_MESSAGE = "Here are your tasks in due:\n\n";

    /**
     * Constructs a {@code DueCommand} with the given date.
     *
     * @param dateTime The due date to check tasks against.
     */
    public DueCommand(LocalDate dateTime) {
        this.dueDate = dateTime;
    }

    @Override
    public String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        TaskList dueTasks = new TaskList(new ArrayList<>());
        for (Task task : taskList.getTasks()) {
            if (task instanceof Deadline deadline) {
                if (deadline.getDeadline().toLocalDate().equals(dueDate)) {
                    dueTasks.add(deadline);
                }
            } else if (task instanceof Events event) {
                if (event.getStart().toLocalDate().equals(dueDate)) {
                    dueTasks.add(event);
                }
            }
        }
        return ui.showToUser(DUE_MESSAGE, ui.formatTaskList(taskList));
    }
}
