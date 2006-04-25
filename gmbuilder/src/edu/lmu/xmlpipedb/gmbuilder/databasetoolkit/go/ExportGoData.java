package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import generated.impl.*;


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
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.xml.sax.SAXException;

public class ExportGoData {

	private Connection connection 	= null;
	private String outputFile 		= null;
	private Go 		 godb;
	private HashMap<String, String> namespace;
	private final String hbmPath = System.getProperty("user.dir")+ "/hbm";
	private final String hibernateProperties = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/properties/hibernate.properties";
	

	public ExportGoData(String outputFile) throws IOException {
		this.outputFile = outputFile;
		godb = new Go();
		namespace = new HashMap<String, String>();
		createNamespaceMappings();
	}
	
	public void export() throws ClassNotFoundException, SQLException, HibernateException, SAXException, IOException, JAXBException {
		CommitGoDataToGenMAPP();
	}
	
	private void createNamespaceMappings() {
		namespace.put("biological_process", "P");
		namespace.put("molecular_function", "F");
		namespace.put("cellular_component", "C");
	}
	
	private void CommitGoDataToGenMAPP() throws ClassNotFoundException, SQLException, HibernateException, SAXException, IOException, JAXBException {
		openConnection();
		godb.createTables(connection);
		populateGoTables();
		closeConnection();
	}
	
	private void populateGoTables() throws SQLException, HibernateException, SAXException, IOException, JAXBException {
		Iterator iter = null;
		HibernateSession hs	= new HibernateSession(hbmPath, hibernateProperties);
		Session session = hs.openSession();

		populateGeneOntologyTable(session);
		
		session.close();
	}
	

	private void populateGeneOntologyTable (Session session) throws SQLException {
    	String table_name = "GeneOntology";
    	Iterator iter;
    	Iterator myiter = null;
    	
    	String Species = null;
    	String Date = "03/09/2002";
    	String Remarks = null;
    	
    	iter = session.createQuery(  "from generated.impl.TermImpl" ).iterate();
    		
    	
    	while( iter.hasNext() ) {
			TermImpl term = (TermImpl) iter.next();
			myiter = term.getContent().iterator();
	    	String Id = "";
	    	String Name = "";
	    	String Type = "";
	    	String Parent = "";
	    	String Relation = "";
	    	boolean is_root = false;
			while (myiter.hasNext()) {
				Object o = myiter.next();
				if (o instanceof generated.impl.IdImpl) {
					Id = ((IdImpl)o).getContent(); 
				} else if(o instanceof generated.impl.NameImpl) {
					Name = ((NameImpl)o).getContent();
				} else if(o instanceof generated.impl.NamespaceImpl) {
					Type = namespace.get(((NamespaceImpl)o).getContent());
				} else if(o instanceof generated.impl.IsRootImpl) {
					is_root = true;
				} else if(o instanceof generated.impl.IsAImpl) {
					Parent = ((IsAImpl)o).getContent();
					Relation = "%";
				} else if(o instanceof generated.impl.RelationshipImpl) {
					ToImpl to = (ToImpl)((RelationshipImpl)o).getContent().get(1);
					Parent = to.getContent();
					Relation = "<";
					
				}
				if (Id != "" && Name != "" && Type != "") {
					if (is_root) {
						String[] values = {Id, Name, Type, null, null, null, Date, null};
						godb.insert(connection,"GeneOntology", values);
						is_root = false;
					} else if (Parent != "" && Relation != "" ) {
						String[] values = {Id, Name, Type, Parent, Relation, Species, Date, Remarks};
						godb.insert(connection,"GeneOntology", values);
						Relation = "";
					}
				}
			}  
    	} 
    }
	
	private void copyFile(File fileIn, File fileOut) throws IOException {
		InputStream in = new FileInputStream(fileIn);
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
	
	private void openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		   
        String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
        database += outputFile.trim() + ";DriverID=22;READONLY=false}"; 
        
        connection = DriverManager.getConnection(database ,"","");
	}
	
	private void closeConnection() throws SQLException {
		connection.close();
	}
}
