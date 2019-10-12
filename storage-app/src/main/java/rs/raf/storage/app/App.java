package rs.raf.storage.app;

import rs.raf.storage.app.model.Lifecycle;
import rs.raf.storage.app.storage.StorageStructure;

public class App {

    public static void main(String[] args) {
        new Lifecycle(new StorageStructure()).run();
    }
}
