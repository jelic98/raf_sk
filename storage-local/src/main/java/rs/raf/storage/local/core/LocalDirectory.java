package rs.raf.storage.local.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;

public class LocalDirectory extends Directory {

	public LocalDirectory(String name) {
		super(name);
	}

	@Override
	protected void onDelete() throws StorageException {
		java.io.File dir = new java.io.File(getAbsolutePath(getPath()));
		dir.delete();
	}

	@Override
	protected void onCopy(Directory destination) throws StorageException {
		String s = getAbsolutePath(getPath());
			
		destination.upload(s);
	}

	@Override
	protected void onDownload(String path) throws StorageException {
		String src = getAbsolutePath(getPath());
		String srcName = getName();
		
		Path sep = new Path(Res.Wildcard.SEPARATOR, null);
		
		String separator = sep.build();
		
		path = path + separator + srcName;
		
		java.nio.file.Path srcP = Paths.get(src);
		java.nio.file.Path dire = Paths.get(path);
		
		try {
			Files.copy(srcP, dire, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new StorageException(e.getMessage());
		}
	}

    @Override
	protected void onUpload(String path, Directory destination) throws StorageException {
    	String srcName = getName();
		String dir = destination.getAbsolutePath(destination.getPath());
		
		Path sep = new Path(Res.Wildcard.SEPARATOR, null);
		
		String separator = sep.build();
		
		dir = dir + separator + srcName;
		
		java.nio.file.Path src = Paths.get(path);
		java.nio.file.Path dire = Paths.get(dir);
		
		try {
			Files.copy(src, dire, StandardCopyOption.REPLACE_EXISTING);
			File tmp = new File(path);
			for (File fileEntry : tmp.listFiles()) {
		        this.upload(fileEntry.getAbsolutePath());
		    }
		} catch (IOException e) {
		}
	}
}
