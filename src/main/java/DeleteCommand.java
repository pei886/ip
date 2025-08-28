public class DeleteCommand extends Command {
    private final int indexToDelete;

    public static final String[] RESPONSE = new String[]{"Ok! I've removed this task:",
            "\nNow you have ",
            " task(s) in the list."};

    public DeleteCommand(int index) {
        this.indexToDelete = index;
    }

    @Override
    public void execute(Storage storage, TextUi ui, TaskList taskList) {
        Task removed = taskList.delete(indexToDelete);
        ui.showToUser(RESPONSE[0], removed.toString(), RESPONSE[1], taskList.size().toString(), RESPONSE[2]);
    }


}
