package data;

import java.util.List;

import models.Purchase;
import utils.DatabaseError;

/**
 * This interface specifies what methods the DAO must provide
 * 
 * @author developer
 *
 */
public interface PurchaseDao extends MetaDao {
	
	/**
	 * Returns a single purchase that is identified with the given id
	 * @param id the id of the purchase
	 * @return a Purchase object with the representation of the Purchase or
	 *  null if there is no purchase detaisl
	 * with the given id
	 * @throws DatabaseError
	 */
	public Purchase getPurchaseById(long id) throws DatabaseError;
	
	/**
	 * Gets all the existing purchases on the database
	 * 
	 * @return the list of purchases
	 * @throws DatabaseError
	 */
	public List<Purchase> getAllPurchases() throws DatabaseError;
	
	/**
	 * Creates a new purchase
	 * @param purchase
	 * @return the identifier that was assigned to the new purchase
	 * @throws DatabaseError
	 */
	public long createPurchase(Purchase purchase) throws DatabaseError;
	
	/**
	 * Updates an existing purchase
	 * 
	 * @param purchase
	 * @throws DatabaseError
	 */
	public void updatePurchase(Purchase purchase) throws DatabaseError;
	
	/**
	 * Deletes an existing purchase
	 * @param purchase
	 * @throws DatabaseError
	 */
	public void deletePurchase(Purchase purchase) throws DatabaseError;

}
