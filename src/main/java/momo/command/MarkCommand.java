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

    @Override
    public void execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        Task marked = taskList.markTask(indexToMark);
        ui.showToUser(RESPONSE[0], marked.toString());
    }
}
