/**
 * 
 */
package com.micro.controllers;

import java.util.List;

/**
 * This class provides a way for the com.micro.controllers to encapsulate list
 * results in JSON object
 * 
 * @author developer
 *
 */
public class ListResultObject {
    private List<?> items;
    private int length;

    public ListResultObject(List<?> items) {
        this.items = items;
        this.length = this.items.size();
    }

    /**
     * @return the items
     */
    public List<?> getItems() {
        return items;
    }

    /**
     * @param items
     *            the items to set
     */
    public void setItems(List<?> items) {
        this.items = items;
        this.length = items.size();
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }
}
