package rs.raf.storage.gdrive.core;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collections;

public class GDriveFile extends File {

    public GDriveFile(String name) {
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
        com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
        fileMetadata.setName(UPLOAD_FILE.getName());

        FileContent mediaContent = new FileContent("text/txt", UPLOAD_FILE);
        com.google.api.services.drive.model.File file = drive.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        System.out.println("File ID: " + file.getId());
    }

    @Override
    protected void onDownload(String path) {
        java.io.File parentDir = new java.io.File(DIR_FOR_DOWNLOADS);

        if(!parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Unable to create parent directory");
        }

        OutputStream out = new FileOutputStream(new java.io.File(parentDir, file.getName()));

        HttpRequestInitializer initializer = drive.getRequestFactory().getInitializer();

        MediaHttpDownloader downloader = new MediaHttpDownloader(httpTransport, initializer);
        downloader.setDirectDownloadEnabled(true);
        downloader.download(new GenericUrl(file.getWebContentLink()), out);
    }

    private static final String UPLOAD_FILE_PATH = "/Users/Lazar/Downloads/test.txt";
    private static final String DIR_FOR_DOWNLOADS = "/Users/Lazar/Downloads";
    private static final java.io.File UPLOAD_FILE = new java.io.File(UPLOAD_FILE_PATH);
}
