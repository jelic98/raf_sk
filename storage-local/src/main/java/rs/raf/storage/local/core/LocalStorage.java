package rs.raf.storage.local.core;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.Storage;
import java.io.File;

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
		file.mkdirs();

		return new LocalDirectory("");
	}
	
}
