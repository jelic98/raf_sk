package rs.raf.storage.app.storage;

import rs.raf.storage.app.model.*;

public final class StorageStructure extends Structure {

    @Override
    protected Question create() {
        return new Question("What operation to execute?")
                .addOption(new Option("Upload file").setQuestion(new Question("Where to upload file?")
                    .addOption(new ExecuteOption("Local storage") {
                        @Override
                        public void execute() {
                            Input source = getInput("source");
                            Input destination = getInput("destination");

                            System.out.println(String.format("Uploading file from %s to %s", source.getValue(), destination.getValue()));
                        }
                    }
                    .addInput(new Input("Source"))
                    .addInput(new Input("Destination")))
                ))
                .addOption(new ExecuteOption("Download file") {
                    @Override
                    public void execute() {
                        System.out.println("Downloading file...");
                    }
                });
    }
}
