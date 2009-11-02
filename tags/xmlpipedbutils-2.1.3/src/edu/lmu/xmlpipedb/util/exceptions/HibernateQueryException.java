package edu.lmu.xmlpipedb.util.exceptions;

public class HibernateQueryException extends XpdException {

	private static final long serialVersionUID = 1L;

	
	/**
	 * Creates HibernateQueryException with no explanatory text.
	 */
	public HibernateQueryException() {
		super();
	}

	/**
	 * Creates HibernateQueryException with a message to explain
	 * what went wrong.
	 * 
	 * @param arg0
	 */
	public HibernateQueryException(String arg0) {
		super(arg0);
	}


}
