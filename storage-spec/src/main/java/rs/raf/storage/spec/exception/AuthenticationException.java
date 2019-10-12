package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.res.Res;

public final class AuthenticationException extends StorageException {

    public AuthenticationException(User user) {
        super(Res.Error.AUTHENTICATION
                .replace(Res.Wildcard.USER, user.getName()));
    }
}
