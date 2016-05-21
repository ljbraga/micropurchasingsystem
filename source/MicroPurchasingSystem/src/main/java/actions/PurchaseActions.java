/**
 * 
 */
package actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.DAOFactory;
import data.PurchaseDao;
import data.PurchaseDetailsDao;
import models.Purchase;
import models.PurchaseDetails;
import utils.DatabaseError;
import utils.DuplicateObjectKeyError;
import utils.InvalidDaoImplementation;
import utils.ObjectNotFoundError;

/**
 * This classes encaplusates the calls to the database
 * to make it easier for the services above to use
 * 
 * @author developer
 *
 */
public class PurchaseActions {
	/**
	 * is used to get the purchases from the store
	 */
	private static PurchaseDao purchaseDao = null;
	
	/**
	 * is used to get the purchase details from the store
	 */
	private static PurchaseDetailsDao purchaseDetailsDao = null;
	
	/**
	 * initialises the DAO objects taking into account the given
	 * dao implementation
	 * 
	 * @param daoImplId
	 * @throws InvalidDaoImplementation
	 */
	public static void initDaoObjects(int daoImplId) throws InvalidDaoImplementation{
		DAOFactory factory = DAOFactory.getDAOFactory(daoImplId);
		// invalid dao implementation
		if (factory == null) {
			throw new InvalidDaoImplementation();
		}
		PurchaseActions.purchaseDao = factory.getPurchaseDao();
		PurchaseActions.purchaseDetailsDao = factory.getPurcahseDetailsDao();
	}
	
	/**
	 * creates a new purchase in the system
	 * 
	 * @param productType the product type of the purchase
	 * @param expires the expire time of the purchase in millis
	 * @param description the description of the purchase
	 * @param quantity the quantity of the purchase
	 * @param value the value of the purchase
	 * @return
	 * @throws DatabaseError
	 * @throws DuplicateObjectKeyError 
	 */
	public static FullPurchase createPurchase(String productType, long expires, 
		String description, int quantity, double value
	) throws DatabaseError, DuplicateObjectKeyError {
		PurchaseDetails details = PurchaseActions.createPurchaseDetails(
			description,
			quantity, 
			value
		);
		Purchase purchase = new Purchase(
			productType,
			expires,
			details.getId()
		);
		long id = PurchaseActions.purchaseDao.createPurchase(purchase);
		purchase.setId(id);
		return new FullPurchase(purchase, details);
	}

	/**
	 * auxiliary method to create the purchase details in the database
	 * 
	 * @param description
	 * @param quantity
	 * @param value
	 * @return
	 * @throws DatabaseError
	 * @throws DuplicateObjectKeyError 
	 */
	private static PurchaseDetails createPurchaseDetails(String description,
		int quantity, double value
	) throws DatabaseError, DuplicateObjectKeyError {
		PurchaseDetails details = new PurchaseDetails(
			description,
			quantity,
			value
		);
		long id = PurchaseActions.purchaseDetailsDao.createPurchaseDetails(details);
		details.setId(id);
		return details;
	}
	
	/**
	 * Updates the contents of an existing purchase
	 * 
	 * @param purchase
	 * @return
	 * @throws DatabaseError
	 * @throws ObjectNotFoundError
	 */
	public static FullPurchase updatePurchase(FullPurchase purchase) 
		throws DatabaseError, ObjectNotFoundError
	{
		return null;
		
	}
	
	/**
	 * Deletes an existing purchase
	 * 
	 * @param purchase
	 * @return
	 * @throws DatabaseError
	 * @throws ObjectNotFoundError
	 */
	public static FullPurchase deletePurchase(FullPurchase purchase) 
			throws DatabaseError, ObjectNotFoundError
	{
		return null;
	}
	
	/**
	 * Gets the contents of an existing purchase with the given id
	 * @param id
	 * @return
	 * @throws DatabaseError
	 * @throws ObjectNotFoundError
	 */
	public static FullPurchase getPurchase(long id)
		throws DatabaseError, ObjectNotFoundError
	{
		// 1st set is to get the purchase
		Purchase purchase = PurchaseActions.purchaseDao.getPurchaseById(id);
		if (purchase == null) {
			throw new ObjectNotFoundError("No such purchase:" + id);
		}
		
		// 2nd step is to get the details
		PurchaseDetails details = PurchaseActions
									.purchaseDetailsDao
									.getPurhcaseDetailsById(
										purchase.getPurchaseDetailsId()
									);
		if (details == null) {
			throw new ObjectNotFoundError(
				"Could not find the details of the purchase: " + id
			);
		}
		
		FullPurchase result = new FullPurchase(purchase, details);
		return result;
	}

	/**
	 * Finds all the valid purchases in the system
	 * @return
	 * @throws DatabasError
	 */
	public static List<FullPurchase> getValidPurchases() throws DatabaseError{
		// 1st step is to get the existing purchases
		List<Purchase> purchases = PurchaseActions.purchaseDao.getAllPurchases();
		
		
		// in order to make the merge of the Purchase object with the PurchaseDetails
		// object faster we will map the id of the PurchaseDetails to the purchase
		// it belongs to
		Map<Long, Purchase> detailsToPurchase = new HashMap<Long, Purchase>();
		for (Purchase purchase : purchases) {
			// purchase is not valid so, we can skip it
			if (!purchase.isValid()) {
				continue ;
			}
			detailsToPurchase.put(purchase.getPurchaseDetailsId(), purchase);
		}
		
		// now that we have the valid purchase details we can find
		// purchase details
		ArrayList<Long> detailsIds = new ArrayList<Long>();
		detailsIds.addAll(detailsToPurchase.keySet());
		List <PurchaseDetails> allDetails = PurchaseActions
												.purchaseDetailsDao
												.findAllPurchaseDetailsById(
														detailsIds
												);
		
		// now that we have the purchase details we can create a "full purchase"
		// object
		ArrayList<FullPurchase> validPurchases = new ArrayList<>();
		for (PurchaseDetails details : allDetails) {
			Purchase purchase = detailsToPurchase.get(details.getId());
			validPurchases.add(new FullPurchase(purchase, details));
		}

		return validPurchases;		
	}
}
