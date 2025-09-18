package momo;

import momo.exceptions.MomoException;
import momo.task.Task;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Handles all interactions with the user through text-based input and output.
 * Provides methods to display messages, errors, task lists, and prompts.
 */
public class TextUi {
    protected static String GREET = "Hello! I'm Momo\nWhat can I do for you?";
    protected static String BYE = "Bye! See you next time!";

    private final Scanner in;
    private final PrintStream out;

    /**
     * Creates a TextUi with default input and output streams.
     */
    public TextUi() {
        this(System.in, System.out);
    }

    /**
     * Creates a TextUi with custom input and output streams.
     *
     * @param in  Input stream for user commands.
     * @param out Output stream for displaying messages.
     */
    public TextUi(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Displays messages to the user with dividers.
     *
     * @param messages Messages to display.
     */
    public String showToUser(String... messages) {
        StringBuilder sb = new StringBuilder();

        for (String message : messages) {
            sb.append(message.trim()).append("\n");
        }

        String output = sb.toString();
        out.println(output);
        return output;
    }

    /**
     * Displays an error message to the user with dividers.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        out.println(message);
    }

    /**
     * Reads a command from the user input.
     *
     * @return The command entered by the user.
     */
    public String readCommand() {
        return in.nextLine();
    }

    /**
     * Prints the greeting message to the user.
     */
    public void printGreetingMessage() {
        String[] message = new String[] {GREET};
        showToUser(message);
    }

    /**
     * Prints the goodbye message to the user.
     */
    public String printByeMessage() {
        String[] message = new String[] {BYE};
        return showToUser(message);
    }

    /**
     * Prints confirmation after adding a task to the list.
     *
     * @param task Task that was added.
     * @param list Updated list of tasks.
     */
    public String printAddedTask(Task task, ArrayList<Task> list) {
        String message = "Ok! I've added this task:\n"
                + task
                + "\nNow you have " + list.size() + " tasks in the list.";
        return showToUser(message);
    }

    /**
     * Returns a formatted string representing all tasks in the list,
     * numbered starting from 1.
     *
     * @param list TaskList to format.
     * @return Formatted string of tasks.
     * @throws MomoException if accessing a task fails.
     */
    public String formatTaskList(TaskList list) throws MomoException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(list.getTask(i))
                    .append("\n");
        }
        return sb.toString().trim();
    }

}
