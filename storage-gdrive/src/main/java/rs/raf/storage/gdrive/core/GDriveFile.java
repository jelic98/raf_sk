package rs.raf.storage.gdrive.core;

import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.StorageException;
import java.io.FileOutputStream;

public class GDriveFile extends File {

    private String id;

    public GDriveFile(String name) {
        super(name);
    }

    @Override
    protected void onDelete() throws StorageException {

    }

    @Override
    protected void onCopy(Directory destination) throws StorageException {

    }

    @Override
    protected void onUpload(Directory destination) throws StorageException {
        try {
            java.io.File uploadFile = new java.io.File(getAbsolutePath(getPath()));

            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(uploadFile.getName());

            com.google.api.services.drive.model.File file = GDriveStorage.drive.files().create(fileMetadata,
                    new FileContent("*/*", uploadFile)).setFields("id").execute();
            id = file.getId();
        }catch(Exception e) {
            e.printStackTrace();
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    protected void onDownload(String path) throws StorageException {
        try {
            com.google.api.services.drive.model.File file = GDriveStorage.drive.files().get("fileID=" + id).execute();

            MediaHttpDownloader downloader = new MediaHttpDownloader(GDriveStorage.transport,
                    GDriveStorage.drive.getRequestFactory().getInitializer());
            downloader.setDirectDownloadEnabled(true);
            downloader.download(new GenericUrl(file.getWebContentLink()),
                    new FileOutputStream(new java.io.File(new java.io.File(path), file.getName())));
        }catch(Exception e) {
            e.printStackTrace();
            throw new StorageException(e.getMessage());
        }
    }
}
