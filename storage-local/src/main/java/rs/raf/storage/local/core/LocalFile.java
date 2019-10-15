package rs.raf.storage.local.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.StorageException;

public class LocalFile extends File {

    public LocalFile(String name) {
		super(name);
	}

	@Override
	protected void onDelete() throws StorageException {
		this.getParent().getChildren().remove(this);
		java.io.File file = new java.io.File(getAbsolutePath(getPath()));
		file.delete();
	}

	@Override
	protected void onCopy(Directory destination) throws StorageException {
		try {
			Files.copy(Paths.get(getAbsolutePath(getPath())), Paths.get(destination.getAbsolutePath(getPath())), StandardCopyOption.REPLACE_EXISTING);
			destination.getChildren().add(this);
		} catch (IOException e) {
			throw new StorageException(e.getMessage());
		}
	}

	@Override
	protected void onUpload(Directory destination) throws StorageException {
		onCopy(destination);
	}

	@Override
	protected void onDownload(String path) throws StorageException {
		try {
			Files.copy(Paths.get(getAbsolutePath(getPath())), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new StorageException(e.getMessage());
		}
	}
}
