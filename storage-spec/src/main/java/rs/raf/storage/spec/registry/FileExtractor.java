package rs.raf.storage.spec.registry;

import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Storage;
import java.util.LinkedList;
import java.util.List;

final class FileExtractor {

    List<File> extract(Storage storage) {
        List<File> files = new LinkedList<>();

        storage.getRoot().extract(files);

        return files;
    }
}
