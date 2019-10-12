package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.res.Res;

public final class DriverNotRegisteredException extends StorageException {

    public DriverNotRegisteredException() {
        super(Res.Error.DRIVER_NOT_REGISTERED);
    }
}
