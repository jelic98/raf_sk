package rs.raf.storage.local;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import rs.raf.storage.local.archive.LocalArchiver;
import rs.raf.storage.local.core.LocalDirectory;
import rs.raf.storage.local.core.LocalFile;
import rs.raf.storage.local.core.LocalStorage;
import rs.raf.storage.spec.StorageDriver;
import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.archive.Archiver;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.DriverAlreadyRegisteredException;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;
import rs.raf.storage.spec.exception.NonExistenceException;
import rs.raf.storage.spec.res.Res;

public class LocalStorageDriver extends StorageDriver {

    private boolean instantiated = false;

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
        try {
        	if(!instantiated) {
        		instantiated = true;
        		return new LocalStorage();
        	}
			return Storage.instance();
		} catch (DriverNotRegisteredException e) {
			e.printStackTrace();
			return null;
		}
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