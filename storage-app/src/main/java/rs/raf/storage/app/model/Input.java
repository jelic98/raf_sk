package rs.raf.storage.app.model;

public class Input {

    private String title, value;

    public Input(String title) {
        this.title = title;
    }

    String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }
}
