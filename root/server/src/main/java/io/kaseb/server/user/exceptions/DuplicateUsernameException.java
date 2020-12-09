package io.kaseb.server.user.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class DuplicateUsernameException extends ServiceException {

	public DuplicateUsernameException() {
		super(ServiceExceptions.DUPLICATE_USERNAME);
	}
}
