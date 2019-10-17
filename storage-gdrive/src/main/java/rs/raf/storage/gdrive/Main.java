package rs.raf.storage.gdrive;

import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.res.Res;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("rs.raf.storage.gdrive.GDriveStorageDriver");

            Storage storage = Storage.instance();
            storage.connect("", "", new User("", ""));

            // FILE
//            Directory destination = (Directory) new Path(Res.Wildcard.SEPARATOR, storage).resolve();
//            destination.upload("/Users/Lazar/Downloads/test.txt");
//
            File test = StorageDriverManager.getDriver().getFile("test/asd.txt");
            test.download("/Users/Lazar/Downloads");
//            test.copy(destination);
//            test.delete();

            // DIRECTORY
//            Directory destination = (Directory) new Path(Res.Wildcard.SEPARATOR, storage).resolve();
//            destination.upload("/Users/Lazar/Downloads/folder");

//            Directory directory = StorageDriverManager.getDriver().getDirectory("folder");
//            directory.download("/Users/Lazar/Downloads");
//            directory.copy(destination);
//            directory.delete();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
