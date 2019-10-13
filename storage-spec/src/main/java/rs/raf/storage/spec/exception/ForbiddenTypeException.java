package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.res.Res;

// TODO Use this exception
public final class ForbiddenTypeException extends StorageException {

    public ForbiddenTypeException(File file) {
        super(Res.Error.FORBIDDEN_TYPE
                .replace(Res.Wildcard.FILE, file.getPath()));
    }
}
