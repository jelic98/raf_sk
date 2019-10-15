package rs.raf.storage.local.core;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.Storage;

public class LocalStorage extends Storage {

	
	public LocalStorage() {
		super();
	}

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
		return new LocalDirectory("root");
	}
	
}
