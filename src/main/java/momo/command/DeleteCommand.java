package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;
import momo.task.Task;

/**
 * Represents a command that deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int indexToDelete;

    public static final String[] RESPONSE = new String[]{
            "Ok! I've removed this task:",
            "\nNow you have ",
            " task(s) in the list."
    };

    /**
     * Constructs a {@code DeleteCommand} with the given index.
     *
     * @param index Index of the task to delete.
     */
    public DeleteCommand(int index) {
        this.indexToDelete = index;
    }

    @Override
    public String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        Task removed = taskList.delete(indexToDelete);
        return ui.showToUser(RESPONSE[0], removed.toString(), RESPONSE[1],
                taskList.size().toString(), RESPONSE[2]);
    }
}
