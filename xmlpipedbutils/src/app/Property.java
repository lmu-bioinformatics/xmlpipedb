package app;

public class Property {


	
	public Property(String name, String value) {
		_name = name;
		_value = value;
	}
	/**
	 * @return Returns the _name.
	 */
	public String getName() {
		return _name;
	}
	/**
	 * @param _name The _name to set.
	 */
	public void setName(String _name) {
		this._name = _name;
	}
	/**
	 * @return Returns the _value.
	 */
	public String getValue() {
		return _value;
	}
	/**
	 * @param _value The _value to set.
	 */
	public void setValue(String _value) {
		this._value = _value;
	}

//  #### DEFINE VARS ####
	String _name;
	String _value;

}
