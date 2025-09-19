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

    /**
     * Executes the DueCommand by finding all tasks in the provided {@link TaskList}
     * that are due on the specified date.
     *
     * <p>Only {@link Deadline} and {@link Events} tasks are checked. Tasks matching
     * the {@code dueDate} are collected and displayed using the {@link TextUi}.
     *
     * @param storage  The {@link Storage} instance (not used in this command).
     * @param ui       The {@link TextUi} instance used to display the list of due tasks.
     * @param taskList The current {@link TaskList} to search for tasks due on the specified date.
     * @return A formatted string listing all tasks that are due on {@code dueDate}.
     * @throws MomoException Not thrown in this command, but part of the method signature.
     */
    @Override
    public String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        TaskList dueTasks = new TaskList(new ArrayList<>());
        for (Task task : taskList.getTasks()) {
            if (task instanceof Deadline deadline) {
                boolean isDue = deadline.getDeadline().toLocalDate().equals(dueDate);
                if (isDue) {
                    dueTasks.add(deadline);
                }
            } else if (task instanceof Events event) {
                boolean isDue = event.getStart().toLocalDate().equals(dueDate);
                if (isDue) {
                    dueTasks.add(event);
                }
            }
        }
        return ui.showToUser(DUE_MESSAGE, ui.formatTaskList(dueTasks));
    }
}
