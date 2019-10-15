package rs.raf.storage.gdrive.core;

import rs.raf.storage.spec.core.Directory;

public class GDriveDirectory extends Directory {

    public GDriveDirectory(String name) {
        super(name);
    }

    @Override
    protected void onDelete() {

    }

    @Override
    protected void onCopy(Directory directory) {

    }

    @Override
    protected void onUpload(Directory directory) {

    }

    @Override
    protected void onDownload(String s) {

    }
}
