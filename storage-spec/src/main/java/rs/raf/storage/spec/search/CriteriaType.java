package rs.raf.storage.spec.search;

import rs.raf.storage.spec.core.File;

/**
 * Search criteria that check if file name contains search query. Case insensitive operation.
 */
public enum CriteriaType {
    NAME_EQUALS(new CriteriaNameEquals()),
    NAME_CONTAINS(new CriteriaNameContains()),
    NAME_STARTS_WITH(new CriteriaNameStartsWith()),
    NAME_ENDS_WITH(new CriteriaNameEndsWith());

    private CriteriaVisitor visitor;

    CriteriaType(CriteriaVisitor visitor) {
        this.visitor = visitor;
    }

    /**
     * Checks if provided file matches search query and search type.
     * @param criteria Search criteria to be used.
     * @param file File to be checked for a match.
     * @return {@code true} if provided file matches search or {@code false} otherwise.
     */
    public boolean matches(Criteria criteria, File file) {
        return visitor.matches(criteria, file);
    }
}