package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;
import momo.task.Deadline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a command that sorts all {@link Deadline} tasks in a {@link TaskList}
 * either in ascending or descending chronological order.
 *
 * <p>This command only affects tasks of type {@link Deadline}. Other task types
 * are ignored. The sorted tasks are then displayed to the user via {@link TextUi}.
 */
public class SortCommand extends Command {

    /** Determines whether the sorting should be in ascending order. */
    private final boolean ascending;


    /**
     * Constructs a SortCommand with the specified sorting order.
     *
     * @param ascending {@code true} for ascending order (earliest deadlines first),
     *                  {@code false} for descending order (latest deadlines first).
     */
    public SortCommand(boolean ascending) {
        this.ascending = ascending;
    }

    /**
     * Executes the SortCommand by sorting all {@link Deadline} tasks in the provided {@link TaskList}.
     *
     * <p>The tasks are sorted chronologically by their deadlines. The sorted tasks
     * are then formatted and displayed using {@link TextUi}.
     *
     * @param storage  The {@link Storage} instance (not used in this command).
     * @param ui       The {@link TextUi} instance used to format and display the sorted task list.
     * @param taskList The current {@link TaskList} containing all tasks to be sorted.
     * @return A formatted string showing the deadline tasks sorted chronologically.
     * @throws MomoException Not thrown in this command, but included in the signature.
     */
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
