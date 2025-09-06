package momo;

import momo.command.Command;
import momo.exceptions.MomoException;

import java.util.Scanner;

/**
 * Entry point of the Momo application.
 * Momo is a simple task manager that allows users to interact with their task list
 * through text-based commands.
 */
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

    /**
     * Application entry point.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new Momo().run();
    }

    /**
     * Runs the main application loop.
     */
    public void run() {
        ui.printGreetingMessage();
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                Command command = Parser.parseCommand(input);
                command.execute(storage, ui, taskList);
                isExit = command.isExit();
            } catch (MomoException e) {
                ui.showError(e.getMessage());
            }
            storage.saveTasksToFile(taskList);
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parseCommand(input);

            String response = command.execute(storage, ui, taskList);

            storage.saveTasksToFile(taskList);

            return response;
        } catch (MomoException e) {
            return e.getMessage();
        }
    }

}
