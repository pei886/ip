import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Momo {
    protected static String greet = "Hello! I'm Momo\nWhat can I do for you?";
    protected static String bye = "Bye! See you next time!";
    private static final String DATA_FILE = "./data/momo.txt";
    private final TextUi ui;
    private final TaskList taskList;
    private final Storage storage;

    public Momo() {
        this.ui = new TextUi();
        this.storage = new Storage(DATA_FILE);
        this.taskList = storage.loadTasksFromFile();

    }

    public static void main(String[] args) {
        new Momo().run();
    }

    public void run() {
        ui.printGreetingMessage();
        boolean isExit = false;
        Scanner sc = new Scanner(System.in);

        while (!isExit) {
            try {
                String input = ui.readCommand();
                Command command = Parser.parseCommand(input);
                command.execute(storage, ui, taskList);
                isExit = command.isExit();
            } catch (MomoException e) {
                ui.showToUser(e.getMessage());
            }
            storage.saveTasksToFile(taskList);
        }
        sc.close();
    }

    public static ArrayList<Task> checkTasksInDue(TaskList list, String date) throws MomoException {
        ArrayList<Task> dueTasks = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate dueDate = LocalDate.parse(date, formatter);
            for (Task task : list.getTasks()) {
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
        } catch (DateTimeParseException e) {
            throw new MomoException("Invalid date format for deadline: " + date
                    + "\n Please use the following format: MM/dd/yyyy");
        }
        return dueTasks;
    }

}
