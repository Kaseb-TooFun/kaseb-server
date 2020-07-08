package io.kaseb.server.user.exceptions;

import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.exceptions.model.ServiceExceptions;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class WebsiteExistsException extends ServiceException {
    public WebsiteExistsException() {
        super(ServiceExceptions.WEBSITE_EXISTS);
    }
}
