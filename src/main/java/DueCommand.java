import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class DueCommand extends Command{
    private final LocalDate dueDate;

    public DueCommand(LocalDate dateTime) {
        this.dueDate = dateTime;
    }


    @Override
    public void execute(Storage storage, TextUi ui, TaskList taskList) {
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
