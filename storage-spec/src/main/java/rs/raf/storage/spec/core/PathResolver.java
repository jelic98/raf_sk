package rs.raf.storage.spec.core;

import rs.raf.storage.spec.res.Res;
import java.nio.file.FileSystems;

final class PathResolver {

    File resolve(String path) {
        return null;
    }

    String separate(String path) {
        return path.replace(Res.Wildcard.SEPARATOR, FileSystems.getDefault().getSeparator());
    }
}
