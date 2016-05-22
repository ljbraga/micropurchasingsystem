/**
 * 
 */
package com.micro.data;

import com.micro.mocking.InMemoryDAOFactory;

/**
 * This class can be seen as an utility class that provides the functionalities
 * needed to get the DAO implementation for a given system
 * 
 * @author developer
 *
 */
public abstract class DAOFactory {
    /**
     * These are the available DAO implementations
     */
    public static final int IN_MEMORY_DAO = 1;

    public abstract PurchaseDao getPurchaseDao();

    public abstract PurchaseDetailsDao getPurcahseDetailsDao();

    public static DAOFactory getDAOFactory(int implementation) {
        switch (implementation) {
        case IN_MEMORY_DAO: {
            return new InMemoryDAOFactory();
        }
        default: {
            return null;
        }
        }
    }

}
