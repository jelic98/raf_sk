package rs.raf.storage.spec.maker;

import rs.raf.storage.spec.StorageDriver;
import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;
import java.util.LinkedList;
import java.util.List;

public final class FileMaker {

    public final List<File> makeRange(String baseName, int rangeBegin, int rangeEnd) {
        List<File> files = new LinkedList<>();

        StorageDriver driver;

        try {
            driver = StorageDriverManager.getDriver();
        }catch(DriverNotRegisteredException e) {
            System.err.println(e.getMessage());
            return files;
        }

        for(int i = rangeBegin; i <= rangeEnd; i++) {
            files.add(driver.getFile(baseName + i));
        }

        return files;
    }
}
