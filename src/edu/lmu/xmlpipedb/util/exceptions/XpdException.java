package edu.lmu.xmlpipedb.util.exceptions;

/**
 * XpdException is the parent exception to all the other exceptions in XpdUtils.
 * 
 * @author Jeffrey Nicholas
 *
 */
public class XpdException extends Exception {


	private static final long serialVersionUID = 1L;

	/**
	 * Creates XpdException exception with no
	 * explanatory text.
	 */
	public XpdException() {
		super();
	}

	/**
	 * Creates XpdException exception with a message
	 * explaining what went wrong.
	 * 
	 * @param arg0
	 */
	public XpdException(String arg0) {
		super(arg0);
	}


}
