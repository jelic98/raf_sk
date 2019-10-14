package rs.raf.storage.app;

import com.konzole.framework.*;
import rs.raf.storage.spec.StorageDriver;
import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.StorageException;

public class App {

    public static void main(String[] args) {
        try {
            new Lifecycle(new StorageStructure()).run();
        }catch(StorageException e) {
            log(e);
        }
    }

    private static final class StorageStructure extends Structure {

        // TODO Grant and revoke privileges?

        private StorageDriver driver;
        private Storage storage;
        private User user;

        public StorageStructure() throws StorageException {
            driver = StorageDriverManager.getDriver();
            storage = driver.getStorage();
        }

        @Override
        protected Question create() {
            return new Question("What operation to execute?")
                    .addOption(new ExecuteOption("Connect to storage") {
                        @Override
                        public void execute() {
                            String uid = getInput("uid").getValue();
                            String path = getInput("path").getValue();
                            String username = getInput("username").getValue();
                            String password = getInput("password").getValue();

                            user = new User(username, password);

                            try {
                                storage.connect(uid, path, user);
                            }catch(StorageException e) {
                                log(e);
                            }

                            log("Connected successfully");
                        }
                    }
                            .addInput(new Input("UID:"))
                            .addInput(new Input("Path:"))
                            .addInput(new Input("Username:"))
                            .addInput(new Input("Password:")))
                    .addOption(new ExecuteOption("Disconnect from storage") {
                        @Override
                        public void execute() {
                            try {
                                storage.disconnect(user);
                            }catch(StorageException e) {
                                log(e);
                            }

                            user = null;

                            log("Disconnected successfully");
                        }
                    })
                    .addOption(new ExecuteOption("Add user to storage") {
                        @Override
                        public void execute() {
                            String username = getInput("username").getValue();
                            String password = getInput("password").getValue();
                        }
                    }
                            .addInput(new Input("Username:"))
                            .addInput(new Input("Password:")))
                    .addOption(new ExecuteOption("Remove user from storage") {
                        @Override
                        public void execute() {
                            String username = getInput("username").getValue();
                        }
                    }
                            .addInput(new Input("Username:")))
                    .addOption(new Option("Upload single file"))
                    .addOption(new Option("Upoad multiple files"))
                    .addOption(new Option("Download file"))
                    .addOption(new Option("Copy file"))
                    .addOption(new Option("Move file"))
                    .addOption(new Option("Delete file"))
                    .addOption(new Option("Get metadata"))
                    .addOption(new Option("Set metadata"))
                    .addOption(new Option("List directory"))
                    .addOption(new Option("Search directory"));
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static void log(Exception e) {
        System.err.println(e.getMessage());
        System.exit(0);
    }
}
