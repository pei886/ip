public abstract class Command {
    public abstract void execute(Storage storage, TextUi ui, TaskList taskList);
    public boolean isExit() {
        return false;
    }
}