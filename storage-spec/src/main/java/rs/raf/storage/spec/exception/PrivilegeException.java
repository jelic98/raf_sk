package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.res.Res;
import rs.raf.storage.spec.core.File;

public final class PrivilegeException extends StorageException {

    public PrivilegeException(User user, File file) {
        super(Res.Error.PRIVILEGE
                .replace(Res.Wildcard.USER, user.getName())
                .replace(Res.Wildcard.FILE, file.getPath()));
    }
}
