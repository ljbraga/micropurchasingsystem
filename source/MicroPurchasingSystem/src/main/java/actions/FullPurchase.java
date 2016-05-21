/**
 * 
 */
package actions;

import models.Purchase;
import models.PurchaseDetails;

/**
 * This class extends the base purchase model to include
 * the purchase details object
 * @author developer
 *
 */
public class FullPurchase extends Purchase {
	private PurchaseDetails details;

	/**
	 * Creates a new FullPurchase object which the purchase
	 * and the purchase details parts are already
	 * saved in the database
	 * 
	 * @param purchase
	 * @param details
	 */
	public FullPurchase(Purchase purchase, PurchaseDetails details) {
		super(
			purchase.getId(),
			purchase.getProductType(),
			purchase.getExpires(),
			details.getId()
		);
		this.details = new PurchaseDetails(
			details.getId(),
			details.getDescription(),
			details.getQuantity(),
			details.getValue()
		);
	}
	
	/**
	 * creates a new FullPurchase object that is not yet saved to the
	 * database
	 * @param productType
	 * @param expires
	 * @param description
	 * @param quantity
	 * @param value
	 */
	public FullPurchase(String productType, long expires,
		long detailsId, String description, int quantity, double value
	) {
			super(productType, expires, -1);
			this.details = new PurchaseDetails(description, quantity, value);
	}
	
	/**
	 * Returns the current purchase details of the object
	 * @return
	 */
	public PurchaseDetails getDetails() {
		return this.details;
	}
	
	/**
	 * sets the description of the purchase details
	 * @param description
	 */
	public void setPurchaseDetails(String description) {
		if (this.details == null) {
			return ;
		}
		this.details.setDescription(description);
	}

	/**
	 * sets the quantity of the purchase details
	 * @param quantity
	 */
	public void setPurchaseDetails(int quantity) {
		if (this.details == null) {
			return ;
		}
		this.details.setQuantity(quantity);
		
	}
	
	/**
	 * sets the value of the purchase details
	 * @param value
	 */
	public void setPurchaseDetails(double value) {
		if (this.details == null) {
			return ;
		}
		this.details.setValue(value);
	}
}
