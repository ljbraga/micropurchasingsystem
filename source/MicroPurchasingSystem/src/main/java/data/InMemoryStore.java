/**
 * 
 */
package data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import static java.lang.Math.toIntExact;

/**
 * Provides a basic definition of an in memory store
 * containing a map from id to value
 * 
 * and an id generator
 * @author developer
 *
 */
public abstract class InMemoryStore <T> {
	/**
	 * this hashmap is used to keep track of the "saved" values
	 */
	protected ConcurrentHashMap<Long, T> store = new ConcurrentHashMap<>();
	
	/**
	 * used to generate new ids
	 */
	private AtomicLong sequenceNumber = new AtomicLong();
	
	/**
	 * this will be used to simulate the access to the database
	 */
	private int waitTime = 2000;
	
	
	/**
	 * generates a new identifier
	 * @return
	 */
	public long generateObjectId() {
		return this.sequenceNumber.getAndIncrement();
	}
	
	/**
	 * halts the execution according to the set SLA
	 */
	public void simulateSLA() {
		// let's introduce some randomness
		int randWait = ThreadLocalRandom.current().nextInt(0, this.waitTime/4);
		long waitTime = this.waitTime;
		
		// if the number is even we shall add the randWait value,
		// thus, taking longer.
		// otherwise we remove, taking less time
		if (randWait % 2 == 0) {
			waitTime = this.waitTime + randWait;
		} else {
			waitTime = this.waitTime - randWait;
		}
		try {
			Thread.sleep(toIntExact(waitTime));
		} catch (InterruptedException e) {
		}
	}
}
