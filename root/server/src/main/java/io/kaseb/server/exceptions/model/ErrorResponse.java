package io.kaseb.server.exceptions.model;

import lombok.ToString;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@ToString
public class ErrorResponse {
    private final Integer errorCode;
    private final String errorMessage;

    public ErrorResponse(ServiceExceptions serviceExceptions) {
        this.errorCode = serviceExceptions.getErrorCode();
        this.errorMessage = serviceExceptions.getErrorMessage();
    }
}
