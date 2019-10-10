package rs.raf.storage.spec.core;

import rs.raf.storage.spec.auth.User;

public abstract class Storage {

    private Directory root;
    private User owner, activeUser;

    public abstract void connect();
    public abstract void disconnect();

    public final void initialize() {

    }

    public final User createUser(String name, String password) {
        return new User(name, password);
    }

    public final Directory getRoot() {
        return root;
    }

    public final User getOwner() {
        return owner;
    }

    public final User getActiveUser() {
        return activeUser;
    }
}
