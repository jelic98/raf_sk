package rs.raf.storage.local.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.StorageException;

public class LocalDirectory extends Directory {

    public LocalDirectory(String name) {
		super(name);
	}

	@Override
	protected void onDelete() {
		this.getParent().getChildren().remove(this);
		java.io.File dir = new java.io.File(this.getPath());
		dir.delete();
	}

	@Override
	protected void onCopy(Directory destination) {
		try {
			Files.copy(Paths.get(this.getPath()), Paths.get(destination.getPath()), StandardCopyOption.REPLACE_EXISTING);
			destination.getChildren().add(this);
			
    	} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onUpload(Directory destination) {
		onCopy(destination);
	}

	@Override
	protected void onDownload(String path) {
		try {
			Files.copy(Paths.get(this.getPath()), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
