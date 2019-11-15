package rs.raf.storage.spec.auth;

import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.PrivilegeException;
import rs.raf.storage.spec.registry.Hasher;

public final class Authorizer {

    private static Hasher hasher;

    static {
        hasher = new Hasher();
    }

    /**
     * Check if provided user can read from provided file.
     * @param user User for which privilege is checked.
     * @param file File for which privilege is checked.
     * @throws PrivilegeException if user does not have read privilege on provided file.
     */
    public void checkRead(User user, File file) throws PrivilegeException {
        User hashedUser = new User(hasher.hashUsername(user), hasher.hashPassword(user));
        hashedUser.setPrivileges(user.getPrivileges());

        if(!hashedUser.getPrivilege(file).canRead()) {
            throw new PrivilegeException(user, file);
        }
    }

    /**
     * Check if provided user can write to provided file.
     * @param user User for which privilege is checked.
     * @param file File for which privilege is checked.
     * @throws PrivilegeException if user does not have write privilege on provided file.
     */
    public void checkWrite(User user, File file) throws PrivilegeException {
        User hashedUser = new User(hasher.hashUsername(user), hasher.hashPassword(user));
        hashedUser.setPrivileges(user.getPrivileges());

        if(!hashedUser.getPrivilege(file).canWrite()) {
            throw new PrivilegeException(user, file);
        }
    }

    /**
     * Check if provided user can delete provided file.
     * @param user User for which privilege is checked.
     * @param file File for which privilege is checked.
     * @throws PrivilegeException if user does not have delete privilege on provided file.
     */
    public void checkDelete(User user, File file) throws PrivilegeException {
        User hashedUser = new User(hasher.hashUsername(user), hasher.hashPassword(user));
        hashedUser.setPrivileges(user.getPrivileges());

        if(!hashedUser.getPrivilege(file).canDelete()) {
            throw new PrivilegeException(user, file);
        }
    }

    /**
     * Check if provided user can manage other users from provided file. Storage owner has this privilege by default.
     * @param user User for which privilege is checked.
     * @param storage Storage for which privilege is checked.
     * @throws PrivilegeException if user does not have manage privilege on provided storage.
     */
    public void checkManage(User user, Storage storage) throws PrivilegeException {
        User hashedUser = new User(hasher.hashUsername(user), hasher.hashPassword(user));
        hashedUser.setPrivileges(user.getPrivileges());

        if(storage.getOwner() != null && !storage.getOwner().equals(hashedUser)) {
            throw new PrivilegeException(user);
        }
    }
}
