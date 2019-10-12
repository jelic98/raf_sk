package rs.raf.storage.spec.auth;

import rs.raf.storage.spec.core.File;
import java.util.LinkedList;
import java.util.List;

public final class User {

    private static final Authorizer authorizer;

    private String name, password;
    private List<Privilege> privileges;

    static {
        authorizer = new Authorizer();
    }

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

    public Privilege getPrivilege(File file) {
        for(Privilege privilege : privileges) {
            if(privilege.getFile().equals(file)) {
                return privilege;
            }
        }

        return authorizer.getDefaultPrivilege(file);
    }
}
