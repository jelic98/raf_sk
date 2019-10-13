package rs.raf.storage.spec.registry;

import rs.raf.storage.spec.core.Storage;

final class RegistrySaver {

    /*
        EXAMPLE REGISTRY JSON

        {
            "users": {
                "user_1_hash": {
                    "password": "password_hash",
                    "privileges": [
                        {
                            "file": "file_1_hash",
                            "read": true,
                            "write": true,
                            "delete": false,
                        }
                    ]
                }
            },
            "metadata": {
                "file_1_hash": {
                    "key1": "value1",
                    "key2": "value2"
                }
            },
            "forbiddenTypes": [
                "exe",
                "rar",
                "sh"
            ]
        }
     */

    private JsonParser parser;
    private FileExtractor extractor;
    private Hasher hasher;

    RegistrySaver(JsonParser parser, FileExtractor extractor, Hasher hasher) {
        this.parser = parser;
        this.extractor = extractor;
        this.hasher = hasher;
    }

    void save(Storage storage) {
        saveFiles(storage);

        saveUsers(storage);
        saveMetadata(storage);
        saveForbiddenTypes(storage);
    }

    private void saveFiles(Storage storage) {
        // TODO Save file tree to local file system in implementation
    }

    private void saveUsers(Storage storage) {
        // TODO Save users in registry json file in implementation
    }

    private void saveMetadata(Storage storage) {
        // TODO Save metadata in registry json file in implementation
    }

    private void saveForbiddenTypes(Storage storage) {
        // TODO Save forbidden types in registry json file in implementation

    }
}
