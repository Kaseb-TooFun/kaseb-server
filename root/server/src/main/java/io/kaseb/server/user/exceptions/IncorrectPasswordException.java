package io.kaseb.server.user.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class IncorrectPasswordException extends ServiceException {
	public IncorrectPasswordException() {
		super(ServiceExceptions.INCORRECT_PASSWORD);
	}
}
