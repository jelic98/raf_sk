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

    public void add(String key, String value) {
        metadata.put(key, value);
    }

    public void remove(String key) {
        metadata.remove(key);
    }

    public void clear() {
        metadata.clear();
    }
}
