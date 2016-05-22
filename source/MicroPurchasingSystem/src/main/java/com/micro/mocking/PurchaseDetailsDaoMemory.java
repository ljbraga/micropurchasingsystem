/**
 * 
 */
package com.micro.mocking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.micro.data.PurchaseDetailsDao;
import com.micro.models.PurchaseDetails;
import com.micro.utils.DatabaseError;
import com.micro.utils.DuplicateObjectKeyError;
import com.micro.utils.ObjectNotFoundError;

/**
 * This class provides an in-memory store for PurchaseDetails objects
 * 
 * @author developer
 *
 */
@Component
public class PurchaseDetailsDaoMemory extends InMemoryStore<PurchaseDetails> implements PurchaseDetailsDao {

    /*
     * (non-Javadoc)
     * 
     * @see com.micro.data.PurchaseDetailsDao#getPurhcaseDetailsById(long)
     */
    @Override
    @Cacheable("purchaseDetails")
    public PurchaseDetails getPurhcaseDetailsById(long id) throws DatabaseError {
        return this.getObjectById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.micro.data.PurchaseDetailsDao#getAllPurchaseDetails()
     */
    @Override
    @Cacheable("purchaseDetails")
    public List<PurchaseDetails> getAllPurchaseDetails() throws DatabaseError {
        return this.getAllObjects();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.micro.data.PurchaseDetailsDao#getAllPurchaseDetailsById(java.util.
     * List)
     */
    @Override
    @Cacheable("purchaseDetails")
    public List<PurchaseDetails> findAllPurchaseDetailsById(List<Long> detailsIds) {
        List<PurchaseDetails> filteredDetails = new ArrayList<>();
        for (long id : detailsIds) {
            if (!this.existsObject(id)) {
                continue;
            }
            filteredDetails.add(this.store.get(id));
        }
        return filteredDetails;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.micro.data.PurchaseDetailsDao#createPurchaseDetails(com.micro.models.
     * PurchaseDetails)
     */
    @Override
    @CachePut("purchaseDetails")
    public long createPurchaseDetails(PurchaseDetails details) throws DatabaseError, DuplicateObjectKeyError {
        // object is saved
        if (details.isSaved()) {
            return -1;
        }
        long newid = this.addObject(details);
        details.setId(newid);
        return newid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.micro.data.PurchaseDetailsDao#updatePurchaseDetails(com.micro.models.
     * PurchaseDetails)
     */
    @Override
    @CachePut("purchaseDetails")
    public void updatePurchaseDetails(PurchaseDetails details) throws DatabaseError, ObjectNotFoundError {
        // object is saved
        if (!details.isSaved()) {
            return;
        }
        this.updateObject(details.getId(), details);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.micro.data.PurchaseDetailsDao#deletePurchaseDetails(com.micro.models.
     * PurchaseDetails)
     */
    @Override
    @CacheEvict("purchaseDetails")
    public void deletePurchaseDetails(PurchaseDetails details) throws DatabaseError, ObjectNotFoundError {
        // object is saved
        if (!details.isSaved()) {
            return;
        }
        this.deleteObject(details.getId());
        details.setDeleted(true);
    }
}
