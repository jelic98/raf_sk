package rs.raf.storage.spec.core;

import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.auth.Authorizer;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;
import rs.raf.storage.spec.exception.PrivilegeException;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.registry.Hasher;
import rs.raf.storage.spec.registry.Registry;
import rs.raf.storage.spec.res.Res;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract representation of storage.
 */
public abstract class Storage {

    private static volatile Storage instance;

    private static Hasher hasher;

    private Registry registry;
    private Directory root;
    private String rootPath;
    private User owner, activeUser;
    private List<User> users;
    private List<String> forbiddenTypes;
    private Authorizer authorizer;
    private String uid;

    static {
        hasher = new Hasher();
    }

    protected Storage() {
        reset();
    }

    private void reset() {
        registry = new Registry();
        users = new LinkedList<>();
        forbiddenTypes = new LinkedList<>();
        authorizer = new Authorizer();
    }

    /**
     * Returns single live {@code Storage} instance.
     * @return Storage instance.
     * @throws DriverNotRegisteredException if storage driver implementation has not been registered.
     */
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

    /**
     * Template method for connecting currently active user to storage
     *      represented by unique id that located on path.
     * @param uid Storage unique identifier.
     * @param path Storage location
     * @param user Currently active user.
     * @throws StorageException if registry file could not be loaded.
     */
    public final void connect(String uid, String path, User user) throws StorageException {
        if(activeUser != null) {
            return;
        }

        this.uid = uid;
        registry.load(user, this);
        activeUser = user;
        users.add(user);

        if(owner == null) {
            owner = new User(hasher.hashUsername(user), hasher.hashPassword(user));
        }

        root = buildRoot(rootPath = path);
        
        onConnect();
    }

    /**
     * Template method for disconnecting currently active user to storage.
     * @param user Currently active user.
     * @throws StorageException if registry file could not be saved.
     */
    public final void disconnect(User user) throws StorageException {
        if(activeUser == null) {
            return;
        }

        registry.save(user, this);
        uid = null;
        activeUser = null;

        onDisconnect();
    }

    /**
     * Should be overridden in {@code Storage} implementation to handle connect operation.
     */
    protected abstract void onConnect();

    /**
     * Should be overridden in {@code Storage} implementation to handle disconnect operation.
     */
    protected abstract void onDisconnect();

    /**
     * Should be overridden in {@code Storage} implementation to handle building root from provided path.
     */
    protected abstract Directory buildRoot(String path);

    /**
     * Returns currently active user.
     */
    public final String getUid() {
        return uid;
    }

    /**
     * Returns abstract root directory representation.
     */
    public final Directory getRoot() {
        return root;
    }

    /**
     * Returns root path.
     */
    public final String getRootPath() {
        return rootPath;
    }

    /**
     * Returns registry file path.
     */
    public final String getRegistryPath() {
        return new Path(Res.Registry.PATH + getUid(), this).build();
    }

    /**
     * Returns storage owner.
     */
    public final User getOwner() {
        return owner;
    }

    /**
     * Returns currently active user.
     */
    public final User getActiveUser() {
        return activeUser;
    }

    /**
     * Adds new user to storage.
     * @param user Newly created user to be added.
     * @throws PrivilegeException if currently active user does not have privilege to manage other users.
     */
    public final void addUser(User user) throws PrivilegeException {
        if(activeUser != null) {
            authorizer.checkManage(activeUser, this);
        }

        users.add(user);
    }

    /**
     * Removes existing user from storage.
     * @param user Existing user to be removed.
     * @throws PrivilegeException if currently active user does not have privilege to manage other users.
     */
    public final void removeUser(User user) throws PrivilegeException {
        authorizer.checkManage(activeUser, this);

        users.remove(user);
    }

    /**
     * Returns list of all storage users.
     */
    public final List<User> getUsers() {
        return new LinkedList<>(users);
    }

    /**
     * Forbid file with provided type to be uploaded to storage.
     * @param type Type to be forbidden, i.e., file extension.
     */
    public final void forbidType(String type) {
        forbiddenTypes.add(type);
    }

    /**
     * Allow file with provided type to be uploaded to storage.
     * @param type Type to be allowed, i.e., file extension.
     */
    public final void allowType(String type) {
        forbiddenTypes.remove(type);
    }

    /**
     *
     * @return List of forbidden types, i.e., file extensions.
     */
    public final List<String> getForbiddenTypes() {
        return new LinkedList<>(forbiddenTypes);
    }
}
