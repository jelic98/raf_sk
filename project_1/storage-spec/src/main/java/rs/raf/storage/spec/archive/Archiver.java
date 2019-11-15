package rs.raf.storage.spec.archive;

import java.util.List;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.StorageException;

public abstract class Archiver {

    /**
     * Archives a list of files.
     * @param files List of file to be archived.
     * @throws StorageException 
     */
    public abstract void archive(List<File> files, Directory destination) throws StorageException;

    /**
     * Extracts a list of files from archive.
     * @param file Archive file.
     */
    public abstract void unarchive(File file, Directory destination);
}
