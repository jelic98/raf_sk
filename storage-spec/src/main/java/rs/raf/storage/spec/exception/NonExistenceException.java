package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.res.Res;
import rs.raf.storage.spec.core.File;

// TODO Use this exception
public final class NonExistenceException extends StorageException {

    public NonExistenceException(File file) {
        super(Res.Error.NON_EXISTENCE
                .replace(Res.Wildcard.TYPE, file.getClass().getSimpleName())
                .replace(Res.Wildcard.FILE, file.getPath()));
    }
}
