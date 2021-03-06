package rs.raf.storage.spec.registry;

import org.json.JSONArray;
import org.json.JSONObject;
import rs.raf.storage.spec.auth.Privilege;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Metadata;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.RegistryException;
import rs.raf.storage.spec.res.Res;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

final class RegistrySaver {

    private JsonParser parser;
    private FileExtractor extractor;
    private Hasher hasher;
    private boolean registryExists;

    RegistrySaver(JsonParser parser, FileExtractor extractor, Hasher hasher) {
        this.parser = parser;
        this.extractor = extractor;
        this.hasher = hasher;
    }

    void save(Storage storage, boolean registryExists) throws RegistryException {
        this.registryExists = registryExists;

        saveUsers(storage);
        saveMetadata(storage);
        saveForbiddenTypes(storage);
    }

    private void saveUsers(Storage storage) throws RegistryException {
        Map<String, File> files = hasher.hashFiles(extractor.extract(storage));

        try {
            JSONObject registryJson = new JSONObject();

            if(registryExists) {
                registryJson = parser.parseJson(storage.getRegistryPath());
            }

            JSONObject usersJson = new JSONObject();

            for(User user : storage.getUsers()) {
                JSONObject userJson = new JSONObject();
                userJson.put(Res.Registry.KEY_PASSWORD, user.isSaved() ? user.getPassword() : hasher.hashPassword(user));

                if(storage.getOwner().getName().equals(user.isSaved() ? user.getName() : hasher.hashUsername(user))) {
                    registryJson.put(Res.Registry.KEY_OWNER, user.isSaved() ? user.getName() : hasher.hashUsername(user));
                }

                JSONArray privilegesJson = new JSONArray();

                for(Map.Entry<String, File> entry : files.entrySet()) {
                    String fileHash = entry.getKey();
                    File file = entry.getValue();

                    Privilege privilege = user.getPrivilege(file);

                    JSONObject privilegeJson = new JSONObject();
                    privilegeJson.put(Res.Registry.KEY_FILE, fileHash);
                    privilegeJson.put(Res.Registry.KEY_READ, privilege.canRead());
                    privilegeJson.put(Res.Registry.KEY_WRITE, privilege.canWrite());
                    privilegeJson.put(Res.Registry.KEY_DELETE, privilege.canDelete());

                    privilegesJson.put(privilegeJson);
                }

                userJson.put(Res.Registry.KEY_PRIVILEGES, privilegesJson);
                usersJson.put(hasher.hashUsername(user), userJson);
            }

            registryJson.remove(Res.Registry.KEY_USERS);
            registryJson.put(Res.Registry.KEY_USERS, usersJson);

            Files.write(Paths.get(storage.getRegistryPath()), registryJson.toString().getBytes());
        }catch(Exception e) {
            throw new RegistryException();
        }
    }

    private void saveMetadata(Storage storage) throws RegistryException {
        Map<String, File> files = hasher.hashFiles(extractor.extract(storage));

        try {
            JSONObject allMetadataJson = new JSONObject();

            for(Map.Entry<String, File> entry : files.entrySet()) {
                String fileHash = entry.getKey();
                File file = entry.getValue();

                JSONObject metadataJson = new JSONObject();

                Metadata metadata = file.getMetadata();

                for(String key : metadata.getKeys()) {
                    metadataJson.put(key, metadata.get(key));
                }

                allMetadataJson.put(fileHash, metadataJson);
            }

            JSONObject registryJson = parser.parseJson(storage.getRegistryPath());
            registryJson.remove(Res.Registry.KEY_METADATA);
            registryJson.put(Res.Registry.KEY_METADATA, allMetadataJson);

            Files.write(Paths.get(storage.getRegistryPath()), registryJson.toString().getBytes());
        }catch(Exception e) {
            throw new RegistryException();
        }
    }

    private void saveForbiddenTypes(Storage storage) throws RegistryException {
        try {
            JSONArray typesArray = new JSONArray();

            for(String type : storage.getForbiddenTypes()) {
                typesArray.put(type);
            }

            JSONObject registryJson = parser.parseJson(storage.getRegistryPath());
            registryJson.remove(Res.Registry.KEY_FORBIDDEN_TYPES);
            registryJson.put(Res.Registry.KEY_FORBIDDEN_TYPES, typesArray);

            Files.write(Paths.get(storage.getRegistryPath()), registryJson.toString().getBytes());
        }catch(Exception e) {
            throw new RegistryException();
        }
    }
}
