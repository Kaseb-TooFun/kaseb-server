package io.kaseb.server.exceptions;

import io.kaseb.server.exceptions.model.ErrorResponse;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Getter
public class ServiceException extends Exception {
    private final HttpStatus responseStatus;
    private final ErrorResponse errorResponse;

    public ServiceException(HttpStatus responseStatus, ServiceExceptions serviceException) {
        this.responseStatus = responseStatus;
        this.errorResponse = new ErrorResponse(serviceException);
    }

    public ServiceException(ServiceExceptions serviceException) {
        this.responseStatus = HttpStatus.BAD_REQUEST;
        this.errorResponse = new ErrorResponse(serviceException);
    }
}
