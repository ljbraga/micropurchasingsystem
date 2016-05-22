/**
 * 
 */
package com.micro.controllers;

/**
 * Used to represent the names of the fields that can be
 * set for a purchase
 * @author developer
 *
 */
public enum FieldsNames {
    ID("id"),
    PRODUCT_TYPE("productType"),
    EXPIRES("expires"),
    DESCRIPTION("descrption"),
    QUANTITY("quantity"),
    VALUE("value");
    
    private final String text;

    private FieldsNames(final String text) {
        this.text = text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

    /**
     * @return the size
     */
    public static int getSize() {
        return FieldsNames.size;
    }

    private static final int size = FieldsNames.values().length;
}