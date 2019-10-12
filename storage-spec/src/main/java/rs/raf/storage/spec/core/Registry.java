package rs.raf.storage.spec.core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import rs.raf.storage.spec.auth.Privilege;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.exception.AuthenticationException;
import rs.raf.storage.spec.res.Res;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class Registry {

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

    private Hasher hasher;

    Registry() {
        hasher = new Hasher();
    }

    void load(User user, Storage storage) throws AuthenticationException {
        if(!authenticationPassed(user)) {
            throw new AuthenticationException(user);
        }

        loadFiles(storage);

        loadUsers(storage);
        loadMetadata(storage);
        loadForbiddenTypes(storage);
    }

    private void loadFiles(Storage storage) {
        // TODO Populate file tree in implementation
    }

    private void loadUsers(Storage storage) {
        Map<String, File> files = hasher.hashFiles(new PathResolver().extract(storage));

        try {
            JSONObject registryJson = parseJson(Res.Registry.PATH);
            JSONObject usersJson = registryJson.getJSONObject("users");

            Iterator<String> userHashes = usersJson.keys();

            while(userHashes.hasNext()) {
                JSONObject userJson = usersJson.getJSONObject(userHashes.next());

                User user = new User(userJson.getString("username"), userJson.getString("password"));
                user.setSaved(true);

                JSONArray privilegesJson = userJson.getJSONArray("privileges");

                for(int i = 0; i < privilegesJson.length(); i++) {
                    JSONObject privilegeJson = privilegesJson.getJSONObject(i);
                    String fileHash = privilegeJson.getString("file");
                    boolean read = privilegeJson.getBoolean("read");
                    boolean write = privilegeJson.getBoolean("write");
                    boolean delete = privilegeJson.getBoolean("delete");

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
        Map<String, File> files = hasher.hashFiles(new PathResolver().extract(storage));

        try {
            JSONObject registryJson = parseJson(Res.Registry.PATH);
            JSONObject allMetadataJson = registryJson.getJSONObject("metadata");

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
            JSONObject registryJson = parseJson(Res.Registry.PATH);
            JSONArray typesJson = registryJson.getJSONArray("forbiddenTypes");

            for(int i = 0; i < typesJson.length(); i++) {
                storage.forbidType(typesJson.getString(i));
            }
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean authenticationPassed(User user) {
        try {
            JSONObject registryJson = parseJson(Res.Registry.PATH);
            JSONObject userJson = registryJson.getJSONObject("users").getJSONObject(hasher.hashUsername(user));

            return userJson.get("password").equals(hasher.hashPassword(user));
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    private JSONObject parseJson(String path) throws IOException {
        return new JSONObject(new JSONTokener(new FileReader(path)));
    }

    private static class Hasher {

        private String hashUsername(User user) {
            return hash(user.getName());
        }

        private String hashPassword(User user) {
            if(user.isSaved()) {
                return user.getPassword();
            }

            return hash(user.getPassword());
        }

        private String hashFilepath(File file) {
            return hash(file.getPath());
        }

        private Map<String, File> hashFiles(List<File> fileList) {
            Map<String, File> fileMap = new HashMap<>();

            for(File file : fileList) {
                fileMap.put(hashFilepath(file), file);
            }

            return fileMap;
        }

        private String hash(String value) {
            try{
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(value.getBytes());

                StringBuilder builder = new StringBuilder();

                for(byte b : md.digest()) {
                    builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
                }

                return builder.toString();
            }catch(Exception e) {
                return value;
            }
        }
    }
}
