package edu.lmu.xmlpipedb.util.app;

import java.util.ArrayList;
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
		String[] ret = new String[types.size()];
		//types.toArray(ret);
		Set s = types.keySet();
		s.toArray(ret);
		
		return ret;
	}
	
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
	
	public void add( HibernateProperty hp ){
		_properties.put(hp.getFullyQualifiedName(), hp);
	}
	
	public HibernateProperty getProperty( String propName ){
		return (HibernateProperty)_properties.get(propName);
	}
	
	
	
	
	//### DEFINE VARS ###
	private HashMap _properties = new HashMap();

}
