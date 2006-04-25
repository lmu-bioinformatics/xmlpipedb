package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Go {
	private HashMap<String, String> create_cmds;
	private HashMap<String, String> insert_cmds;
	private String[] tables = {"GeneOntology", "GeneOntologyTree",  "GOCount"};
	
	public Go() {
		create_cmds = new HashMap<String, String>();
		insert_cmds = new HashMap<String, String>();
		createMaps();
	}
	
	public void createTables(Connection connection) throws SQLException {
		Statement s = connection.createStatement();
		
		for (int x = 0; x < tables.length; x++) {
			String table_name =  tables[x];
			String create_cmd = create_cmds.get(table_name);
//			String alter_cmd = "ALTER TABLE " + table_name + " ADD CONSTRAINT " + table_name +"_constraint PRIMARY KEY(ID)";
			s.execute(create_cmd);
//			s.execute(alter_cmd);
		}
		
		s.close();
	}
	
	public void insert(Connection connection, String table_name, String[] values) throws SQLException {
    	String insert_cmd = insert_cmds.get(table_name);
    	PreparedStatement ps = connection.prepareStatement(insert_cmd);
    	
    	for (int index = 1; index <= values.length; index++) {
    		ps.setString(index, values[index-1]);
    	}
    	ps.executeUpdate();
    	ps.close();
 	}
	
	private void createMaps() {
		int index = 0;
		String create_cmd; 	
		String insert_cmd; 
		String dollar_signs;
		
		String[][] fields = new String[][]
		       {
				{"ID", "NAME", "Type","Parent","Relation","Species", "\"Date\"", "Remarks"},
				{"ID", "OrderNo", "Level", "NAME"},
				{"ID", "Count", "Total"}
		       };
		String[][] types  = new String[][]
		       {
				{"VARCHAR(50) NOT NULL", "MEMO", "VARCHAR(2)","VARCHAR(50)","CHAR","MEMO", "DATE", "MEMO"},
				{"VARCHAR(50) NOT NULL", "LONG", "Int", "MEMO"},
				{"VARCHAR(50) NOT NULL", "Int", "Long"}
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