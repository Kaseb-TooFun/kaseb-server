package io.kaseb.server.authenticate.model.annotations;

import io.kaseb.server.authenticate.model.enums.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface AuthenticationRequired {
    Role getRole();
}
