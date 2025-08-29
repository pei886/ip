public abstract class Command {
    public abstract void execute(Storage storage, TextUi ui, TaskList taskList) throws MomoException;
    public boolean isExit() {
        return false;
    }
}