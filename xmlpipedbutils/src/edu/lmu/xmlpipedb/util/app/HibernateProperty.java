package edu.lmu.xmlpipedb.util.app;

public class HibernateProperty {

	public HibernateProperty(String category, String type, String name,
			String value, boolean isSaved) {
		_category = category;
		_type = type;
		_name = name;
		_value = value;
		_isSaved = isSaved;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s;

		s = "Category = " + _category + "\n";
		s += "Type = " + _type + "\n";
		s += "Name = " + _name + "\n";
		s += "Value = " + _value + "\n";

		return s;
	}

	/**
	 * Returns the fully qualified name of the property, which includes the
	 * category, type and property name in the form:
	 * category.type.name
	 * 
	 * @return
	 */
	public String getFullyQualifiedName() {
		String s;

		s = _category + ".";
		s += _type + ".";
		s += _name;

		return s;
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return _category;
	}

	/**
	 * @param category
	 *            The _category to set.
	 */
	public void setCategory(String category) {
		this._category = category;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return _type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this._type = type;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this._name = name;
	}

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return _value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(String value) {
		this._value = value;
	}
	
	/**
	 * @return Returns the isSaved.
	 */
	public boolean isSaved() {
		return _isSaved;
	}

	/**
	 * @param isSaved The isSaved to set.
	 */
	public void setSaved(boolean isSaved) {
		this._isSaved = isSaved;
	}
	

	// ### DEFINE VARS ###
	private String _category;
	private String _type;
	private String _name;
	private String _value;
	private boolean _isSaved;


} // end class
