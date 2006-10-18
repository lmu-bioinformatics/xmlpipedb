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
	 * @return Returns the count.
	 */
	public int getCount() {
		return _count;
	}
	/**
	 * @param Count The count to set.
	 */
	public void setCount(int count) {
		this._count = count;
	}
	

	// CLASS MEMBERS
	private String _name;
	private String _digesterPath;
	private String _table;
	private int _count;
	
	
}
