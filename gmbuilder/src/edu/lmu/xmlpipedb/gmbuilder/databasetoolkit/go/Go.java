package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Go {
	private HashMap<String, String> create_cmds;
	private HashMap<String, String> insert_cmds;
	
	protected static String GeneOntologyTree 	= "GeneOntologyTree";
	protected static String GeneOntology		= "GeneOntology";
	protected static String GeneOntologyCount	= "GeneOntologyCount";
	protected static String Uniprot_Go		    = "Uniprot-GeneOntology";
	
	
	private String[] tables = {GeneOntology, GeneOntologyTree, GeneOntologyCount, Uniprot_Go};
	
	/**
	 * Constuctor
	 *
	 */
	public Go() {
		create_cmds = new HashMap<String, String>();
		insert_cmds = new HashMap<String, String>();
		createMaps();
	}
	
	/**
	 * Create GO tables
	 * 
	 * @param connection
	 * 			The database connection
	 * @throws SQLException
	 */
	public void createTables(Connection connection) throws SQLException {
		Statement s = connection.createStatement();
		
		for (int x = 0; x < tables.length; x++) {
			String table_name =  tables[x];
			String create_cmd = create_cmds.get(table_name);
			s.execute(create_cmd);
		}
		
		s.close();
	}
	
	/**
	 * Insert data into a GO table
	 * 
	 * @param connection
	 * 				The database connection
	 * @param table_name
	 * 				Go table name 
	 * @param values
	 * 				go data 
	 * @throws SQLException
	 */
	public void insert(Connection connection, String table_name, String[] values) throws SQLException {
    	String insert_cmd = insert_cmds.get(table_name);
    	PreparedStatement ps = connection.prepareStatement(insert_cmd);

    	for (int index = 1; index <= values.length; index++) {
    		ps.setString(index, values[index-1]);
    	}
    	
    	ps.executeUpdate();
    	ps.close();
 	}
	
	/**
	 * Updates the date for GO in the Systems table 
	 * 
	 * @param connection
	 * 				the database connection
	 * @param date
	 * 				today date
	 * @param systemCode
	 * 				Unique identifier for the GO table
	 * @throws SQLException
	 */
	public void updateSystemsTable(Connection connection, String date, String systemCode) throws SQLException {
		String stmt = "UPDATE Systems SET \"Date\" = ? WHERE SystemCode = ?";
		PreparedStatement ps = connection.prepareStatement(stmt);
		ps.setString(1, date);
		ps.setString(2, systemCode);
    	ps.executeUpdate();
    	ps.close();
		
	}
	
	/**
	 * Creates the sql statements to create tables and insert 
	 * data 
	 *
	 */
	private void createMaps() {
		int index = 0;
		String create_cmd; 	
		String insert_cmd; 
		String dollar_signs;
		
		String[][] fields = new String[][]
		       {
				{"ID", "NAME", "Type","Parent","Relation","Species", "\"Date\"", "Remarks"}, /* GO */
				{"OrderNo", "Level", "ID", "NAME"}, /* GOTree */
				{"ID", "Count"}, /* GOCount */
				{"Primary", "Related", "Bridge"} /* Uniprot-Go */
		       };
		String[][] types  = new String[][]
		       {
				{"VARCHAR(50) NOT NULL", "MEMO", "VARCHAR(2)","VARCHAR(50)","CHAR","MEMO", "DATE", "MEMO"}, /* GO */
				{"LONG", "Int", "VARCHAR(50)", "MEMO"}, /* GOTree */
				{"VARCHAR(50) NOT NULL", "Int"}, /* GOCount */
				{"VARCHAR(50) NOT NULL", "VARCHAR(50) NOT NULL", "VARCHAR(3) NOT NULL"}  /* Uniprot-Go */
		       };

		
		for (int x = 0; x < tables.length; x++) {
			String table_name 	= tables[x];
			create_cmd			= "CREATE TABLE " + table_name + " (";
			insert_cmd			= "INSERT INTO " + table_name + " (";
			dollar_signs		= "";
			for (int y = 0; y < fields[x].length; y++ ) {
				create_cmd += fields[x][y] + " " + types[x][y];
				insert_cmd += fields[x][y];
				dollar_signs += "?"; // Yeah I know ? is not a dollar sign :)
				if (y != fields[x].length - 1) {
					create_cmd += ",";
					insert_cmd += ",";
					dollar_signs += ",";
				}
			}
			create_cmd += ")";
			insert_cmd += ") VALUES (" + dollar_signs + ")";
			create_cmds.put(table_name, create_cmd);
			insert_cmds.put(table_name, insert_cmd);
		}
		
	}
	
}