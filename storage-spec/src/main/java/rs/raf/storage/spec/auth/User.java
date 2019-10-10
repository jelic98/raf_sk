package rs.raf.storage.spec.auth;

import java.util.LinkedList;
import java.util.List;

public final class User {

    private String name, password;
    private List<Privilege> privileges;

    public User(String name, String password) {
        this.name = name;
        this.password = password;

        privileges = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
