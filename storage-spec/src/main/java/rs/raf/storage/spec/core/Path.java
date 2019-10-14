package rs.raf.storage.spec.core;

import rs.raf.storage.spec.res.Res;
import java.nio.file.FileSystems;

public final class Path {

    private String path;

    // TODO Provide storage and resolve path using it or throw nonexistenceexception
    public Path(String path) {
        this.path = path;
    }

    public File resolve() {
        return null;
    }

    public String build() {
        return path
                .replace(Res.Wildcard.SEPARATOR, FileSystems.getDefault().getSeparator())
                .replace(Res.Wildcard.HOME, System.getProperty("user.home"));
    }
}
