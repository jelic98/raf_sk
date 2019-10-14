package rs.raf.storage.app;

import com.konzole.framework.*;
import rs.raf.storage.spec.StorageDriver;
import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.*;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.search.Criteria;
import rs.raf.storage.spec.search.CriteriaType;
import java.util.List;

public class App {

    public static void main(String[] args) {
        try {
            new Lifecycle(new StorageStructure()).run();
        } catch (Exception e) {
            log(e);
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static void log(Exception e) {
        System.err.println(e.getMessage());
        System.exit(0);
    }

    private static final class StorageStructure extends Structure {

        // TODO Grant and revoke privileges?

        private StorageDriver driver;
        private Storage storage;
        private User user;

        public StorageStructure() throws Exception {
            Class.forName("rs.raf.storage.local.LocalStorageDriver");

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
                            } catch (StorageException e) {
                                log(e);
                            }

                            log("Connected successfully");
                        }
                    }
                            .addInput(new Input("UID"))
                            .addInput(new Input("Path"))
                            .addInput(new Input("Username"))
                            .addInput(new Input("Password")))
                    .addOption(new ExecuteOption("Disconnect from storage") {
                        @Override
                        public void execute() {
                            try {
                                storage.disconnect(user);
                            } catch (StorageException e) {
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

                            try {
                                storage.addUser(new User(username, password));
                            } catch (StorageException e) {
                                log(e);
                            }
                        }
                    }
                            .addInput(new Input("Username"))
                            .addInput(new Input("Password")))
                    .addOption(new ExecuteOption("Remove user from storage") {
                        @Override
                        public void execute() {
                            String username = getInput("username").getValue();

                            try {
                                storage.removeUser(new User(username, null));
                            } catch (StorageException e) {
                                log(e);
                            }
                        }
                    }
                            .addInput(new Input("Username")))
                    .addOption(new ExecuteOption("Upload single file") {
                        @Override
                        public void execute() {
                            String sourcePath = getInput("source").getValue();
                            String destinationPath = getInput("destination").getValue();

                            File source = new Path(sourcePath).resolve();

                            Directory destination;

                            try {
                                destination = (Directory) new Path(destinationPath).resolve();
                            } catch (ClassCastException e) {
                                log("Directory not selected");
                                return;
                            }

                            try {
                                source.upload(destination);
                            } catch (StorageException e) {
                                log(e);
                            }

                            log("Uploaded successfully");
                        }
                    }
                            .addInput(new Input("File"))
                            .addInput(new Input("Destination")))
                    .addOption(new Option("Upload multiple files"))
                    .addOption(new ExecuteOption("Download file") {
                        @Override
                        public void execute() {
                            String sourcePath = getInput("source").getValue();
                            String destination = getInput("destination").getValue();

                            File source = new Path(sourcePath).resolve();

                            try {
                                source.download(destination);
                            } catch (StorageException e) {
                                log(e);
                            }

                            log("Downloaded successfully");
                        }
                    }.addInput(new Input("Source"))
                            .addInput(new Input("Destination")))
                    .addOption(new ExecuteOption("Copy file") {
                        @Override
                        public void execute() {
                            String sourcePath = getInput("source").getValue();
                            String destinationPath = getInput("destination").getValue();

                            File source = new Path(sourcePath).resolve();
                            Directory destination;

                            try {
                                destination = (Directory) new Path(destinationPath).resolve();
                            } catch (ClassCastException e) {
                                log("Directory not selected");
                                return;
                            }

                            try {
                                source.copy(destination);
                            } catch (StorageException e) {
                                log(e);
                            }

                            log("Copied successfully");
                        }
                    }.addInput(new Input("Source"))
                            .addInput(new Input("Destination")))
                    .addOption(new ExecuteOption("Move file") {
                        @Override
                        public void execute() {
                            String sourcePath = getInput("source").getValue();
                            String destinationPath = getInput("destination").getValue();

                            File source = new Path(sourcePath).resolve();
                            Directory destination;

                            try {
                                destination = (Directory) new Path(destinationPath).resolve();
                            } catch (ClassCastException e) {
                                log("Directory not selected");
                                return;
                            }

                            try {
                                source.copy(destination);
                            } catch (StorageException e) {
                                log(e);
                            }

                            log("Moved successfully");
                        }
                    }.addInput(new Input("Source"))
                            .addInput(new Input("Destination")))
                    .addOption(new ExecuteOption("Delete file") {
                        @Override
                        public void execute() {
                            String path = getInput("file").getValue();

                            File file = new Path(path).resolve();

                            try {
                                file.delete();
                            } catch (StorageException e) {
                                log(e);
                            }
                        }
                    }.addInput(new Input("File")))
                    .addOption(new ExecuteOption("Get metadata") {
                        @Override
                        public void execute() {
                            String path = getInput("file").getValue();

                            File file = new Path(path).resolve();
                            Metadata metadata = file.getMetadata();

                            for (String key : metadata.getKeys()) {
                                String value = metadata.get(key);

                                log(key + ": " + value);
                            }
                        }
                    }
                            .addInput(new Input("File")))
                    .addOption(new ExecuteOption("Add metadata") {
                        @Override
                        public void execute() {
                            String path = getInput("file").getValue();
                            String key = getInput("key").getValue();
                            String value = getInput("value").getValue();

                            File file = new Path(path).resolve();
                            Metadata metadata = file.getMetadata();
                            metadata.add(key, value);

                            log("Metadata successfully added");
                        }
                    }
                            .addInput(new Input("File"))
                            .addInput(new Input("Key"))
                            .addInput(new Input("Value")))
                    .addOption(new ExecuteOption("List directory") {
                        @Override
                        public void execute() {
                            String path = getInput("directory").getValue();

                            Directory directory;

                            try {
                                directory = (Directory) new Path(path).resolve();
                            } catch (ClassCastException e) {
                                log("Directory not selected");
                                return;
                            }

                            List<File> children = directory.getChildren();

                            if (children.isEmpty()) {
                                log("Empty directory");
                                return;
                            }

                            for (File child : children) {
                                log(child.getName());
                            }
                        }
                    }
                            .addInput(new Input("Directory")))
                    .addOption(new Option("Search directory")
                            .setQuestion(new Question("What is search type?")
                                    .addOption(new ExecuteOption(CriteriaType.NAME_EQUALS.toString()) {
                                        @Override
                                        public void execute() {
                                            CriteriaType type = CriteriaType.NAME_EQUALS;

                                            String path = getInput("directory").getValue();
                                            String query = getInput("query").getValue();

                                            Directory directory;

                                            try {
                                                directory = (Directory) new Path(path).resolve();
                                            } catch (ClassCastException e) {
                                                log("Directory not selected");
                                                return;
                                            }

                                            List<File> matches = directory.search(new Criteria(type, query));

                                            if (matches.isEmpty()) {
                                                log("No matches");
                                                return;
                                            }

                                            for (File match : matches) {
                                                log(match.getName());
                                            }
                                        }
                                    })
                                    .addOption(new ExecuteOption(CriteriaType.NAME_CONTAINS.toString()) {
                                        @Override
                                        public void execute() {
                                            CriteriaType type = CriteriaType.NAME_CONTAINS;

                                            String path = getInput("directory").getValue();
                                            String query = getInput("query").getValue();

                                            Directory directory;

                                            try {
                                                directory = (Directory) new Path(path).resolve();
                                            } catch (ClassCastException e) {
                                                log("Directory not selected");
                                                return;
                                            }

                                            List<File> matches = directory.search(new Criteria(type, query));

                                            if (matches.isEmpty()) {
                                                log("No matches");
                                                return;
                                            }

                                            for (File match : matches) {
                                                log(match.getName());
                                            }
                                        }
                                    })
                                    .addOption(new ExecuteOption(CriteriaType.NAME_STARTS_WITH.toString()) {
                                        @Override
                                        public void execute() {
                                            CriteriaType type = CriteriaType.NAME_STARTS_WITH;

                                            String path = getInput("directory").getValue();
                                            String query = getInput("query").getValue();

                                            Directory directory;

                                            try {
                                                directory = (Directory) new Path(path).resolve();
                                            } catch (ClassCastException e) {
                                                log("Directory not selected");
                                                return;
                                            }

                                            List<File> matches = directory.search(new Criteria(type, query));

                                            if (matches.isEmpty()) {
                                                log("No matches");
                                                return;
                                            }

                                            for (File match : matches) {
                                                log(match.getName());
                                            }
                                        }
                                    })
                                    .addOption(new ExecuteOption(CriteriaType.NAME_ENDS_WITH.toString()) {
                                        @Override
                                        public void execute() {
                                            CriteriaType type = CriteriaType.NAME_ENDS_WITH;

                                            String path = getInput("directory").getValue();
                                            String query = getInput("query").getValue();

                                            Directory directory;

                                            try {
                                                directory = (Directory) new Path(path).resolve();
                                            } catch (ClassCastException e) {
                                                log("Directory not selected");
                                                return;
                                            }

                                            List<File> matches = directory.search(new Criteria(type, query));

                                            if (matches.isEmpty()) {
                                                log("No matches");
                                                return;
                                            }

                                            for (File match : matches) {
                                                log(match.getName());
                                            }
                                        }
                                    })
                            )
                            .addInput(new Input("Directory"))
                            .addInput(new Input("Query")));
        }
    }
}
