package edu.lmu.xmlpipedb.util.engines;

import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

public class Criterion {

	/**
	 * Sets the fields named with the values passed in. Uses the setQuery method
	 * to set the query, which then also sets the table name(s). 
	 * 
	 * @param name
	 * @param path
	 * @param query
	 * @throws InvalidParameterException 
	 */
	public Criterion( String name, String path, String query ) throws InvalidParameterException {
		_name = name;
		if( path == null && query == null )
			throw new InvalidParameterException("Both path and query were null, one of them must be non-null.");
		_digesterPath = path;
		this.setQuery( query );
	}

	/**
	 * @return Returns the digesterPath.
	 */
	public String getDigesterPath() {
		return _digesterPath;
	}
	/**
	 * @param path The digesterPath to set.
	 */
	public void setDigesterPath(String path) {
		_digesterPath = path;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return _name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String _name) {
		this._name = _name;
	}
	/**
	 * @return Returns the table.
	 */
	public String getTable() {
		return _table;
	}
	/**
	 * @param table The table to set.
	 */
	public void setTable(String table) {
		this._table = table;
	}
	
	/**
	 * @return the _query
	 */
	public String getQuery() {
		return _query;
	}

	/**
	 * Sets the query field to be the String passed in. Also, parses out the
	 * table name(s) from the query and sets the table field.
	 * 
	 * 
	 * @param _query the _query to set
	 */
	public void setQuery(String _query) {

		int fromIndex = _query.toLowerCase().indexOf(" from ");

		String fromPart = _query.substring(fromIndex + 5);
		
		int whereIndex = fromPart.toLowerCase().indexOf(" where ");
		if( whereIndex != -1 )
			fromPart = fromPart.substring(0, whereIndex);
		
		int semicolonIndex = fromPart.indexOf(";");
		if(semicolonIndex != -1 )
			fromPart = fromPart.substring(0, semicolonIndex);
		
		setTable(fromPart.trim());
		this._query = _query;
	}
	
	/**
	 * @return Returns the Database record count for the table specified. If
	 * no table has been specified or this count has not been obtained, the
	 * value will be -1.
	 */
	public int getDbCount() {
		return _dbCount;
	}
	/**
	 * @param Count The count to set.
	 */
	public void setDbCount(int count) {
		this._dbCount = count;
	}
	
	/**
	 * @return Returns the Database record count for the table specified. If
	 * no table has been specified or this count has not been obtained, the
	 * value will be -1.
	 */
	public int getXmlCount() {
		return _xmlCount;
	}
	/**
	 * Sets the count to the value passed in. The caller must be aware that
	 * the initial count is set to -1, so if getXmlCount is being called and
	 * incremented by 1 before calling setXmlCount, the count may be off by 1
	 * unless the count was initialized to 0 by the caller before the series
	 * was started.
	 * 
	 * @param Count The count to set.
	 */
	public void setXmlCount(int count) {
		this._xmlCount = count;
	}

	
	

	// CLASS MEMBERS
	private String _name = null;
	private String _digesterPath = null;
	private String _table = null;
	private String _query = null;
	private int _dbCount = -1;
	private int _xmlCount = -1;

	
	
}
