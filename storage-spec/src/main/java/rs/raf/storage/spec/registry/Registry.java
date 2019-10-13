package rs.raf.storage.spec.registry;

import org.json.JSONObject;
import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.Storage;
import rs.raf.storage.spec.exception.AuthenticationException;
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

    public void load(User user, Storage storage) throws AuthenticationException {
        if(!authenticationPassed(user)) {
            throw new AuthenticationException(user);
        }

        loader.load(storage);
    }

    public void save(User user, Storage storage) throws AuthenticationException {
        if(!authenticationPassed(user)) {
            throw new AuthenticationException(user);
        }

        saver.save(storage);
    }

    private boolean authenticationPassed(User user) {
        try {
            JSONObject registryJson = parser.parseJson(Res.Registry.PATH);
            JSONObject userJson = registryJson.getJSONObject("users").getJSONObject(hasher.hashUsername(user));

            return userJson.get("password").equals(hasher.hashPassword(user));
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return false;
    }
}
