package edu.lmu.xmlpipedb.util.exceptions;

public class InvalidParameterException extends XpdException {


	private static final long serialVersionUID = 1L;

	/**
	 * Creates InvalidParameterException exception with no
	 * explanatory text.
	 */
	public InvalidParameterException() {
		super();
	}

	/**
	 * Creates InvalidParameterException exception with a message
	 * explaining what went wrong.
	 * 
	 * @param arg0
	 */
	public InvalidParameterException(String arg0) {
		super(arg0);
	}


}
