package rs.raf.storage.spec;

import rs.raf.storage.spec.exception.DriverAlreadyRegisteredException;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;

public final class StorageDriverManager {

    private static StorageDriver driver;

    public static void register(StorageDriver implementation) throws DriverAlreadyRegisteredException {
        if(driver != null) {
            throw new DriverAlreadyRegisteredException(driver);
        }

        driver = implementation;
    }

    public static StorageDriver getDriver() throws DriverNotRegisteredException {
        if(driver == null) {
            throw new DriverNotRegisteredException();
        }

        return driver;
    }
}