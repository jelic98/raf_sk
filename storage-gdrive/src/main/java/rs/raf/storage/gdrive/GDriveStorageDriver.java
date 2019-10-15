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
import rs.raf.storage.spec.exception.DriverAlreadyRegisteredException;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;

public class GDriveStorageDriver extends StorageDriver {

    private boolean instantiated = false;

    static {
        try {
            StorageDriverManager.register(new GDriveStorageDriver("Google Drive Storage"));
        }catch(DriverAlreadyRegisteredException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private GDriveStorageDriver(String name) {
        super(name);
    }

    @Override
    public Storage getStorage() {
        try {
            if(!instantiated) {
                instantiated = true;
                return new GDriveStorage();
            }
            return Storage.instance();
        } catch (DriverNotRegisteredException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Archiver getArchiver() {
        return new GDriveArchiver();
    }

    @Override
    public Directory getDirectory(String name) {
        return new GDriveDirectory(name);
    }

    @Override
    public File getFile(String name) {
        return new GDriveFile(name);
    }
}