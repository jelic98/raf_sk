package rs.raf.storage.spec.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Holds file metadata as key value pairs.
 */
public final class Metadata {

    private Map<String, String> metadata;

    public Metadata() {
        metadata = new HashMap<>();
    }

    /**
     * Retrieves metadata from key.
     * @param key Key to be searched for in set of metadata.
     * @return Requested metadata value.
     */
    public String get(String key) {
        return metadata.get(key);
    }

    /**
     * Adds metadata key value pair.
     * @param key Key to be used for searching metadata later.
     * @param value Value to be stored.
     */
    public void add(String key, String value) {
        metadata.put(key, value);
    }

    /**
     * Removes matadata key value pair.
     * @param key Key to be removed from metadata along with corresponding value.
     */
    public void remove(String key) {
        metadata.remove(key);
    }

    /**
     * Clears metadata. Another way of doing this will be
     *      calling {@link #remove(String)} for every key in current metadata.
     */
    public void clear() {
        metadata.clear();
    }

    /**
     * Retrieves set of all metadata keys.
     * @return Set of all keys present in current metadata.
     */
    public Set<String> getKeys() {
        return metadata.keySet();
    }
}
