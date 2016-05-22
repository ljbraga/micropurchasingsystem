/**
 * 
 */
package com.micro.models;

/**
 * This class provides the representation of the details of a given purchase.
 *
 * @author leonel.braga
 *
 */
public class PurchaseDetails extends MetaModel {
    /**
     * the description of the purchase
     */
    private String description;
    /**
     * the quantity of the purchase
     */
    private int quantity;
    /**
     * the value of the purchase
     */
    private double value;

    /**
     * Creates a new object that represents the details of a purchase
     * 
     * @param id
     *            the identifier of the purchasedetails, as it has to saved to
     *            the database
     * @param description
     *            the description of the purchase
     * @param quantity
     *            the quantity of the purchase
     * @param value
     *            the value of the purchase
     */
    public PurchaseDetails(long id, String description, int quantity, double value) {
        super(id);
        this.description = description;
        this.quantity = quantity;
        this.value = value;
    }

    /**
     * creates a new object that is not yet saved to the database
     * 
     * @param description
     * @param quantity
     * @param value
     */
    public PurchaseDetails(String description, int quantity, double value) {
        super();
        this.description = description;
        this.quantity = quantity;
        this.value = value;
    }

    /**
     * Returns the current description of the purchase
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a new description for the purchase
     * 
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        if (this.isDeleted()) {
            return;
        }
        this.description = description;
    }

    /**
     * Returns the current quantity of the purchase
     * 
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets a new quantity for the purchase
     * 
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity(int quantity) {
        if (this.isDeleted()) {
            return;
        }

        this.quantity = quantity;
    }

    /**
     * Returns the current value of the purchase
     * 
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * sets the new value for the purchase
     * 
     * @param value
     *            the value to set
     */
    public void setValue(double value) {
        if (this.isDeleted()) {
            return;
        }

        this.value = value;
    }
}
