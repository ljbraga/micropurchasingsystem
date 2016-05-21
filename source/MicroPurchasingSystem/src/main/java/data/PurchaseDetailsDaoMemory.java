/**
 * 
 */
package data;

import java.util.List;

import models.PurchaseDetails;
import utils.DatabaseError;

/**
 * This class provides an in-memory store for PurchaseDetails objects
 * @author developer
 *
 */
public class PurchaseDetailsDaoMemory extends InMemoryStore implements PurchaseDetailsDao {

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#getPurhcaseDetailsById(long)
	 */
	@Override
	public PurchaseDetails getPurhcaseDetailsById(long id) throws DatabaseError {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#getAllPurchaseDetails()
	 */
	@Override
	public List<PurchaseDetails> getAllPurchaseDetails() throws DatabaseError {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#getAllPurchaseDetailsById(java.util.List)
	 */
	@Override
	public List<PurchaseDetails> findAllPurchaseDetailsById(List<Long> detailsIds) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#createPurchaseDetails(models.PurchaseDetails)
	 */
	@Override
	public long createPurchaseDetails(PurchaseDetails details) throws DatabaseError {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#updatePurchaseDetails(models.PurchaseDetails)
	 */
	@Override
	public void updatePurchaseDetails(PurchaseDetails details) throws DatabaseError {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see data.PurchaseDetailsDao#deletePurchaseDetails(models.PurchaseDetails)
	 */
	@Override
	public void deletePurchaseDetails(PurchaseDetails details) throws DatabaseError {
		// TODO Auto-generated method stub

	}
}
