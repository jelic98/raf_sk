package rs.raf.storage.local;

import rs.raf.storage.local.archive.LocalArchiver;
import rs.raf.storage.local.core.LocalDirectory;
import rs.raf.storage.local.core.LocalFile;
import rs.raf.storage.local.core.LocalStorage;
import rs.raf.storage.spec.StorageDriver;
import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.archive.Archiver;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.DriverAlreadyRegisteredException;

public class LocalStorageDriver extends StorageDriver {

    static {
        try {
            StorageDriverManager.register(new LocalStorageDriver("Local Storage"));
        }catch(DriverAlreadyRegisteredException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private LocalStorageDriver(String name) {
        super(name);
    }

    @Override
    public Storage getStorage() {
        return new LocalStorage();
    }

    @Override
    public Archiver getArchiver() {
        return new LocalArchiver();
    }

	@Override
	public Directory getDirectory(String name) {
		return new LocalDirectory(name);
	}

	@Override
	public File getFile(String name) {
		return new LocalFile(name);
	}
}