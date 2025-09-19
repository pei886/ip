package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;

/**
 * Represents a command that exits the program.
 */
public class ByeCommand extends Command {
    protected static String BYE = "Bye! See you next time!";

    /**
     * Constructs a {@code ByeCommand}.
     */
    public ByeCommand() {
    }

    /**
     * Executes the ByeCommand, which ends the Momo application session.
     */
    @Override
    public String execute(Storage storage, TextUi textUi, TaskList taskList) throws MomoException {
        return textUi.printByeMessage();
    }

    /**
     * Indicates that this command will terminate the application.
     *
     * @return true, since ByeCommand signals the application should exit.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
