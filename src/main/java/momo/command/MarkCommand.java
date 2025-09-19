package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;
import momo.task.Task;

/**
 * Represents a command that marks a task as completed.
 */
public class MarkCommand extends Command {
    private final int indexToMark;

    public static final String[] RESPONSE = new String[]{
            "Nice! I've marked this task as done:"
    };

    /**
     * Constructs a {@code MarkCommand} with the given index.
     *
     * @param index Index of the task to mark as completed.
     */
    public MarkCommand(int index) {
        this.indexToMark = index;
    }

    /**
     * Executes the MarkCommand by marking a task in the provided {@link TaskList} as done.
     *
     * <p>The task to mark is identified by its index. After marking, a message is
     * returned via the {@link TextUi} showing which task was marked.
     *
     * @param storage  The {@link Storage} instance (not used in this command).
     * @param ui       The {@link TextUi} instance used to display the confirmation message.
     * @param taskList The current {@link TaskList} containing the task to be marked.
     * @return A formatted string confirming the task has been marked as done.
     * @throws MomoException If the task index is invalid or marking fails.
     */
    @Override
    public String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        Task marked = taskList.markTask(indexToMark);
        return ui.showToUser(RESPONSE[0], marked.toString());
    }
}
