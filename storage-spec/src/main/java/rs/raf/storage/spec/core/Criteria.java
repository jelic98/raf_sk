package rs.raf.storage.spec.core;

public final class Criteria {

    public enum Type {
        NAME_EQUALS,
        NAME_CONTAINS,
        NAME_STARTS_WITH,
        NAME_ENDS_WITH,
        CONTENT_CONTAINS
    }

    private final Type type;
    private final String query;

    public Criteria(Type type, String query) {
        this.type = type;
        this.query = query;
    }

    public final Type getType() {
        return type;
    }

    public final String getQuery() {
        return query;
    }
}
