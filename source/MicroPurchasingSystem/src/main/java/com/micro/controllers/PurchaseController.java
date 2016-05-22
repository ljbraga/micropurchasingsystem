/**
 * 
 */
package com.micro.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.micro.actions.FullPurchase;
import com.micro.actions.PurchaseActions;
import com.micro.utils.ValidationError;

import eu.hinsch.spring.boot.actuator.metric.ExecutionMetric;

/**
 * This class provides web interface that allows external services to get, set,
 * delete, and add purchases
 * 
 * This service is provided in the following path: /purchases(/id)?
 * 
 * The following HTTP methods are available:
 * 
 * GET: used to get either one or multiple purchases POST: used to add a new
 * purchase PUT: used to change the com.micro.data of a given purchase DELETE:
 * used to remove an existing purchase
 * 
 * @author leonel.braga
 * 
 */
@RestController
public class PurchaseController {

    private static final Logger log = LoggerFactory.getLogger(PurchaseController.class);

    /**
     * This method provides an entry point to get a single purchase (as well as
     * its details)
     * 
     * @param id
     *            the id of the purchase
     * @return A JSON representation of the purchase
     */
    @RequestMapping(path = "/purchases/{id}", method = RequestMethod.GET)
    @ExecutionMetric("set-purchase")
    public @ResponseBody FullPurchase getOnePurchase(@PathVariable("id") long id) {
        log.info("Trying to get the purchase with the id: " + id);
        FullPurchase purchase = PurchaseActions.getPurchase(id);
        log.info("\t Could get the purchase with the id: " + id);
        return purchase;
    }

    /**
     * This method provides an entry point to get all the purchases on the
     * system (as well as their details)
     * 
     * @return A JSON object containing the following items: - items: List of
     *         Purchase Objects - length: the number of items found
     */
    @ExecutionMetric("get-all-purchase")
    @RequestMapping(path = "/purchases/*", method = RequestMethod.GET)
    public @ResponseBody ListResultObject getAllPurchases() {
        log.info("Getting all the valid purchases");
        List<FullPurchase> purchases = PurchaseActions.getValidPurchases();
        log.info("\t all valid purchases fetched with success");
        return new ListResultObject(purchases);
    }

    /**
     * This method provides an entry point to create a new purchase
     * 
     * @return
     */
    @ExecutionMetric("create-purchase")
    @RequestMapping(path = "/purchases/", method = RequestMethod.POST)
    public @ResponseBody FullPurchase createPurchase(@RequestBody RequestPurchase purchaseValues) {
        List<String> definedFields = purchaseValues.getDefinedValues();

        // the id cannot be set for new items
        if (definedFields.contains(FieldsNames.ID.toString())) {
            throw new ValidationError("ID cannot be set for new purchases");
        }

        // caller has not specified
        if (definedFields.size() != (FieldsNames.getSize() - 1)) {
            throw new ValidationError("All fields must be defined");
        }

        log.info(
                "Trying to create a new purchase with the following details: \n" +
                "\t productType: " + purchaseValues.getProductType() + "\n",
                "\t expires: " + purchaseValues.getExpires() + "\n",
                "\t description: " + purchaseValues.getDescription() + "\n",
                "\t quantity:" + purchaseValues.getQuantity() + "\n",
                "\t value: " + purchaseValues.getValue() + "\n"
        );

        return PurchaseActions.createPurchase(
            purchaseValues.getProductType(),
            purchaseValues.getExpires(),
            purchaseValues.getDescription(),
            purchaseValues.getQuantity(),
            purchaseValues.getValue()
        );
    }

    /**
     * This method provides an entry point to modify an existing purchase
     * 
     * @param id
     *            the id of the purchase
     * @return
     */
    @ExecutionMetric("set-purchase")
    @RequestMapping(path = "/purchases/{id}", method = RequestMethod.PUT)
    public @ResponseBody FullPurchase modifyPurchase(@PathVariable("id") long id,
            @RequestBody RequestPurchase purchaseValues) {
        FullPurchase purchase = PurchaseActions.getPurchase(id);
        List<String> definedFields = purchaseValues.getDefinedValues();

        if (definedFields.size() == 0) {
            throw new ValidationError("No field was set to be modified");
        }
        ArrayList<String> modifications = new ArrayList<>();
        for (String field : definedFields) {
            if (field == FieldsNames.PRODUCT_TYPE.toString()) {
                modifications.add(
                    "\tproductType: " + purchaseValues.getProductType()
                );
                purchase.setProductType(purchaseValues.getProductType());
            } else if (field == FieldsNames.EXPIRES.toString()) {
                modifications.add(
                    "\texpires: " + purchaseValues.getExpires()
                );
                purchase.setExpires(purchaseValues.getExpires());
            } else if (field == FieldsNames.DESCRIPTION.toString()) {
                modifications.add(
                    "\tdescription: " + purchaseValues.getDescription()
                );
                purchase.setDetails(purchaseValues.getDescription());
            } else if (field == FieldsNames.QUANTITY.toString()) {
                modifications.add(
                    "\tquantity: " + purchaseValues.getQuantity()
                );
                purchase.setDetails(purchaseValues.getQuantity());
            } else if (field == FieldsNames.VALUE.toString()) {
                modifications.add(
                    "\tvalue: " + purchaseValues.getValue()
                );
                purchase.setDetails(purchaseValues.getValue());
            }
        }
        log.info(
            "Trying to modify the purchase <" + id +"> with the following data:" +
            String.join("\n", modifications)
        );
        PurchaseActions.updatePurchase(purchase);
        return purchase;
    }

    /**
     * This method provides an entry point to delete an existing purchase
     * 
     * @param id
     *            the id of the purchase
     * @return
     */
    @ExecutionMetric("delete-purchases")
    @RequestMapping(path = "/purchases/{id}", method = RequestMethod.DELETE)
    public @ResponseBody FullPurchase deletePurchase(@PathVariable("id") long id) {
        FullPurchase purchase = PurchaseActions.getPurchase(id);
        PurchaseActions.deletePurchase(purchase);
        return purchase;
    }
}
