package edu.lmu.xmlpipedb.util.app;

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
	public String[] getCategories(){
		Vector cats = new Vector();
		Set keys = _properties.keySet();
		Iterator iter = keys.iterator();
		while( iter.hasNext() ){
			HibernateProperty hp = (HibernateProperty)iter.next();
			cats.add(hp.getCategory());
		}
		
		return (String[])cats.toArray();
	}
	
	/**
	 * Returns a string array of type names for the category passed in.
	 * 
	 * @param category
	 * @return
	 */
	public String[] getTypes(String category){
		Vector types = new Vector();
		Set keys = _properties.keySet();
		Iterator iter = keys.iterator();
		while( iter.hasNext() ){
			HibernateProperty hp = (HibernateProperty)iter.next();
			if( hp.getCategory().equals(category))
				types.add(hp.getType());
		}
		
		return (String[])types.toArray();
	}
	
	public String[] getPropertyNames(String category, String type){
		Vector props = new Vector();
		Set keys = _properties.keySet();
		Iterator iter = keys.iterator();
		while( iter.hasNext() ){
			HibernateProperty hp = (HibernateProperty)iter.next();
			if( hp.getCategory().equals(category) && hp.getType().equals(type))
				props.add(hp.getName());
		}
		
		return (String[])props.toArray();
	}
	
	public void setProperty( HibernateProperty hp ){
		_properties.put(hp.getFullyQualifiedName(), hp);
	}
	
	public HibernateProperty getProperty( String propName ){
		return (HibernateProperty)_properties.get(propName);
	}
	
	
	//### DEFINE VARS ###
	private HashMap _properties = new HashMap();

}
