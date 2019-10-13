package rs.raf.storage.spec.registry;

import org.json.JSONArray;
import org.json.JSONObject;
import rs.raf.storage.spec.auth.Privilege;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Metadata;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.res.Res;
import java.util.Iterator;
import java.util.Map;

final class RegistryLoader {

    private JsonParser parser;
    private FileExtractor extractor;
    private Hasher hasher;

    RegistryLoader(JsonParser parser, FileExtractor extractor, Hasher hasher) {
        this.parser = parser;
        this.extractor = extractor;
        this.hasher = hasher;
    }

    void load(Storage storage) {
        loadFiles(storage);

        loadUsers(storage);
        loadMetadata(storage);
        loadForbiddenTypes(storage);
    }

    private void loadFiles(Storage storage) {
        // TODO [CONSIDER] Load file tree with call to storage.setRoot()
    }

    private void loadUsers(Storage storage) {
        Map<String, File> files = hasher.hashFiles(extractor.extract(storage));

        try {
            JSONObject registryJson = parser.parseJson(Res.Registry.PATH);
            JSONObject usersJson = registryJson.getJSONObject(Res.Registry.KEY_USERS);

            Iterator<String> userHashes = usersJson.keys();

            while(userHashes.hasNext()) {
                JSONObject userJson = usersJson.getJSONObject(userHashes.next());

                User user = new User(userJson.getString(Res.Registry.KEY_USERNAME), userJson.getString(Res.Registry.KEY_PASSWORD));
                user.setSaved(true);

                if(registryJson.getString(Res.Registry.KEY_OWNER).equals(user.getName())) {
                    storage.setOwner(user);
                }

                JSONArray privilegesJson = userJson.getJSONArray(Res.Registry.KEY_PRIVILEGES);

                for(int i = 0; i < privilegesJson.length(); i++) {
                    JSONObject privilegeJson = privilegesJson.getJSONObject(i);
                    String fileHash = privilegeJson.getString(Res.Registry.KEY_FILE);
                    boolean read = privilegeJson.getBoolean(Res.Registry.KEY_READ);
                    boolean write = privilegeJson.getBoolean(Res.Registry.KEY_WRITE);
                    boolean delete = privilegeJson.getBoolean(Res.Registry.KEY_DELETE);

                    File file = files.get(fileHash);

                    if(file != null) {
                        user.addPrivilege(new Privilege(file, read, write, delete));
                    }
                }

                storage.addUser(user);
            }
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void loadMetadata(Storage storage) {
        Map<String, File> files = hasher.hashFiles(extractor.extract(storage));

        try {
            JSONObject registryJson = parser.parseJson(Res.Registry.PATH);
            JSONObject allMetadataJson = registryJson.getJSONObject(Res.Registry.KEY_METADATA);

            for(Map.Entry<String, File> entry : files.entrySet()) {
                String fileHash = entry.getKey();
                File file = entry.getValue();

                Metadata metadata = new Metadata();

                JSONObject metadataJson = allMetadataJson.getJSONObject(fileHash);

                Iterator<String> fileHashes = metadataJson.keys();

                while(fileHashes.hasNext()) {
                    String metadataKey = fileHashes.next();
                    String metadataValue = metadataJson.getString(metadataKey);

                    metadata.add(metadataKey, metadataValue);
                }

                file.setMetadata(metadata);
            }
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void loadForbiddenTypes(Storage storage) {
        try {
            JSONObject registryJson = parser.parseJson(Res.Registry.PATH);
            JSONArray typesJson = registryJson.getJSONArray(Res.Registry.KEY_FORBIDDEN_TYPES);

            for(int i = 0; i < typesJson.length(); i++) {
                storage.forbidType(typesJson.getString(i));
            }
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
