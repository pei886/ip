import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class TextUi {
    protected static String GREET = "Hello! I'm Momo\nWhat can I do for you?";
    protected static String BYE = "Bye! See you next time!";
    protected static String DIVIDER = "==========================================";

    private final Scanner in;
    private final PrintStream out;

    public TextUi() {
        this(System.in, System.out);
    }

    public TextUi(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public void showToUser(String... messages) {
        out.println(DIVIDER);
        for (String message : messages) {
            out.println(message.trim());
        }
        out.println(DIVIDER);
    }

    public void showError(String message) {
        out.println(DIVIDER);
        out.println(message);
        out.println(DIVIDER);
    }

    public String readCommand() {
        return in.nextLine();
    }

    public void printGreetingMessage() {
        String[] message = new String[] {GREET};
        showToUser(message);
    }

    public void printByeMessage() {
        String[] message = new String[] {BYE};
        showToUser(message);
    }

    public void printAddedTask(Task task, ArrayList<Task> list) {
        String message = "Ok! I've added this task:\n"
                + task
                + "\nNow you have " + list.size() + " tasks in the list.";
        showToUser(message);
    }

    public void printList(TaskList list) throws MomoException {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(list.getTask(i))
                    .append("\n");
        }
        showToUser(sb.toString().trim());
    }

    private void formatOutput(String output) {
        String[] lines = output.split("\n");
        out.println(DIVIDER);
        for (String line : lines) {
            out.println(line);
        }
        out.println(DIVIDER);
    }
}
