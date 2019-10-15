package rs.raf.storage.app;

import com.konzole.framework.*;
import rs.raf.storage.spec.StorageDriver;
import rs.raf.storage.spec.StorageDriverManager;
import rs.raf.storage.spec.auth.Privilege;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.*;
import rs.raf.storage.spec.exception.NonExistenceException;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;
import rs.raf.storage.spec.search.Criteria;
import rs.raf.storage.spec.search.CriteriaType;
import java.util.LinkedList;
import java.util.List;

public class App {

    private StorageStructure structure;

    public static void main(String[] args) {
        App app = new App();

        try {
            app.structure = new StorageStructure(app);

            new Lifecycle(app.structure, app.structure.getCallback()).run();
        } catch (Exception e) {
            app.log(e);
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private void log(Exception e) {
        // FOR TESTING PURPOSES ONLY
         e.printStackTrace();

        System.err.println(e.getMessage());

        try {
            structure.getStorage().disconnect(structure.getUser());
        }catch(StorageException e1) {
            e1.printStackTrace();
        }

        System.exit(0);
    }

    private static final class StorageStructure extends Structure {

        private LifecycleCallback callback;
        private StorageDriver driver;
        private Storage storage;
        private User user;
        private App app;

        StorageStructure(App app) throws Exception {
            this.app = app;

            Class.forName("rs.raf.storage.local.LocalStorageDriver");

            driver = StorageDriverManager.getDriver();
            storage = Storage.instance();
            callback = new LifecycleCallbackAdapter() {
                @Override
                public void onExit() {
                    try {
                        storage.disconnect(user);
                    }catch(StorageException e) {
                        app.log(e);
                    }
                }
            };
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
                                app.log(e);
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
                                app.log(e);
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
                                app.log(e);
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
                                app.log(e);
                            }
                        }
                    }
                            .addInput(new Input("Username")))
                    .addOption(new ExecuteOption("Set privilege") {
						@Override
						public void execute() {
							String username = getInput("username").getValue();
							String path = getInput("file").getValue();
							boolean read = Boolean.parseBoolean(getInput("read").getValue());
							boolean write = Boolean.parseBoolean(getInput("write").getValue());
							boolean delete = Boolean.parseBoolean(getInput("delete").getValue());
							
							File source;
							
							try {
                                source = driver.getFile(path);
								
								for(User u : storage.getUsers()) {
									if(u.getName().equals(username)) {
										u.addPrivilege(new Privilege(source, read, write, delete));
									}
								}
							} catch (ClassCastException e) {
                                log("Directory not selected");
                                return;
                            }

                            log("Privilege set successfully");
						}
					}
                    		.addInput(new Input("Username"))
                    		.addInput(new Input("File"))
                    		.addInput(new Input("Read"))
                    		.addInput(new Input("Write"))
                    		.addInput(new Input("Delete")))
                    .addOption(new ExecuteOption("Upload single file") {
                        @Override
                        public void execute() {
                            String sourcePath = getInput("file").getValue();
                            String destinationPath = getInput("destination").getValue();

                            File source;
                            Directory destination;

                            try {
                                source = driver.getFile(sourcePath);
                                destination = (Directory) new Path(Res.Wildcard.SEPARATOR + destinationPath, storage).resolve();
                                source.upload(destination);
                            } catch (ClassCastException e) {
                                log("Directory not selected");
                                return;
                            } catch (StorageException e) {
                                app.log(e);
                            }

                            log("Uploaded successfully");
                        }
                    }
                            .addInput(new Input("File"))
                            .addInput(new Input("Destination")))
                    .addOption(new ExecuteOption("Upload multiple files") {
                        @Override
                        public void execute() {
                            String[] sourcePaths = getInput("sources").getValue().split("\\w");
                            String destinationPath = getInput("destination").getValue();

                            List<File> files = new LinkedList<>();

                            for(String sourcePath : sourcePaths) {
                                files.add(driver.getFile(sourcePath));
                            }

                            try {
                                Directory destination = (Directory) new Path(Res.Wildcard.SEPARATOR + destinationPath, storage).resolve();
                                destination.upload(files);
                            } catch (ClassCastException e) {
                                log("Directory not selected");
                                return;
                            } catch (StorageException e) {
                                app.log(e);
                            }

                            log("Uploaded successfully");
                        }
                    }.addInput(new Input("sources", "Sources (space separated)"))
                    .addInput(new Input("Destination")))
                    .addOption(new ExecuteOption("Download file") {
                        @Override
                        public void execute() {
                            String sourcePath = getInput("source").getValue();
                            String destination = getInput("destination").getValue();

                            File source;

                            try {
                                source = driver.getFile(sourcePath);
                                source.download(destination);
                            }catch (StorageException e) {
                                app.log(e);
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

                            File source;
                            Directory destination;

                            try {
                                source = driver.getFile(sourcePath);
                                destination = (Directory) new Path(destinationPath, storage).resolve();
                                source.copy(destination);
                            } catch (ClassCastException e) {
                                log("Directory not selected");
                                return;
                            } catch (StorageException e) {
                                app.log(e);
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

                            File source;
                            Directory destination;

                            try {
                                source = driver.getFile(sourcePath);
                                destination = (Directory) new Path(destinationPath, storage).resolve();
                                source.copy(destination);
                            } catch (ClassCastException e) {
                                log("Directory not selected");
                                return;
                            } catch (StorageException e) {
								app.log(e);
                            }

                            log("Moved successfully");
                        }
                    }.addInput(new Input("Source"))
                            .addInput(new Input("Destination")))
                    .addOption(new ExecuteOption("Delete file") {
                        @Override
                        public void execute() {
                            String path = getInput("file").getValue();

                            File file;

                            try {
                            	file = new Path(path, storage).resolve();
                                file.delete();
                            } catch (StorageException e) {
                                app.log(e);
                            }
                        }
                    }.addInput(new Input("File")))
                    .addOption(new ExecuteOption("Get metadata") {
                        @Override
                        public void execute() {
                            String path = getInput("file").getValue();

                            File file = null;

							try {
								file = new Path(path, storage).resolve();
							} catch(StorageException e) {
                            	app.log(e);
                            }

							if(file != null) {
                                Metadata metadata = file.getMetadata();

                                for (String key : metadata.getKeys()) {
                                    String value = metadata.get(key);

                                    log(key + ": " + value);
                                }
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

                            File file = null;

							try {
								file = new Path(path, storage).resolve();
							} catch(StorageException e) {
                            	app.log(e);
                            }

							if(file != null) {
                                Metadata metadata = file.getMetadata();
                                metadata.add(key, value);
                            }

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

                            Directory directory = null;

                            try {
                                directory = (Directory) new Path(path, storage).resolve();
                            } catch (ClassCastException e) {
                                log("Directory not selected");
                                return;
                            } catch(StorageException e) {
                                app.log(e);
                            }

                            if(directory != null) {
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

                                            Directory directory = null;

                                            try {
                                                directory = (Directory) new Path(path, storage).resolve();
                                            } catch (ClassCastException e) {
                                                log("Directory not selected");
                                                return;
                                            } catch(NonExistenceException e) {
                                                app.log(e);
                                            }

                                            if(directory != null) {
                                                List<File> matches = directory.search(new Criteria(type, query));

                                                if (matches.isEmpty()) {
                                                    log("No matches");
                                                    return;
                                                }

                                                for (File match : matches) {
                                                    log(match.getName());
                                                }
                                            }
                                        }
                                    })
                                    .addOption(new ExecuteOption(CriteriaType.NAME_CONTAINS.toString()) {
                                        @Override
                                        public void execute() {
                                            CriteriaType type = CriteriaType.NAME_CONTAINS;

                                            String path = getInput("directory").getValue();
                                            String query = getInput("query").getValue();

                                            Directory directory = null;

                                            try {
                                                directory = (Directory) new Path(path, storage).resolve();
                                            } catch (ClassCastException e) {
                                                log("Directory not selected");
                                                return;
                                            } catch(NonExistenceException e) {
                                                app.log(e);
                                            }

                                            if(directory != null) {
                                                List<File> matches = directory.search(new Criteria(type, query));

                                                if (matches.isEmpty()) {
                                                    log("No matches");
                                                    return;
                                                }

                                                for (File match : matches) {
                                                    log(match.getName());
                                                }
                                            }
                                        }
                                    })
                                    .addOption(new ExecuteOption(CriteriaType.NAME_STARTS_WITH.toString()) {
                                        @Override
                                        public void execute() {
                                            CriteriaType type = CriteriaType.NAME_STARTS_WITH;

                                            String path = getInput("directory").getValue();
                                            String query = getInput("query").getValue();

                                            Directory directory = null;

                                            try {
                                                directory = (Directory) new Path(path, storage).resolve();
                                            } catch (ClassCastException e) {
                                                log("Directory not selected");
                                                return;
                                            } catch(NonExistenceException e) {
                                                app.log(e);
                                            }

                                            if(directory != null) {
                                                List<File> matches = directory.search(new Criteria(type, query));

                                                if (matches.isEmpty()) {
                                                    log("No matches");
                                                    return;
                                                }

                                                for (File match : matches) {
                                                    log(match.getName());
                                                }
                                            }
                                        }
                                    })
                                    .addOption(new ExecuteOption(CriteriaType.NAME_ENDS_WITH.toString()) {
                                        @Override
                                        public void execute() {
                                            CriteriaType type = CriteriaType.NAME_ENDS_WITH;

                                            String path = getInput("directory").getValue();
                                            String query = getInput("query").getValue();

                                            Directory directory = null;

                                            try {
                                                directory = (Directory) new Path(path, storage).resolve();
                                            } catch (ClassCastException e) {
                                                log("Directory not selected");
                                                return;
                                            } catch(NonExistenceException e) {
                                                app.log(e);
                                            }

                                            if(directory != null) {
                                                List<File> matches = directory.search(new Criteria(type, query));

                                                if (matches.isEmpty()) {
                                                    log("No matches");
                                                    return;
                                                }

                                                for (File match : matches) {
                                                    log(match.getName());
                                                }
                                            }
                                        }
                                    })
                            )
                            .addInput(new Input("Directory"))
                            .addInput(new Input("Query")));
        }

        private Storage getStorage() {
            return storage;
        }

        private User getUser() {
            return user;
        }

        private LifecycleCallback getCallback() {
            return callback;
        }
    }
}
