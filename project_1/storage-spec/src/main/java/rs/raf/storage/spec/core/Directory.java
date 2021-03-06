package rs.raf.storage.spec.core;

import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.auth.Authorizer;
import rs.raf.storage.spec.exception.ForbiddenTypeException;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;
import rs.raf.storage.spec.search.Criteria;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract representation of directory.
 */
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
        Path p = new Path(destination.getName() + Res.Wildcard.SEPARATOR + getName(), Storage.instance());
        
        for(File child : getChildren()) {
            child.copy((Directory) p.resolve());
        }
    }

    @Override
    public void download(String path) throws StorageException {
        super.download(path);
        Path p = new Path(path + Res.Wildcard.SEPARATOR + getName(), null);

        for(File child : getChildren()) {
            child.download(p.build());
        }
    }

    @Override
    public void extract(List<File> files) {
        super.extract(files);

        for(File child : getChildren()) {
            child.extract(files);
        }
    }

    /**
     * Uploads single file from provided {@code path} to directory.
     * @param path Path of file to be uploaded.
     * @throws StorageException if used does not have privileges or file type is forbidden.
     */
    public void upload(String path) throws StorageException {
        authorizer.checkWrite(Storage.instance().getActiveUser(), this);

        String name = new Path(path, Storage.instance()).extractName();
        
        if(name.isEmpty()) {
            name = new Path(path.substring(0, path.length() - 1), Storage.instance()).extractName();
        }

        String type = new Path(path, Storage.instance()).extractType();

        if(Storage.instance().getForbiddenTypes().contains(type)) {
            throw new ForbiddenTypeException(this);
        }

        File file;

        if(type.isEmpty()) {
            file = StorageDriverManager.getDriver()
                    .getDirectory(new Path(name + Res.Wildcard.SEPARATOR, null).build());
        }else {
            file = StorageDriverManager.getDriver().getFile(name);
        }

        file.setParent(this);
        file.onUpload(path, this);

        children.add(file);
    }

    /**
     * Uploads multiple files from provided array of {@code paths} to directory.
     * @param paths Paths array of files to be uploaded.
     * @throws StorageException if used does not have privileges or file types are forbidden.
     */
    public final void upload(String[] paths) throws StorageException {
        for(String path : paths) {
            upload(path);
        }
    }

    /**
     * Searches directory for files that match provided criteria.
     *      Note that this method performs only one level search and does not recurse.
     * @param criteria Searched criteria which holds search type and search query.
     * @return List of file that matches search criteria.
     */
    public final List<File> search(Criteria criteria) {
        List<File> files = new LinkedList<>();

        for(File child : getChildren()) {
            if(criteria.matches(child)) {
                files.add(child);
            }
        }

        return files;
    }

    public final void removeChild(File child) {
    	children.remove(child);
    }
    
    public final List<File> getChildren() {
        return new LinkedList<>(children);
    }
}
