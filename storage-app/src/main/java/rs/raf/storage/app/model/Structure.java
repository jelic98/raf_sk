package rs.raf.storage.app.model;

public abstract class Structure {

    private Question question;

    public Structure() {
        question = create();
    }

    protected abstract Question create();

    Question getQuestion() {
        return question;
    }
}
