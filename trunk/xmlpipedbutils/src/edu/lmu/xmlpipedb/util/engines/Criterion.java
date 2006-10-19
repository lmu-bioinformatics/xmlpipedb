package edu.lmu.xmlpipedb.util.engines;

public class Criterion {

	public Criterion( String name, String path, String table ) {
		_name = name;
		_digesterPath = path;
		_table = table;
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
	private String _name;
	private String _digesterPath;
	private String _table;
	private int _dbCount = -1;
	private int _xmlCount = -1;
	
	
}
