public class UnmarkCommand extends Command {
    private final int indexToUnmark;

    public static final String[] RESPONSE = new String[]{"Ok... I've unmarked this task: "};

    public UnmarkCommand(int index) {
        this.indexToUnmark = index;
    }

    @Override
    public void execute(Storage storage, TextUi ui, TaskList taskList) {
        Task unmarked = taskList.unmarkTask(indexToUnmark);
        ui.showToUser(RESPONSE[0], unmarked.toString());
    }


}
