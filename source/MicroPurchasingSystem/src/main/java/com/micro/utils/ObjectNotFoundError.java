/**
 * 
 */
package com.micro.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * To be used when an object could not be found
 * 
 * @author developer
 *
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundError extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundError() {
        super();
    }

    public ObjectNotFoundError(String message) {
        super(message);
    }

}
