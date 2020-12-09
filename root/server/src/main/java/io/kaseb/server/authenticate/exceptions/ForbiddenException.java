package io.kaseb.server.authenticate.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class ForbiddenException extends ServiceException {
	public ForbiddenException() {
		super(HttpStatus.FORBIDDEN, ServiceExceptions.FORBIDDEN);
	}
}
