package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.res.Res;
import rs.raf.storage.spec.core.File;

public final class NonExistenceException extends StorageException {

    public NonExistenceException(String path) {
        super(Res.Error.NON_EXISTENCE_PATH
                .replace(Res.Wildcard.FILE, path));
    }

    public NonExistenceException(File file) {
        super(Res.Error.NON_EXISTENCE_FILE
                .replace(Res.Wildcard.TYPE, file.getClass().getSimpleName())
                .replace(Res.Wildcard.FILE, file.getPath()));
    }
}
