package rs.raf.storage.spec.core;

import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.search.Criteria;
import java.util.LinkedList;
import java.util.List;

public abstract class Directory extends File {

    private List<File> children;

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
    public void upload(Directory destination) throws StorageException {
        super.upload(destination);

        for(File child : getChildren()) {
            child.upload(destination);
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

    public final void upload(List<File> files) throws StorageException {
        children.addAll(files);

        for(File file : files) {
            file.setParent(this);
            file.upload(this);
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
}
