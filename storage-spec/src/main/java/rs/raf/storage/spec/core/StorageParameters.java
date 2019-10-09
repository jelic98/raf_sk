package rs.raf.storage.spec.core;

import java.util.HashMap;
import java.util.Map;

public final class StorageParameters {

    private Map<String, String> parameters;

    public StorageParameters() {
        parameters = new HashMap<>();
    }

    public String get(String key) {
        return parameters.get(key);
    }

    public void set(String key, String value) {
        parameters.put(key, value);
    }
}
