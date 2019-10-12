package rs.raf.storage.spec.auth;

import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.PrivilegeException;

public final class Authorizer {

    public Privilege getDefaultPrivilege(File file) {
        return new Privilege(file, false, false, false);
    }

    public void checkRead(User user, File file) throws PrivilegeException {
        if(user.getPrivilege(file).canRead()) {
            throw new PrivilegeException(user, file);
        }
    }

    public void checkWrite(User user, File file) throws PrivilegeException {
        if(user.getPrivilege(file).canWrite()) {
            throw new PrivilegeException(user, file);
        }
    }

    public void checkDelete(User user, File file) throws PrivilegeException {
        if(user.getPrivilege(file).canDelete()) {
            throw new PrivilegeException(user, file);
        }
    }
}
