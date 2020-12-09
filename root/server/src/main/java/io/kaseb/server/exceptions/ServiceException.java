package io.kaseb.server.exceptions;

import io.kaseb.server.exceptions.model.ErrorResponse;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class ServiceException extends Exception {
	private final HttpStatus responseStatus;
	private final ErrorResponse errorResponse;
	private final ServiceExceptions exceptionEnumerator;

	public ServiceException(HttpStatus responseStatus, ServiceExceptions serviceException) {
		super(serviceException.getErrorMessage());
		this.responseStatus = responseStatus;
		this.errorResponse = new ErrorResponse(serviceException);
		this.exceptionEnumerator = serviceException;
	}

	public ServiceException(ServiceExceptions serviceException) {
		super(serviceException.getErrorMessage());
		this.responseStatus = HttpStatus.BAD_REQUEST;
		this.errorResponse = new ErrorResponse(serviceException);
		this.exceptionEnumerator = serviceException;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ServiceException{");
		sb.append("responseStatus=").append(responseStatus);
		sb.append(", errorResponse=").append(errorResponse);
		sb.append('}');
		return sb.toString();
	}
}
