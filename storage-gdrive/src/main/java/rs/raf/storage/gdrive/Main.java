package rs.raf.storage.gdrive;

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
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collections;

public class Main {

    private static final String APPLICATION_NAME = "raf-sk-storage";
    private static final String UPLOAD_FILE_PATH = "/Users/Lazar/Downloads/test.txt";
    private static final String DIR_FOR_DOWNLOADS = "/Users/Lazar/Downloads";
    private static final java.io.File UPLOAD_FILE = new java.io.File(UPLOAD_FILE_PATH);
    private static final java.io.File DATA_STORE_DIR = new java.io.File("/Users/Lazar/Downloads/DriveSample");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static FileDataStoreFactory dataStoreFactory;
    private static HttpTransport httpTransport;
    private static Drive drive;

    private static Credential authorize() throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(Main.class.getResourceAsStream("/credentials.json")));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(DriveScopes.DRIVE_FILE)).setDataStoreFactory(dataStoreFactory)
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static void main(String[] args) throws Exception {
        Preconditions.checkArgument(
                !UPLOAD_FILE_PATH.startsWith("Enter ") && !DIR_FOR_DOWNLOADS.startsWith("Enter "),
                "Please enter the upload file path and download directory in %s", Main.class);

        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

        drive = new Drive.Builder(httpTransport, JSON_FACTORY, authorize()).setApplicationName(APPLICATION_NAME).build();

        File uploadedFile = uploadFile();

        downloadFile(uploadedFile);
    }

    private static File uploadFile() throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(UPLOAD_FILE.getName());

        FileContent mediaContent = new FileContent("text/txt", UPLOAD_FILE);
        File file = drive.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        System.out.println("File ID: " + file.getId());

        return file;
    }

    private static void downloadFile(File file) throws IOException {
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
}