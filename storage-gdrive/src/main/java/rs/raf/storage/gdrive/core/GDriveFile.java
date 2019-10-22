package rs.raf.storage.gdrive.core;

import com.google.api.client.http.FileContent;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GDriveFile extends File {

    public GDriveFile(String name) {
        super(name);
    }

    @Override
    protected void onDelete() throws StorageException {
        try {
            GDriveStorage.drive.files().delete(GDriveUtils.getFileFromPath(this).getId()).execute();
        }catch(Exception e) {
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    protected void onCopy(Directory destination) throws StorageException {
        try {
            String tmpPath = new Path(GDriveUtils.TMP_PATH, Storage.instance()).build();
            java.io.File tmpDir = new java.io.File(tmpPath);
            tmpDir.mkdirs();

            String name = new Path(getPath(), Storage.instance()).extractName();

            onDownload(tmpPath);
            onUpload(new Path(tmpPath + Res.Wildcard.SEPARATOR + name, Storage.instance()).build(), destination);

            String[] tmpFiles = tmpDir.list();

            for(String s : tmpFiles) {
                new java.io.File(tmpDir.getPath(), s).delete();
            }

            tmpDir.delete();
        }catch(Exception e) {
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    protected void onUpload(String path, Directory destination) throws StorageException {
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
        try {
            GDriveStorage.drive.files().get(GDriveUtils.getFileFromPath(this).getId())
                    .executeMediaAndDownloadTo(new FileOutputStream(new java.io.File(path,
                            new Path(getPath(), Storage.instance()).extractName())));
        }catch(Exception e) {
            throw new StorageException(e.getMessage());
        }
    }
}
