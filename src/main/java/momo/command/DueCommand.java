package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;
import momo.task.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class DueCommand extends Command{
    private final LocalDate dueDate;

    public DueCommand(LocalDate dateTime) {
        this.dueDate = dateTime;
    }


    @Override
    public void execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        TaskList dueTasks = new TaskList(new ArrayList<>());
        for (Task task : taskList.getTasks()) {
            if (task instanceof Deadline deadline) {
                if (deadline.getDeadline().toLocalDate().equals(dueDate)) {
                    dueTasks.add(deadline);
                }
            } else if (task instanceof Events event) {
                if (event.getStart().toLocalDate().equals(dueDate)) {
                    dueTasks.add(event);
                }
            }
        }
        ui.printList(dueTasks);
    }
}
