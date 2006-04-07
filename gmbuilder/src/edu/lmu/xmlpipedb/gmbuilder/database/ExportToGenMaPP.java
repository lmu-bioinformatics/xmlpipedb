package edu.lmu.xmlpipedb.gmbuilder.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ExportToGenMaPP {

	private Connection connection = null;
	private String outputFile = null;
	
	public ExportToGenMaPP(String templateFile, String outputFile) throws ClassNotFoundException, SQLException, IOException {
		
		this.outputFile = outputFile;
		
		//make a copy of the template file.
		copyFile(new File(templateFile), new File(outputFile));
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
	
	public void openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		   
        String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
        database += outputFile.trim() + ";DriverID=22;READONLY=false}"; 
        
        connection = DriverManager.getConnection(database ,"","");
	}
	
	public void closeConnection() throws SQLException {
		connection.close();
	}
	
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
	
	public void createUniProtTable() throws SQLException {
		
        Statement s = connection.createStatement();
        s.execute("CREATE TABLE UniProt (" +
        		"ID VARCHAR(50) NOT NULL," +
        		"EntryName VARCHAR(50) NOT NULL," +
        		"GeneName VARCHAR(50) NOT NULL," +
        		"ProteinName MEMO," +
        		"Function MEMO," +
        		"Species MEMO," +
        		"Date DATE," +
        		"Remarks MEMO)");
        s.execute("ALTER TABLE UniProt ADD CONSTRAINT UniProt_constraint PRIMARY KEY(ID)"); 

        s.close();
	}
	
    public void fillUniProtTable(String id, 
    			String entryName, String geneName, String proteinName, 
    			String function, String species, String date, 
    			String remarks) throws SQLException {
    	
    	Statement s = connection.createStatement();
        s.execute("INSERT INTO UniProt (" +
        		"ID," +
        		"EntryName," +
        		"GeneName," +
        		"ProteinName," +
        		"Function," +
        		"Species," +
        		"Date," +
        		"Remarks)" +
        		"VALUES (" +
        		"'" + id + "'," +
        		"'" + entryName + "'," +
        		"'" + geneName + "'," +
        		"'" + proteinName + "'," +
        		"'" + function + "'," +
        		"'" + species + "'," +
        		"'" + date + "'," +
        		"'" + remarks + "')");
        
        s.close();
	}
	
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
	
	public void createAndFillRelationTable(String table, 
			String primary, List<String> relatedList, 
			String bridge) throws SQLException {
        
		Statement s = connection.createStatement();
		
        s.execute("CREATE TABLE " + table +" (" +
        		"Primary VARCHAR(50) NOT NULL," +
        		"Related VARCHAR(50) NOT NULL," +
        		"Bridge VARCHAR(3) NOT NULL)");
        
        //Primary and related are foriegn keys, the bridge is the value S
       // s.execute("ALTER TABLE " + table + " ADD CONSTRAINT " + table + "_constraint PRIMARY KEY(ID)"); 

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
	
	public void updateSystemsTable(
			List<String> systemCodeList, String date) throws SQLException {
        
		Statement s = connection.createStatement();
		
		for(String systemCode : systemCodeList) {
            s.execute("UPDATE Systems " +
            		"SET Date='" + date + "'" +
            		"WHERE SystemCode='" + systemCode + "'");
		}
        s.close();
	}
}
