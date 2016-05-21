/**
 * 
 */
package utils;

/**
 * To be used if someone tries to insert a new object that contains
 * a key that already exists
 * @author developer
 *
 */
public class DuplicateObjectKeyError extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateObjectKeyError(String message) {
		super(message);
	}
	
	public DuplicateObjectKeyError() {
		super();
	}

}
