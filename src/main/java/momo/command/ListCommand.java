package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;

public class ListCommand extends Command {
    private final String LIST_MESSAGE = "Here are the tasks in your list:\n\n";
    public ListCommand() {}

    @Override
    public void execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        ui.showToUser(LIST_MESSAGE, ui.formatTaskList(taskList));
    }
}
