package momo;

import momo.command.Command;
import momo.exceptions.MomoException;

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
                ui.showError(e.getMessage());
            }
            storage.saveTasksToFile(taskList);
        }
        sc.close();
    }
}
