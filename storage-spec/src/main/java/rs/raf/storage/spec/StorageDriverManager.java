package rs.raf.storage.spec;

import rs.raf.storage.spec.exception.DriverRegisteredException;

public abstract class StorageDriverManager {

    // Driver is set by calling
    // Class.forName("rs.raf.storage.impl.StorageDriverImpl");
    // which then executes following implementation static block
    // static {
    // try {
    // register(new StorageDriverImpl("Implementation"));
    // }catch(DriverRegisteredException e) {
    // throw new IllegalStateException(e.getMessage());
    // }
    // }
    private static StorageDriver driver;

    public static void register(StorageDriver implementation) throws DriverRegisteredException {
        if(driver != null) {
            throw new DriverRegisteredException(driver);
        }

        driver = implementation;
    }
}