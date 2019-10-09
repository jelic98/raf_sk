package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.res.Res;
import rs.raf.storage.spec.core.File;

public abstract class NonExistenceException extends StorageException {

    public NonExistenceException(File file) {
        super(Res.ERROR_PRIVILEGE
                // TODO Does getName() actually return classname of superclass
                .replace(Res.WILDCARD_TYPE, file.getClass().getName())
                .replace(Res.WILDCARD_FILE, file.getPath()));
    }
}
