package io.kaseb.server.user.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;
import org.springframework.http.HttpStatus;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class WebsiteConfigNotFoundException extends ServiceException {
    public WebsiteConfigNotFoundException() {
        super(HttpStatus.NOT_FOUND, ServiceExceptions.WEBSITE_NOT_FOUND);
    }
}
