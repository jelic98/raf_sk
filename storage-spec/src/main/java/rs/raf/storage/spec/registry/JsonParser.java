package rs.raf.storage.spec.registry;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;

final class JsonParser {

    JSONObject parseJson(String path) throws Exception {
        return new JSONObject(new JSONTokener(new FileReader(path)));
    }
}
