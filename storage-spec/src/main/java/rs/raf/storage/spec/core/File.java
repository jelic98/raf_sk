package rs.raf.storage.spec.core;

import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.auth.Authorizer;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;
import java.util.List;

/**
 * Abstract representation of file.
 */
public abstract class File {

    private static final Authorizer authorizer;

    private String name;
    private Directory parent;
    private Metadata metadata;

    static {
         authorizer = new Authorizer();
    }

    /**
     * One and only constructor that accepts just file name;
     * @param name Name of the abstract file.
     */
    public File(String name) {
        this.name = name;

        metadata = new Metadata();
    }

    /**
     * Template method for deleting abstract file from real storage.
     * @throws StorageException if user does not have required privileges or file does not exist.
     */
    public void delete() throws StorageException {
        authorizer.checkDelete(Storage.instance().getActiveUser(), this);

        onDelete();
    }

    /**
     * Template method for copying abstract file from and to real storage.
     * @throws StorageException if user does not have required privileges or file does not exist.
     */
    public void copy(Directory destination) throws StorageException {
        authorizer.checkWrite(Storage.instance().getActiveUser(), this);

        onCopy(destination);
    }

    /**
     * Template method for downloading abstract file from real storage.
     * @throws StorageException if user does not have required privileges or file does not exist.
     */
    public void download(String path) throws StorageException {
        authorizer.checkRead(Storage.instance().getActiveUser(), this);

        onDownload(path);
    }

    /**
     * Should be overridden in {@code File} implementation to handle delete operation.
     * @throws StorageException if there was a problem in implementation.
     */
    protected abstract void onDelete() throws StorageException;

    /**
     * Should be overridden in {@code File} implementation to handle copy operation.
     * @throws StorageException if there was a problem in implementation.
     */
    protected abstract void onCopy(Directory destination) throws StorageException;

    /**
     * Should be overridden in {@code File} implementation to handle upload operation.
     * @throws StorageException if there was a problem in implementation.
     */
    protected abstract void onUpload(String path, Directory destination) throws StorageException;

    /**
     * Should be overridden in {@code File} implementation to handle download operation.
     * @throws StorageException if there was a problem in implementation.
     */
    protected abstract void onDownload(String path) throws StorageException;

    public void extract(List<File> files) {
        files.add(this);
    }

    /**
     * Moves current file to provided destination.
     * @param destination New parent directory of current file.
     * @throws StorageException if user does not have required privileges or file does not exist.
     */
    public final void move(Directory destination) throws StorageException {
        this.copy(destination);
        this.delete();
    }

    /**
     * Builds path from current file node to the root of file tree and return absolute path.
     * @return Absolute path of current file.
     */
    public final String getPath() {
        return getPath(true);
    }

    /**
     * Appends provided relative path to the root path and returns abolute path.
     * @param relative Relative path to be expanded to absolute path.
     * @return Absolute path corresponding to provided relative path.
     */
    public final String getAbsolutePath(String relative) {
        try {
            Storage storage = StorageDriverManager.getDriver().getStorage();

            return new Path(storage.getRootPath() + Res.Wildcard.SEPARATOR + relative, storage).build();
        }catch(StorageException e) {
            return relative;
        }
    }

    final String getPath(boolean initial) {
        StringBuilder path = new StringBuilder();

        if(hasParent()) {
            path.append(getParent().getPath(false));
        }

        path.append(Res.Wildcard.SEPARATOR);
        path.append(getName());

        if(initial) {
            return new Path(path.toString(), null).build();
        }else {
            return path.toString();
        }
    }
    
    public final String getName() {
        return name;
    }

    public final boolean hasParent() {
        return parent != null;
    }

    public final Directory getParent() {
        return parent;
    }

    public final void setParent(Directory parent) {
        this.parent = parent;
    }

    public final Metadata getMetadata() {
        return metadata;
    }

    public final void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof File)) {
            return false;
        }

        File file = (File) obj;

        return file.getPath().equals(getPath());
    }
}
