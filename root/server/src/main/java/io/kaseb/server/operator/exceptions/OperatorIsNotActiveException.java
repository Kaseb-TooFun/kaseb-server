package io.kaseb.server.operator.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class OperatorIsNotActiveException extends ServiceException {
	public OperatorIsNotActiveException() {
		super(HttpStatus.FORBIDDEN, ServiceExceptions.OPERATOR_NOT_ACTIVATED);
	}
}
