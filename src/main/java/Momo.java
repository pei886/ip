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
        Scanner sc = new Scanner(System.in);

        formatOutput(greet);

        String input;
        while (true) {
            input = sc.nextLine();
            try {
                if (input.equalsIgnoreCase("bye")) {
                    formatOutput(bye);
                    break;
                } else if (input.equalsIgnoreCase("list")) {
                    ui.printList(taskList);
                } else if (input.startsWith("mark")) {
                    int taskIndex = parseTaskIndex(input.substring(5), taskList.size());
                    taskList.markTask(taskIndex);
                    formatOutput("Nice! I've marked this task as done: \n" + taskList.printTask(taskIndex));
                } else if (input.startsWith("unmark")) {
                    int taskIndex = parseTaskIndex(input.substring(7), taskList.size());
                    taskList.unmarkTask(taskIndex);
                    formatOutput("OK, I've marked this task as not done yet: \n" + taskList.printTask(taskIndex));
                } else if (input.startsWith("delete")) {
                    int taskIndex = parseTaskIndex(input.substring(7), taskList.size());
                    Task removed = taskList.delete(taskIndex);
                    formatOutput("Ok! I've removed this task:\n  " + removed.toString()
                            + "\nNow you have " + taskList.size() + " task(s) in the list.");
                } else if (input.startsWith("due")) {
                    String date = input.substring(4).trim();
                    System.out.println(date);
                    printList(checkTasksInDue(taskList, date));
                } else {
                    Command newCommand = Parser.parseCommand(input);
                    newCommand.execute(storage, ui, taskList);
                }
            } catch (MomoException e) {
                formatOutput(e.getMessage());
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


    private static int parseTaskIndex(String input, int maxTasks) throws MomoException {
        try {
            int taskIndex = Integer.parseInt(input.trim()) - 1;
            if (taskIndex < 0 || taskIndex >= maxTasks) {
                throw new TaskOutOfRangeException(maxTasks);
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new InvalidTaskException();
        }
    }

    private static void printAddedTask(Task task, TaskList list) {
        String sb = "Ok! I've added this task:\n" + task.toString() + "\n" +
                "Now you have " + list.size() + " tasks in the list.";
        formatOutput(sb);
    }

    private static void printList(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(".").append(tasks.get(i).toString());
            sb.append("\n");
        }
        formatOutput(sb.toString());
    }

    private static void formatOutput(String output) {
        System.out.println("____________________________________________________________");
        String[] lines = output.split("\n");
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("____________________________________________________________");
    }
}
