/**
 * 
 */
package data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import models.PurchaseDetails;
import utils.DatabaseError;
import utils.DuplicateObjectKeyError;
import utils.ObjectNotFoundError;

/**
 * This class provides an in-memory store for PurchaseDetails objects
 * @author developer
 *
 */
@Component
public class PurchaseDetailsDaoMemory 
	extends InMemoryStore<PurchaseDetails>
	implements PurchaseDetailsDao {

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#getPurhcaseDetailsById(long)
	 */
	@Override
	@Cacheable("purchaseDetails")
	public PurchaseDetails getPurhcaseDetailsById(long id) throws DatabaseError {
		return this.getObjectById(id);
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#getAllPurchaseDetails()
	 */
	@Override
	@Cacheable("purchaseDetails")
	public List<PurchaseDetails> getAllPurchaseDetails() throws DatabaseError {
		return this.getAllObjects();
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#getAllPurchaseDetailsById(java.util.List)
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
		return null;
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#createPurchaseDetails(models.PurchaseDetails)
	 */
	@Override
	@CachePut("purchaseDetails")
	public long createPurchaseDetails(PurchaseDetails details)
		throws DatabaseError, DuplicateObjectKeyError
	{
		// object is saved
		if (details.isSaved()) {
			return -1;
		}
		long newid = this.addObject(details);
		details.setId(newid);
		return newid;
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#updatePurchaseDetails(models.PurchaseDetails)
	 */
	@Override
	@CachePut("purchaseDetails")
	public void updatePurchaseDetails(PurchaseDetails details)
		throws DatabaseError, ObjectNotFoundError
	{
		// object is saved
		if (!details.isSaved()) {
			return ;
		}
		this.updateObject(details.getId(), details);
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#deletePurchaseDetails(models.PurchaseDetails)
	 */
	@Override
	@CachePut("purchaseDetails")
	public void deletePurchaseDetails(PurchaseDetails details)
		throws DatabaseError, ObjectNotFoundError
	{
		// object is saved
		if (!details.isSaved()) {
			return ;
		}
		this.deleteObject(details.getId());
		details.setDeleted(true);
	}
}
