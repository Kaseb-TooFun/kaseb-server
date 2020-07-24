package io.kaseb.server.exceptions;

import io.kaseb.server.exceptions.model.ErrorResponse;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@NoArgsConstructor
public class ServiceException extends Exception {
    private HttpStatus responseStatus;
    private ErrorResponse errorResponse;

    public ServiceException(HttpStatus responseStatus, ServiceExceptions serviceException) {
        super(serviceException.getErrorMessage());
        this.responseStatus = responseStatus;
        this.errorResponse = new ErrorResponse(serviceException);
    }

    public ServiceException(ServiceExceptions serviceException) {
        super(serviceException.getErrorMessage());
        this.responseStatus = HttpStatus.BAD_REQUEST;
        this.errorResponse = new ErrorResponse(serviceException);
    }
}
