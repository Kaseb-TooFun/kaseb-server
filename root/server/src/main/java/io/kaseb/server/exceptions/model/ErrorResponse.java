package io.kaseb.server.exceptions.model;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@ToString
@Data
public class ErrorResponse {
    private final Integer errorCode;
    private final String errorMessage;
    @Setter
    private String debugMessage;

    public ErrorResponse(ServiceExceptions serviceExceptions) {
        this.errorCode = serviceExceptions.getErrorCode();
        this.errorMessage = serviceExceptions.getErrorMessage();
    }
}
