package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;

/**
 * Represents a command that lists all tasks in the task list.
 */
public class ListCommand extends Command {

    /**
     * Constructs a {@code ListCommand}.
     */
    private final String LIST_MESSAGE = "Here are the tasks in your list:\n\n";
    public ListCommand() {}

    @Override
    public String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        return ui.showToUser(LIST_MESSAGE, ui.formatTaskList(taskList));
    }
}
