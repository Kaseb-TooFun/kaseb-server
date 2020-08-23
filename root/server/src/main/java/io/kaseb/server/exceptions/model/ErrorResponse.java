package io.kaseb.server.exceptions.model;

import io.kaseb.server.base.MessageService;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@ToString
@Data
public class ErrorResponse implements Serializable {
    private final Integer errorCode;
    private final String errorMessage;
    private final String localizedErrorMessage;
    @Setter
    private String debugMessage;

    public ErrorResponse(ServiceExceptions serviceExceptions, MessageService messageService) {
        this.errorCode = serviceExceptions.getErrorCode();
        this.errorMessage = serviceExceptions.getErrorMessage();
        this.localizedErrorMessage = messageService.getMessage(serviceExceptions.name());
    }
}
