package io.kaseb.server.user.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class UnauthorizedAccessException extends ServiceException {
    public UnauthorizedAccessException() {
        super(HttpStatus.UNAUTHORIZED, ServiceExceptions.UNAUTHORIZED);
    }
}
