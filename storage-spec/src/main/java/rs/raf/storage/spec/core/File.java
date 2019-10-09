package rs.raf.storage.spec.core;

public abstract class File {

    private Directory parent;
    private Metadata metadata;

    public abstract void delete();

    public abstract void copy(Directory destination);

    public abstract void move(Directory destination);

    public abstract void upload(Directory destination);

    public abstract void download(String path);

    // TODO Return member path or build it recursively to the root
    public final String getPath() {
        return null;
    }

    public final Directory getParent() {
        return parent;
    }

    public final Metadata getMetadata() {
        return metadata;
    }
}
