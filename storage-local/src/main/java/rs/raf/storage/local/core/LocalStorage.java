package rs.raf.storage.local.core;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.res.Res;

import java.io.File;
import java.io.IOException;
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
		file.mkdirs();

		// TODO Get all child files and attach them to root

		return new LocalDirectory("");
	}
}
