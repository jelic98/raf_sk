package rs.raf.storage.spec.search;

import rs.raf.storage.spec.core.File;

/**
 * Search criteria that check if file name starts with search query. Case insensitive operation.
 */
public final class CriteriaNameStartsWith implements CriteriaVisitor {

    @Override
    public boolean matches(Criteria criteria, File file) {
        return criteria.getQuery().toLowerCase()
                .startsWith(file.getName().toLowerCase());
    }
}
