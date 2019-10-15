package rs.raf.storage.gdrive.core;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.Storage;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Collections;

public class GDriveStorage extends Storage {

    private static final String CREDENTIALS_PATH = "/credentials.json";

    private static Drive drive;
    private static HttpTransport transport;

    @Override
    protected void onConnect() {
        JacksonFactory factory = JacksonFactory.getDefaultInstance();
        try {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(factory,
                    new InputStreamReader(getClass().getResourceAsStream(CREDENTIALS_PATH)));

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    transport, factory, clientSecrets,
                    Collections.singleton(DriveScopes.DRIVE_FILE))
                    .setDataStoreFactory(new FileDataStoreFactory(new File(getRootPath())))
                    .build();

            transport = GoogleNetHttpTransport.newTrustedTransport();

            Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
            drive = new Drive.Builder(transport, factory, credential).setApplicationName(getClass().getSimpleName()).build();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDisconnect() {

    }

    @Override
    protected Directory buildRoot(String s) {
        return null;
    }
}
