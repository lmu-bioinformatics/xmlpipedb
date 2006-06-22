/********************************************************
 * Filename: ExportGoData.java
 * Author: LMU
 * Program: gmBuilder
 * Description: Export the data to the access database.    
 * Revision History:
 * 20060422: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.gmbuilder.GenMAPPBuilder;
import edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export.ExportWizard;
import generated.impl.IdImpl;
import generated.impl.IsAImpl;
import generated.impl.NameImpl;
import generated.impl.NamespaceImpl;
import generated.impl.RelationshipImpl;
import generated.impl.TermImpl;
import generated.impl.ToImpl;

public class ExportGoData {

	private Connection connection 	= null;
	private Go 		 godb;
	private HashMap<String, String> namespace;
	private HashMap<String, Integer> goCount;
	private HashMap<String, Boolean> duplicates;
	
	
	
	// GO DB variables 
	private final int NUM_OF_GO_COLS	= 8;
	private final int PARENT_COL		= 4 - 1;
	private int orderNo 				= 1;
	
	/**
	 *  Constructor 
	 *  
	 * @param outputFile 
	 * 			The genMAPP file to populate
	 * @throws IOException
	 * 			I/O error
	 */
	public ExportGoData(Connection connection) throws IOException {
		this.connection = connection;
		godb 		= new Go();
		namespace 	= new HashMap<String, String>();
		goCount		= new HashMap<String, Integer>();
		duplicates	= new HashMap<String, Boolean>();
		createNamespaceMappings();
				
	}
	
	/**
	 * Staring point for exporing go data to genMAPP 
	 *  
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws HibernateException
	 * @throws SAXException
	 * @throws IOException
	 * @throws JAXBException
	 */
	public void export(File GOA_File) throws ClassNotFoundException, 
			SQLException, HibernateException, 
			SAXException, IOException, JAXBException {
		
		String Date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		ExportWizard.updateExportProgress(3, "GeneOntology export - creating tables...");
		godb.createTables(connection);
		ExportWizard.updateExportProgress(10, "GeneOntology export - populating tables...");
		populateGoTables(GOA_File);
		ExportWizard.updateExportProgress(40, "GeneOntology export - flushing tables...");
		godb.updateSystemsTable(connection, Date, "T");
		closeConnection();
		System.out.println("done!");
	}
	
	/**
	 * A mapping from obo.xml namespaces to GO types 
	 *
	 */
	private void createNamespaceMappings() {
		namespace.put("biological_process", "P");
		namespace.put("molecular_function", "F");
		namespace.put("cellular_component", "C");
	}
	
	/**
	 * Populate genMAPP's GO tables
	 * 
	 * @throws SQLException
	 * @throws HibernateException
	 * @throws SAXException
	 * @throws IOException
	 * @throws JAXBException
	 */
	private void populateGoTables(File GOA_File) throws SQLException, 
			HibernateException, SAXException, IOException, 
			JAXBException {
		
		Configuration hibernateConfiguration = GenMAPPBuilder.createHibernateConfiguration();
    	SessionFactory sessionFactory = hibernateConfiguration.buildSessionFactory();
		Session session = sessionFactory.openSession(); // open Hibernate session

		populateUniprotGoTable(GOA_File);
		populateGeneOntologyTable(session);
		populateGeneOntologyTree();
		populateGeneOntologyCount();
		populateUniProtGoCount();
		
		session.close();		
		
	}
	
	private QueryHolder getUniProtIDs(String sql, String id) throws SQLException {
        QueryHolder qh = new QueryHolder();
        qh.ps = connection.prepareStatement(sql);
        qh.ps.setString(1, id);
        qh.rs = qh.ps.executeQuery();
        return qh;
    }
	
	/**
	 * Populates genMAPP's Uniprot-GOCount table
	 * 
	 * @throws SQLException
	 */
	private void populateUniProtGoCount() throws SQLException {
		System.out.println("creating: " + Go.UniProt_GoCount);
		// Create an entry for each unique GO ID 
		String sql = "select Id from " + Go.GeneOntologyCount;
		ResultSet goids = connection.prepareStatement(sql).executeQuery();
		while(goids.next())  {
			String id = goids.getString(1);
            QueryHolder qh = getUniProtIDs("select \"Primary\" from " + Go.UniProt_Go + " where Related = ?", id);
			int count = 0;
			HashMap<String, Boolean> uniprot_IDs = new HashMap<String, Boolean>();
			// Count the number of times each GO ID maps to a unique UP ID 
			// as defined in the Uniprot-GO table
			while (qh.rs.next()) {
				++count;
				uniprot_IDs.put(qh.rs.getString(1), true);
			}
			qh.close();
			// Count the number of times each GO ID child maps to a unique UP ID
			// as defined in the Uniprot-GO table
			getTotalCount(id, uniprot_IDs);
			String[] values = new String[] {id, count + "" , uniprot_IDs.size() + ""};
			godb.insert(connection, Go.UniProt_GoCount, values);
		}
		
		// Get Overall Totals for Entire GO Tree
		sql = "select COUNT(Primary) as total from (select DISTINCT Primary from " + Go.UniProt_Go + ")";
        // Alternative query when using a database other than Access.
//        sql = "select COUNT(\"Primary\") as total from (select DISTINCT \"Primary\" from " + Go.UniProt_Go + ") as primaries";
		ResultSet r = connection.prepareStatement(sql).executeQuery();
		r.next();
		String total = r.getString(1);
		godb.insert(connection, Go.UniProt_GoCount, new String[] {"GO", 0 + "", total});

	}
	
	
	/**
	 * Recursively counts the number of time each GO ID maps to a unique Uniprot ID
	 * as defined in the Uniprot-GO table
	 * 
	 * @param parent
	 * 			parent GO ID
	 * @param uniprot_IDs
	 * 			map of existing Uniprot IDs
	 * @throws SQLException
	 */
	private void getTotalCount(String parent, HashMap<String, Boolean> uniprot_IDs) throws SQLException {
		QueryHolder qh = getUniProtIDs("select Id from " + Go.GeneOntology + " where Parent = ?", parent);
		while(qh.rs.next()) {
			String id = qh.rs.getString(1);
			
			String mysql = "select \"Primary\" from " + Go.UniProt_Go + " where Related = ?";
			PreparedStatement myps = connection.prepareStatement(mysql);
			myps.setString(1, id);
			ResultSet upResults = myps.executeQuery();
			while (upResults.next()) {
				uniprot_IDs.put(upResults.getString(1), true);
			}
			myps.close();
			getTotalCount(id, uniprot_IDs);
			
		}
		qh.close();
		
	}
	
	/**
	 * Populate genMAPP's Uniprot-GeneOntology table
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	private void populateUniprotGoTable(File GOA_File) throws IOException, SQLException {
		BufferedReader in = new BufferedReader(new FileReader(GOA_File.getCanonicalPath()));
		String line = null;
		HashMap<String, Boolean> unique = new HashMap<String, Boolean>();
	    while ((line = in.readLine()) != null) {
	    	// Grab the Uniprot ID 
	    	Matcher m1 = Pattern.compile("UniProtKB/[\\w-]+:(\\w+)").matcher(line);
	    	Matcher m2 = Pattern.compile("UniProt\\s+(\\w{6})").matcher(line);
	    	boolean regexp1 = m1.find();
	    	boolean regexp2 = m2.find();
	    	if (regexp1 || regexp2) {
	            String Up_ID = regexp1 ?  m1.group(1) : m2.group(1);
	            // Grab the GO ID(s) 
	            Matcher match  = Pattern.compile("GO:(\\w+)").matcher(line);
	            while (match.find()) {
	            	String GO_ID  = match.group(1);
	    			String key = Up_ID + "," +  GO_ID;
	    			if (!unique.containsKey(key)) {
	    				unique.put(key, true);
	    				String[] values = new String[] {Up_ID, GO_ID, ""};
	    				godb.insert(connection,Go.UniProt_Go, values);
	    			}
	            }
	        }
	     }
	}		
	
	
	/**
	 *  Populate genMAPP's two tables:
	 *  	GeneOntologyStage -- data containing all species
	 *  	GeneOntology	  -- data containing user specied species
	 *  
	 * @param session
	 * 				A hibernate session (needed for HQL queries)
	 * @throws SQLException
	 */
	private void populateGeneOntologyTable (Session session) throws SQLException {
		populateGeneOntologyStage(session);
		populateGeneOntology();
	}

	/**
	 *  Create a staging database of the entire
	 *  GeneOntology, including all species 
	 *  
	 * @param session
	 * 				A hibernate session (needed for HQL queries)
	 * @throws SQLException
	 */
	private void populateGeneOntologyStage (Session session) throws SQLException {
		System.out.println("creating: " + Go.GeneOntologyStage);
		Iterator iter	= null;
    	Iterator myiter = null;
    	
    	String Species = null;
    	String Remarks = null;
    	
    	// Get today's date
    	String Date	   = new SimpleDateFormat("MM/dd/yyyy").format(new Date()); 
    	
    	// Grab all term object
    	iter = session.createQuery(  "from generated.impl.TermImpl" ).iterate();
    		
    	while( iter.hasNext() ) {
			TermImpl term = (TermImpl) iter.next();
			
			// Each term may contain a number of objects
			myiter = term.getContent().iterator();
	    	String Id = "";
	    	String Name = "";
	    	String Type = "";
	    	String Parent = "";
	    	String Relation = "";
	    	boolean is_root = false;
			/*
			 *  Each Term may have more than one parent (is_a) and a part_of. In that
			 *  case, create an entry for each object. Thus each term may have max of three
			 *  entries: two parents and one part of
			 */
	    	while (myiter.hasNext()) {
				Object o = myiter.next();
				if (o instanceof generated.impl.IdImpl) {
					Id = (((IdImpl)o).getContent()).substring(3); // Strip off "GO:" from ID
				} else if(o instanceof generated.impl.NameImpl) {
					Name = ((NameImpl)o).getContent();
				} else if(o instanceof generated.impl.NamespaceImpl) {
					Type = namespace.get(((NamespaceImpl)o).getContent());
				} else if(o instanceof generated.impl.IsRootImpl) {
					is_root = true;
				} else if(o instanceof generated.impl.IsAImpl) {
					Parent = (((IsAImpl)o).getContent()).substring(3); // Strip off "GO:" from parent 
					Relation = "%";
				} else if(o instanceof generated.impl.RelationshipImpl) {
					ToImpl to = (ToImpl)((RelationshipImpl)o).getContent().get(1);
					Parent = (to.getContent()).substring(3);
					Relation = "<";
					
				}
				if (Id != "" && Name != "" && Type != "") {
					if (is_root) {
						// create root ID entry
						String[] values = {Id, Name, Type, null, null, null, Date, null};
						godb.insert(connection,Go.GeneOntologyStage, values);
						is_root = false;
					} else if (Parent != "" && Relation != "" ) { 
						// create child ID 
						String[] values = {Id, Name, Type, Parent, Relation, Species, Date, Remarks};
						godb.insert(connection,Go.GeneOntologyStage, values);
						Relation = "";
					}
				}
			}  
    	} 
    }
	

	/**
	 *  Populates genMAPP's GeneOntology table based on a user selected species
	 *  
	 * 
	 * @throws SQLException
	 */
	private void populateGeneOntology () throws SQLException {
		String sql 			= "select * from " + Go.GeneOntologyStage 
							+ " where Id in (select DISTINCT(Related) from " 
							+ Go.UniProt_Go + ") order by Id";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet results 	 = ps.executeQuery();
		System.out.println("creating: " + Go.GeneOntology);
		while (results.next()) {
			String[] values = getGoValues(results);
			godb.insert(connection,Go.GeneOntology, values);
			insertParents(values[PARENT_COL]);
		}
	}
	
	/**
	 * Recursively inserts data based on a parent until reaching the root
	 * 
	 * @param id
	 * 		parent ID
	 * @throws SQLException
	 */
	private void insertParents(String id) throws SQLException {
		String sql 		= "select * from " + Go.GeneOntologyStage 
						+ " where Id = ? order by Id";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, id);
		ResultSet results 	 = ps.executeQuery();
		while (results.next()) {
			String[] values = getGoValues(results);
			String key = id + "," + values[PARENT_COL];
			if (!duplicates.containsKey(key)) {
				duplicates.put(key, true);
				godb.insert(connection,Go.GeneOntology, values);
				insertParents(values[PARENT_COL]);
			}
			
		}
		ps.close();
	}
	
	private String[] getGoValues(ResultSet results) throws SQLException {
		String[] values 	= new String[NUM_OF_GO_COLS];
		for (int x = 0; x < values.length; x++) {
			try {
				values[x] = results.getString(x + 1).trim();
			} catch (NullPointerException ex) {}
		}
		return values;
		
	}
	
	
	/**
	 * Populate genMAPP's GeneOntologyTree
	 * 
	 * @throws SQLException
	 */
	private void populateGeneOntologyTree () throws SQLException {
		String[] root_ids	= {"0003674", "0005575", "0008150" };
		String[] names 		= {"molecular_function", "cellular_component", "biological_process"};
		System.out.println("creating: " + Go.GeneOntologyTree);
		// Traverse the graph beginning with each root ID
		for (int index = 0; index < root_ids.length; index++) {
			String id 	= root_ids[index];
			String name = names[index];
			goCount.put(id, 1);
			godb.insert(connection, Go.GeneOntologyTree, new String[]{orderNo++ + "", 1+"" ,id, name});
			insertChildren(id, 2);
			
		}
	}
	
	/**
	 * Populate genMAPP's populateGeneOntologyCount
	 * 
	 * @throws SQLException
	 */
	private void populateGeneOntologyCount() throws SQLException {
		Iterator iter = goCount.keySet().iterator();
		String id;
		int count;
		System.out.println("creating: " + Go.GeneOntologyCount);
		while (iter.hasNext()) {
			id = (String)iter.next();
			count = goCount.get(id);
			godb.insert(connection, Go.GeneOntologyCount, new String[]{id, count + ""});
		}
	}
	
	/**
	 * A recursive fucntion that traverse each branch
	 * 
	 * @param parent
	 * 			The parent ID 
	 * @param level
	 * 			The level in the tree
	 * @throws SQLException
	 */
	private void insertChildren(String parent, int level) throws SQLException {
		
		String sqlStatement = "SELECT name,id from " + Go.GeneOntology + " where parent = ? order by parent";
		PreparedStatement ps = connection.prepareStatement(sqlStatement);
		ps.setString(1, parent);
		ResultSet results = ps.executeQuery();
		while(results.next()) {
			String name = results.getString(1);
			String id	= results.getString(2);
			// Keep a track of how many time each ID shows up in the tree
			if (goCount.containsKey(id)) {
				int count = goCount.get(id);
				goCount.put(id, count + 1);
			} else {
				goCount.put(id, 1);
			}
			godb.insert(connection, Go.GeneOntologyTree, new String[]{orderNo++ + "", level+"" ,id, name});
			// Used for feedback; should be replaced with something else
			//System.out.println(orderNo);
			insertChildren(id, level + 1);
		}
		ps.close();
	}
	
	/**
	 * Close database connection
	 * 
	 * @throws SQLException
	 */
	private void closeConnection() throws SQLException {
		connection.close();
	}
    
    private class QueryHolder {
        public PreparedStatement ps;
        public ResultSet rs;
        
        public void close() {
            try { rs.close(); } catch(Exception exc) { exc.printStackTrace(); }
            try { ps.close(); } catch(Exception exc) { exc.printStackTrace(); }
        }
    }
}
