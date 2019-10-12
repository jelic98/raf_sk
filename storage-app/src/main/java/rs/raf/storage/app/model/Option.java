package rs.raf.storage.app.model;

public class Option implements SelectionListener {

    private String title;
    private Question question;

    public Option(String title) {
        this.title = title;
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

    @Override
    public void onSelected() {

    }
}
