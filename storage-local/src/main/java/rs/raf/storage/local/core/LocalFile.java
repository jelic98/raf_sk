package rs.raf.storage.local.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;

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
			String s = getPath();
			Path sep = new Path(Res.Wildcard.SEPARATOR, null);
			
			String separator = sep.build();
			
			while(s.startsWith(separator))
				s = s.substring(1);

			String srcName = this.getName();
			String dir = destination.getAbsolutePath(destination.getPath());
			
			dir = dir + separator + srcName;
			
			java.nio.file.Path src = Paths.get(s);
			java.nio.file.Path dire = Paths.get(dir);
			
			Files.copy(src, dire, StandardCopyOption.REPLACE_EXISTING);

    	} catch (IOException e) {
    		e.printStackTrace();
		}
	}

	@Override
	protected void onDownload(String path) throws StorageException {
		try {
			Files.copy(Paths.get(getAbsolutePath(getPath())), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new StorageException(e.getMessage());
		}
	}

	@Override
	protected void onUpload(String path, Directory destination) throws StorageException {
		String srcName = this.getName();
		String dir = destination.getAbsolutePath(destination.getPath());
		
		Path sep = new Path(Res.Wildcard.SEPARATOR, null);
		
		String separator = sep.build();
		
		dir = dir + separator + srcName;
		
		java.nio.file.Path src = Paths.get(path);
		java.nio.file.Path dire = Paths.get(dir);
		
		try {
			Files.copy(src, dire, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
		}
	}
}
