/********************************************************
 * Filename: ConnectionManager.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: Manages all connection required by
 * GenMAPP builder.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.cfg.Configuration;

/**
 * @author Joey J. Barrett
 * Class: ConnectionManager
 */
public class ConnectionManager {

	private static final File GENMAPP_DATABASE_TEMPLATE = new File(
		"src/edu/lmu/xmlpipedb/gmbuilder/resource/dbFiles/GeneDBTmpl.mdb");
	
	private static File genMAPPDatabase = null;

	private static Connection relationalDBConnection = null;
	private static Connection genMAPPDBConnection = null;
	private static Connection genMAPPTemplateDBConnection = null;
	
	/**
	 * Opens a connection to the relational database.  If the parameter 
	 * is null an exception is thrown.  If the connection is still open, 
	 * an exception is thrown.
	 * 
	 * @param hibernateConfiguration
	 * @throws Exception
	 */
	public static void openRelationalDB(Configuration hibernateConfiguration) throws Exception {
		//Open a connection to the relational database, requires a hibernate configuration.
		if (hibernateConfiguration != null) {
			if(relationalDBConnection != null) {
				throw new Exception("A relational database connection cannot be created " +
						"while a previous connection is still open.");
			}
			openRelationalDatabaseConnection(hibernateConfiguration);
		} else {
			throw new Exception("A relational database connection is not specified.");
		}
	}
	
	/**
	 * Opens a connection to the GenMAPP Access database.  If the parameter 
	 * is null an exception is thrown.  If the connection is still 
	 * open, an exception is thrown.
	 * 
	 * @param genMAPPDatabase
	 * @throws Exception
	 */
	public static void openGenMAPPDB(File genMAPPDatabase) throws Exception {
		
		if(genMAPPDatabase != null) {
			if(genMAPPDBConnection == null) {
				ConnectionManager.genMAPPDatabase = genMAPPDatabase;
				createGenMAPPDatabase();
				genMAPPDBConnection = openAccessDatabaseConnection(genMAPPDatabase);
			} else {				
				throw new Exception("A GenMAPP database connection cannot be created " +
						"while a previous connection is still open.");
			}	
		} else {
			throw new Exception("A GenMAPP database connection is not specified.");
		}
	}
	
	/**
	 * Opens a connection to the GenMAPP relational database.  If the parameter 
	 * is null an exception is thrown.  If the connection is still 
	 * open, an exception is thrown.
	 * 
	 * @param connectionConfiguration
	 * @throws Exception
	 */
	public static void openGenMAPPDB(ConnectionConfiguration connectionConfiguration) throws Exception {

		//Open a connection to the GenMAPP database, requires a connection configuration.
		if (connectionConfiguration != null) {
			if(genMAPPDBConnection == null) {
				
				DriverManager.registerDriver(connectionConfiguration.getDriver());
			    genMAPPDBConnection = DriverManager.getConnection(connectionConfiguration.getConnectionURL(), 
			    		connectionConfiguration.getUserName(), connectionConfiguration.getPassword());
			} else {				
				throw new Exception("A GenMAPP database connection cannot be created " +
						"while a previous connection is still open.");
			}	
		} else {
			throw new Exception("A GenMAPP database connection is not specified.");
		}
	}
	
	/**
	 * Opens a connection to the GenMAPP template database.  If the 
	 * connection is still open, an exception is thrown.  
	 * NOTE: THIS CONNECTION SHOULD BE USED AS READ ONLY!!!
	 * @throws Exception 
	 * @throws Exception
	 */
	public static void openGenMAPPTemplateDB() throws Exception {
		//Open a connection to the GenMAPP template database.
		if(genMAPPTemplateDBConnection == null) {
			genMAPPTemplateDBConnection = openAccessDatabaseConnection(GENMAPP_DATABASE_TEMPLATE);
		} else {
			throw new Exception("A GenMAPP template database connection cannot be created " +
			"while a previous connection is still open.");
		}	
	}
	
	/**
	 * @return A connection to the GenMAPP database.
	 * @throws Exception
	 */
	public static Connection getGenMAPPDBConnection() throws Exception {
		if(genMAPPDBConnection == null) {
			throw new Exception("A GenMAPP database connection is not open.");
		}
		return genMAPPDBConnection;
	}
	
	/**
	 * @return A connection to the relational dabase.
	 * @throws Exception
	 */
	public static Connection getRelationalDBConnection() throws Exception {
		if(relationalDBConnection == null) {
			throw new Exception("A relational database connection is not open.");
		}
		return relationalDBConnection;
	}
	
	/**
	 * NOTE: THIS CONNECTION SHOULD BE USED AS READ ONLY!!!
	 * @return A connection to the GenMAPP template database.
	 * @throws Exception
	 */
	public static Connection getGenMAPPTemplateDBConnection() throws Exception {
		if(genMAPPTemplateDBConnection == null) {
			throw new Exception("A GenMAPP template database connection is not open.");
		}
		return genMAPPTemplateDBConnection;
	}
	
	/**
	 * Returns true if the connection is open, false otherwise.
	 * @return
	 */
	public static boolean isGenMAPPDBConnectionOpen() {
		if(genMAPPDBConnection != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if the connection is open, false otherwise.
	 * @return
	 */
	public static boolean isRelationalDBConnectionOpen() {
		if(relationalDBConnection != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if the connection is open, false otherwise.
	 * @return
	 */
	public static boolean isGenMAPPTemplateDBConnectionOpen() {
		if(genMAPPTemplateDBConnection != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Close and set null the open GenMAPP database and connection.  
	 * If the connection is null nothing happens.
	 * 
	 * @throws SQLException
	 */
	public static void closeGenMAPPDB() throws SQLException {
		if(genMAPPDBConnection != null) {
			genMAPPDBConnection.close();
			genMAPPDBConnection = null;
			genMAPPDatabase = null;
		}
	}
	
	/**
	 * Close and set null the open relational database connection.  
	 * If the connection is null nothing happens.
	 * 
	 * @throws SQLException
	 */
	public static void closeRelationalDB() throws SQLException {
		if(relationalDBConnection != null) {
			relationalDBConnection.close();
			relationalDBConnection = null;
		}
	}
	
	/**
	 * Close and set null the open GenMAPP template database connection.  
	 * If the connection is null nothing happens.
	 * 
	 * @throws SQLException
	 */
	public static void closeGenMAPPTemplateDB() throws SQLException {
		if(genMAPPTemplateDBConnection != null) {
			genMAPPTemplateDBConnection.close();
			genMAPPTemplateDBConnection = null;
		}
	}
	
	/**
	 * Close and set null all open connections.  
	 * If a connection is null nothing happens.
	 * 
	 * @throws SQLException
	 */
	public static void closeAll() throws SQLException {
		closeRelationalDB();
		closeGenMAPPDB();
		closeGenMAPPTemplateDB();
	}
	

	/**
	 * Open a relational database connection.
	 * 
	 * @param hibernateConfiguration
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	private static void openRelationalDatabaseConnection(Configuration hibernateConfiguration) throws SQLException, ClassNotFoundException {
		//TODO Fix THIS!!!!!!!!!!!!!!!!!!
		Class.forName("org.postgresql.Driver");
		relationalDBConnection = DriverManager.getConnection(
				hibernateConfiguration.getProperty("hibernate.connection.url"), 
				hibernateConfiguration.getProperty("hibernate.connection.username"), 
				hibernateConfiguration.getProperty("hibernate.connection.password"));
	}
	
	/**
	 * Open the GenMAPP database connection.
	 * 
	 * @param databaseFile
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static Connection openAccessDatabaseConnection(File databaseFile) throws ClassNotFoundException, SQLException {
			
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			   
	    StringBuffer databaseConnectionString = new StringBuffer("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=");
	    databaseConnectionString.append(databaseFile.getAbsolutePath());
	    databaseConnectionString.append(";DriverID=22;READONLY=false}"); 
	        
	    return DriverManager.getConnection(databaseConnectionString.toString(), "", "");
	}
	
	/**
	 * Create new GenMAPP database.
	 * @throws IOException
	 */
	private static void createGenMAPPDatabase() throws IOException {
		InputStream in = new FileInputStream(GENMAPP_DATABASE_TEMPLATE);
		OutputStream out = new FileOutputStream(genMAPPDatabase);
	    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
	


}
