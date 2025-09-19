package momo;

import momo.exceptions.MomoException;
import momo.task.*;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handles persistent storage of tasks to and from the local file system.
 * The Storage class is responsible for initializing the data file,
 * saving tasks to disk, and loading tasks when the application starts.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a new Storage instance with the given file path.
     * Ensures that the storage file and its parent directories exist.
     *
     * @param filePath Path to the file used for storing tasks.
     */
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

    /**
     * Saves all tasks from the given TaskList to the storage file.
     *
     * @param list Task list to save.
     */
    public void saveTasksToFile(TaskList list) {
        try (FileWriter writer = new FileWriter(filePath)) { // auto-close
            for (Task task : list.getTasks()) {
                writer.write(taskToFileString(task) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file into a new TaskList.
     * If the file cannot be read, an empty task list is returned.
     *
     * @return Task list containing the loaded tasks.
     */
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

        } catch (IOException | MomoException e) {
            System.err.println("Error loading tasks from file: " + e.getMessage());
        }
        return tasklist;
    }

    /**
     * Converts a string representation of a task (as stored in the file) into a {@link Task} object.
     *
     * @param s The string read from storage representing a task.
     * @return A {@link Task} object corresponding to the string.
     * @throws MomoException If the string is invalid or contains unknown task type.
     */
    private Task fileStringToTask(String s) throws MomoException {
        String[] chunks = Arrays.stream(s.split("\\|"))
                .map(String::trim)
                .toArray(String[]::new);

        Task newTask = createTaskFromChunks(chunks);

        if ("1".equals(chunks[1])) {
            newTask.markAsDone();
        }

        return newTask;
    }

    /**
     * Creates a {@link Task} object from an array of parsed string chunks.
     *
     * @param chunks Array of strings parsed from a storage line.
     * @return A {@link Task} object corresponding to the parsed chunks.
     * @throws MomoException If the task type is unknown or chunks are invalid.
     */

    private Task createTaskFromChunks(String[] chunks) throws MomoException {
        return switch (chunks[0]) {
            case "T" -> new ToDo(chunks[2]);
            case "D" -> new Deadline(chunks[2], chunks[3]);
            case "E" -> {
                String[] period = chunks[3].split("-");
                yield new Events(chunks[2], period[0], period[1]);
            }
            default -> throw new MomoException("Unknown task type in storage: " + chunks[0]);
        };
    }


    /**
     * Converts a Task object into a string representation
     * suitable for saving to the storage file.
     *
     * @param task Task to convert.
     * @return Encoded string representing the task.
     * @throws IllegalArgumentException If the task type is unrecognized.
     */
    public String taskToFileString(Task task) {

        assert task != null : "task should not be null";

        String status = task.isDone() ? "1" : "0";
        if (task instanceof ToDo todo) {
            return String.format("T | %s | %s", status, todo.getDescription());
        } else if (task instanceof Deadline deadline) {
            return String.format("D | %s | %s | %s", status, deadline.getDescription(), deadline.getFormattedDeadline());
        } else if (task instanceof Events event) {
            return String.format("E | %s | %s | %s-%s", status, event.getDescription(), event.getFormattedStart(), event.getFormattedEnd());
        } else {
            throw new IllegalArgumentException("Unknown task type: " + task);
        }
    }

}
