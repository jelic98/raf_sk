package rs.raf.storage.spec.auth;

import java.util.HashMap;
import java.util.Map;
import rs.raf.storage.spec.core.File;

public final class User {

    private String name, password;
    private Map<File, Privilege> privileges;

    public User(String name, String password) {
        this.name = name;
        this.password = password;

        privileges = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
