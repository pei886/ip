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

    /**
     * Constructs a {@code FindCommand} with the specified keyword.
     *
     * @param keyword Keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        TaskList matchedTasks = new TaskList(new ArrayList<>());

        for (Task task : taskList.getTasks()) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchedTasks.add(task);
            }
        }

        if (matchedTasks.size() == 0) {
            ui.showToUser("No tasks found containing: \"" + keyword + "\"");
        } else {
            ui.showToUser("Here are the matching tasks in your list:");
            ui.printList(matchedTasks);
        }
    }
}
