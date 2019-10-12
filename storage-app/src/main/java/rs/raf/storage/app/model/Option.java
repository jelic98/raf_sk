package rs.raf.storage.app.model;

import java.util.*;

public class Option implements SelectionListener {

    private String title;
    private Question question;
    private Map<String, Input> inputs;

    public Option(String title) {
        this.title = title;

        inputs = new LinkedHashMap<>();
    }

    String getTitle() {
        return title;
    }

    boolean hasQuestion() {
        return question != null;
    }

    public Option setQuestion(Question question) {
        this.question = question;

        return this;
    }

    Question getQuestion() {
        return question;
    }

    boolean hasInputs() {
        return !inputs.isEmpty();
    }

    public Input getInput(String title) {
        return inputs.get(title.toLowerCase());
    }

    public Option addInput(Input input) {
        inputs.put(input.getTitle().toLowerCase(), input);

        return this;
    }

    Collection<Input> getInputs() {
        return inputs.values();
    }

    @Override
    public void onSelected() {

    }
}
