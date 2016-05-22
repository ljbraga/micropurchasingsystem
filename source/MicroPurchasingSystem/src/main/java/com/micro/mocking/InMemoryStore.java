/**
 * 
 */
package com.micro.mocking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import com.micro.utils.DuplicateObjectKeyError;
import com.micro.utils.ObjectNotFoundError;

import static java.lang.Math.toIntExact;

/**
 * Provides a basic definition of an in memory store containing a map from id to
 * value
 * 
 * and an id generator
 * 
 * @author developer
 *
 */
public abstract class InMemoryStore<T> {
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
    private int waitTime = 5000;

    /**
     * generates a new identifier
     * 
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
        int randWait = ThreadLocalRandom.current().nextInt(0, this.waitTime / 4);
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

    /**
     * Checks if there is an object with the given key
     * 
     * @param id
     * @return true is returned
     */
    public boolean existsObject(Long id) {
        return this.store.containsKey(id);
    }

    /**
     * Gets an existing object with the given id
     * 
     * @param id
     * @return null if the object could not be found
     */
    public T getObjectById(long id) {
        this.simulateSLA();
        if (this.existsObject(id)) {
            return this.store.get(id);
        } else {
            return null;
        }
    }

    /**
     * Gets all objects in the store
     * 
     * @return
     */
    public List<T> getAllObjects() {
        ArrayList<T> objects = new ArrayList<T>();
        objects.addAll(this.store.values());
        this.simulateSLA();
        return objects;
    }

    /**
     * Creates a new object in the store
     * 
     * @param the
     *            new object
     * @return
     */
    public long addObject(T object) throws DuplicateObjectKeyError {
        long newId = this.generateObjectId();
        this.store.put(newId, object);
        this.simulateSLA();
        return newId;
    }

    /**
     * updates the contents of a given object in the store
     * 
     * @param id
     *            the id of the object
     * @param object
     *            to be set
     * @throws ObjectNotFoundError
     */
    public void updateObject(long id, T object) throws ObjectNotFoundError {
        if (!this.existsObject(id)) {
            throw new ObjectNotFoundError("No such object: " + id);
        }
        this.store.put(id, object);
        this.simulateSLA();
    }

    /**
     * Removes an existing object from the store
     * 
     * @param id
     *            the id of the object
     * @throws ObjectNotFoundError
     */
    public void deleteObject(long id) throws ObjectNotFoundError {
        if (!this.existsObject(id)) {
            throw new ObjectNotFoundError("No such object: " + id);
        }
        this.store.remove(id);
        this.simulateSLA();
    }
}
