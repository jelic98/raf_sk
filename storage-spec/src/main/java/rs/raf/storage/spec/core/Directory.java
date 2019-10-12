package rs.raf.storage.spec.core;

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
    void extract(List<File> files) {
        super.extract(files);

        for(File child : getChildren()) {
            child.extract(files);
        }
    }

    @Override
    public final void download(String path) {

    }

    public final void upload(List<File> files) {
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
        return children;
    }
}
