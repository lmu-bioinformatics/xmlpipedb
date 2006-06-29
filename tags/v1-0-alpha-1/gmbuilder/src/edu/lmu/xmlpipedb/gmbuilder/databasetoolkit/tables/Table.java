/********************************************************
 * Filename: Table.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: A Table object is created by submitting
 * to it a TableManager.  The single method Export(),
 * given a connection, prepares the contents of a 
 * TableManager by creating SQL insert and update 
 * statements which are then flushed to a given database
 * connection.  If a table does not exist it is created. 
 * 
 * Revision History:
 * 20060605: Initial Revision.
 * 20060620: Major update.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export.ExportWizard;

/**
 * @author Joey J. Barrett
 * Class: Table
 */
public class Table {
	
	/**
	 * @author Joey J. Barrett
	 * SQLStatement.  An sql statement and its corrisponding
	 * values.
	 */
	public class SQLStatement {
		private final String sqlStatement;
		private final String[] values;
		protected SQLStatement(String sqlStatement, String[] values) {
			this.sqlStatement = sqlStatement;
			this.values = values;
		}
		protected String getSQL() {
			return sqlStatement;
		}
		protected String[] getValues() {
			return values;
		}
	}
	
	/**
	 * @author Joey J. Barrett
	 * Attributes of a table.  They are the column
	 * names and types.
	 */
	public class Attributes {

		private final Map<String, String> nameToType; 
		
		/**
		 * Create a new Attribute Set.  The param must follow the 
		 * convention {{name,type},{...},{name,type}}.
		 * @param attributes
		 * @throws Exception
		 */
		public Attributes(String[][] attributes) throws Exception {
			
			nameToType = new LinkedHashMap<String, String>();
			
			for(int i = 0; i < attributes.length; i++) {
				if(attributes[i].length != 2) {
					throw new Exception("Incorrect number of arugments");
				}
				nameToType.put(attributes[i][0], attributes[i][1]);
			}
		}
		
		/**
		 * Returns whether a name is an attribute.
		 * @param name
		 * @return
		 */
		protected boolean validName(String name) {
			return nameToType.containsKey(name);
		}
		
		/**
		 * @return
		 */
		protected Set<String> nameSet() {
			return nameToType.keySet();
		}
		
		/**
		 * @param name
		 * @return
		 */
		protected String get(String name) {
			return nameToType.get(name);
		}
	}
	
	private TableManager tableManager;
	private Attributes tableAttributes;
	private List<SQLStatement> sqlBuffer = new ArrayList<SQLStatement>();
	
	/**
	 * Constructor.
	 * @param tableManager
	 * @throws Exception
	 */
	public Table(TableManager tableManager) throws Exception {
		
		this.tableManager = tableManager;
		
		if(tableManager.getTableDefinition() != null) {
			tableAttributes = new Attributes(tableManager.getTableDefinition());
		} else {
			tableAttributes = null;
		}
	}
	
	/**
	 * @param exportConnection
	 * @throws Exception
	 */
	public void export(Connection exportConnection) throws Exception {
		
		if(tableAttributes != null) {
			
			Set<String> tableNames = new HashSet<String>();
	
			for(Row row : tableManager.getRows()) {
				
				if(row.getValue(TableManager.QUERY_TYPE_COLUMN).equals(QueryType.insert.name())) {
					tableNames.add(row.getValue(TableManager.TABLE_NAME_COLUMN));
				}
			}
			
			for(String tableName : tableNames) {
                // TODO Would be better to decouple business logic from UI calls.
				ExportWizard.updateExportProgress(66, "Creating tables - " + tableName + " table...");
				create(tableName);
			}
		}
		
		String previousTableName = "";
		
		for(Row row : tableManager.getRows()) {
			
			if(!row.getValue(TableManager.TABLE_NAME_COLUMN).equals(previousTableName)) {
				ExportWizard.updateExportProgress(66, "Populating tables - " + 
						row.getValue(TableManager.TABLE_NAME_COLUMN) + " table...");
			}
			
			if(row.getValue(TableManager.QUERY_TYPE_COLUMN).equals(QueryType.insert.name())) {
				
				insert(row.getValue(TableManager.TABLE_NAME_COLUMN), row.getRowAsMap());
				
			} else if(row.getValue(TableManager.QUERY_TYPE_COLUMN).equals(QueryType.update.name())) {
				
				update(row.getValue(TableManager.TABLE_NAME_COLUMN), row.getRowAsMap());
			}
		}
		ExportWizard.updateExportProgress(66, "Flushing tables...");
		
		flush(exportConnection);
		
	}
	
	/**
	 * Create Table.  This call will prepare the create table sql statement
	 * and submit it to the buffer, flush() will still need to be called.
	 * 
	 * @param tableName
	 */
	private void create(String tableName) {
		
		StringBuffer sqlStatement = new StringBuffer("CREATE TABLE \"" + tableName + "\" (");
		for(String columnName : tableAttributes.nameSet()) {
			sqlStatement.append(columnName + " " + tableAttributes.get(columnName) + ",");
		}
		sqlStatement = new StringBuffer(sqlStatement.substring(0, sqlStatement.length()-1));
		sqlStatement.append(")");
		sqlBuffer.add(new SQLStatement(sqlStatement.toString(), null));
	}
	
	
	/**
	 * Adds an sql insert statement to the sqlBuffer 
	 * given a row in a TableManager and a table name.
	 * 
	 * @param tableName
	 * @param namesAndValues
	 * @throws Exception
	 */
	private void insert(String tableName, Map<String, String> namesAndValues) throws Exception {
		
		List<String> valueBag = new ArrayList<String>();
		
		StringBuffer sqlStatement = new StringBuffer("INSERT INTO \"" + tableName + "\" (");
			
		
		for(Entry<String, String> nameAndValue : namesAndValues.entrySet()) {
			
			if(!nameAndValue.getKey().equals(TableManager.QUERY_TYPE_COLUMN) &&
					!nameAndValue.getKey().equals(TableManager.TABLE_NAME_COLUMN)) {
				
				if(tableAttributes == null) {
						
					sqlStatement.append(nameAndValue.getKey() + ",");
					valueBag.add(nameAndValue.getValue());
					
				} else {
					if(tableAttributes.validName(nameAndValue.getKey())) {
						sqlStatement.append(nameAndValue.getKey() + ",");
						valueBag.add(nameAndValue.getValue());
					}
				}
			}
		}
		
		sqlStatement = new StringBuffer(sqlStatement.substring(0, sqlStatement.length()-1));
		sqlStatement.append(") VALUES (");
		
		for(int i = 0; i < valueBag.size(); i++) {
			sqlStatement.append("?,");
		}
		sqlStatement = new StringBuffer(sqlStatement.substring(0, sqlStatement.length()-1));
		sqlStatement.append(")");
		
		sqlBuffer.add(new SQLStatement(sqlStatement.toString(), valueBag.toArray(new String[0])));
	}
	

	/**
	 * Creates an SQL update query given a row
	 * in a TableManager and a table name.
	 * 
	 * @param tableName
	 * @param namesAndValues
	 * @throws Exception
	 */
	private void update(String tableName, Map<String, String> namesAndValues) throws Exception {
		
		if(tableAttributes == null && tableManager.getPrimaryKeys().size() == 0) {
			throw new Exception("Cannot due an update query with out a " +
					"table definition or primary keys for table: " + tableName);
		}
		
		List<String> valueBag = new ArrayList<String>();
	
		StringBuffer sqlStatement = new StringBuffer("UPDATE \"" + tableName + "\" SET ");
			
		for(Entry<String, String> nameAndValue : namesAndValues.entrySet()) {	
	
			if(!nameAndValue.getKey().equals(TableManager.QUERY_TYPE_COLUMN) &&
					!nameAndValue.getKey().equals(TableManager.TABLE_NAME_COLUMN) &&
					!tableManager.getPrimaryKeys().contains(nameAndValue.getKey())) {
			
				if(!nameAndValue.getValue().equals("")) {
					if(tableAttributes == null) {
						
						sqlStatement.append(nameAndValue.getKey() + "= ?,");
						valueBag.add(nameAndValue.getValue());
						
					} else if(tableAttributes.validName(nameAndValue.getKey())) {
							
						sqlStatement.append(nameAndValue.getKey() + "= ?,");
						valueBag.add(nameAndValue.getValue());
					}
				}
			}
		}
		
		sqlStatement = new StringBuffer(sqlStatement.substring(0, sqlStatement.length()-1));
		
		if(tableAttributes != null || tableManager.getPrimaryKeys().size() > 0) {
			sqlStatement.append(" WHERE ");
			
			for(String primaryKey : tableManager.getPrimaryKeys()) {
				sqlStatement.append(primaryKey).append("= ?").append(",");
				valueBag.add(namesAndValues.get(primaryKey));
			}
		}
		sqlStatement = new StringBuffer(sqlStatement.substring(0, sqlStatement.length()-1));
		
		sqlBuffer.add(new SQLStatement(sqlStatement.toString(), valueBag.toArray(new String[0])));
	}


	/**
	 * FLush the sqlBuffer to the database given by the connection.
	 * @param connection
	 * @throws SQLException
	 */
	private void flush(Connection connection) throws SQLException {
		if(sqlBuffer.size() > 0) {
			PreparedStatement ps = null;
			for(SQLStatement sqlStatement : sqlBuffer.toArray(new SQLStatement[0])) {
				ps = connection.prepareStatement(sqlStatement.getSQL());
				if(sqlStatement.getValues() != null) {
					for(int i = 0; i < sqlStatement.getValues().length; i++) {
						
						ps.setString(i+1, sqlStatement.getValues()[i]);
					}
				}
			
			    ps.executeUpdate();
			    ps.close();
			}
		}
	}
} 
	
