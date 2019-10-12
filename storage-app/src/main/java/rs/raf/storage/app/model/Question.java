package rs.raf.storage.app.model;

import java.util.LinkedList;
import java.util.List;

public class Question {

    private String title;
    private Question parent;
    private List<Option> options;

    public Question(String title) {
        this.title = title;

        options = new LinkedList<>();
    }

    String getTitle() {
        return title;
    }

    boolean hasParent() {
        return parent != null;
    }

    Question getParent() {
        return parent;
    }

    void setParent(Question parent) {
        this.parent = parent;
    }

    List<Option> getOptions() {
        return new LinkedList<>(options);
    }

    public Question addOption(Option option) {
        options.add(option);

        if(option.hasQuestion()) {
            option.getQuestion().setParent(this);
        }

        return this;
    }
}
