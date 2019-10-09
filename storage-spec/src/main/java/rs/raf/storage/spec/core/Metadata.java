package rs.raf.storage.spec.core;

import java.util.HashMap;
import java.util.Map;

public final class Metadata {

    private Map<String, String> metadata;

    public Metadata() {
        metadata = new HashMap<>();
    }

    public String get(String key) {
        return metadata.get(key);
    }

    public void set(String key, String value) {
        metadata.put(key, value);
    }
}
