package rs.raf.storage.spec.search;

import rs.raf.storage.spec.core.File;

public enum CriteriaType {
    NAME_EQUALS(new CriteriaNameEquals()),
    NAME_CONTAINS(new CriteriaNameContains()),
    NAME_STARTS_WITH(new CriteriaNameStartsWith()),
    NAME_ENDS_WITH(new CriteriaNameEndsWith());

    private CriteriaVisitor visitor;

    CriteriaType(CriteriaVisitor visitor) {
        this.visitor = visitor;
    }

    public boolean matches(Criteria criteria, File file) {
        return visitor.matches(criteria, file);
    }
}
