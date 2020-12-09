package io.kaseb.server.user.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class UserNotFoundException extends ServiceException {
	public UserNotFoundException() {
		super(HttpStatus.NOT_FOUND, ServiceExceptions.USER_NOT_FOUND);
	}
}
