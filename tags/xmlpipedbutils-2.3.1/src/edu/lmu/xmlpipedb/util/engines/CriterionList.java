package edu.lmu.xmlpipedb.util.engines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * A structure that creates a bucket list that references each XML pathway for
 * every criterion that is added.
 * 
 * @author geocoso2
 * 
 */
public class CriterionList {

    public CriterionList() {

        _bucketList = new HashMap<String, ArrayList<Criterion>>();

    }

    /**
     * Adds a single Criterion object to the bucketlist, creating a bucket when
     * needed.
     * 
     * @param c
     *            The Criterion to add
     */
    public void addCriteria(Criterion c) {

        String key = c.getDigesterPath();

        if (_bucketList.get(key) == null) {

            ArrayList<Criterion> list = new ArrayList<Criterion>();
            list.add(c);
            _bucketList.put(key, list);

        } else {

            _bucketList.get(key).add(c);

        }

    }

    /**
     * Adds a list of criteria to the proper buckets
     * 
     * @param criteria
     *            The list of criteria to add
     */
    public void addCriteria(Collection<Criterion> criteria) {

        for (Criterion c : criteria) {
            addCriteria(c);
        }

    }

    /**
     * Returns whether or not the given criterion already exists within the
     * CriterionList (name + query + element essentially serves as a key).
     */
    public boolean containsCriterion(Criterion c) {

        String key = c.getDigesterPath();
        
        // No key, no criterion.
        if (_bucketList.get(key) != null) {
            for (Criterion currentC: _bucketList.get(key)) {
                if (currentC.equals(c)) {
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * Removes a bucket from the list.
     * 
     * @param key
     *            The bucket to drop.
     */
    public void removeBucket(String key) {

        if (key == null) {
            return;
        }

        _bucketList.remove(key);
    }

    /**
     * Returns a single bucket from the list.
     * 
     * @param key
     *            The bucket to access
     * @return A list of Criterion
     */
    public List<Criterion> getBucket(String key) {

        return key == null ? null : _bucketList.get(key);

    }

    /**
     * Gathers all of the Criterion objects and places them in a list to be
     * returned.
     * 
     * @return The list of Criterion
     */
    public List<Criterion> getAllCriteria() {

        List<Criterion> list = new ArrayList<Criterion>();

        Set<String> keys = keySet();
        for (String key : keys) {
            list.addAll(_bucketList.get(key));
        }

        return list;

    }

    /**
     * Returns the list of keys used to index the buckets
     * 
     * @return The list
     */
    public Set<String> keySet() {
        return _bucketList.keySet();
    }

    /**
     * The main list that contains all the buckets of criteria.
     */
    private Map<String, ArrayList<Criterion>> _bucketList;

    /**
     * The first criterion of the list.
     */
    public Criterion firstCriterion;
}
