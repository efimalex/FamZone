package ru.familyportal.familyPortalException;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 02.09.12
 * Time: 22:27
 */
public class userAlreadyExistsException extends AuthenticationException {
    public userAlreadyExistsException(String msg, Throwable t) {
        super(msg, t);
    }

    public userAlreadyExistsException(String msg) {
        super(msg);
    }
}
