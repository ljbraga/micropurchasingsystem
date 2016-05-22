/**
 * 
 */
package com.micro.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author developer
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationError extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ValidationError() {
        super();
    }

    public ValidationError(String message) {
        super(message);
    }

}
