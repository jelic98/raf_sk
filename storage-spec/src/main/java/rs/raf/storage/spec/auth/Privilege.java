package rs.raf.storage.spec.auth;

import rs.raf.storage.spec.core.File;

public final class Privilege {

    private File file;
    private boolean read, write, delete;

    public Privilege(File file, boolean read, boolean write, boolean delete) {
        this.file = file;
        this.read = read;
        this.write = write;
        this.delete = delete;
    }

    File getFile() {
        return file;
    }

    public boolean canRead() {
        return read;
    }

    public boolean canWrite() {
        return write;
    }

    public boolean canDelete() {
        return delete;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Privilege)) {
            return false;
        }

        Privilege privilege = (Privilege) obj;

        return privilege.getFile().equals(getFile());
    }
}
