/**
 * 
 */
package com.micro.mocking;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.micro.data.PurchaseDao;
import com.micro.models.Purchase;
import com.micro.utils.DatabaseError;
import com.micro.utils.DuplicateObjectKeyError;
import com.micro.utils.ObjectNotFoundError;

/**
 * This class provides an "in-memory" store for Purchase objects
 * 
 * @author developer
 *
 */
@Component
public class PurchaseDaoMemory extends InMemoryStore<Purchase> implements PurchaseDao {
    /*
     * (non-Javadoc)
     * 
     * @see com.micro.data.PurchaseDao#getPurchaseById(long)
     */
    @Override
    @Cacheable("purchases")
    public Purchase getPurchaseById(long id) throws DatabaseError {
        return this.getObjectById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.micro.data.PurchaseDao#getAllPurchases()
     */
    @Override
    @Cacheable("purchases")
    public List<Purchase> getAllPurchases() throws DatabaseError {
        return this.getAllObjects();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.micro.data.PurchaseDao#createPurchase(com.micro.models.Purchase)
     */
    @Override
    @CachePut("purchases")
    public long createPurchase(Purchase purchase) throws DatabaseError, DuplicateObjectKeyError {
        // object is saved
        if (purchase.isSaved()) {
            return -1;
        }
        long newid = this.addObject(purchase);
        purchase.setId(newid);
        return newid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.micro.data.PurchaseDao#updatePurchase(com.micro.models.Purchase)
     */
    @Override
    @CachePut("purchases")
    public void updatePurchase(Purchase purchase) throws DatabaseError, ObjectNotFoundError {
        // object is not saved
        if (!purchase.isSaved()) {
            return;
        }
        this.updateObject(purchase.getId(), purchase);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.micro.data.PurchaseDao#deletePurchase(com.micro.models.Purchase)
     */
    @Override
    @CacheEvict("purchases")
    public void deletePurchase(Purchase purchase) throws DatabaseError, ObjectNotFoundError {
        // object is not saved or is deleted
        if (!purchase.isSaved() || purchase.isDeleted()) {
            return;
        }
        this.deleteObject(purchase.getId());
        purchase.setDeleted(true);
    }

}
