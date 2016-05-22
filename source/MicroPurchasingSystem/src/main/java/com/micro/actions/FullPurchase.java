/**
 * 
 */
package com.micro.actions;

import com.micro.models.Purchase;
import com.micro.models.PurchaseDetails;

/**
 * This class extends the base purchase model to include the purchase details
 * object
 * 
 * @author developer
 *
 */
public class FullPurchase extends Purchase {
    private PurchaseDetails details;

    /**
     * Creates a new FullPurchase object which the purchase and the purchase
     * details parts are already saved in the database
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
     * creates a new FullPurchase object that is not yet saved to the database
     * 
     * @param productType
     * @param expires
     * @param description
     * @param quantity
     * @param value
     */
    public FullPurchase(String productType, long expires, long detailsId, 
        String description, int quantity, double value
    ) {
        super(productType, expires, -1);
        this.details = new PurchaseDetails(description, quantity, value);
    }

    /**
     * Returns the current purchase details of the object
     * 
     * @return
     */
    public PurchaseDetails getDetails() {
        return this.details;
    }

    /**
     * sets a new purchasedetails object
     * 
     * @param description
     */
    public void setDetails(PurchaseDetails details) {
        this.details = details;
    }

    /**
     * sets a new description for the details object
     * 
     * @param description
     */
    public void setDetails(String description) {
        if (this.details == null) {
            return;
        }
        this.details.setDescription(description);
    }

    /**
     * sets a new quantity for the details object
     * 
     * @param description
     */
    public void setDetails(int quantity) {
        if (this.details == null) {
            return;
        }
        this.details.setQuantity(quantity);
    }

    /**
     * sets a new value for the details object
     * 
     * @param description
     */
    public void setDetails(double value) {
        if (this.details == null) {
            return;
        }
        this.details.setValue(value);
    }

}
