package rs.raf.storage.spec.archive;

import java.util.List;
import rs.raf.storage.spec.core.File;

public abstract class Archiver {

    public abstract void archive(List<File> files);
    public abstract void unarchive(File file);
}
