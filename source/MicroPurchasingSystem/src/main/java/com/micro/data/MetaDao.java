/**
 * 
 */
package com.micro.data;

/**
 * Provides a generic definition of a dao for this project
 * 
 * @author developer
 * 
 *
 */
public interface MetaDao {
    /**
     * shall provide the implementation that creates a new identifier that must
     * be unique in the store where the object will be saved
     * 
     * @return
     */
    public long generateObjectId();

}
