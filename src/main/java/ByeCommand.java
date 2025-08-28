import java.awt.*;

public class ByeCommand extends Command {
    protected static String BYE = "Bye! See you next time!";

    public ByeCommand() {

    }

    @Override
    public void execute(Storage storage, TextUi textUi, TaskList taskList) {
        textUi.printByeMessage();
    }

}
