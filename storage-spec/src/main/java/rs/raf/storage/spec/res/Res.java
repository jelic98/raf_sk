package rs.raf.storage.spec.res;

public class Res {

    public static final String FILE_CONFIG = "/.config";

    public static final String FORMAT_DATE = "dd. MMM YYYY";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_DATETIME = "dd. MMM YYYY, HH:mm:ss";

    public static final String WILDCARD_USER = "${user}";
    public static final String WILDCARD_FILE = "${file}";
    public static final String WILDCARD_TYPE = "${type}";

    public static final String ERROR_NON_EXISTENCE = String.format(
            "%s %s does not exist",
            WILDCARD_TYPE,
            WILDCARD_FILE);
    public static final String ERROR_PRIVILEGE = String.format(
            "User %s is not authorized to perform the specified operation on the file %s",
            WILDCARD_USER,
            WILDCARD_FILE);
}
