package rs.raf.storage.spec.core;

import java.util.LinkedList;
import java.util.List;

public abstract class Directory extends File {

    private List<File> children;

    public Directory() {
        children = new LinkedList<>();
    }

    @Override
    public final void download(String path) {

    }

    public final void upload(List<File> files) {

    }

    public final List<File> search(Criteria criteria) {
        return null;
    }

    public final List<File> getChildren() {
        return children;
    }
}
