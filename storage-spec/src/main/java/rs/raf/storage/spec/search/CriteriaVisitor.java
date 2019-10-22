package rs.raf.storage.spec.search;

import rs.raf.storage.spec.core.File;

public interface CriteriaVisitor {

    boolean matches(Criteria criteria, File file);
}
