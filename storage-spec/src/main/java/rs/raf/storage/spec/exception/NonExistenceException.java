package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.res.Res;
import rs.raf.storage.spec.core.File;

public final class NonExistenceException extends StorageException {

    public NonExistenceException(File file) {
        super(Res.ERROR_NON_EXISTENCE
                .replace(Res.WILDCARD_TYPE, file.getClass().getSimpleName())
                .replace(Res.WILDCARD_FILE, file.getPath()));
    }
}
