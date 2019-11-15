package rs.raf.storage.gdrive.archive;

import rs.raf.storage.spec.archive.Archiver;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.StorageException;

import java.util.List;

public class GDriveArchiver extends Archiver {

    @Override
    public void archive(List<File> list, Directory directory) throws StorageException {
        System.out.println("Archiver is not supported in Google Drive");
    }

    @Override
    public void unarchive(File file, Directory directory) {
        System.out.println("Archiver is not supported in Google Drive");
    }
}
