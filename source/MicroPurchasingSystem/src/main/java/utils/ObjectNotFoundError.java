/**
 * 
 */
package utils;

/**
 * To be used when an object could not be found
 * @author developer
 *
 */
public class ObjectNotFoundError extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundError() {
		super();
	}
	
	public ObjectNotFoundError(String message) {
		super(message);
	}

}
