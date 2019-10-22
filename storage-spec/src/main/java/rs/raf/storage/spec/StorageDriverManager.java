package rs.raf.storage.spec;

import rs.raf.storage.spec.exception.DriverAlreadyRegisteredException;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;

/**
 * Holds {@link rs.raf.storage.spec.StorageDriver}
 */
public final class StorageDriverManager {

    private static StorageDriver driver;

    /**
     * Should be called in storage driver implementation static block
     *      which will be executed when implementation class is
     *      loaded for the first time using {@link java.lang.Class#forName(String)}.
     * @param implementation Storage driver implementation.
     * @throws DriverAlreadyRegisteredException if some other driver is already registered.
     */
    public static void register(StorageDriver implementation) throws DriverAlreadyRegisteredException {
        if(driver != null) {
            throw new DriverAlreadyRegisteredException(driver);
        }

        driver = implementation;
    }

    /**
     * Should be called in application that uses storage driver implementation for instantiating storage entities.
     * @return Storage driver implementation that is registered.
     * @throws DriverNotRegisteredException if driver has not been registered using {@link #register(StorageDriver)}.
     */
    public static StorageDriver getDriver() throws DriverNotRegisteredException {
        if(driver == null) {
            throw new DriverNotRegisteredException();
        }

        return driver;
    }
}