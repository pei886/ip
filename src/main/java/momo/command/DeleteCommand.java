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

    /**
     * Executes the DeleteCommand by removing a task from the provided {@link TaskList}.
     *
     * <p>After deletion, a message is returned via the {@link TextUi} showing:
     * <ul>
     *     <li>The task that was removed</li>
     *     <li>The updated number of tasks in the list</li>
     * </ul>
     *
     * @param storage  The {@link Storage} instance (can be used for saving changes if needed).
     * @param ui       The {@link TextUi} instance used to display messages to the user.
     * @param taskList The current {@link TaskList} from which the task will be deleted.
     * @return A string message confirming deletion of the task.
     * @throws MomoException If the task cannot be deleted (e.g., invalid index).
     */
    @Override
    public String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        Task removed = taskList.delete(indexToDelete);
        return ui.showToUser(
                RESPONSE[0],
                removed.toString(),
                RESPONSE[1],
                taskList.size().toString(),
                RESPONSE[2]
        );
    }
}
