package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;
import momo.task.Task;

public class MarkCommand extends Command {
    private final int indexToMark;

    public static final String[] RESPONSE = new String[]{"Nice! I've marked this task as done:"};

    public MarkCommand(int index) {
        this.indexToMark = index;
    }

    @Override
    public void execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        Task marked = taskList.markTask(indexToMark);
        ui.showToUser(RESPONSE[0], marked.toString());
    }


}
