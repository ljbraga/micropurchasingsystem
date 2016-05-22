/**
 * 
 */
package com.micro.mocking;

import com.micro.data.DAOFactory;
import com.micro.data.PurchaseDao;
import com.micro.data.PurchaseDetailsDao;

/**
 * This class provides an implementation of a factory that provides DAO objects
 * that save the com.micro.data in memory
 * 
 * @author developer
 *
 */
public class InMemoryDAOFactory extends DAOFactory {

    /*
     * (non-Javadoc)
     * 
     * @see com.micro.data.DAOFactory#getPurchaseDao()
     */
    @Override
    public PurchaseDao getPurchaseDao() {
        return new PurchaseDaoMemory();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.micro.data.DAOFactory#getPurcahseDetailsDao()
     */
    @Override
    public PurchaseDetailsDao getPurcahseDetailsDao() {
        return new PurchaseDetailsDaoMemory();
    }

}
