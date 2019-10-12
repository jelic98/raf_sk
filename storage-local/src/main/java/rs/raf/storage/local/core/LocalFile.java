package rs.raf.storage.local.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;

public class LocalFile extends File {

    public LocalFile(String name) {
		super(name);
	}

	@Override
    public void delete() {
		java.io.File file = new java.io.File(this.getPath());
		file.delete();
		this.getParent().getChildren().remove(this);
    }

    @Override
    public void copy(Directory directory) {
    	try {
			Files.copy(Paths.get(this.getPath()), Paths.get(directory.getPath()), StandardCopyOption.REPLACE_EXISTING);
			directory.getChildren().add(this);
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void upload(Directory directory) {

    }

    @Override
    public void download(String s) {
    	try {
			Files.copy(Paths.get(this.getPath()), Paths.get(s), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}
