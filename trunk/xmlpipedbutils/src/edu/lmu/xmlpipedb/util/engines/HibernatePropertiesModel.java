package edu.lmu.xmlpipedb.util.engines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class HibernatePropertiesModel {
    /**
     * Returns all the categories available.
     * 
     * @return Set
     */
    public Set<String> getCategories() {
        Map<String, String> cats = new HashMap<String, String>();
        Set<String> keys = _properties.keySet();
        Iterator<String> iter = keys.iterator();

        // store the categories as the key to a hashmap with no value
        while (iter.hasNext()) {
            String key = iter.next();
            HibernateProperty hp = _properties.get(key);
            cats.put(hp.getCategory(), "");
        }

        // get the keyset, which is just the keys (categories)
        return cats.keySet();
    }

    /**
     * Returns a string array of type names for the category passed in. The
     * returned array is sorted alphabetically.
     * 
     * @param category
     * @return
     */
    public String[] getTypes(String category) {
        Set<String> types = new HashSet<String>();
        Set<String> keys = _properties.keySet();
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            HibernateProperty hp = _properties.get(key);
            if (hp.getCategory().equals(category)){
                types.add(hp.getType());
            }
        }

        // ### sort the list ###
        // create an ArrayList, l
        List<String> l = new ArrayList<String>();
        // add all the types to it
        l.addAll(types);
        // use the static sort method in Collections to 
        //sort the list alphabetically 
        Collections.sort(l);

        // create a new String array with length equal to the arraylist's length
        String[] ret = new String[l.size()];
        // add the contents of the array list to the string array
        l.toArray(ret);

        // return the string array
        return ret;
    }

    /**
     * This method determines what type is saved as the selected type for the
     * category given. It selects the type of the first item encountered for
     * the category.
     * 
     * @param category
     * @return String - the type selected for this category, if there is one
     *         otherwise null.
     */
    public String getSelectedType(String category) {
        // set selectedType to null so null will return if no match
    	String selectedType = null;
    	// get a Set and Iterator
        Set<String> keys = _properties.keySet();
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
        	// get the key and the HibernateProperty for that key
            String key = iter.next();
            HibernateProperty hp = _properties.get(key);
            // if the hp has the category we want and is marked as saved
            // then set its type to selectedType and exit the loop
            if (hp.getCategory().equals(category) && hp.isSaved()) {
                selectedType = hp.getType();
                break;
            }
        }

        return selectedType;
    }

    /**
     * Returns an Enumeration of the property names for this category and type.
     * @param category
     * @param type
     * @return Enumeration
     */
    public Enumeration<String> getPropertyNames(String category, String type) {
    	// use the generic <String> to specify the type of objects the
    	// vector will hold
        Vector<String> props = new Vector<String>();
        // get a Set and then its iterator
        Set<String> keys = _properties.keySet();
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
        	// get the key and its HibernateProperty object
            String key = iter.next();
            HibernateProperty hp = _properties.get(key);
            // if the category and type of the hp match the params passed in
            // add the property name to the Vector
            if (hp.getCategory().equals(category) && hp.getType().equals(type))
                props.add(hp.getName());
        }

        // return an enumeration of property names from the Vector
        return props.elements();
    }

    /**
     * Returns an Iterator with all the property names in the model
     * 
     * @return Iterator
     */
    public Iterator<String> getProperties() {
        Set<String> keys = _properties.keySet();
        return keys.iterator();
    }

    
    /**
     * Returns an ArrayList with the properties that match the category and 
     * type passed in.
     * 
     * @param category
     * @param type
     * @return ArrayList
     */
    public List<HibernateProperty> getProperties(String category, String type) {
    	// use the <HibernateProperty> to specify the type of objects this
    	// ArrayList will hold
        List<HibernateProperty> props = new ArrayList<HibernateProperty>();
        // get a Set and its Iterator
        Set<String> keys = _properties.keySet();
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
        	// get a key and its HibernateProperty
            String key = iter.next();
            HibernateProperty hp = _properties.get(key);
            // if the category and type match, add the hp object 
            // to the ArrayList
            if (hp.getCategory().equals(category) && hp.getType().equals(type)){
                props.add(hp);
            }
        }

        // return the ArrayList
        return props;
    }


    /**
     * Add a HibernateProperty object to the model.
     * @param hp - HibernateProperty object
     */
    public void add(HibernateProperty hp) {
    	//Add the property with the fully qualified name as the key
        _properties.put(hp.getFullyQualifiedName(), hp);
    }

    /**
     * Gets the HibernateProperty object for the given property name
     * 
     * @param propName
     * @return
     */
    public HibernateProperty getProperty(String propName) {
        return (HibernateProperty)_properties.get(propName);
    }

    /**
     * Removes the HibernateProperty specified from the model.
     * 
     * @param hp
     */
    public void remove(HibernateProperty hp) {
        _properties.remove(hp.getFullyQualifiedName());
    }

    /**
     * Get the current category
     * @return Returns the currentCategory.
     */
    public String getCurrentCategory() {
        return currentCategory;
    }

    /**
     * Set the current category
     * @param currentCategory The currentCategory to set.
     */
    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    /**
     * Get the current type
     * @return Returns the currentType.
     */
    public String getCurrentType() {
        return currentType;
    }

    /**
     * Set the current type
     * @param currentType The currentType to set.
     */
    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    //### DEFINE VARS ###
    private HashMap<String, HibernateProperty> _properties = new HashMap<String, HibernateProperty>();
    private String currentType = null;
    private String currentCategory = null;
}
