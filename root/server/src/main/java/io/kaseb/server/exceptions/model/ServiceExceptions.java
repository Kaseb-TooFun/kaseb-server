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
    UNAUTHORIZED(1004, "unauthorized"),
    WEBSITE_EXISTS(1005, "website_exists"),
    WEBSITE_NOT_FOUND(1006, "website_not_found"),
    UNAUTHORIZED_ACTION(1007, "unauthorized_action"),
    WEBSITE_CONFIG_NOT_FOUND(1008, "website_config_not_found"),
    FORBIDDEN(1009, "forbidden"),
    OPERATOR_NOT_FOUND(1010, "operator_not_found"),
    OPERATOR_NOT_ACTIVATED(1011, "operator_not_activated"),
    ADMIN_AUTH_REQUIRED(1012, "admin_auth_required"),
    OPERATOR_AUTH_REQUIRED(1013, "operator_auth_required"),
    ;


    private ServiceExceptions(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private final Integer errorCode;
    private final String errorMessage;
}
