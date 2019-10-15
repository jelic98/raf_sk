package rs.raf.storage.gdrive;

import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.res.Res;

public class Main {

    public static void main(String[] args) throws Exception {
        Class.forName("rs.raf.storage.gdrive.GDriveStorageDriver");

        Storage storage = Storage.instance();
        storage.connect("", "", new User("", ""));

        Directory destination = (Directory) new Path(Res.Wildcard.SEPARATOR, storage).resolve();
        destination.upload("/Users/Lazar/Downloads/test.txt");
    }
}
