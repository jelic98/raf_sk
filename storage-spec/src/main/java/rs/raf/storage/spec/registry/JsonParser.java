package rs.raf.storage.spec.registry;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;

final class JsonParser {

    JSONObject parseJson(String path) throws IOException {
        return new JSONObject(new JSONTokener(new FileReader(path)));
    }
}
