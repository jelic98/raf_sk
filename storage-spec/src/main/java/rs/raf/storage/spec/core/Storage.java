package rs.raf.storage.spec.core;

import rs.raf.storage.spec.auth.User;

import java.util.HashMap;
import java.util.Map;

public abstract class Storage {

    private Directory root;
    private User owner, activeUser;

    public abstract void connect();
    public abstract void disconnect();

    public final void initialize(Parameters parameters) {

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

    public static final class Parameters {

        private Map<String, String> parameters;

        public Parameters() {
            parameters = new HashMap<>();
        }

        public String get(String key) {
            return parameters.get(key);
        }

        public void set(String key, String value) {
            parameters.put(key, value);
        }
    }
}
