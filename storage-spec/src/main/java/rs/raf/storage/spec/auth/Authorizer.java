package rs.raf.storage.spec.auth;

import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.PrivilegeException;

public final class Authorizer {

    public void checkRead(User user, File file) throws PrivilegeException {
        if(!user.getPrivilege(file).canRead()) {
            throw new PrivilegeException(user, file);
        }
    }

    public void checkWrite(User user, File file) throws PrivilegeException {
        if(!user.getPrivilege(file).canWrite()) {
            throw new PrivilegeException(user, file);
        }
    }

    public void checkDelete(User user, File file) throws PrivilegeException {
        if(!user.getPrivilege(file).canDelete()) {
            throw new PrivilegeException(user, file);
        }
    }

    public void checkManage(User user, Storage storage) throws PrivilegeException {
        if(!storage.getOwner().equals(user)) {
            throw new PrivilegeException(user);
        }
    }
}
