package io.kaseb.server.authenticate.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class OperatorAuthenticationException extends ServiceException {
	public OperatorAuthenticationException() {
		super(HttpStatus.UNAUTHORIZED, ServiceExceptions.OPERATOR_AUTH_REQUIRED);
	}
}
