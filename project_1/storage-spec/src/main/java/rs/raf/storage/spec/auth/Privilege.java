package rs.raf.storage.spec.auth;

import rs.raf.storage.spec.core.File;

public final class Privilege {

    private File file;
    private boolean read, write, delete;

    /**
     * One and only constructor that accepts file and flags.
     * @param file File onto which this privilege will be applied.
     * @param read Read flag. Has value of {@code true} if user can read provided file.
     * @param write Write flag. Has value of {@code true} if user can write provided file.
     * @param delete Delete flag. Has value of {@code true} if user can delete provided file.
     */
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
