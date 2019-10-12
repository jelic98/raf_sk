package rs.raf.storage.spec.search;

import rs.raf.storage.spec.core.File;

public final class CriteriaNameEndsWith implements CriteriaVisitor {

    @Override
    public boolean matches(Criteria criteria, File file) {
        return criteria.getQuery().toLowerCase()
                .endsWith(file.getName().toLowerCase());
    }
}
