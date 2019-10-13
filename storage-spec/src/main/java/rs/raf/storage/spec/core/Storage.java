package rs.raf.storage.spec.core;

import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.exception.AuthenticationException;
import rs.raf.storage.spec.registry.Registry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Storage {

    private static volatile User activeUser;

    private Registry registry;
    private Directory root;
    private User owner;
    private List<User> users;
    private List<String> forbiddenTypes;

    public Storage() {
        registry = new Registry();
        users = new LinkedList<>();
        forbiddenTypes = new LinkedList<>();
    }

    public void connect(User user) throws AuthenticationException {
        registry.load(user, this);

        activeUser = user;

        onConnect();
    }

    public void disconnect(User user) throws AuthenticationException {
        registry.save(user, this);

        activeUser = null;

        onDisconnect();
    }

    protected abstract void onConnect();
    protected abstract void onDisconnect();

    public synchronized static User getActiveUser() {
        return activeUser;
    }

    public final Directory getRoot() {
        return root;
    }

    public void setRoot(Directory root) {
        this.root = root;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public final void addUser(User user) {
        users.add(user);
    }

    public final void removeUser(User user) {
        users.remove(user);
    }

    public final List<User> getUsers() {
        return new LinkedList<>(users);
    }

    public final void forbidType(String type) {
        forbiddenTypes.add(type);
    }

    public final void allowType(String type) {
        forbiddenTypes.remove(type);
    }

    public final List<String> getForbiddenTypes() {
        return new LinkedList<>(forbiddenTypes);
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
