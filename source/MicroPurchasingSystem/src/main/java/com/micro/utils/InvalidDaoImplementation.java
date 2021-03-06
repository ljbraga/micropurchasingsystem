/**
 * 
 */
package com.micro.utils;

/**
 * This exception will be used when an invalid configuration was chosen for the
 * dao implementation
 * 
 * @author developer
 *
 */
public class InvalidDaoImplementation extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InvalidDaoImplementation() {
        super();
    }

    public InvalidDaoImplementation(String message) {
        super(message);
    }

}
