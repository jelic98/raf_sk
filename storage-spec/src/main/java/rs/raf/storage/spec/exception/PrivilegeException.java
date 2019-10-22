package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.res.Res;
import rs.raf.storage.spec.core.File;

/**
 * Notifies the caller that requested operation cannot be done because user does not have required privileges.
 */
public final class PrivilegeException extends StorageException {

    /**
     * Should be used if called does not have reference to {@code File}
     *      or user does not have privileges to manage other users.
     * @param user User that does not have required privileges.
     */
    public PrivilegeException(User user) {
        super(Res.Error.USER_PRIVILEGE
                .replace(Res.Wildcard.USER, user.getName()));
    }

    /**
     * Should be used if called has reference to {@code File}.
     * @param user User that does not have required privileges.
     * @param file File that required specific privileges.
     */
    public PrivilegeException(User user, File file) {
        super(Res.Error.FILE_PRIVILEGE
                .replace(Res.Wildcard.USER, user.getName())
                .replace(Res.Wildcard.FILE, file.getPath()));
    }
}
