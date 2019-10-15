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

    static Drive drive;
    static HttpTransport transport;

    @Override
    protected void onConnect() {
        try {
            JacksonFactory factory = JacksonFactory.getDefaultInstance();

            Credential credential = new AuthorizationCodeInstalledApp(new GoogleAuthorizationCodeFlow
                    .Builder(transport = GoogleNetHttpTransport.newTrustedTransport(),
                        factory,
                        GoogleClientSecrets.load(factory,
                                new InputStreamReader(GDriveStorage.class.getResourceAsStream(CREDENTIALS_PATH))),
                        Collections.singleton(DriveScopes.DRIVE_FILE))
                    .setDataStoreFactory(new FileDataStoreFactory(new File(getRootPath())))
                    .build(), new LocalServerReceiver()).authorize("user");

            drive = new Drive.Builder(transport, factory, credential).setApplicationName(getClass().getSimpleName()).build();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDisconnect() {

    }

    @Override
    protected Directory buildRoot(String path) {
        return new GDriveDirectory("");
    }
}
