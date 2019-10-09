package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.res.Res;
import rs.raf.storage.spec.core.File;

public abstract class PrivilegeException extends StorageException {

    public PrivilegeException(User user, File file) {
        super(Res.ERROR_PRIVILEGE
                .replace(Res.WILDCARD_USER, user.getName())
                .replace(Res.WILDCARD_FILE, file.getPath()));
    }
}
