package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.res.Res;

/**
 * Notifies the caller that there was a problem with reading from or writing to registry file.
 */
public final class RegistryException extends StorageException {

    public RegistryException() {
        super(Res.Error.REGISTRY);
    }
}
