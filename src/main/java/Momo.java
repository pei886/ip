import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Momo {
    protected static String greet = "Hello! I'm Momo\nWhat can I do for you?";
    protected static String bye = "Bye! See you next time!";
    private static final String DATA_FILE = "./data/momo.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> list = loadTasksFromFile();

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
            saveTasksToFile(list);
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

    private static void saveTasksToFile(ArrayList<Task> list) {
        try {
            //Create directory if it does not exist
            Files.createDirectories(Paths.get("./data"));

            FileWriter writer = new FileWriter(DATA_FILE);
            for (Task task : list) {
                writer.write(taskToFileString(task) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    private static ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> list = new ArrayList<>();
        try {
            FileReader reader = new FileReader(DATA_FILE);
            BufferedReader br = new BufferedReader(reader);
            String line;

            while ((line = br.readLine()) != null) {
                list.add(fileStringToTask(line));
            }
            br.close();
            return list;

        } catch (IOException e) {
            System.err.println("Error loading tasks from file: " + e.getMessage());
        }
        return list;
    }

    private static Task fileStringToTask(String s) {
        try {
            String[] chunks = Arrays.stream(s.split("\\|"))
                    .map(String::trim)
                    .toArray(String[]::new);
            StringBuilder command = new StringBuilder();
            switch (chunks[0]) {
                case "T":
                    command.append("todo ").append(chunks[2]);
                    break;
                case "D":
                    command.append("deadline ").append(chunks[2]);
                    command.append(" /by ").append(chunks[3]);
                    break;
                case "E":
                    String[] period = chunks[3].split("-");
                    command.append("event ").append(chunks[2]);
                    command.append(" /from ").append(period[0]);
                    command.append(" /to ").append(period[1]);
                    break;
            }
            Task newTask = handleTaskCreation(command.toString());
            if (chunks[1].equals("1")) {
                newTask.markAsDone();
            }
            return newTask;
        } catch (MomoException e) {
            formatOutput(e.getMessage());
            return null;
        }
    }

    public static String taskToFileString(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof ToDo todo) {
            return String.format("T | %s | %s", status, todo.getDescription());
        } else if (task instanceof Deadline deadline) {
            return String.format("D | %s | %s | %s", status, deadline.getDescription(), deadline.getDeadline());
        } else if (task instanceof Events event) {
            return String.format("E | %s | %s | %s-%s", status, event.getDescription(), event.getStart(), event.getEnd());
        } else {
            throw new IllegalArgumentException("Unknown task type: " + task.toString());
        }
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
