/**
 * 
 */
package data;

import java.util.List;

import models.PurchaseDetails;
import utils.DatabaseError;
import utils.DuplicateObjectKeyError;
import utils.ObjectNotFoundError;

/**
 * This interface provides the methods that are needed to manipulate
 * the purchase details
 * @author developer
 *
 */
public interface PurchaseDetailsDao extends MetaDao {
	/**
	 * Gets a purhcasedetails object with the given id
	 * @param id
	 * @return The purchase details. null if there is no purchase detaisl
	 * with the given id
	 */
	public PurchaseDetails getPurhcaseDetailsById(long id) throws DatabaseError;
	
	/**
	 * Finds all the existing purcahsedetails
	 * @return the list of the existing purchase details
	 * @throws DatabaseError
	 */
	public List<PurchaseDetails> getAllPurchaseDetails() throws DatabaseError;
	
	/**
	 * Finds all the existing purhcasedetails with the given ids
	 * @return the list of the existing purchase details with the given ids
	 * @throws DatabaseError
	 */
	public List<PurchaseDetails> findAllPurchaseDetailsById(List<Long> detailsIds) ;
	
	/**
	 * Creates a new object in the database of PurchaseDetails
	 * @param details
	 * @return the identifier that was assigned to the given purchase details object
	 * @throws DatabaseError
	 */
	public long createPurchaseDetails(PurchaseDetails details) throws DatabaseError, DuplicateObjectKeyError;
	
	/**
	 * Modifies an existing object in the database represented by the given Purchase details
	 * object
	 * @param details
	 * @throws DatabaseError
	 */
	public void updatePurchaseDetails(PurchaseDetails details) throws DatabaseError, ObjectNotFoundError;
	
	
	/**
	 * Deletes an existing purchase details object from the database;
	 * @param details
	 * @throws DatabaseError
	 */
	public void deletePurchaseDetails(PurchaseDetails details) throws DatabaseError, ObjectNotFoundError;
}
