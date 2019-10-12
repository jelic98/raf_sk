package rs.raf.storage.gdrive;

import rs.raf.storage.gdrive.archive.GDriveArchiver;
import rs.raf.storage.gdrive.core.GDriveDirectory;
import rs.raf.storage.gdrive.core.GDriveFile;
import rs.raf.storage.gdrive.core.GDriveStorage;
import rs.raf.storage.spec.StorageDriver;
import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.archive.Archiver;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.DriverRegisteredException;

public class GDriveStorageDriver extends StorageDriver {

    static {
        try {
            StorageDriverManager.register(new GDriveStorageDriver("Google Drive Storage"));
        }catch(DriverRegisteredException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private GDriveStorageDriver(String name) {
        super(name);
    }

    @Override
    public Storage getStorage() {
        return new GDriveStorage();
    }

    @Override
    public Directory getDirectory() {
        return new GDriveDirectory();
    }

    @Override
    public File getFile() {
        return new GDriveFile();
    }

    @Override
    public Archiver getArchiver() {
        return new GDriveArchiver();
    }
}