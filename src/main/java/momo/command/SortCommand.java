package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;
import momo.task.Deadline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortCommand extends Command {
    private final boolean ascending;

    public SortCommand(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        List<Deadline> sortedDeadlines = taskList.getTasks().stream()
                .filter(task -> task instanceof Deadline)
                .map(task -> (Deadline)task)
                .sorted(ascending
                    ? Comparator.comparing(Deadline::getDeadline)
                    : Comparator.comparing(Deadline::getDeadline).reversed())
                .toList();

        String orderMessage = ascending ? "in ascending order:\n\n" : "in descending order:\n\n";
        return ui.showToUser("Here are the deadline tasks sorted chronologically, " + orderMessage,
                ui.formatTaskList(new TaskList(new ArrayList<>(sortedDeadlines))));
    }
}
