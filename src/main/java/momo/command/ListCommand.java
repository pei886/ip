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
    public ListCommand() {}

    @Override
    public void execute(Storage storage, TextUi textUi, TaskList taskList) throws MomoException {
        textUi.printList(taskList);
    }
}
