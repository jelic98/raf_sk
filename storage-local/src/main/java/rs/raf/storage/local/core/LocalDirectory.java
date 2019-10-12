package rs.raf.storage.local.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;

public class LocalDirectory extends Directory {

    public LocalDirectory(String name) {
		super(name);
	}

	@Override
    public void delete() {
		for(File f : this.getChildren()) {
			f.delete();
		}
		this.getParent().getChildren().remove(this);
		java.io.File dir = new java.io.File(this.getPath());
		dir.delete();
    }

    @Override
    public void copy(Directory directory) {
    	try {
			Files.copy(Paths.get(this.getPath()), Paths.get(directory.getPath()), StandardCopyOption.REPLACE_EXISTING);
			directory.getChildren().add(this);
			
			for(File f : this.getChildren()) {
				f.copy(this);
			}
			
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void upload(Directory directory) {

    }
}
