package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.StorageDriver;
import rs.raf.storage.spec.res.Res;

public final class DriverRegisteredException extends StorageException {

    public DriverRegisteredException(StorageDriver driver) {
        super(Res.ERROR_DRIVER_REGISTERED
                .replace(Res.WILDCARD_FILE, driver.getName()));
    }
}
