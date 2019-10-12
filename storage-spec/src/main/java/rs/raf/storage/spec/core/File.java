package rs.raf.storage.spec.core;

import rs.raf.storage.spec.res.Res;
import java.util.List;

public abstract class File {

    private static final PathResolver resolver;

    private String name;
    private Directory parent;
    private Metadata metadata;

    static {
         resolver = new PathResolver();
    }

    public File(String name) {
        this.name = name;
    }

    public abstract void delete();

    public abstract void copy(Directory destination);

    public abstract void upload(Directory destination);

    public abstract void download(String path);

    void extract(List<File> files) {
        files.add(this);
    }

    public void move(Directory destination) {
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

    final void setMetadata(Metadata metadata) {
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
