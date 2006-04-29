/********************************************************
 * Filename: AccessFileCreator.java
 * Author: LMU
 * Program: gmBuilder
 * Description: Export the data to the access database.    
 * Revision History:
 * 20060422: Initial Revision.
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Class that exports to GenMaPP
 * @author LMU
 *
 */
public class AccessFileCreator {

	private static final File GENE_DB_TMPL = new File(
		"/edu/lmu/xmlpipedb/gmbuilder/resource/dbFiles/GeneDBTmpl.mdb");
	
	private static Connection connection = null;
	
	/**
	 * Empty Constructor 
	 */
	public AccessFileCreator() {}
	
	/**
	 * Copy template file to output file
	 * @param templateFile
	 * @param fileOut
	 * @throws IOException
	 */
	private static void copyFile(File outputFile) throws IOException {
		InputStream in = new FileInputStream(GENE_DB_TMPL);
		OutputStream out = new FileOutputStream(outputFile);
	    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
	
	/**
	 * Open connection to the access database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static void openConnection(File outputFile) throws ClassNotFoundException, SQLException, IOException {
		//make a copy of the template file.
		copyFile(outputFile);
		
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		   
        String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
        database += outputFile.getAbsolutePath() + ";DriverID=22;READONLY=false}"; 
        
        connection = DriverManager.getConnection(database ,"","");
	}
	
	/**
	 * Close connection to the access database
	 * @throws SQLException
	 */
	public static void closeConnection() throws SQLException {
		if(connection != null) {
			connection.close();
		}
	}
	
	/**
	 * Update info table
	 * @param owner
	 * @param version
	 * @param modSystem
	 * @param species
	 * @param modify
	 * @param displayOrder
	 * @param notes
	 * @throws SQLException
	 */
	public static void updateInfoTable(String owner, 
			String version, String modSystem, String species,
			String modify, String displayOrder, 
			String notes) throws SQLException {
		
        PreparedStatement ps = connection.prepareStatement(
        		"INSERT INTO Info (" +
        		"Owner," +
        		"Version," +
        		"MODSystem," +
        		"Species," +
        		"Modify," +
        		"DisplayOrder," +
        		"Notes) " +
        		"VALUES (?, ?, ?, ?, ?, ?, ?)");
    	ps.setString(1, owner);
    	ps.setString(2, version);
    	ps.setString(3, modSystem);
    	ps.setString(4, species);
    	ps.setString(5, modify);
    	ps.setString(6, displayOrder);
    	ps.setString(7, notes);
    	ps.executeUpdate();
    	ps.close();
	}
	
	/**
	 * Create the Uniprot table
	 * @throws SQLException
	 */
	public static void createUniProtTable() throws SQLException {
		
		PreparedStatement ps = connection.prepareStatement(
				"CREATE TABLE UniProt (" +
        		"ID VARCHAR(50) NOT NULL," +
        		"EntryName VARCHAR(50) NOT NULL," +
        		"GeneName VARCHAR(50) NOT NULL," +
        		"ProteinName MEMO," +
        		"Function MEMO," +
        		"Species MEMO," +
        		"\"Date\" DATE," +
        		"Remarks MEMO)");
		ps.executeUpdate();
		
		ps = connection.prepareStatement(
				"ALTER TABLE UniProt " +
				"ADD CONSTRAINT UniProt_constraint " +
				"PRIMARY KEY(ID)"); 
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * Fill in the Uniprot table
	 * @param id
	 * @param entryName
	 * @param geneName
	 * @param proteinName
	 * @param function
	 * @param species
	 * @param date
	 * @param remarks
	 * @throws SQLException
	 */
    public static void fillUniProtTable(String id, 
    			String entryName, String geneName, String proteinName, 
    			String function, String species, String date, 
    			String remarks) throws SQLException {
	    	PreparedStatement ps = connection.prepareStatement(
	    			"INSERT INTO UniProt (" +
	        		"ID," +
	        		"EntryName," +
	        		"GeneName," +
	        		"ProteinName," +
	        		"Function," +
	        		"Species," +
	        		"\"Date\"," +
	        		"Remarks)" +
	        		"VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
	    	ps.setString(1, id);
	    	ps.setString(2, entryName);
	    	ps.setString(3, geneName);
	    	ps.setString(4, proteinName);
	    	ps.setString(5, function);
	    	ps.setString(6, species);
	    	ps.setString(7, date);
	    	ps.setString(8, remarks);
	    	ps.executeUpdate();
	    	ps.close();
    	}
	
    /**
     * Create and fill the System Table
     * @param table
     * @param idList
     * @param species
     * @param date
     * @param remarks
     * @throws SQLException
     */
	public static void createAndFillSystemTable(String table, 
			List<String> idList, String species, String date, 
			String remarks) throws SQLException {
        
		PreparedStatement ps = connection.prepareStatement(
				"CREATE TABLE ? (" +
        		"ID VARCHAR(50) NOT NULL," +
        		"Species MEMO," +
        		"Date DATE," +
        		"Remarks MEMO)");     
		ps.setString(1, table);
		ps.executeUpdate();
		
		ps = connection.prepareStatement(
				"ALTER TABLE ? ADD CONSTRAINT ?_constraint PRIMARY KEY(ID)"); 
		ps.setString(1, table);
		ps.executeUpdate();
		
        for(String id : idList) {
        	ps = connection.prepareStatement(
        			"INSERT INTO ? (" +
            		"ID," +
            		"Species," +
            		"Date," +
            		"Remarks)" +
            		"VALUES (?, ?, ?, ?)");
	    	ps.setString(1, table);
	    	ps.setString(2, id);
	    	ps.setString(3, species);
	    	ps.setString(4, date);
	    	ps.setString(5, remarks);
	    	ps.executeUpdate();
        }
        ps.close();
	}
	
	/**
	 * Create and fill the Relations table
	 * @param table
	 * @param primary
	 * @param relatedList
	 * @param bridge
	 * @throws SQLException
	 */
	public static void createAndFillRelationTable(String table, 
			String primary, List<String> relatedList, 
			String bridge) throws SQLException {
        
		PreparedStatement ps = connection.prepareStatement(
				"CREATE TABLE ? (" +
        		"Primary VARCHAR(50) NOT NULL," +
        		"Related VARCHAR(50) NOT NULL," +
        		"Bridge VARCHAR(3) NOT NULL)");
		ps.setString(1, table);
		ps.executeUpdate();
		
        for(String related : relatedList) {
        	ps = connection.prepareStatement(
        			"CREATE TABLE ? (" +
            		"Primary," +
            		"Related," +
            		"Bridge)" +
            		"VALUES (?, ?, ?)");
	    	ps.setString(1, table);
	    	ps.setString(2, primary);
	    	ps.setString(3, related);
	    	ps.setString(4, bridge);
	    	ps.executeUpdate();
        }
        ps.close();
	}
	
	/**
	 * Update the systems table
	 * @param systemCodeList
	 * @param dateString
	 * @param columns (may be null)
	 * @throws SQLException
	 */
	public static void updateSystemsTable(String systemCode, String dateString, String columns) throws SQLException {
		
		if(columns != null) {
			PreparedStatement ps = connection.prepareStatement(
					"UPDATE Systems " +
	            	"SET \"Date\"= ?, " +
	            	"Columns= ? " +
	            	"WHERE SystemCode= ?");
	    	ps.setString(1, dateString);
	    	ps.setString(2, columns);
	    	ps.setString(3, systemCode);
	    	ps.executeUpdate();
	    	ps.close();
		} else {
			PreparedStatement ps = connection.prepareStatement(
					"UPDATE Systems " +
	            	"SET \"Date\"= ? " +
	            	"WHERE SystemCode= ?");
	    	ps.setString(1, dateString);
	    	ps.setString(2, columns);
	    	ps.setString(3, systemCode);
	    	ps.executeUpdate();
	    	ps.close();
		}
	}
}
