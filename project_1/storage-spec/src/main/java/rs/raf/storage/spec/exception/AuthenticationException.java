package rs.raf.storage.spec.exception;

import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.res.Res;

/**
 * Notifies the caller that requested operation cannot be done because provided user credentials were not correct.
 */
public final class AuthenticationException extends StorageException {

    public AuthenticationException(User user) {
        super(Res.Error.AUTHENTICATION
                .replace(Res.Wildcard.USER, user.getName()));
    }
}
