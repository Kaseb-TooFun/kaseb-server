package io.kaseb.server.authenticate.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class AuthenticationException extends ServiceException {
    public AuthenticationException() {
        super(HttpStatus.UNAUTHORIZED, ServiceExceptions.UNAUTHORIZED);
    }
}
