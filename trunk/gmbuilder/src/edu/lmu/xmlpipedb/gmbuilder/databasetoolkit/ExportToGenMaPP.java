/********************************************************
 * Filename: ExportToGenMaPP.java
 * Author: LMU
 * Program: gmBuilder
 * Description: Export the data to the access database.    
 * Revision History:
 * 20060422: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Class that exports to GenMaPP
 * @author LMU
 *
 */
public class ExportToGenMaPP {

	private Connection connection = null;
	private String outputFile = null;
	
	/**
	 * Constructor 
	 * @param templateFile
	 * @param outputFile
	 * @throws IOException
	 */
	public ExportToGenMaPP(String templateFile, String outputFile) throws IOException {
		this.outputFile = outputFile;
		
		//make a copy of the template file.
		copyFile(templateFile, new File(outputFile));
	}
	
	/**
	 * Copy template file to output file
	 * @param templateFile
	 * @param fileOut
	 * @throws IOException
	 */
	private void copyFile(String templateFile, File fileOut) throws IOException {
		InputStream in = getClass().getResourceAsStream(templateFile);
		OutputStream out = new FileOutputStream(fileOut);
	    
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
	 */
	public void openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		   
        String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
        database += outputFile.trim() + ";DriverID=22;READONLY=false}"; 
        
        connection = DriverManager.getConnection(database ,"","");
	}
	
	/**
	 * Close connection to the access database
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		connection.close();
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
	public void updateInfoTable(String owner, 
			String version, String modSystem, String species,
			String modify, String displayOrder, 
			String notes) throws SQLException {
		
        Statement s = connection.createStatement();
        s.execute("INSERT INTO Info (" +
        		"Owner," +
        		"Version," +
        		"MODSystem," +
        		"Species," +
        		"Modify," +
        		"DisplayOrder," +
        		"Notes) " +
        		"VALUES (" +
        		"'" + owner + "'," +
        		"'" + version + "'," +
        		"'" + modSystem + "'," +
        		"'" + species + "'," +
        		"'" + modify + "'," +
        		"'" + displayOrder + "'," +
        		"'" + notes + "')");
        s.close();
	}
	
	/**
	 * Create the Uniprot table
	 * @throws SQLException
	 */
	public void createUniProtTable() throws SQLException {
		
        Statement s = connection.createStatement();
        s.execute("CREATE TABLE UniProt (" +
        		"ID VARCHAR(50) NOT NULL," +
        		"EntryName VARCHAR(50) NOT NULL," +
        		"GeneName VARCHAR(50) NOT NULL," +
        		"ProteinName MEMO," +
        		"Function MEMO," +
        		"Species MEMO," +
        		"\"Date\" DATE," +
        		"Remarks MEMO)");
        s.execute("ALTER TABLE UniProt ADD CONSTRAINT UniProt_constraint PRIMARY KEY(ID)"); 

        s.close();
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
    public void fillUniProtTable(String id, 
    			String entryName, String geneName, String proteinName, 
    			String function, String species, String date, 
    			String remarks) throws SQLException {
	    	PreparedStatement ps = connection.prepareStatement("INSERT INTO UniProt (" +
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
	public void createAndFillSystemTable(String table, 
			List<String> idList, String species, String date, 
			String remarks) throws SQLException {
        
		Statement s = connection.createStatement();

        s.execute("CREATE TABLE " + table +" (" +
        		"ID VARCHAR(50) NOT NULL," +
        		"Species MEMO," +
        		"Date DATE," +
        		"Remarks MEMO)");
        
        s.execute("ALTER TABLE " + table + " ADD CONSTRAINT " + table + "_constraint PRIMARY KEY(ID)"); 
        
        for(String id : idList) {
            s.execute("INSERT INTO " + table +" (" +
            		"ID," +
            		"Species," +
            		"Date," +
            		"Remarks)" +
            		"VALUES (" +
            		"'" + id + "'," +
            		"'" + species + "'," +
            		"'" + date + "'," +
            		"'" + remarks + "')");
        }
        s.close();
	}
	
	/**
	 * Create and fill the Relations table
	 * @param table
	 * @param primary
	 * @param relatedList
	 * @param bridge
	 * @throws SQLException
	 */
	public void createAndFillRelationTable(String table, 
			String primary, List<String> relatedList, 
			String bridge) throws SQLException {
        
		Statement s = connection.createStatement();
		
        s.execute("CREATE TABLE " + table +" (" +
        		"Primary VARCHAR(50) NOT NULL," +
        		"Related VARCHAR(50) NOT NULL," +
        		"Bridge VARCHAR(3) NOT NULL)");

        for(String related : relatedList) {
            s.execute("CREATE TABLE " + table +" (" +
            		"Primary," +
            		"Related," +
            		"Bridge)" +
            		"VALUES (" +
            		"'" + primary + "'," +
            		"'" + related + "'," +
            		"'" + bridge + "')");
        }
        s.close();
	}
	
	/**
	 * Update the systems table
	 * @param systemCodeList
	 * @param date
	 * @throws SQLException
	 */
	public void updateSystemsTable(
			List<String> systemCodeList, String date) throws SQLException {
        
		Statement s = connection.createStatement();
		
		for(String systemCode : systemCodeList) {
            s.execute("UPDATE Systems " +
            		"SET \"Date\"='04/06/2006'" +
            		"WHERE SystemCode='" + systemCode + "'");
		}
        s.close();
	}
}
