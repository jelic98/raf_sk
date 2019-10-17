package rs.raf.storage.spec.auth;

import rs.raf.storage.spec.core.File;
import java.util.LinkedList;
import java.util.List;

public final class User {

    private String name, password;
    private List<Privilege> privileges;
    private boolean saved;

    /**
     * One and only constructor that accepts file and flags.
     * @param name User name.
     * @param password User password. Hashing is applied before serialization.
     */
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

    /**
     * Adds new privilege to the list. Used for granting and revoking file access.
     * @param privilege New user privilege.
     */
    public void addPrivilege(Privilege privilege) {
        privileges.remove(privilege);
        privileges.add(privilege);
    }

    /**
     * Returns requested privilege or creates a default one which is granting all access.
     * @param file File for which privilege will be returned.
     * @return Requested privilege or default one.
     */
    public Privilege getPrivilege(File file) {
        for(Privilege privilege : privileges) {
            if(privilege.getFile().equals(file)) {
                return privilege;
            }
        }

        return new Privilege(file, true, true, true);
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
