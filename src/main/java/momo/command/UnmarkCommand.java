package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;
import momo.task.Task;

/**
 * Represents a command that unmarks a task (marks it as not completed).
 */
public class UnmarkCommand extends Command {
    private final int indexToUnmark;

    public static final String[] RESPONSE = new String[]{
            "Ok... I've unmarked this task: "
    };

    /**
     * Constructs an {@code UnmarkCommand} with the given index.
     *
     * @param index Index of the task to unmark.
     */
    public UnmarkCommand(int index) {
        this.indexToUnmark = index;
    }

    /**
     * Executes the UnmarkCommand by marking a task in the provided {@link TaskList} as not done.
     *
     * <p>The task to unmark is identified by its index. After unmarking, a message is
     * returned via the {@link TextUi} showing which task was unmarked.
     *
     * @param storage  The {@link Storage} instance (not used in this command).
     * @param ui       The {@link TextUi} instance used to display the confirmation message.
     * @param taskList The current {@link TaskList} containing the task to be unmarked.
     * @return A formatted string confirming the task has been marked as not done.
     * @throws MomoException If the task index is invalid or unmarking fails.
     */
    @Override
    public String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        Task unmarked = taskList.unmarkTask(indexToUnmark);
        return ui.showToUser(RESPONSE[0], unmarked.toString());
    }
}
