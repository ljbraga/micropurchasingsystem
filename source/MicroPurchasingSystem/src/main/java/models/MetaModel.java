/**
 * 
 */
package models;

/**
 * Provides several base features for any Model in the system
 * 
 * @author leonel.braga
 *
 */
public abstract class MetaModel {
	/**
	 * every object shall have an identifier that is an instance
	 * of long
	 */
	private long id;
	/**
	 * to be used internally to specify if the object is
	 * saved to the database or not
	 */
	private boolean newObject;
	/**
	 * indicates if the purchase details
	 * is deleted. once deleted, the purchase details
	 * cannot be undeleted
	 */

	private boolean deleted;
	
	/**
	 * creating a new object identifier will make the DAO
	 * to assume that this is a new object
	 */
	public MetaModel() {
		this.deleted = false;
		this.id = -1;
		this.newObject = true;
	}
	
	/**
	 * creating an object with a specified id will make the DAO
	 * to assume that this is an existing object
	 * @param id
	 */
	public MetaModel(long id){
		this.deleted = false;
		this.id = id;
		this.newObject = false;
	}
	
	/**
	 * sets a new id for the object
	 * 
	 * @param id
	 */
	public void setId(long id) {
		// for the moment we will not allow the id to be re-set
		if (this.isSaved()) {
			return;
		}
		this.id = id;
	}
	
	/**
	 * returns the current id of the object
	 * 
	 * @return the identifier of the object
	 */
	public long getId() {
		return this.id;
	}
	/**
	 * sets the delete state of the purchase details
	 * @param deleted
	 */
	public void setDeleted(boolean deleted) {
		/* cannot be undeleted  modified */
		if (this.isDeleted()) {
			return;
		}
		this.deleted = deleted;
	}
	
	/**
	 * checks if the current purchasedetails are marked as deleted
	 * @return the current delete state
	 */
	public boolean isDeleted() {
		return this.deleted;
	}

	/**
	 * indicates if the current object is saved to the database
	 * or not
	 */
	public boolean isSaved(){
		return !this.newObject;
	}
}