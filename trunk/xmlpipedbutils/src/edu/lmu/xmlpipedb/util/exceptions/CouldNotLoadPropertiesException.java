package edu.lmu.xmlpipedb.util.exceptions;

public class CouldNotLoadPropertiesException extends Exception {


	private static final long serialVersionUID = 1L;

	/**
	 * Creates CouldNotLoadPropertiesException exception with no
	 * explanatory text.
	 */
	public CouldNotLoadPropertiesException() {
		super();
	}

	/**
	 * Creates CouldNotLoadPropertiesException exception with a message
	 * explaining what went wrong.
	 * 
	 * @param arg0
	 */
	public CouldNotLoadPropertiesException(String arg0) {
		super(arg0);
	}


}
