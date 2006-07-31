/********************************************************
 * Filename: ConnectionConfiguration.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: An instance of this class is a 
 * connection to a database server.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.sql.Driver;

/**
 * @author Joey J. Barrett
 * Class: ConnectionConfiguration
 */
public class ConnectionConfiguration {

	private Driver driver;
	private String connectionURL;
	private String userName;
	private String password;
	
	/**
	 * Creates a new <code>ConnectionConfiguration</code> object.  All
	 * parameters are required.  An example instance of this object is
	 * shown below.
	 * 
	 * <code>
	 * ConnectionConfiguration oracleConnection = 
	 * 		new ConnectionConfiguration(new oracle.jdbc.driver.OracleDriver(), 
	 * 		"jdbc:oracle:thin:@<<machineName>>:<<port>>:<<SID>>", 
	 * 		"<<username>>", "<<password>>");
	 * </code>
	 * 
	 * @param driver
	 * @param connectionURL
	 * @param userName
	 * @param password
	 */
	public ConnectionConfiguration(Driver driver, String connectionURL, String userName, String password) {
		this.driver = driver;
		this.connectionURL = connectionURL;
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * @return A driver for this configuration.
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * @return A connection URL for this configuration.
	 */
	public String getConnectionURL() {
		return connectionURL;
	}
	
	/**
	 * @return a user name for this configuration.
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * @return a password for this configuration.
	 */
	public String getPassword() {
		return password;
	}
}
