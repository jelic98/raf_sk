package rs.raf.storage.spec.core;

import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.auth.Authorizer;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;
import java.util.List;

public abstract class File {

    private static final Authorizer authorizer;

    private String name;
    private Directory parent;
    private Metadata metadata;

    static {
         authorizer = new Authorizer();
    }

    public File(String name) {
        this.name = name;

        metadata = new Metadata();
    }

    public void delete() throws StorageException {
        authorizer.checkDelete(Storage.instance().getActiveUser(), this);

        onDelete();
    }

    public void copy(Directory destination) throws StorageException {
        authorizer.checkWrite(Storage.instance().getActiveUser(), this);

        onCopy(destination);
    }

    public void download(String path) throws StorageException {
        authorizer.checkRead(Storage.instance().getActiveUser(), this);

        onDownload(path);
    }

    protected abstract void onDelete() throws StorageException;
    protected abstract void onCopy(Directory destination) throws StorageException;
    protected abstract void onUpload(String path, Directory destination) throws StorageException;
    protected abstract void onDownload(String path) throws StorageException;

    public void extract(List<File> files) {
        files.add(this);
    }

    public final void move(Directory destination) throws StorageException {
        this.copy(destination);
        this.delete();
    }

    public final String getPath() {
        return getPath(true);
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

    public final String getAbsolutePath(String relative) {
        try {
            Storage storage = StorageDriverManager.getDriver().getStorage();

            return new Path(storage.getRootPath() + Res.Wildcard.SEPARATOR + relative, storage).build();
        }catch(StorageException e) {
            return relative;
        }
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
