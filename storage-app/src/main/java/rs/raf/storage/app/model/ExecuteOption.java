package rs.raf.storage.app.model;

public abstract class ExecuteOption extends Option {

    public ExecuteOption(String title) {
        super(title);
    }

    public abstract void execute();

    @Override
    public void onSelected() {
        execute();
    }
}
