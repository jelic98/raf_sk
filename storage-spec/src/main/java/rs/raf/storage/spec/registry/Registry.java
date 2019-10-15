package rs.raf.storage.spec.registry;

import org.json.JSONObject;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.Path;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.AuthenticationException;
import rs.raf.storage.spec.exception.NonExistenceException;
import rs.raf.storage.spec.exception.RegistryException;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;

import java.io.File;
import java.io.IOException;

public final class Registry {

    private RegistryLoader loader;
    private RegistrySaver saver;
    private JsonParser parser;
    private FileExtractor extractor;
    private Hasher hasher;

    public Registry() {
        parser = new JsonParser();
        extractor = new FileExtractor();
        hasher = new Hasher();

        loader = new RegistryLoader(parser, extractor, hasher);
        saver = new RegistrySaver(parser, extractor, hasher);
    }

    public void load(User user, Storage storage) throws StorageException {
        if(!registryExists(storage)) {
            return;
        }

        if(!authenticationPassed(user, storage)) {
            throw new AuthenticationException(user);
        }

        loader.load(storage);
    }

    public void save(User user, Storage storage) throws StorageException {
        if(!registryExists(storage)) {
            return;
        }

        if(!authenticationPassed(user, storage)) {
            throw new AuthenticationException(user);
        }

        saver.save(storage);
    }

    private boolean authenticationPassed(User user, Storage storage) throws RegistryException {
        try {
            JSONObject registryJson = parser.parseJson(storage.getRegistryPath());
            JSONObject userJson = registryJson.getJSONObject(Res.Registry.KEY_USERS).getJSONObject(hasher.hashUsername(user));

            return userJson.get(Res.Registry.KEY_PASSWORD).equals(hasher.hashPassword(user));
        }catch(Exception e) {
            throw new RegistryException();
        }
    }

    private boolean registryExists(Storage storage) throws RegistryException {
        File registryFile = new File(storage.getRegistryPath());

        if(!registryFile.exists()) {
            registryFile.getParentFile().mkdirs();

            try {
                registryFile.createNewFile();
            }catch(IOException e) {
                throw new RegistryException();
            }

            return false;
        }

        return registryFile.length() > 0;
    }
}
