package io.kaseb.server.exceptions.model;

import lombok.Getter;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Getter
public enum ServiceExceptions {
    INTERNAL(1000, "internal"),
    DUPLICATE_USERNAME(1001, "duplicate_username"),
    USER_NOT_FOUND(1002, "user_not_found"),
    INCORRECT_PASSWORD(1003, "incorrect_password"),
    UNAUTHORIZED(1004, "unauthorized");


    private ServiceExceptions(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private final Integer errorCode;
    private final String errorMessage;
}
