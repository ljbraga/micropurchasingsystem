/**
 * 
 */
package com.micro.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * To be used if someone tries to insert a new object that contains a key that
 * already exists
 * 
 * @author developer
 *
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DuplicateObjectKeyError extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DuplicateObjectKeyError(String message) {
        super(message);
    }

    public DuplicateObjectKeyError() {
        super();
    }

}
