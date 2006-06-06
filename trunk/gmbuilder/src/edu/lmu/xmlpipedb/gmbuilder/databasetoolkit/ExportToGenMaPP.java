/********************************************************
 * Filename: ExportToGenMaPP.java
 * Author: LMU
 * Program: gmBuilder
 * Description: Extract the data from the relational 
 * database to the GenMAPP database.   
 *  
 * Revision History:
 * 20060422: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.cfg.Configuration;

import edu.lmu.xmlpipedb.gmbuilder.GenMAPPBuilder;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go.ExportGoData;

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
	private static Map<String, String> uniprotTable_Function = new HashMap<String, String>();
	
	private static Map<String, String> blattnerTable_IDs = new HashMap<String, String>();
	
	
	/**
	 * Empty Constructor.
	 */
	public ExportToGenMaPP() {}

	/**
	 * Extract the accesion names
	 * @throws SQLException
	 */
	private static void extractAccessionNames(Connection relationalDBConnetion) throws SQLException {
        //
        //  uniprotTable_ID <UID> <Uniprot Table ID>
        //
		PreparedStatement ps = relationalDBConnetion.prepareStatement(
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
	private static void extractEntryName(Connection relationalDBConnetion, String id) throws SQLException {
        //
        //  uniprotTable_EntryName <UID> <Uniprot Table EntryName>
        //  
    	PreparedStatement ps = relationalDBConnetion.prepareStatement(
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
	 * Queries for the gene names in the database and adds them to the given map
	 * keyed on the given ID.
	 * 
	 * @param map
	 */
	private static void extractNames(Connection relationalDBConnetion, String id, Map<String, String> map) throws SQLException {
		// First, yank out the primary names.
		extractNamesForType(relationalDBConnetion, id, map, "primary");

		// Then, yank out the ordered locus names.
		extractNamesForType(relationalDBConnetion, id, map, "ordered locus");
		
		// Finally, add anything else that has neither.
		extractNamesForType(relationalDBConnetion, id, map, null);
	}

	/**
	 * Extracts names with a given name type.
	 */
	private static void extractNamesForType(Connection relationalDBConnetion, String id, Map<String, String> map, String type) throws SQLException {
        //
        //  uniprotTable_GeneName <UID> <Uniprot Table GeneName>
        //
		final String baseQuery = "select value from genenametype inner join " +
				"entrytype_genetype on(entrytype_genetype_name_hjid = entrytype_genetype.hjid) " +
				"where entrytype_gene_hjid = ?";
		final String baseQueryWithFilter = baseQuery + " and type = ?";

		PreparedStatement ps = relationalDBConnetion.prepareStatement(
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
        	if(!blattnerTable_IDs.containsKey(id) && type=="ordered locus") {
        		blattnerTable_IDs.put(id, result.getString(1));
        	}
        }
        ps.close();
	}
	
	/**
	 * Extract the protein names
	 * @param id
	 * @throws SQLException
	 */
	private static void extractProteinName(Connection relationalDBConnetion, String id) throws SQLException {           
        //
        //  uniprotTable_ProteinName <UID> <Uniprot Table ProteinName>
        //
		PreparedStatement ps = relationalDBConnetion.prepareStatement(
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
	 * Extract the function field for the uniprot table.
	 * @param id
	 * @throws SQLException
	 */
	private static void extractFunction(Connection relationalDBConnetion, String id) throws SQLException {
        //
        //  uniprotTable_Function <UID> <Uniprot Table Function>
        //
		PreparedStatement ps = relationalDBConnetion.prepareStatement("select text " +
				"from commenttype inner join entrytype_comment " +
				"on (entrytype_comment_hjchildid = hjid) " +
				"where type = 'function' " +
				"and entrytype_comment_hjid = ?");
    	ps.setString(1, id);
    	ResultSet result = ps.executeQuery();
        while(result.next()) {
        	uniprotTable_Function.put(id, result.getString(1));
        }
        ps.close();
	}
	
	/**
	 * Builds the System tables by extracting the required
	 * data from the relational database, building the tables, 
	 * filling them with data and submitting them to the 
	 * GenMAPP database.
	 * 
	 * @param relationalDBConnetion
	 * @param genMAPPDBConnection
	 * @throws Exception
	 */
	private static void buildSystemTables(Connection relationalDBConnetion, Connection genMAPPDBConnection) throws Exception {
			
		//Initialize the System tables.
		Table EMBL = new SystemTable("EMBL");
		Table InterPro = new SystemTable("InterPro");		
		Table PDB = new SystemTable("PDB");
		Table Pfam = new SystemTable("Pfam");
		Table EcoGene = new SystemTable("EcoGene");
		Table EchoBASE = new SystemTable("EchoBASE");
		
		//Extract the data.
		PreparedStatement ps = relationalDBConnetion.prepareStatement("select distinct(id) from dbreferencetype where type = ?");
		String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		String species = "|Escherichia coli K12|";
		String remarks = " ";
		ResultSet result;
		
	    ps.setString(1, "EMBL");
	    result = ps.executeQuery();
	    while(result.next()) {
			EMBL.insert(result.getString(1) + ";" + species + ";" + date + ";" + remarks);
	    }	
	    ps.setString(1, "InterPro");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	InterPro.insert(result.getString(1) + ";" + species + ";" + date + ";" + remarks);
	    }   
	    ps.setString(1, "PDB");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	PDB.insert(result.getString(1) + ";" + species + ";" + date + ";" + remarks);
	    }  
	    ps.setString(1, "Pfam");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	Pfam.insert(result.getString(1) + ";" + species + ";" + date + ";" + remarks);
	    }
	    ps.setString(1, "EcoGene");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EcoGene.insert(result.getString(1) + ";" + species + ";" + date + ";" + remarks);
	    }
	    ps.setString(1, "EchoBASE");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EchoBASE.insert(result.getString(1) + ";" + species + ";" + date + ";" + remarks);
	    }
	    result.close();
		ps.close();
		
		//Build the GenMAPP database.
		EMBL.flush(genMAPPDBConnection);
		InterPro.flush(genMAPPDBConnection);
		PDB.flush(genMAPPDBConnection);
		Pfam.flush(genMAPPDBConnection);
		EcoGene.flush(genMAPPDBConnection);
		EchoBASE.flush(genMAPPDBConnection);
	}

	/**
	 * Builds the Relation tables by extracting the required
	 * data from the relational database, building the tables, 
	 * filling them with data and submitting them to the 
	 * GenMAPP database.
	 * 
	 * @param relationalDBConnetion
	 * @param genMAPPDBConnection
	 * @throws Exception
	 */
	
	private static void buildRelationTables(Connection relationalDBConnetion, Connection genMAPPDBConnection) throws Exception {
			
		//Initialize the Relations tables.
		Table UniProt_EMBL = new RelationsTable("UniProt-EMBL");
		Table UniProt_InterPro = new RelationsTable("UniProt-InterPro");
		Table UniProt_PDB = new RelationsTable("UniProt-PDB");
		Table UniProt_Pfam = new RelationsTable("UniProt-Pfam");
		Table UniProt_Blattner = new RelationsTable("UniProt-Blattner");
		Table UniProt_EcoGene = new RelationsTable("UniProt-EcoGene");
		Table UniProt_EchoBASE = new RelationsTable("UniProt-EchoBASE");
		
		Table EMBL_InterPro = new RelationsTable("EMBL-InterPro");
		Table EMBL_PDB = new RelationsTable("EMBL-PDB");
		Table EMBL_Pfam = new RelationsTable("EMBL-Pfam");
		Table EMBL_Blattner = new RelationsTable("EMBL-Blattner");
		Table EMBL_EcoGene = new RelationsTable("EMBL-EcoGene");
		Table EMBL_EchoBASE = new RelationsTable("EMBL-EchoBASE");
		
		Table Blattner_InterPro = new RelationsTable("Blattner-InterPro");
		Table Blattner_PDB = new RelationsTable("Blattner-PDB");
		Table Blattner_Pfam = new RelationsTable("Blattner-Pfam");
		
		Table EcoGene_InterPro = new RelationsTable("EcoGene-InterPro");
		Table EcoGene_PDB = new RelationsTable("EcoGene-PDB");
		Table EcoGene_Pfam = new RelationsTable("EcoGene-Pfam");
		Table EcoGene_Blattner = new RelationsTable("EcoGene-Blattner");
		Table EcoGene_EchoBASE = new RelationsTable("EcoGene-EchoBASE");
		
		Table EchoBASE_InterPro = new RelationsTable("EchoBASE-InterPro");
		Table EchoBASE_PDB = new RelationsTable("EchoBASE-PDB");
		Table EchoBASE_Pfam = new RelationsTable("EchoBASE-Pfam");
		Table EchoBASE_Blattner = new RelationsTable("EchoBASE-Blattner");
		
		//
		//Extract the data.
		//
		
		//All UniProt to x-tables.
		PreparedStatement ps = relationalDBConnetion.prepareStatement("select hjvalue, id from dbreferencetype inner join entrytype_accession on entrytype_dbreference_hjid = entrytype_accession_hjid where type = ?");
		String bridge = "S";
		ResultSet result;

	    ps.setString(1, "EMBL");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	UniProt_EMBL.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "InterPro");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	UniProt_InterPro.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "PDB");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	UniProt_PDB.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "Pfam");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	UniProt_Pfam.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EcoGene");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	UniProt_EcoGene.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EchoBASE");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	UniProt_EchoBASE.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.close();
	    for(String id : blattnerTable_IDs.keySet()) {
	    	UniProt_Blattner.insert(uniprotTable_ID.get(id) + ";" + blattnerTable_IDs.get(id) + ";" + bridge);
	    }
	    
	    ps = relationalDBConnetion.prepareStatement("select dbref1.id as id1, " +
	    		"dbref2.id as id2, dbref1.type as type1,  " +
	    		"dbref2.type as type2 " +
	    		"from dbreferencetype as dbref1 inner join dbreferencetype as dbref2 " +
	    		"using (entrytype_dbreference_hjid) " +
	    		"where dbref1.type <> dbref2.type " +
	    		"and dbref1.type = ? " +
	    		"and dbref2.type = ?");
		bridge = "S";
		
		//EMBL to x-table minus EMBL to Blattner and GeneOntology.
	    ps.setString(1, "EMBL");
	    ps.setString(2, "InterPro");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EMBL_InterPro.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EMBL");
	    ps.setString(2, "PDB");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EMBL_PDB.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EMBL");
	    ps.setString(2, "Pfam");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EMBL_Pfam.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EMBL");
	    ps.setString(2, "EcoGene");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EMBL_EcoGene.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EMBL");
	    ps.setString(2, "EchoBASE");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EMBL_EchoBASE.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    
	    //EcoGene to x-table minus EcoGene to Blattner and GeneOntology.
	    ps.setString(1, "EcoGene");
	    ps.setString(2, "InterPro");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EcoGene_InterPro.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EcoGene");
	    ps.setString(2, "PDB");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EcoGene_PDB.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EcoGene");
	    ps.setString(2, "Pfam");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EcoGene_Pfam.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EcoGene");
	    ps.setString(2, "EchoBASE");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EcoGene_EchoBASE.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    
	    //EchoBASE to x-table minus EcoGene to Blattner and GeneOntology.
	    ps.setString(1, "EchoBASE");
	    ps.setString(2, "InterPro");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EchoBASE_InterPro.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EchoBASE");
	    ps.setString(2, "PDB");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EchoBASE_PDB.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps.setString(1, "EchoBASE");
	    ps.setString(2, "Pfam");
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EchoBASE_Pfam.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
		
		//Blattner to x-table minus Blattner to GeneOntology.
	    ps = relationalDBConnetion.prepareStatement("select id " +
	    		"from dbreferencetype " +
	    		"where type = ? and " +
	    		"entrytype_dbreference_hjid = ?");    

	    ps.setString(1, "InterPro");
	    for(String id : blattnerTable_IDs.keySet()) {
		    ps.setString(2, id);
		    result = ps.executeQuery();
		    while(result.next()) {
		    	Blattner_InterPro.insert(blattnerTable_IDs.get(id) + ";" + result.getString(1) + ";" + bridge);
		    }
	    }
	    ps.setString(1, "PDB");
	    for(String id : blattnerTable_IDs.keySet()) {
		    ps.setString(2, id);
		    result = ps.executeQuery();
		    while(result.next()) {
		    	Blattner_PDB.insert(blattnerTable_IDs.get(id) + ";" + result.getString(1) + ";" + bridge);
		    }
	    }
	    ps.setString(1, "Pfam");
	    for(String id : blattnerTable_IDs.keySet()) {
		    ps.setString(2, id);
		    result = ps.executeQuery();
		    while(result.next()) {
		    	Blattner_Pfam.insert(blattnerTable_IDs.get(id) + ";" + result.getString(1) + ";" + bridge);
		    }
	    }
		
		//EMBL-Blattner, EcoGene-Blattner, EchoBASE-Blattner.
	    ps = relationalDBConnetion.prepareStatement("select entrytype_dbreference_hjid, id from dbreferencetype where type = ?");    
	    
	    ps.setString(1, "EMBL");  
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EMBL_Blattner.insert(result.getString(2) + ";" + blattnerTable_IDs.get(result.getString(1)) + ";" + bridge);
	    }
	    ps.setString(1, "EcoGene");  
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EcoGene_Blattner.insert(result.getString(2) + ";" + blattnerTable_IDs.get(result.getString(1)) + ";" + bridge);
	    }
	    ps.setString(1, "EchoBASE");  
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EchoBASE_Blattner.insert(result.getString(2) + ";" + blattnerTable_IDs.get(result.getString(1)) + ";" + bridge);
	    }
	    result.close();
		ps.close();
		
		//Build the GenMAPP database.
		UniProt_EMBL.flush(genMAPPDBConnection);
		UniProt_InterPro.flush(genMAPPDBConnection);
		UniProt_PDB.flush(genMAPPDBConnection);
		UniProt_Pfam.flush(genMAPPDBConnection);
		UniProt_Blattner.flush(genMAPPDBConnection);
		UniProt_EcoGene.flush(genMAPPDBConnection);
		UniProt_EchoBASE.flush(genMAPPDBConnection);
		
		EMBL_InterPro.flush(genMAPPDBConnection);
		EMBL_PDB.flush(genMAPPDBConnection);
		EMBL_Pfam.flush(genMAPPDBConnection);
		EMBL_Blattner.flush(genMAPPDBConnection);
		EMBL_EcoGene.flush(genMAPPDBConnection);
		EMBL_EchoBASE.flush(genMAPPDBConnection);
		
		Blattner_InterPro.flush(genMAPPDBConnection);
		Blattner_PDB.flush(genMAPPDBConnection);
		Blattner_Pfam.flush(genMAPPDBConnection);
		
		EcoGene_InterPro.flush(genMAPPDBConnection);
		EcoGene_PDB.flush(genMAPPDBConnection);
		EcoGene_Pfam.flush(genMAPPDBConnection);
		EcoGene_Blattner.flush(genMAPPDBConnection);
		EcoGene_EchoBASE.flush(genMAPPDBConnection);
		
		EchoBASE_InterPro.flush(genMAPPDBConnection);
		EchoBASE_PDB.flush(genMAPPDBConnection);
		EchoBASE_Pfam.flush(genMAPPDBConnection);
		EchoBASE_Blattner.flush(genMAPPDBConnection);
	}
	
	/**
	 * Builds the GeneOntologyRelationTables.
	 * @param genMAPPDBConnection
	 * @throws Exception
	 */
	private static void buildGeneOntologyRelationTables(Connection genMAPPDBConnection) throws Exception {
		
		Table EMBL_GeneOntology = new RelationsTable("EMBL-GeneOntology");
		Table Blattner_GeneOntology = new RelationsTable("Blattner-GeneOntology");
		Table EcoGene_GeneOntology = new RelationsTable("EcoGene-GeneOntology");
		Table EchoBASE_GeneOntology = new RelationsTable("EchoBASE-GeneOntology");
		
		PreparedStatement ps;
		String bridge = "S";
		ResultSet result;
		
	    ps = genMAPPDBConnection.prepareStatement(sqlQueryBuilder("UniProt-EMBL"));
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EMBL_GeneOntology.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps = genMAPPDBConnection.prepareStatement(sqlQueryBuilder("UniProt-Blattner"));
	    result = ps.executeQuery();
	    while(result.next()) {
	    	Blattner_GeneOntology.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps = genMAPPDBConnection.prepareStatement(sqlQueryBuilder("UniProt-EcoGene"));
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EcoGene_GeneOntology.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    ps = genMAPPDBConnection.prepareStatement(sqlQueryBuilder("UniProt-EchoBASE"));
	    result = ps.executeQuery();
	    while(result.next()) {
	    	EchoBASE_GeneOntology.insert(result.getString(1) + ";" + result.getString(2) + ";" + bridge);
	    }
	    
	    result.close();
	    ps.close();
	    
		//Build the remaining GenMAPP database.
		EMBL_GeneOntology.flush(genMAPPDBConnection);
		Blattner_GeneOntology.flush(genMAPPDBConnection);
		EcoGene_GeneOntology.flush(genMAPPDBConnection);
		EchoBASE_GeneOntology.flush(genMAPPDBConnection);
	}
	
	/**
	 * Helps build an sql statment for 
	 * the buildGeneOntologyRelationTables() method.
	 * @param tableName
	 * @return
	 */
	private static String sqlQueryBuilder(String tableName) {
		return "SELECT [" + tableName + "].Related, " +
		"[UniProt-GeneOntology].Related " +
		"FROM [" + tableName + "] " +
		"INNER JOIN [UniProt-GeneOntology] " +
		"ON [" + tableName + "].Primary = [UniProt-GeneOntology].Primary";
	}
	
	/**
	 * Build the OriginalRowCounts table.
	 * @param genMAPPDBConnection
	 * @throws Exception
	 */
	private static void buildOriginalRowCountsTable(Connection genMAPPDBConnection) throws Exception {
		
		Table originalRowCountsTable = new RowCountsTable("OriginalRowCounts");
		
		String sqlStatement;
		PreparedStatement ps = null;
		ResultSet result = null;

		//List of all tables in the GenMAPP database.
		String[] tableNames = new String[] {"Blattner-GeneOntology", 
				"Blattner-InterPro", "Blattner-PDB", "Blattner-Pfam", 
				"EchoBASE", "EchoBASE-Blattner", "EchoBASE-GeneOntology", 
				"EchoBASE-InterPro", "EchoBASE-PDB", "EchoBASE-Pfam",
				"EcoGene", "EcoGene-Blattner", "EcoGene-EchoBASE",
				"EcoGene-GeneOntology", "EcoGene-InterPro", "EcoGene-PDB",
				"EcoGene-Pfam", "EMBL", "EMBL-Blattner", "EMBL-EchoBASE", 
				"EMBL-EcoGene", "EMBL-GeneOntology", "EMBL-InterPro",
				"EMBL-PDB", "EMBL-Pfam", "GeneOntology", "GeneOntologyCount", 
				"GeneOntologyStage", "GeneOntologyTree", "Info", "InterPro",
				"Other", "PDB", "Pfam", "Relations", "Systems", "UniProt",
				"UniProt-BLattner", "UniProt-EchoBASE", "UniProt-EcoGene",
				"UniProt-EMBL", "UniProt-GeneOntology", "UniProt-InterPro",
				"UniProt-PDB", "UniProt-Pfam"};
				
		for(String tableName : tableNames) {
			sqlStatement = "SELECT Count(*) FROM [" + tableName + "]";
			ps = genMAPPDBConnection.prepareStatement(sqlStatement);  
		    result = ps.executeQuery();
		    while(result.next()) {
		    	originalRowCountsTable.insert(tableName + ";" + result.getString(1));
		    }
		    ps.close();
		}
		
		//Build the table into the GenMAPP database.
		originalRowCountsTable.flush(genMAPPDBConnection);
	}
	
	

	/**
	 * Extract all the table data.
	 * @param relationalDBConnetion
	 * @throws SQLException
	 */
	private static void extractUniProtTableData(Connection relationalDBConnetion) throws SQLException {
			
		extractAccessionNames(relationalDBConnetion);

		for(String id : uniprotTable_ID.keySet()) {
			extractEntryName(relationalDBConnetion, id);		
			extractNames(relationalDBConnetion, id, uniprotTable_GeneName);
			extractProteinName(relationalDBConnetion, id);
			extractFunction(relationalDBConnetion, id);
		}
	}
	
	

	/**
	 * Export all data to a new Access GenMaPP file.
	 * @param outputFile
	 * @throws Exception 
	 */
	public static void exportToGenMaPP(File outputFile) 
			throws Exception {
		
		//Get todays date to tag in access file for creation date.
		String dateString1 = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		//Get todays date to tag in access file for creation date.
		String dateString2 = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		
		//Create a new GenMAPP database file.
		AccessFileCreator.createNewAccessFile(outputFile);
		
		//Open a connection to the relational database, requires a hibernate configuration.
		Connection relationalDBConnetion = null;
		Class.forName("org.postgresql.Driver");
		Configuration hibernateConfiguration = GenMAPPBuilder.createHibernateConfiguration();
		if (hibernateConfiguration != null) {
			relationalDBConnetion = DriverManager.getConnection(
					hibernateConfiguration.getProperty("hibernate.connection.url"), 
					hibernateConfiguration.getProperty("hibernate.connection.username"), 
					hibernateConfiguration.getProperty("hibernate.connection.password"));
		}
		
		//Open a connection to the GenMAPP database file.
		Connection genMAPPDBConnection = AccessFileCreator.openConnection(outputFile);
		
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

		//Extract the UniProt table data from the relational database.
		extractUniProtTableData(relationalDBConnetion);
		
		//Fill the "Uniprot Table".
		for(String id : uniprotTable_ID.keySet()) {
			AccessFileCreator.fillUniProtTable(
					uniprotTable_ID.get(id), 
					uniprotTable_EntryName.get(id), 
					uniprotTable_GeneName.get(id),
					uniprotTable_ProteinName.get(id),
					uniprotTable_Function.get(id), 
					"|Escherichia coli K12|", dateString2, "");
		}
		
		//Free the UniProt data.
		uniprotTable_ID = null;
		uniprotTable_EntryName = null;
		uniprotTable_GeneName = null;
		uniprotTable_ProteinName = null;
		uniprotTable_Function = null;
		
		//Build the System tables.
		buildSystemTables(relationalDBConnetion, genMAPPDBConnection);
		
		//Build the Relation tables.
		buildRelationTables(relationalDBConnetion, genMAPPDBConnection);		
		
		//Close both database connections.
		relationalDBConnetion.close();
		AccessFileCreator.closeConnection();

		//Build the GO tables.
		(new ExportGoData(outputFile)).export();
		
		//Open a connection to the GenMAPP database file.
		genMAPPDBConnection = AccessFileCreator.openConnection(outputFile);
		
		//Build the Relation tables.
		buildGeneOntologyRelationTables(genMAPPDBConnection);	
		
		//Build OriginalRowCounts table.
		buildOriginalRowCountsTable(genMAPPDBConnection);
		
		//Close the database connection.
		AccessFileCreator.closeConnection();
		
		
	}
}
