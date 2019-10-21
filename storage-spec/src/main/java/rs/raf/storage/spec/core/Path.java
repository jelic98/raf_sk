package rs.raf.storage.spec.core;

import rs.raf.storage.spec.exception.NonExistenceException;
import rs.raf.storage.spec.res.Res;
import java.nio.file.FileSystems;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper class for handling paths.
 */
public final class Path {

    private String path;
    private Storage storage;

    /**
     * One and only constructor that accepts
     * @param path
     * @param storage
     */
    public Path(String path, Storage storage) {
        this.path = path;
        this.storage = storage;
    }

    /**
     * Returns abstract representation of file corresponding to provided path.
     * @return File with provided path.
     * @throws NonExistenceException if requested file does not exist.
     */
    public File resolve() throws NonExistenceException {
        List<File> files = new LinkedList<>();

        Directory root = storage.getRoot();

        String path = build();

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

    /**
     * Replaces platform independent wildcards and return platform dependent path.
     * @return Path that is platform dependent.
     */
    public String build() {
    	path = Res.Wildcard.SEPARATOR + path;
        return path
                .replace(Res.Wildcard.SEPARATOR, FileSystems.getDefault().getSeparator())
                .replace(FileSystems.getDefault().getSeparator() + FileSystems.getDefault().getSeparator(), FileSystems.getDefault().getSeparator())
                .replace(Res.Wildcard.HOME, System.getProperty("user.home"));
    }

    /**
     * Does the opposite of {@link #build()}.
     *      Converts platform dependent paths to platform independent path with wildcards.
     * @return Path that is platform independent.
     */
    public String reverseBuild() {
        return path
                .replace(FileSystems.getDefault().getSeparator(), Res.Wildcard.SEPARATOR)
                .replace(System.getProperty("user.home"), Res.Wildcard.HOME);
    }

    /**
     * Extracts file name from any type of path.
     * @return File name with extension.
     */
    public String extractName() {
        String path = reverseBuild();

        return path.contains(Res.Wildcard.SEPARATOR) ? path.substring(path.lastIndexOf(Res.Wildcard.SEPARATOR) + Res.Wildcard.SEPARATOR.length()) : path;
    }

    /**
     * Extracts file type from any type of path.
     * @return File extension.
     */
    public String extractType() {
        return path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : "";
    }
}
