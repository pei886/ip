package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;

/**
 * Represents an abstract command to be executed by Momo.
 */
public abstract class Command {
    /**
     * Executes the command with the given storage, UI, and task list.
     *
     * @param storage Storage handler for saving/loading tasks.
     * @param ui User interface for displaying messages.
     * @param taskList Task list to operate on.
     * @throws MomoException If an error occurs during execution.
     */
    public abstract String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException;

    /**
     * Returns whether this command causes Momo to exit.
     *
     * @return {@code true} if the command exits, {@code false} otherwise.
     */
    public boolean isExit() {
        return false;
    }
}