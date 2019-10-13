package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.res.Res;

public final class RegistryException extends StorageException {

    public RegistryException() {
        super(Res.Error.REGISTRY);
    }
}
