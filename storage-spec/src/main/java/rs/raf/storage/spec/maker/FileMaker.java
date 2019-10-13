package rs.raf.storage.spec.maker;

import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;
import java.util.LinkedList;
import java.util.List;

public final class FileMaker {

    public final List<File> makeRange(String baseName, int rangeBegin, int rangeEnd)
            throws DriverNotRegisteredException {
        List<File> files = new LinkedList<>();

        for(int i = rangeBegin; i <= rangeEnd; i++) {
            files.add(StorageDriverManager.getDriver().getFile(baseName + i));
        }

        return files;
    }
}
