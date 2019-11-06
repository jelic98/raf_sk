package rs.raf.storage.local.maker;

import java.util.List;

import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.exception.DriverNotRegisteredException;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.maker.FileMaker;
import rs.raf.storage.spec.res.Res;

public class LocalFileMaker extends FileMaker{

	@Override
	public List<File> makeRange(String baseName, int rangeBegin, int rangeEnd, Directory directory) throws DriverNotRegisteredException, StorageException {
		List<File> files = super.makeRange(baseName, rangeBegin, rangeEnd, directory);
		
		for(File f : files) {
			String path = new Path(Res.Wildcard.HOME + Res.Wildcard.SEPARATOR + f.getName(), null).build();
			java.io.File file = new java.io.File(new Path(Res.Wildcard.HOME + Res.Wildcard.SEPARATOR + f.getName(), null).build());
			file.mkdirs();
			directory.upload(path);
			file.delete();
		}
		
		return files;
	}

}
