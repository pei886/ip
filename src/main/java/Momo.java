import java.util.ArrayList;
import java.util.Scanner;

public class Momo {
    protected static String greet = "Hello! I'm Momo\nWhat can I do for you?";
    protected static String bye = "Bye! See you next time!";
    private static final String DATA_FILE = "./data/momo.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        formatOutput(greet);

        String input;
        while (true) {
            input = sc.nextLine();
            try {
                if (input.equalsIgnoreCase("bye")) {
                    formatOutput(bye);
                    break;
                } else if (input.equalsIgnoreCase("list")) {
                    printList(list);
                } else if (input.startsWith("mark")) {
                    int taskIndex = parseTaskIndex(input.substring(5), list.size());
                    list.get(taskIndex).markAsDone();
                    formatOutput("Nice! I've marked this task as done: \n" + list.get(taskIndex));
                } else if (input.startsWith("unmark")) {
                    int taskIndex = parseTaskIndex(input.substring(7), list.size());
                    list.get(taskIndex).markAsNotDone();
                    formatOutput("OK, I've marked this task as not done yet: \n" + list.get(taskIndex));
                } else if (input.startsWith("delete")) {
                    int taskIndex = parseTaskIndex(input.substring(7), list.size());
                    Task removed = list.remove(taskIndex);
                    formatOutput("Ok! I've removed this task:\n  " + removed.toString()
                            + "\nNow you have " + list.size() + " task(s) in the list.");
                } else {
                    Task newTask = handleTaskCreation(input);
                    list.add(newTask);
                    printAddedTask(newTask, list);
                }
            } catch (MomoException e) {
                formatOutput(e.getMessage());
            }
        }

        sc.close();
    }

    private static Task handleTaskCreation(String input) throws MomoException {
        Task newTask;
        if (input.startsWith("todo ")) {
            String desc = input.substring(5).trim();
            if (desc.isEmpty()) throw new MomoException("The description of a todo cannot be empty!");
            newTask = new ToDo(desc);

        } else if (input.startsWith("deadline ")) {
            String[] parts = input.substring(9).split(" /by ");
            if (parts.length < 2) throw new MomoException("Deadline must include a description and /by time!");
            newTask = new Deadline(parts[0], parts[1]);

        } else if (input.startsWith("event ")) {
            String[] parts = input.substring(6).split(" /from ");
            if (parts.length < 2) throw new MomoException("Event must include /from and /to!");
            String desc = parts[0];
            String[] period = parts[1].split(" /to ");
            if (period.length < 2) throw new MomoException("Event must include both start and end times!");
            newTask = new Events(desc, period[0], period[1]);

        } else {
            throw new InvalidCommandException();
        }
        return newTask;
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

    private static void printAddedTask(Task task, ArrayList<Task> list) {
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
