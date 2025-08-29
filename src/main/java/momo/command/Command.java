package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;

public abstract class Command {
    public abstract void execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException;
    public boolean isExit() {
        return false;
    }
}