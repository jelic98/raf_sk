package rs.raf.storage.spec.core;

import rs.raf.storage.spec.auth.Authorizer;
import rs.raf.storage.spec.exception.AuthenticationException;
import rs.raf.storage.spec.exception.PrivilegeException;
import rs.raf.storage.spec.res.Res;
import java.util.List;

public abstract class File {

    private static final PathResolver resolver;
    private static final Authorizer authorizer;

    private String name;
    private Directory parent;
    private Metadata metadata;

    static {
         resolver = new PathResolver();
         authorizer = new Authorizer();
    }

    public File(String name) {
        this.name = name;
    }

    public void delete() throws PrivilegeException {
        authorizer.checkDelete(Storage.getActiveUser(), this);

        onDelete();
    }

    public void copy(Directory destination) throws PrivilegeException {
        authorizer.checkWrite(Storage.getActiveUser(), this);

        onCopy(destination);
    }

    public void upload(Directory destination) throws PrivilegeException {
        authorizer.checkWrite(Storage.getActiveUser(), this);

        onUpload(destination);
    }

    public void download(String path) throws PrivilegeException {
        authorizer.checkRead(Storage.getActiveUser(), this);

        onDownload(path);
    }

    protected abstract void onDelete();
    protected abstract void onCopy(Directory destination);
    protected abstract void onUpload(Directory destination);
    protected abstract void onDownload(String path);

    public void extract(List<File> files) {
        files.add(this);
    }

    public void move(Directory destination) throws PrivilegeException {
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
            return resolver.separate(path.toString());
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
