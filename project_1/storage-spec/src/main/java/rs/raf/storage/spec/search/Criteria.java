package rs.raf.storage.spec.search;

import rs.raf.storage.spec.core.File;

/**
 * Holds search query and type of search.
 */
public final class Criteria {

    private final CriteriaType type;
    private final String query;

    /**
     * One and only contructor that accepts search query adn type of search.
     * @param type Type of search to be used.
     * @param query Query to be searched by.
     */
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

    /**
     * Checks if provided file matches search query and search type.
     * @param file File to be checked for a match.
     * @return {@code true} if provided file matches search or {@code false} otherwise.
     */
    public final boolean matches(File file) {
        return type.matches(this, file);
    }
}
