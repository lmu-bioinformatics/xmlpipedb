/********************************************************
 * Filename: ExportToGenMaPP.java
 * Author: LMU
 * Program: gmBuilder
 * Description: Extract the data from the Postgresql 
 * database.    
 * Revision History:
 * 20060422: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go.ExportGoData;
import edu.lmu.xmlpipedb.gmbuilder.GenMAPPBuilder;

/**
 * Class to extract from the database
 * @author LMU
 *
 */
public class ExportToGenMaPP {

	
	private static Map<String, String> uniprotTable_ID = new HashMap<String, String>();
	private static Map<String, String> uniprotTable_EntryName = new HashMap<String, String>();
	private static Map<String, String> uniprotTable_GeneName = new HashMap<String, String>();
	private static Map<String, String> uniprotTable_ProteinName = new HashMap<String, String>();
	
	private static Connection connection = null;
	
	/**
	 * Empty Constructor.
	 */
	public ExportToGenMaPP() {}

	/**
	 * Queries for the gene names in the database and adds them to the given map
	 * keyed on the given ID.
	 * 
	 * @param map
	 */
	private static void extractNames(String id, Map<String, String> map) throws SQLException {
		// First, yank out the primary names.
		extractNamesForType(id, map, "primary");

		// Then, yank out the ordered locus names.
		extractNamesForType(id, map, "ordered locus");
		
		// Finally, add anything else that has neither.
		extractNamesForType(id, map, null);
	}

	/**
	 * Extracts names with a given name type.
	 */
	private static void extractNamesForType(String id, Map<String, String> map, String type) throws SQLException {
        //
        //  uniprotTable_GeneName <UID> <Uniprot Table GeneName>
        //
		final String baseQuery = "select value from genenametype inner join entrytype_genetype on(entrytype_genetype_name_hjid = entrytype_genetype.hjid) where entrytype_gene_hjid = ?";
		final String baseQueryWithFilter = baseQuery + " and type = ?";

		PreparedStatement ps = connection.prepareStatement(
				(type != null) ? baseQueryWithFilter : baseQuery);
		ps.setString(1, id);
		if (type != null) {
			ps.setString(2, type);
		}
		ResultSet result = ps.executeQuery();
        while(result.next()) {
        	if (!map.containsKey(id)) {
            	map.put(id, result.getString(1));
        	}
        }
        ps.close();
	}

	/**
	 * Extract the unique entries
	 * @throws SQLException
	 */
	private static void extractUniqueEntries() throws SQLException {
        //
        //  uniprotTable_ID <UID> <Uniprot Table ID>
        //
		PreparedStatement ps = connection.prepareStatement(
				"SELECT " +
        		"entrytype_accession_hjid, " +
        		"hjvalue " +
        		"FROM entrytype_accession where entrytype_accession_hjindex = 0");
		ResultSet result = ps.executeQuery();
        while(result.next()) {
        	uniprotTable_ID.put(result.getString(1), result.getString(2));
        }
        ps.close();
	}
	
	/**
	 * Extract the accesion names
	 * @throws SQLException
	 */
	private static void extractAccessionNames() throws SQLException {
        //
        //  uniprotTable_ID <UID> <Uniprot Table ID>
        //
		PreparedStatement ps = connection.prepareStatement(
				"SELECT " +
        		"entrytype_accession_hjid, " +
        		"hjvalue " +
        		"FROM entrytype_accession where entrytype_accession_hjindex = 0");
		ResultSet result = ps.executeQuery();
        while(result.next()) {
        	uniprotTable_ID.put(result.getString(1), result.getString(2));
        }
        ps.close();
	}
	
	/**
	 * Extract the entry names
	 * @param id
	 * @throws SQLException
	 */
	private static void extractEntryName(String id) throws SQLException {
        //
        //  uniprotTable_EntryName <UID> <Uniprot Table EntryName>
        //  
    	PreparedStatement ps = connection.prepareStatement(
    			"SELECT hjvalue " +
        		"FROM entrytype_name " +
        		"WHERE entrytype_name_hjid = ?");
    	ps.setString(1, id);
    	ResultSet result = ps.executeQuery();
        while(result.next()) {
        	uniprotTable_EntryName.put(id, result.getString(1));
        }
        ps.close();
	}
	
	/**
	 * Extract the protein names
	 * @param id
	 * @throws SQLException
	 */
	private static void extractProteinName(String id) throws SQLException {           
        //
        //  uniprotTable_ProteinName <UID> <Uniprot Table ProteinName>
        //
		PreparedStatement ps = connection.prepareStatement(
				"select value from entrytype " +
				"inner join proteinnametype on " +
				"(protein = proteintype_name_hjid) " +
				"where entrytype.hjid = ? and " +
				"proteintype_name_hjindex = 0;");
    	ps.setString(1, id);
    	ResultSet result = ps.executeQuery();
        while(result.next()) {
        	uniprotTable_ProteinName.put(id, result.getString(1));
        }
        
        ps.close();
	}
	
	
	/**
	 * Extract all the table data
	 * @throws SQLException
	 */
	private static void extractAllData() throws SQLException {
		
		extractUniqueEntries();		
		extractAccessionNames();

		for(String id : uniprotTable_ID.keySet()) {
			extractEntryName(id);		
			extractNames(id, uniprotTable_GeneName);
			extractProteinName(id);
		}
	}
	
	

	/**
	 * Export all data to a new Access GenMaPP file.
	 * @param outputFile
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JAXBException 
	 * @throws SAXException 
	 * @throws HibernateException 
	 */
	public static void exportToGenMaPP(File outputFile) 
			throws IOException, ClassNotFoundException, 
			SQLException, HibernateException, SAXException, 
			JAXBException {
		
		//Create relational database connection.
		Class.forName("org.postgresql.Driver");
		Configuration hibernateConfiguration = GenMAPPBuilder.createHibernateConfiguration();
		
		// need to get the configuration
		if (hibernateConfiguration != null) {
			connection = DriverManager.getConnection(
					hibernateConfiguration.getProperty("hibernate.connection.url"), 
					hibernateConfiguration.getProperty("hibernate.connection.username"), 
					hibernateConfiguration.getProperty("hibernate.connection.password"));
		}
		
		//Get todays date to tag in access file for creation date.
		String dateString1 = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		//Get todays date to tag in access file for creation date.
		String dateString2 = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		
		//Extract the data from the relational database.
		extractAllData();
		
		//Close the relational database connection.
		connection.close();
		
		// ********************************************************
		// ************** EXPORT UNIPROT TABLES TO GENAMPP ********
		// ********************************************************
	
		//Create and build the Access GenMaPP file.
		try {
			//Create the file,.
			AccessFileCreator.createNewAccessFile(outputFile);
			
			//Open the Access database connection.
			AccessFileCreator.openConnection(outputFile);
			
			//Update the "Info Table".
			AccessFileCreator.updateInfoTable("Loyola Marymount University",
					dateString1, "UniProt", "|Escherichia coli K12|",
					dateString1, "|S|T|", "");
			
			//Update the "Systems Table".
			AccessFileCreator.updateSystemsTable("S", dateString2, 
					"ID|EntryName\\sBF|GeneName\\sBF|");
			
			//Update the "Relations Table".
			AccessFileCreator.updateRelationsTable("S", "T", 
					"UniProt-GeneOntology", "Direct", "");
			
			//Create the "Uniprot Table".
			AccessFileCreator.createUniProtTable();
			
			//Fill the "Uniprot Table".
			for(String id : uniprotTable_ID.keySet()) {
				AccessFileCreator.fillUniProtTable(
						uniprotTable_ID.get(id), 
						uniprotTable_EntryName.get(id), 
						uniprotTable_GeneName.get(id),
						uniprotTable_ProteinName.get(id),
						"", "|Escherichia coli K12|", dateString2, "");
			}
					
			
		} finally {
			//Close the Access database connection.
			AccessFileCreator.closeConnection();
		}
		
		// ********************************************************
		// ************** EXPORT GO TABLES TO GENAMPP ********
		// ********************************************************

		(new ExportGoData(outputFile)).export();
		
		
	}
}
