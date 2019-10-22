package rs.raf.storage.spec;

import rs.raf.storage.spec.archive.Archiver;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Storage;

/**
 * Abstract factory for creating storage entities.
 */
public abstract class StorageDriver {

    private String name;

    /**
     * One and only contructor that accepts just storage driver name.
     * @param name Storage driver name.
     */
    protected StorageDriver(String name) {
        this.name = name;
    }

    public abstract Storage getStorage();

    public abstract Directory getDirectory(String name);

    public abstract File getFile(String name);

    public abstract Archiver getArchiver();

    public final String getName() {
        return name;
    }
}