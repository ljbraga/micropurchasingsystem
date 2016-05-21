/**
 * 
 */
package data;

/**
 * This class provides an implementation of a factory that
 * provides DAO objects that save the data in memory
 * 
 * @author developer
 *
 */
public class InMemoryDAOFactory extends DAOFactory {

	/* (non-Javadoc)
	 * @see data.DAOFactory#getPurchaseDao()
	 */
	@Override
	public PurchaseDao getPurchaseDao() {
		return new PurchaseDaoMemory();
	}

	/* (non-Javadoc)
	 * @see data.DAOFactory#getPurcahseDetailsDao()
	 */
	@Override
	public PurchaseDetailsDao getPurcahseDetailsDao() {
		return new PurchaseDetailsDaoMemory();
	}

}
