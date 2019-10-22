package rs.raf.storage.local.core;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalStorage extends Storage {

	@Override
	protected void onConnect() {
		System.out.println("Connected to storage");
	}

	@Override
	protected void onDisconnect() {
		System.out.println("Disconnected from storage");
	}

	@Override
	protected Directory buildRoot(String path) {
		File file = new File(path);
		Directory root = new LocalDirectory("");
		file.mkdirs();

		String[] files = file.list();
		
		try {
			if(files.length != 0 && files != null) {
				for(String f : files) {
					root.upload(new Path(path + Res.Wildcard.SEPARATOR + f, null).build());
				}
			}
		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*try {
			DirectoryStream<java.nio.file.Path> dirStream = Files.newDirectoryStream(file.toPath());
			while(dirStream.iterator().hasNext()) {
				root.upload(dirStream.iterator().next().toString());
			}
			
		} catch (IOException e) {
			
		} catch (StorageException e) {
			
		}*/

		return root;
	}
}
