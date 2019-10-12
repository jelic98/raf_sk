package rs.raf.storage.spec.search;

import rs.raf.storage.spec.core.File;

public final class Criteria {

    private final CriteriaType type;
    private final String query;

    public Criteria(CriteriaType type, String query) {
        this.type = type;
        this.query = query;
    }

    public final CriteriaType getType() {
        return type;
    }

    public final String getQuery() {
        return query;
    }

    public final boolean matches(File file) {
        return type.matches(this, file);
    }
}
