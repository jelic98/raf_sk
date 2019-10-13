package rs.raf.storage.spec.registry;

import org.json.JSONObject;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.AuthenticationException;
import rs.raf.storage.spec.exception.RegistryException;
import rs.raf.storage.spec.exception.StorageException;
import rs.raf.storage.spec.res.Res;

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
        if(!authenticationPassed(user)) {
            throw new AuthenticationException(user);
        }

        initializeRegistryFile();

        loader.load(storage);
    }

    public void save(User user, Storage storage) throws StorageException {
        if(!authenticationPassed(user)) {
            throw new AuthenticationException(user);
        }

        initializeRegistryFile();

        saver.save(storage);
    }

    private boolean authenticationPassed(User user) throws RegistryException {
        try {
            JSONObject registryJson = parser.parseJson(Res.Registry.PATH);
            JSONObject userJson = registryJson.getJSONObject(Res.Registry.KEY_USERS).getJSONObject(hasher.hashUsername(user));

            return userJson.get(Res.Registry.KEY_PASSWORD).equals(hasher.hashPassword(user));
        }catch(Exception e) {
            throw new RegistryException();
        }
    }

    private void initializeRegistryFile() {
        // TODO [CONSIDER] Create registry file if it does not exist
    }
}
