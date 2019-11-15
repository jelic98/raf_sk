package rs.raf.storage.gdrive.core;

import com.google.api.client.http.FileContent;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GDriveDirectory extends Directory {

    public GDriveDirectory(String name) {
        super(name);
    }

    @Override
    protected void onDelete() throws StorageException {
        try {
            GDriveStorage.drive.files().delete(GDriveUtils.getDirFromPath(this).getId()).execute();
        }catch(Exception e) {
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    protected void onCopy(Directory destination) throws StorageException {
        System.out.println("Directories cannot be copied in Google Drive");
    }

    @Override
    protected void onUpload(String path, Directory directory) throws StorageException {
        try {
            java.io.File source = new java.io.File(path);

            com.google.api.services.drive.model.File metadata = new com.google.api.services.drive.model.File();
            metadata.setName(source.getName());

            if(source.isDirectory()) {
                metadata.setMimeType("application/vnd.google-apps.folder");

                GDriveStorage.drive.files().create(metadata).execute();
            }else {
                GDriveStorage.drive.files().create(metadata,
                        new FileContent(Files.probeContentType(Paths.get(path)), source)).execute();
            }
        }catch(Exception e) {
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    protected void onDownload(String path) throws StorageException {
        System.out.println("Directories cannot be downloaded in Google Drive");
    }
}
