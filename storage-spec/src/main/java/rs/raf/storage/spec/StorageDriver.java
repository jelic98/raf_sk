package rs.raf.storage.spec;

import rs.raf.storage.spec.archive.Archiver;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Storage;

public abstract class StorageDriver {

    private String name;

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