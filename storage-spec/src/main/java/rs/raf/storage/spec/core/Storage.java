package rs.raf.storage.spec.core;

import rs.raf.storage.spec.auth.User;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Storage {

    private Directory root;
    private List<User> users;
    private List<String> forbiddenTypes;

    public abstract void connect();
    public abstract void disconnect();

    public Storage() {
        users = new LinkedList<>();
        forbiddenTypes = new LinkedList<>();
    }

    public final Directory getRoot() {
        return root;
    }

    public final void addUser(User user) {
        users.add(user);
    }

    public final void removeUser(User user) {
        users.remove(user);
    }

    public final void forbidType(String type) {
        forbiddenTypes.add(type);
    }

    public final void allowType(String type) {
        forbiddenTypes.remove(type);
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
