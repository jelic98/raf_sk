package rs.raf.storage.spec.res;

public class Res {

    public static class Registry {
        public static final String PATH = "/.registry";
    }

    public static class Wildcard {
        public static final String SEPARATOR = "${separator}";
        public static final String USER = "${user}";
        public static final String FILE = "${file}";
        public static final String TYPE = "${type}";
        public static final String DRIVER = "${driver}";
    }

    public static class Error {
        public static final String NON_EXISTENCE = String.format(
                "%s %s does not exist",
                Wildcard.TYPE,
                Wildcard.FILE);
        public static final String PRIVILEGE = String.format(
                "User %s is not authorized to perform the specified operation on the file %s",
                Wildcard.USER,
                Wildcard.FILE);
        public static final String FORBIDDEN_TYPE = String.format(
                "Type of file %s is forbidden in specified storage",
                Wildcard.FILE);
        public static final String DRIVER_NOT_REGISTERED = "Driver is not registered";
        public static final String DRIVER_ALREADY_REGISTERED = String.format(
                "Driver %s is already registered",
                Wildcard.DRIVER);
    }
}
