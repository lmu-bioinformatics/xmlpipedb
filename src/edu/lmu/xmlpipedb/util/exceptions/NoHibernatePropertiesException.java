package edu.lmu.xmlpipedb.util.exceptions;

public class NoHibernatePropertiesException extends XpdException {

	private static final long serialVersionUID = 1L;

	
	/**
	 * Creates NoHibernatePropertiesException with no explanatory text.
	 */
	public NoHibernatePropertiesException() {
		super();
	}

	/**
	 * Creates NoHibernatePropertiesException with a message to explain
	 * what went wrong.
	 * 
	 * @param arg0
	 */
	public NoHibernatePropertiesException(String arg0) {
		super(arg0);
	}


}
