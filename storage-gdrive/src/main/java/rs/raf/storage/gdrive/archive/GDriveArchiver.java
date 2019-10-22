package rs.raf.storage.gdrive.archive;

import rs.raf.storage.spec.archive.Archiver;
import rs.raf.storage.spec.core.File;
import java.util.List;

public class GDriveArchiver extends Archiver {

    @Override
    public void archive(List<File> list) {
        System.out.println("Archiver is not supported in Google Drive");
    }

    @Override
    public void unarchive(File file) {
        System.out.println("Archiver is not supported in Google Drive");
    }
}
