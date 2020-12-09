package io.kaseb.server.operator.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class OperatorNotFoundException extends ServiceException {
	public OperatorNotFoundException() {
		super(HttpStatus.NOT_FOUND, ServiceExceptions.OPERATOR_NOT_FOUND);
	}
}
