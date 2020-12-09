package io.kaseb.server.authenticate.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class AdminAuthenticationException extends ServiceException {
	public AdminAuthenticationException() {
		super(HttpStatus.UNAUTHORIZED, ServiceExceptions.ADMIN_AUTH_REQUIRED);
	}
}
