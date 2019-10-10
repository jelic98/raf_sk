package rs.raf.storage.spec;

import rs.raf.storage.spec.archive.Archiver;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.Storage;
import java.io.File;

public abstract class StorageDriver {

    private String name;

    public StorageDriver(String name) {
        this.name = name;
    }

    public abstract Storage getStorage();

    public abstract Directory getDirectory();

    public abstract File getFile();

    public abstract Archiver getArchiver();

    public final String getName() {
        return name;
    }
}