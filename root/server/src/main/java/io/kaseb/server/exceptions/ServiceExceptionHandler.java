package io.kaseb.server.exceptions;

import io.kaseb.server.exceptions.model.ErrorResponse;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception ex, WebRequest request) {
        if (ex instanceof ServiceException)
            return handleServiceException((ServiceException) ex);
        logger.error("internal server error on request :{}", request.getContextPath(), ex);
        return handleInternalException(ex);
    }

    private ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        return ResponseEntity.status(ex.getResponseStatus()).body(ex.getErrorResponse());
    }

    private ResponseEntity<ErrorResponse> handleInternalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ServiceExceptions.INTERNAL);
        errorResponse.setDebugMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
