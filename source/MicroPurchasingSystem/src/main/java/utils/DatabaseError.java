/**
 * 
 */
package utils;

/**
 * Used to represent a generic database error
 * @author developer
 *
 */
public class DatabaseError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DatabaseError() {
		super();
	}
	
	public DatabaseError(String message) {
		super(message);
	}
}
