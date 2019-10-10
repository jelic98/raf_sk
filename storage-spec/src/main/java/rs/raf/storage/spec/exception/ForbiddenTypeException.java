package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.res.Res;

public final class ForbiddenTypeException extends StorageException {

    public ForbiddenTypeException(File file) {
        super(Res.ERROR_FORBIDDEN_TYPE
                .replace(Res.WILDCARD_FILE, file.getPath()));
    }
}
