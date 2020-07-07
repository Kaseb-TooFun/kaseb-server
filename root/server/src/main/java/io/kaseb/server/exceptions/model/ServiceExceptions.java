package io.kaseb.server.exceptions.model;

import lombok.Getter;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Getter
public enum ServiceExceptions {
    DUPLICATE_USERNAME(200, "duplicate_username"),
    USER_NOT_FOUND(201, "user_not_found"),
    INCORRECT_PASSWORD(202, "incorrect_password");


    private ServiceExceptions(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private final Integer errorCode;
    private final String errorMessage;
}
