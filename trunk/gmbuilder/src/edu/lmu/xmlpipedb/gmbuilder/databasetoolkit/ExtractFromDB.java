package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;



public class ExtractFromDB {

	
	private Map<String, String> uniprotTable_ID = new HashMap<String, String>();
	private Map<String, String> uniprotTable_EntryName = new HashMap<String, String>();
	private Map<String, String> uniprotTable_GeneName = new HashMap<String, String>();
	private Map<String, String> uniprotTable_ProteinName = new HashMap<String, String>();
	
	private Connection connection = null;
	
	private int count =0;
	
	private static StringBuffer errorList = new StringBuffer();
	
	public ExtractFromDB() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
        try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/uniprot", "postgres", "password");
//					,"jjbarret","tu00ylyI");
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
        		"FROM entrytype_accession where entrytype_accession_hjindex = 0");
        
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
	        	//System.out.println("FOUND: " + result.getString(1));
	        }
	        
	        //
	        //  uniprotTable_GeneName <UID> <Uniprot Table GeneName>
	        //
	       /*
	        result = s.executeQuery("SELECT value " +
	        		"FROM genenametype " +
	        		"WHERE entrytype_genetype_name_hjid='" + id + "' " +
	        		"AND type='ordered locus'");
                    */
//	        PreparedStatement ps = connection.prepareStatement("select value from genenametype " +
//	        		"inner join entrytype_genetype on" +
//	        		"(entrytype_genetype_name_hjid = entrytype_genetype.hjid) " +
//	        		"where entrytype_gene_hjid = '" + id + "' and type = 'primary'");
//	        result = ps.executeQuery();
	    	
	        
	        result = s.executeQuery("select value from genenametype inner join entrytype_genetype on(entrytype_genetype_name_hjid = entrytype_genetype.hjid) where entrytype_gene_hjid = '" + id + "' and type = 'primary'");

            while(result.next()) {
            	uniprotTable_GeneName.put(id, result.getString(1));
            	String trying = uniprotTable_GeneName.get(id);
            	if(trying == null) {
            		System.out.println("***********NULL");
            	}
//	        	String theResult = result.getString(1);
//	        	if(result.wasNull()) {
//	        		System.out.println("***********NULL");
//	        	}
//
//	        	if(!result.wasNull()) {
//	        		System.out.println("NOT NULL");
//	        		
//	        	} else {
//	        		
//	                result = s.executeQuery("select value from genenametype inner join entrytype_genetype on(entrytype_genetype_name_hjid = entrytype_genetype.hjid) where entrytype_gene_hjid = '" + id + "' and type = 'ordered locus'");
//	                uniprotTable_GeneName.put(id, theResult);
//	                System.exit(0);
//	        	}
	        	//System.out.println("FOUND: " + result.getString(1));
	        }
            
            //ps.close();
	        
	        //
	        //  uniprotTable_ProteinName <UID> <Uniprot Table ProteinName>
	        //
	        /*
	        result = s.executeQuery("SELECT value " +
	        		"FROM proteinnametype " +
	        		"WHERE proteintype_name_hjid='" + id + "' " +
	        		"AND proteintype_name_hjindex='0'");
                    */
            result = s.executeQuery("select value from entrytype inner join proteinnametype on(protein = proteintype_name_hjid) where entrytype.hjid = '" + id + "' and proteintype_name_hjindex = 0;");
            
	        while(result.next()) {
	        	uniprotTable_ProteinName.put(id, result.getString(1));
	        	//System.out.println("FOUND: " + result.getString(1));
	        }
        }
        
        /*
        result = s.executeQuery("SELECT value " +
        		"FROM genenametype ");

        
        result = s.executeQuery("SELECT type, id " +
		"FROM dbreferencetype ");
        */

        s.close();
        
	}
	
	public void PushToAccessDB() {
		ExportToGenMaPP export = null;
		try {
			export = new ExportToGenMaPP("/edu/lmu/xmlpipedb/gmbuilder/resource/dbFiles/GeneDBTmpl.mdb", "output.mdb");
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
				count++;
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			SQLException s;
			while((s = e.getNextException()) != null) {
				s.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
    			export.closeConnection();
			} catch(Exception exc) {
				
			}
		}
		
		System.out.println("COUNT: " + count);
		
		
	}
	
	public static void insertIntoErrorList(String error) {
		errorList.append(error).append("\n");
	}
	
	public static void main(String args[]) {
		
		

//		  try{
//				// Create a new configuration
//			    final Configuration cfg = new Configuration();
//			    
//			   // cfg.addDirectory(new File("hbm/org/uniprot/uniprot"));
//
//			    // Create the properties for Hibernate
//			    final Properties properties = new Properties();
//			    properties.load(new FileInputStream(System.getProperty(
//			    		"user.dir") + System.getProperty("file.separator") +
//			    		"src" + System.getProperty("file.separator") +
//			    		"edu" + System.getProperty("file.separator") +
//			    		"lmu" + System.getProperty("file.separator") + 
//			    		"xmlpipedb" + System.getProperty("file.separator") +
//			    		"gmbuilder" + System.getProperty("file.separator") + 
//			    		"resource" + System.getProperty("file.separator") + 
//			    		"properties" + System.getProperty("file.separator") + 
//			    		"hibernate.properties"));
//			    
//			    cfg.setProperties(properties);
//			    
//			    
//		    // This step will read hibernate.cfg.xml and prepare hibernate for use
//			 SessionFactory sessionFactory = cfg.buildSessionFactory();
//			 Session session = sessionFactory.openSession();
//			 Transaction tx = session.beginTransaction();
//		     
//		      //Using from Clause
//		      String SQL_QUERY ="from Insurance insurance";	      
//		      Query query = session.createQuery(SQL_QUERY);
//		      
//		       for(Iterator it=query.iterate();it.hasNext();){
//		         Object o = it.next();
//		         System.out.println("ID: " + o.toString());
//		         System.out.println("First Name: " + o.toString());
//		       }
//		       
//		       tx.commit();
//		       session.close();
//		  }catch(Exception e){
//		    System.out.println(e.getMessage());
//		  }finally{
//	}
		
		
		
		
		
		
		
		
		long time1 = System.currentTimeMillis();
		ExtractFromDB extract = new ExtractFromDB();
		long time2 = System.currentTimeMillis();
		System.out.println("START: " + time1 + " CREATED POSTGRESQL CONNECTION... " + time2);
		try {
			extract.ExtractUniprotTableData();
			long time3 = System.currentTimeMillis();
			System.out.println("EXTRACTED DATA..." + time3);
			extract.PushToAccessDB();
			long time4 = System.currentTimeMillis();
			System.out.println("SAVED ACCESS DATABASE" + time4);
			System.out.println(errorList.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
}
