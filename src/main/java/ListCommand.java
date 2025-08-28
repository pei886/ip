public class ListCommand extends Command {
    public ListCommand() {}

    @Override
    public void execute(Storage storage, TextUi textUi, TaskList taskList) {
        textUi.printList(taskList);
    }
}
