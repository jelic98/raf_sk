package rs.raf.storage.app.storage;

import rs.raf.storage.app.model.ExecuteOption;
import rs.raf.storage.app.model.Option;
import rs.raf.storage.app.model.Question;
import rs.raf.storage.app.model.Structure;

public final class StorageStructure extends Structure {

    @Override
    protected Question create() {
        return new Question("What operation to execute?")
                .addOption(new Option("Upload file").setQuestion(new Question("Where to upload file?")
                    .addOption(new ExecuteOption("Local storage") {
                        @Override
                        public void execute() {
                            System.out.println("Uploading file...");
                        }
                    })))
                .addOption(new ExecuteOption("Download file") {
                    @Override
                    public void execute() {
                        System.out.println("Downloading file...");
                    }
                });
    }
}
