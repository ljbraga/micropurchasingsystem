/**
 * 
 */
package models;

/**
 * This class provides a representation of a Purchase
 * 
 * @author leonel.braga
 * 
 */
public class Purchase extends MetaModel {
	/**
	 *  the product type that is associated to the purchase
	 */
	private String productType;
	/**
	 * the time in millis since the epoch that represents the
	 * expire time of the purchase 
	 */
	private long expires;
	/**
	 * the details of the purchase
	 */
	private long purchaseDetailsId;
	
	/**
	 * Creates a new purchase object. this constructor uses a details
	 * object
	 * @param id the id of the purchase
	 * @param productType the type of the product
	 * @param expires when the purchase expires
	 * @param purchaseDetailsId the id of the purhcase details associated to this purhcase
	 */
	public Purchase(long id, String productType, long expires,
		long  purhcaseDetailsId
	) {
		super(id);
		this.productType = productType;
		this.expires = expires;
		this.purchaseDetailsId = purhcaseDetailsId;
	}
	
	/**
	 * creates a new object that is not yet saved to the database
	 *  
	 * @param productType
	 * @param expires
	 * @param purhcaseDetailsId
	 */
	public Purchase( String productType, long expires,
		long  purhcaseDetailsId
	) {
		super();
		this.productType = productType;
		this.expires = expires;
		this.purchaseDetailsId = purhcaseDetailsId;
	}

	/**
	 * Returns the current product type of the purchase
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	
	/**
	 * Sets a new product type for the purchase
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		if (this.isDeleted()) {
			return ;
		}
		this.productType = productType;
	}
	
	/**
	 * Returns the current expire time of the purchase
	 * @return the expires
	 */
	public long getExpires() {
		return expires;
	}
	
	/**
	 * sets a new expire time for the purchase
	 * @param expires the expires to set
	 */
	public void setExpires(long expires) {
		if (this.isDeleted()) {
			return ;
		}

		this.expires = expires;
	}
	
	/**
	 * returns the current details of the purchase
	 * @return the details
	 */
	public long getPurchaseDetailsId() {
		return this.purchaseDetailsId;
	}
	
	/**
	 * sets new details for the purchase using a full
	 * PurchaseDetails object
	 * 
	 * @param details the details to set
	 */
	public void setPurchaseDetailsId(long  purhcaseDetailsId) {
		if (this.isDeleted()) {
			return ;
		}
		this.purchaseDetailsId = purhcaseDetailsId;
	}

	/**
	 * checks if the given purchase is valid.
	 * for a purchase to be valid it must not have expired 
	 * @return true if it is still valid
	 */
	public boolean isValid() {
		return this.expires >= System.currentTimeMillis() && !this.isDeleted();
	}
}
