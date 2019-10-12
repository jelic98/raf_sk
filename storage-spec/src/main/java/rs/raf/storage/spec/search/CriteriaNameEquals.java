package rs.raf.storage.spec.search;

import rs.raf.storage.spec.core.File;

public final class CriteriaNameEquals implements CriteriaVisitor {

    @Override
    public boolean matches(Criteria criteria, File file) {
        return criteria.getQuery()
                .equalsIgnoreCase(file.getName());
    }
}
