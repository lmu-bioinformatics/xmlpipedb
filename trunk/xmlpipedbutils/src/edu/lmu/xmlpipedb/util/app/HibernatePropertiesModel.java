package edu.lmu.xmlpipedb.util.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class HibernatePropertiesModel {

	
	/**
	 * Returns all the categories available.
	 * 
	 * @return
	 */
	public Set getCategories(){
		HashMap cats = new HashMap();
		Set keys = _properties.keySet();
		Iterator iter = keys.iterator();
		while( iter.hasNext() ){
			String key = (String) iter.next();
			HibernateProperty hp = (HibernateProperty)_properties.get(key);
			cats.put(hp.getCategory(), "");
		}
		
		return cats.keySet();
	}
	
	/**
	 * Returns a string array of type names for the category passed in.
	 * The returned array is sorted alphabetically.
	 * 
	 * @param category
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String[] getTypes(String category){
		HashMap types = new HashMap();
		Set keys = _properties.keySet();
		Iterator iter = keys.iterator();
		while( iter.hasNext() ){
			String key = (String) iter.next();
			HibernateProperty hp = (HibernateProperty)_properties.get(key);
			if( hp.getCategory().equals(category))
				types.put(hp.getType(), hp.getType());
		}
		
		//String[] test =  (String[])types.toArray();
		// sort the list
		ArrayList l = new ArrayList();
		l.addAll(types.values());
		Collections.sort(l);
		
		
		String[] ret = new String[l.size()];
		l.toArray(ret);
		//types.toArray(ret);
		//Set s = types.keySet();
		//s.toArray(ret);
		
		
		return ret;
	}
	
	/**
	 * @param category
	 * @param type
	 * @return
	 */
	public Enumeration getPropertyNames(String category, String type){
		Vector props = new Vector();
		Set keys = _properties.keySet();
		Iterator iter = keys.iterator();
		while( iter.hasNext() ){
			String key = (String) iter.next();
			HibernateProperty hp = (HibernateProperty)_properties.get(key);
			if( hp.getCategory().equals(category) && hp.getType().equals(type))
				props.add(hp.getName());
		}
		
		return props.elements();
	}
	
	/**
	 * Returns an Enumeration with all the property names in the model
	 * @return
	 */
	public Iterator getProperties(){
		Set keys = _properties.keySet();
		return keys.iterator();
	}
	
	public ArrayList getProperties(String category, String type){
		ArrayList props = new ArrayList();
		Set keys = _properties.keySet();
		Iterator iter = keys.iterator();
		while( iter.hasNext() ){
			String key = (String) iter.next();
			HibernateProperty hp = (HibernateProperty)_properties.get(key);
			if( hp.getCategory().equals(category) && hp.getType().equals(type))
				props.add(hp);
		}
		
		//return props.elements();
		return props;
	}
	
//	/**
//	 * Returns an Enumeration with all the property names in the model
//	 * @return
//	 */
//	public Iterator getProperties(){
//		Set keys = _properties.keySet();
//		return keys.iterator();
//
//	}
	
	/**
	 * @param hp
	 */
	public void add( HibernateProperty hp ){
		_properties.put(hp.getFullyQualifiedName(), hp);
	}
	
	/**
	 * Gets the HibernateProperty object for the given property name
	 * @param propName
	 * @return
	 */
	public HibernateProperty getProperty( String propName ){
		return (HibernateProperty)_properties.get(propName);
	}
	
	public void remove( HibernateProperty hp ){
		_properties.remove(hp.getFullyQualifiedName());
	}
	
	
	//### DEFINE VARS ###
	private HashMap _properties = new HashMap();

}
