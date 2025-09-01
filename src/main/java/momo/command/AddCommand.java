package momo.command;

import momo.Storage;
import momo.TaskList;
import momo.TextUi;
import momo.exceptions.InvalidCommandException;
import momo.exceptions.MomoException;
import momo.task.*;

/**
 * Represents a command that adds a new task to the task list.
 */
public class AddCommand extends Command {
    private final String type;
    private final String[] args;

    /**
     * Constructs an {@code AddCommand}.
     *
     * @param type The type of task to add (todo, deadline, event).
     * @param args Arguments required to construct the task.
     */
    public AddCommand(String type, String... args) {
        this.type = type;
        this.args = args;
    }

    @Override
    public void execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException {
        try {
            Task newTask = switch (type) {
                case "todo" -> new ToDo(args[0]);
                case "deadline" -> new Deadline(args[0], args[1]);
                case "event" -> new Events(args[0], args[1], args[2]);
                default -> throw new InvalidCommandException();
            };

            taskList.add(newTask);
            ui.printAddedTask(newTask, taskList.getTasks());
        } catch (MomoException e) {
            ui.showToUser(e.getMessage());
        }
    }
}
