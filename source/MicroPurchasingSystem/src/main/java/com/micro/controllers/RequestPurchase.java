/**
 * 
 */
package com.micro.controllers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This is an utility class that can be used to represent the input coming from
 * the web regarding a purhcase request
 * 
 * @author developer
 *
 */

public class RequestPurchase {
    private long id;
    private String productType;
    private long expires;
    private String description;
    private int quantity;
    private double value;

    @JsonIgnore
    private ArrayList<String> definedValues;

    public RequestPurchase() {
        this.definedValues = new ArrayList<>();

    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.definedValues.add(FieldsNames.ID.toString());
        this.id = id;
    }

    /**
     * @return the productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @param productType
     *            the productType to set
     */
    public void setProductType(String productType) {
        this.definedValues.add(FieldsNames.PRODUCT_TYPE.toString());
        this.productType = productType;
    }

    /**
     * @return the expires
     */
    public long getExpires() {
        return expires;
    }

    /**
     * @param expires
     *            the expires to set
     */
    public void setExpires(long expires) {
        this.definedValues.add(FieldsNames.EXPIRES.toString());
        this.expires = expires;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.definedValues.add(FieldsNames.DESCRIPTION.toString());
        this.description = description;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity(int quantity) {
        this.definedValues.add(FieldsNames.QUANTITY.toString());
        this.quantity = quantity;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(double value) {
        this.definedValues.add(FieldsNames.VALUE.toString());
        this.value = value;
    }

    /**
     * returns the names of the fields that were defined in the request
     * 
     * @return
     */
    public List<String> getDefinedValues() {
        return this.definedValues;
    }

}
