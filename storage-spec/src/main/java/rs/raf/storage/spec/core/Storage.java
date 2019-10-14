package rs.raf.storage.spec.core;

import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.auth.Authorizer;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;
import rs.raf.storage.spec.exception.PrivilegeException;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.registry.Registry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Storage {

    private static volatile Storage instance;

    private Registry registry;
    private Directory root;
    private User owner, activeUser;
    private List<User> users;
    private List<String> forbiddenTypes;
    private Authorizer authorizer;
    private String uid;

    private Storage() {
        reset();
    }

    private void reset() {
        registry = new Registry();
        users = new LinkedList<>();
        forbiddenTypes = new LinkedList<>();
        authorizer = new Authorizer();
    }

    public static Storage instance() throws DriverNotRegisteredException {
        if(instance == null) {
            Storage.synchronize();
        }

        return instance;
    }

    private static synchronized void synchronize() throws DriverNotRegisteredException {
        if(instance == null) {
            instance = StorageDriverManager.getDriver().getStorage();
        }
    }

    public final void connect(String uid, String path, User user) throws StorageException {
        this.uid = uid;

        setRoot(buildRoot(path));

        registry.load(user, this);

        activeUser = user;

        onConnect();
    }

    public final void disconnect(User user) throws StorageException {
        uid = null;

        registry.save(user, this);

        activeUser = null;

        onDisconnect();
    }

    protected abstract void onConnect();
    protected abstract void onDisconnect();
    protected abstract Directory buildRoot(String path);

    public final String getUid() {
        return uid;
    }

    public final Directory getRoot() {
        return root;
    }

    public final void setRoot(Directory root) {
        this.root = root;
    }

    public final User getOwner() {
        return owner;
    }

    public final User getActiveUser() {
        return activeUser;
    }

    public final void addUser(User user) throws PrivilegeException {
        authorizer.checkManage(activeUser, this);

        users.add(user);
    }

    public final void removeUser(User user) throws PrivilegeException {
        authorizer.checkManage(activeUser, this);

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
