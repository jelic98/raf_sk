package rs.raf.storage.spec;

import rs.raf.storage.spec.exception.DriverRegisteredException;

public abstract class StorageDriverManager {

    private static StorageDriver driver;

    public static void register(StorageDriver implementation) throws DriverRegisteredException {
        if(driver != null) {
            throw new DriverRegisteredException(driver);
        }

        driver = implementation;
    }
}