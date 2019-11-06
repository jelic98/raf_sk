package rs.raf.storage.spec.maker;

import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;
import rs.raf.storage.spec.exception.StorageException;

import java.util.LinkedList;
import java.util.List;

public class FileMaker {

    /**
     * Creates multiple files by appending numbers in range to the {@code baseName}.
     * @param baseName Name that will be at the beginning of every file name.
     * @param rangeBegin Numbers that will be appended to the {@code baseName} will start from this value.
     * @param rangeEnd Numbers that will be appended to the {@code baseName} will end on this value.
     * @param directory Directory in which the files are made.
     * @return List of newly created files.
     * @throws DriverNotRegisteredException
     * @throws StorageException 
     */
    public List<File> makeRange(String baseName, int rangeBegin, int rangeEnd, Directory directory)
            throws DriverNotRegisteredException, StorageException {
        List<File> files = new LinkedList<>();

        for(int i = rangeBegin; i <= rangeEnd; i++) {
            files.add(StorageDriverManager.getDriver().getFile(baseName + i));
        }

        return files;
    }
}
