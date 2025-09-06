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

    @Override
    public String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        Task unmarked = taskList.unmarkTask(indexToUnmark);
        return ui.showToUser(RESPONSE[0], unmarked.toString());
    }
}
