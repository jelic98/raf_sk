package rs.raf.storage.spec.core;

import rs.raf.storage.spec.exception.NonExistenceException;
import rs.raf.storage.spec.res.Res;
import java.nio.file.FileSystems;
import java.util.LinkedList;
import java.util.List;

public final class Path {

    private String path;
    private Storage storage;

    public Path(String path, Storage storage) {
        this.path = path;
        this.storage = storage;
    }

    public File resolve() throws NonExistenceException {
        List<File> files = new LinkedList<>();

        Directory root = storage.getRoot();

        if(root != null) {
            root.extract(files);
        }else {
            throw new NonExistenceException(path);
        }

        for(File file : files) {
            if(file.getPath().equals(path)) {
                return file;
            }
        }

        throw new NonExistenceException(path);
    }

    public String build() {
        return path
                .replace(Res.Wildcard.SEPARATOR, FileSystems.getDefault().getSeparator())
                .replace(Res.Wildcard.HOME, System.getProperty("user.home"));
    }
}
