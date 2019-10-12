package rs.raf.storage.spec.auth;

import rs.raf.storage.spec.core.File;
import java.util.LinkedList;
import java.util.List;

public final class User {

    private static final Authorizer authorizer;

    private String name, password;
    private List<Privilege> privileges;
    private boolean saved;

    static {
        authorizer = new Authorizer();
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;

        privileges = new LinkedList<>();
        saved = false;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void addPrivilege(Privilege privilege) {
        privileges.remove(privilege);
        privileges.add(privilege);
    }

    public Privilege getPrivilege(File file) {
        for(Privilege privilege : privileges) {
            if(privilege.getFile().equals(file)) {
                return privilege;
            }
        }

        return authorizer.getDefaultPrivilege(file);
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User)) {
            return false;
        }

        User user = (User) obj;

        return user.getName().equals(getName());
    }
}
