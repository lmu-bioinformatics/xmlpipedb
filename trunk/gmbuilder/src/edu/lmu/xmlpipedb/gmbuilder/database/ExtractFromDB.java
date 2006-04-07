package edu.lmu.xmlpipedb.gmbuilder.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExtractFromDB {

	
	private Map<String, String> uniprotTable_ID = new HashMap<String, String>();
	private Map<String, String> uniprotTable_EntryName = new HashMap<String, String>();
	private Map<String, String> uniprotTable_GeneName = new HashMap<String, String>();
	private Map<String, String> uniprotTable_ProteinName = new HashMap<String, String>();
	
	private Connection connection = null;
	
	public ExtractFromDB() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
        String database = "jdbc:postgresql://database"; 
        
        try {
			connection = DriverManager.getConnection("jdbc:postgresql://database"
					,"jjbarret","tu00ylyI");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		
	public void ExtractUniprotTableData() throws SQLException {
		
        Statement s = connection.createStatement();
        
        //
        //  uniprotTable_ID <UID> <Uniprot Table ID>
        //
        
        ResultSet result = s.executeQuery("SELECT " +
        		"entrytype_accession_hjid, " +
        		"hjvalue " +
        		"FROM entrytype_accession");
        
        while(result.next()) {
        	uniprotTable_ID.put(result.getString(1), result.getString(2));
        }
        

        for(String id : uniprotTable_ID.keySet()) {
 
            //
            //  uniprotTable_EntryName <UID> <Uniprot Table EntryName>
            //
            
	        result = s.executeQuery("SELECT hjvalue " +
	        		"FROM entrytype_name " +
	        		"WHERE entrytype_name_hjid='" + id + "'");
	        while(result.next()) {
	        	uniprotTable_EntryName.put(id, result.getString(1));
	        }
	        
	        //
	        //  uniprotTable_GeneName <UID> <Uniprot Table GeneName>
	        //
	       
	        result = s.executeQuery("SELECT value " +
	        		"FROM genenametype " +
	        		"WHERE entrytype_genetype_name_hjid='" + id + "' " +
	        		"AND type='ordered locus'");
	        while(result.next()) {
	        	uniprotTable_GeneName.put(id, result.getString(1));
	        }
	        
	        //
	        //  uniprotTable_ProteinName <UID> <Uniprot Table ProteinName>
	        //
	        
	        result = s.executeQuery("SELECT value " +
	        		"FROM proteinnametype " +
	        		"WHERE proteintype_name_hjid='" + id + "' " +
	        		"AND proteintype_name_hjindex='0'");
	        while(result.next()) {
	        	uniprotTable_ProteinName.put(id, result.getString(1));
	        }
        }
        
        result = s.executeQuery("SELECT value " +
        		"FROM genenametype ");

        
        result = s.executeQuery("SELECT type, id " +
		"FROM dbreferencetype ");

        s.close();
        
	}
	
	public void PushToAccessDB() {
		
		try {
			ExportToGenMaPP export = new ExportToGenMaPP(
					System.getProperty("user.dir")+ "/dbFiles/GeneDBTmpl.mdb", System.getProperty("user.dir")+ "/dbFiles/output.mdb");
		
			export.openConnection();
			
			export.updateInfoTable("Loyola Marymount University",
					"20060406", "UniProt", "|Escherichia coli K12|",
					"", "20060406", "");
			
			List<String> systemCodeList = new ArrayList<String>();
			systemCodeList.add("S");
			
			export.updateSystemsTable(systemCodeList, "20060406");
			
			export.createUniProtTable();
			
			for(String id : uniprotTable_ID.keySet()) {
				
				export.fillUniProtTable(uniprotTable_ID.get(id), 
						uniprotTable_EntryName.get(id), 
						uniprotTable_GeneName.get(id),
						uniprotTable_ProteinName.get(id),
						"", "|Escherichia coli K12|", "20060406", "");
				
			}

		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public static void main(String args[]) {
		ExtractFromDB extract = new ExtractFromDB();
		System.out.println("CREATED POSTGRESQL CONNECTION...");
		try {
			extract.ExtractUniprotTableData();
			System.out.println("EXTRACTED DATA...");
			extract.PushToAccessDB();
			System.out.println("SAVED ACCESS DATABASE");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
}
