package rs.raf.storage.app.model;

import java.util.LinkedList;
import java.util.List;

public class Question {

    private String title;
    private List<Option> options;

    public Question(String title) {
        this.title = title;

        options = new LinkedList<>();
    }

    public void addOption(Option option) {
        options.add(option);
    }
}
