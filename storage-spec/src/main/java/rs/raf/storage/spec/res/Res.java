package rs.raf.storage.spec.res;

/**
 * Isolates strings from runnable code and makes library as well as application more portable.
 */
public final class Res {

    /**
     * Strings related to registry file.
     */
    public static final class Registry {
        public final static String KEY_OWNER = "owner";
        public final static String KEY_USERS = "users";
        public final static String KEY_PASSWORD = "password";
        public final static String KEY_PRIVILEGES = "privileges";
        public final static String KEY_FILE = "file";
        public final static String KEY_READ = "read";
        public final static String KEY_WRITE = "write";
        public final static String KEY_DELETE = "delete";
        public final static String KEY_METADATA = "metadata";
        public final static String KEY_FORBIDDEN_TYPES = "forbiddenTypes";
        public static final String PATH = String.format(
                "%s%s.registry_",
                Wildcard.HOME,
                Wildcard.SEPARATOR);
    }

    /**
     * String wildcards that allow cross platform path usage.
     */
    public static final class Wildcard {
        public static final String SEPARATOR = "${separator}";
        public static final String HOME = "${home}";
        public static final String USER = "${user}";
        public static final String FILE = "${file}";
        public static final String TYPE = "${type}";
        public static final String DRIVER = "${driver}";
    }

    /**
     * Error messages.
     */
    public static final class Error {
        public static final String USER_PRIVILEGE = String.format(
                "User %s is not authorized to perform the specified operation",
                Wildcard.USER);
        public static final String FILE_PRIVILEGE = String.format(
                "User %s is not authorized to perform the specified operation on the file %s",
                Wildcard.USER,
                Wildcard.FILE);
        public static final String AUTHENTICATION = String.format(
                "Wrong password for user %s",
                Wildcard.USER);
        public static final String NON_EXISTENCE_PATH = String.format(
                "%s does not exist",
                Wildcard.FILE);
        public static final String NON_EXISTENCE_FILE = String.format(
                "%s %s does not exist",
                Wildcard.TYPE,
                Wildcard.FILE);
        public static final String FORBIDDEN_TYPE = String.format(
                "Type of file %s is forbidden in specified storage",
                Wildcard.FILE);
        public static final String REGISTRY = "Registry cannot be accessed";
        public static final String DRIVER_NOT_REGISTERED = "Driver is not registered";
        public static final String DRIVER_ALREADY_REGISTERED = String.format(
                "Driver %s is already registered",
                Wildcard.DRIVER);
    }
}
