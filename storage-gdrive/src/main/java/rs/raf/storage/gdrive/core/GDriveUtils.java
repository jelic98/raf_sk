package rs.raf.storage.gdrive.core;

import com.google.api.services.drive.model.FileList;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;

import java.io.IOException;
import java.util.List;

class GDriveUtils {

    static final String TMP_PATH = Res.Wildcard.HOME + Res.Wildcard.SEPARATOR + ".tmp";

    static com.google.api.services.drive.model.File getFileFromPath(File localFile, String path) throws StorageException {
        try {
            String pageToken = null;

            do {
                FileList result = GDriveStorage.drive.files().list()
                        .setQ("mimeType='application/vnd.google-apps.folder'")
                        .setSpaces("drive")
                        .setFields("nextPageToken, files(id, name)")
                        .setPageToken(pageToken)
                        .execute();

                for(com.google.api.services.drive.model.File file : result.getFiles()) {
                    if(equalFiles(localFile, file) || (localFile.getParent() == null && file.getParents() == null)) {
                        return file;
                    }
                }

                pageToken = result.getNextPageToken();
            }while(pageToken != null);
        }catch(Exception e) {
            throw new StorageException(e.getMessage());
        }

        return null;
    }

    static boolean equalFiles(File localFile, com.google.api.services.drive.model.File file) throws IOException {
        Directory local = localFile.getParent();
        List<String> parents = file.getParents();

        while(local != null && parents != null) {
            com.google.api.services.drive.model.File remote = GDriveStorage.drive.files().get(parents.get(0)).execute();

            if(!local.getName().equals(remote.getName())) {
                return false;
            }

            local = local.getParent();
            parents = file.getParents();
        }

        return true;
    }
}
