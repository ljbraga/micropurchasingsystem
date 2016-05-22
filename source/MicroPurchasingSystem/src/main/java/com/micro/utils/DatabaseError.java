/**
 * 
 */
package com.micro.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Used to represent a generic database error
 * 
 * @author developer
 *
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseError extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DatabaseError() {
        super();
    }

    public DatabaseError(String message) {
        super(message);
    }
}
