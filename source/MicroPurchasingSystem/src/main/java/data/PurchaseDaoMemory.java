/**
 * 
 */
package data;

import java.util.ArrayList;
import java.util.List;

import models.Purchase;
import utils.DatabaseError;

/**
 * This class provides an "in-memory" store for Purchase objects
 * @author developer
 *
 */
public class PurchaseDaoMemory extends InMemoryStore<Purchase> implements PurchaseDao{
	/* (non-Javadoc)
	 * @see data.PurchaseDao#getPurchaseById(long)
	 */
	@Override
	public Purchase getPurchaseById(long id) throws DatabaseError {
		// for the sake of simplicity we will use the current
		// time in millis
		return null;
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDao#getAllPurchases()
	 */
	@Override
	public List<Purchase> getAllPurchases() throws DatabaseError {
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		purchases.addAll(this.store.values());
		this.simulateSLA();
		return purchases;
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDao#createPurchase(models.Purchase)
	 */
	@Override
	public long createPurchase(Purchase purchase) throws DatabaseError {
		long newId = this.generateObjectId();
		purchase.setId(newId);
		this.store.put(newId, purchase);
		this.simulateSLA();
		return newId;
	}

	/* (non-Javadoc)
	 * @see data.PurchaseDao#updatePurchase(models.Purchase)
	 */
	@Override
	public void updatePurchase(Purchase purchase) throws DatabaseError {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see data.PurchaseDao#deletePurchase(models.Purchase)
	 */
	@Override
	public void deletePurchase(Purchase purchase) throws DatabaseError {
		if (!this.store.containsKey(purchase.getId()) {
			
		}
		this.simulateSLA();

	}
	
	/**
	 * Checks if there is an object with the given key
	 * 
	 * @param id
	 * @return true is returned 
	 */
	public boolean existsObject(Long id) {
		return this.store.containsKey(id);
	}
}
