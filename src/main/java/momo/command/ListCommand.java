package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;

public class ListCommand extends Command {
    public ListCommand() {}

    @Override
    public void execute(Storage storage, TextUi textUi, TaskList taskList) throws MomoException {
        textUi.printList(taskList);
    }
}
