package rs.raf.storage.gdrive.maker;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.maker.FileMaker;
import java.util.List;

public class GDriveFileMaker extends FileMaker {

	@Override
	public List<File> makeRange(String baseName, int rangeBegin, int rangeEnd, Directory directory) throws DriverNotRegisteredException, StorageException {
		return super.makeRange(baseName, rangeBegin, rangeEnd, directory);
	}
}
