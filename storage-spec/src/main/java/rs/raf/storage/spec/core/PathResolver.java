package rs.raf.storage.spec.core;

import rs.raf.storage.spec.res.Res;
import java.nio.file.FileSystems;
import java.util.LinkedList;
import java.util.List;

final class PathResolver {

    File resolve(String path) {
        return null;
    }

    String separate(String path) {
        return path.replace(Res.Wildcard.SEPARATOR, FileSystems.getDefault().getSeparator());
    }

    List<File> extract(Storage storage) {
        List<File> files = new LinkedList<>();

        storage.getRoot().extract(files);

        return files;
    }
}
