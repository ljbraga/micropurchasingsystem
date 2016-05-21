/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import actions.FullPurchase;
import actions.PurchaseActions;
import utils.DatabaseError;
import utils.ObjectNotFoundError;

/**
 * This class provides web interface that allows external
 * services to get, set, delete, and add purchases
 * 
 * This service is provided in the following path: /purchases(/id)?
 * 
 * The following HTTP methods are available:
 * 
 * GET: used to get either one or multiple purchases
 * POST: used to add a new purchase
 * PUT: used to change the data of a given purchase
 * DELETE: used to remove an existing purchase
 * 
 * @author leonel.braga
 * 
 */
@Controller
//@RequestMapping("/purchases/**")
public class PurchaseController {

	/**
	 * This method provides an entry point to get a single
	 * purchase (as well as its details)
	 * 
	 * @param id the id of the purchase
	 * @return A JSON representation of the purchase
	 */
	@RequestMapping(path="/purchases/{id}", method=RequestMethod.GET)
	public @ResponseBody FullPurchase getOnePurchase(@PathVariable("id") long id) {
		FullPurchase purchase = null;
		try {
			purchase = PurchaseActions.getPurchase(id);
		} catch (DatabaseError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ObjectNotFoundError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return purchase;
	}

	/**
	 * This method provides an entry point to get all
	 * the purchases on the system (as well as their details)
	 * 
	 * @return A JSON object containing the following items:
	 * 	- items: List of Purchase Objects
	 *  - length: the number of items found
	 */
	@RequestMapping(path="/purchases/*", method=RequestMethod.GET)
	public @ResponseBody ListResultObject getAllPurchases() {
		List <FullPurchase> purchases = null;
		try {
			purchases = PurchaseActions.getValidPurchases();
		} catch (DatabaseError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (purchases == null) {
			purchases = new ArrayList<FullPurchase>();
		}
		return new ListResultObject(purchases);
	}

	/**
	 * This method provides an entry point to
	 * create a new purchase
	 * 
	 * @return
	 */
	@RequestMapping(path="/purchases/", method=RequestMethod.POST)
	public @ResponseBody String createPurchase() {
		return "create";
	}

	/**
	 * This method provides an entry point to modify
	 * an existing purchase
	 * 
	 * @param id the id of the purchase
	 * @return
	 */
	@RequestMapping(path="/purchases/{id}", method=RequestMethod.PUT)
	public @ResponseBody String modifyPurchase(@PathVariable("id") long id) {
		return "modify: " + id;
	}

	/**
	 * This method provides an entry point to delete an existing
	 * purchase
	 * @param id the id of the purchase
	 * @return
	 */
	@RequestMapping(path="/purchases/{id}", method=RequestMethod.DELETE)
	public @ResponseBody String deletePurchase(@PathVariable("id") long id) {
		return "delete: " + id;
	}
}
