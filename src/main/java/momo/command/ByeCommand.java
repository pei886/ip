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

    @Override
    public void execute(Storage storage, TextUi textUi, TaskList taskList) throws MomoException {
        textUi.printByeMessage();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
