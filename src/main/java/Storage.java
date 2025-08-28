import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
        intializeFile();
    }

    private void intializeFile() {
        try {
            File file = new File(filePath);

            Files.createDirectories(file.getParentFile().toPath());

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error initializing: " + e.getMessage());
        }
    }


    public void saveTasksToFile(TaskList list) {
        try (FileWriter writer = new FileWriter(filePath)) { // auto-close
            for (Task task : list.getTasks()) {
                writer.write(taskToFileString(task) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public TaskList loadTasksFromFile() {
        TaskList tasklist = new TaskList(new ArrayList<>());
        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(reader);
            String line;

            while ((line = br.readLine()) != null) {
                tasklist.add(fileStringToTask(line));
            }
            br.close();
            return tasklist;

        } catch (IOException e) {
            System.err.println("Error loading tasks from file: " + e.getMessage());
        }
        return tasklist;
    }

    private Task fileStringToTask(String s) {
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
            Task newTask = AddCommand.handleTaskCreation(command.toString());
            if (chunks[1].equals("1")) {
                newTask.markAsDone();
            }
            return newTask;
        } catch (MomoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String taskToFileString(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof ToDo todo) {
            return String.format("T | %s | %s", status, todo.getDescription());
        } else if (task instanceof Deadline deadline) {
            return String.format("D | %s | %s | %s", status, deadline.getDescription(), deadline.getFormattedDeadline());
        } else if (task instanceof Events event) {
            return String.format("E | %s | %s | %s-%s", status, event.getDescription(), event.getFormattedStart(), event.getFormattedEnd());
        } else {
            throw new IllegalArgumentException("Unknown task type: " + task.toString());
        }
    }



}
