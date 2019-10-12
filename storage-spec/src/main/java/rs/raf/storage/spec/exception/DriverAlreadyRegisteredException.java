package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.StorageDriver;
import rs.raf.storage.spec.res.Res;

public final class DriverAlreadyRegisteredException extends StorageException {

    public DriverAlreadyRegisteredException(StorageDriver driver) {
        super(Res.Error.DRIVER_ALREADY_REGISTERED
                .replace(Res.Wildcard.DRIVER, driver.getName()));
    }
}
