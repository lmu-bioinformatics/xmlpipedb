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

/**
 * Class that exports to GenMaPP
 * @author LMU
 *
 */
public class AccessFileCreator {

	private static final File GENE_DB_TMPL = new File(
		"src/edu/lmu/xmlpipedb/gmbuilder/resource/dbFiles/GeneDBTmpl.mdb");
	
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
	 * Create a new Access file.
	 * @param outputFile
	 * @throws IOException
	 */
	public static void createNewAccessFile(File outputFile) throws IOException {
		//make a copy of the template file.
		copyFile(outputFile);
	}

	/**
	 * Open connection to the access database
	 * @param outputFile
	 * @return the connection, may be ignored.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Connection openConnection(File outputFile) throws 
			ClassNotFoundException, SQLException, IOException {

		
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		   
        String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
        database += outputFile.getAbsolutePath() + ";DriverID=22;READONLY=false}"; 
        
        connection = DriverManager.getConnection(database ,"","");
        
        // Alternative connection URL when using a database other than Access.
        //connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gmproxy", "<<username>>", "<<password>>");
        
        return connection;
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
        // Alternative column definitions when not using Access.
        //"ProteinName varchar," +
        //"Function varchar," +
        //"Species varchar," +
        //"\"Date\" varchar," +
        //"Remarks varchar)");
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
	 * Update the Ralations Table
	 * @param systemCode
	 * @param relatedCode
	 * @param relation
	 * @param type
	 * @param source (may be null)
	 * @throws SQLException
	 */
	public static void updateRelationsTable(String systemCode, 
			String relatedCode, String relation, String type, 
			String source) throws SQLException {
	
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO Relations (" +
            	"SystemCode," +
            	"RelatedCode," +
            	"Relation," +
            	"Type," +
            	"Source)" +
            	"VALUES (?, ?, ?, ?, ?)");
    	ps.setString(1, systemCode);
    	ps.setString(2, relatedCode);
    	ps.setString(3, relation);
    	ps.setString(4, type);
    	ps.setString(5, source);
    	ps.executeUpdate();
    	ps.close();
	}
	
	
	/**
	 * Update the systems table
	 * @param systemCodeList
	 * @param dateString
	 * @param columns (may be null)
	 * @throws SQLException
	 */
	public static void updateSystemsTable(String systemCode, 
			String dateString, String columns) throws SQLException {
		
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
