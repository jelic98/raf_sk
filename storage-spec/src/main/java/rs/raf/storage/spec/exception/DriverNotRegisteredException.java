package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.res.Res;

/**
 * Notifies the caller that {@link rs.raf.storage.spec.StorageDriver} needs to be registered before using it.
 *      This is done by calling {@link java.lang.Class#forName(String)} with implementation driver class canonical name.
 */
public final class DriverNotRegisteredException extends StorageException {

    public DriverNotRegisteredException() {
        super(Res.Error.DRIVER_NOT_REGISTERED);
    }
}
