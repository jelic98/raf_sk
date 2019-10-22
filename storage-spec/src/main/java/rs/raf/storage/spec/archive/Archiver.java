package rs.raf.storage.spec.archive;

import java.util.List;
import rs.raf.storage.spec.core.File;

public abstract class Archiver {

    /**
     * Archives a list of files.
     * @param files List of file to be archived.
     */
    public abstract void archive(List<File> files);

    /**
     * Extracts a list of files from archive.
     * @param file Archive file.
     */
    public abstract void unarchive(File file);
}
