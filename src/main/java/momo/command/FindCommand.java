package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.MomoException;
import momo.task.Task;

import java.util.ArrayList;

/**
 * Represents a command that finds tasks containing a keyword in their description.
 */
public class FindCommand extends Command {
    private final String keyword;
    private final String FIND_MESSAGE = "Here are the matching tasks in your list:\n\n";

    /**
     * Constructs a {@code FindCommand} with the specified keyword.
     *
     * @param keyword Keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the FindCommand by searching for tasks in the provided {@link TaskList}
     * that contain the specified keyword in their description.
     *
     * <p>The search is case-insensitive. All tasks whose descriptions contain
     * the {@code keyword} are collected into a new {@link TaskList} and displayed
     * via the {@link TextUi}.
     *
     * @param storage  The {@link Storage} instance (not used in this command).
     * @param ui       The {@link TextUi} instance used to display search results.
     * @param taskList The current {@link TaskList} to search for matching tasks.
     * @return A formatted string showing all tasks that contain the keyword,
     *         or a message indicating no tasks matched.
     * @throws MomoException Not thrown in this command, but part of the method signature.
     */
    @Override
    public String execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        TaskList matchedTasks = new TaskList(new ArrayList<>());

        for (Task task : taskList.getTasks()) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchedTasks.add(task);
            }
        }

        if (matchedTasks.size() == 0) {
            return ui.showToUser("No tasks found containing: \"" + keyword + "\"");
        } else {
            return ui.showToUser(FIND_MESSAGE, ui.formatTaskList(taskList));
        }
    }
}
