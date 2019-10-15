package rs.raf.storage.spec.core;

import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.auth.Authorizer;
import rs.raf.storage.spec.exception.ForbiddenTypeException;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;
import rs.raf.storage.spec.search.Criteria;
import java.util.LinkedList;
import java.util.List;

public abstract class Directory extends File {

    private static final Authorizer authorizer;

    private List<File> children;

    static {
        authorizer = new Authorizer();
    }

    public Directory(String name) {
        super(name);

        children = new LinkedList<>();
    }

    @Override
    public void delete() throws StorageException {
        for(File child : getChildren()) {
            child.delete();
        }

        super.delete();
    }

    @Override
    public void copy(Directory destination) throws StorageException {
        super.copy(destination);

        for(File child : getChildren()) {
            child.copy(destination);
        }
    }

    @Override
    public void download(String path) throws StorageException {
        super.download(path);

        for(File child : getChildren()) {
            child.download(path);
        }
    }

    @Override
    public void extract(List<File> files) {
        super.extract(files);

        for(File child : getChildren()) {
            child.extract(files);
        }
    }

    public void upload(String path) throws StorageException {
        authorizer.checkWrite(Storage.instance().getActiveUser(), this);

        String name = extractName(path);
        String type = extractType(name);

        if(Storage.instance().getForbiddenTypes().contains(type)) {
            throw new ForbiddenTypeException(this);
        }

        File file = StorageDriverManager.getDriver().getFile(name);
        file.setParent(this);

        children.add(file);
    }

    public final void upload(String[] paths) throws StorageException {
        for(String path : paths) {
            upload(path);
        }
    }

    public final List<File> search(Criteria criteria) {
        List<File> files = new LinkedList<>();

        for(File child : getChildren()) {
            if(criteria.matches(child)) {
                files.add(child);
            }
        }

        return files;
    }

    public final List<File> getChildren() {
        return new LinkedList<>(children);
    }

    private String extractName(String path) throws StorageException {
        path = new Path(path, Storage.instance()).reverseBuild();

        return path.contains(Res.Wildcard.SEPARATOR) ? path.substring(path.lastIndexOf(Res.Wildcard.SEPARATOR) + Res.Wildcard.SEPARATOR.length()) : path;
    }

    private String extractType(String name) {
        return name.contains(".") ? name.substring(name.lastIndexOf('.') + 1) : "";
    }
}
