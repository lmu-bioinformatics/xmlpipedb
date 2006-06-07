/********************************************************
 * Filename: Table.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: Table should be sub-classed
 * to create different types of tables without
 * the need to write any code except what the table
 * contains (attributes) and how a given program will
 * insert into the table.
 * 
 * Revision History:
 * 20060605: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Table {
	
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
		 * convention {name,type,...,name,type}.
		 * @param attributes
		 * @throws Exception
		 */
		public Attributes(String[] attributes) throws Exception {
			
			nameToType = new LinkedHashMap<String, String>();
			
			for(int i = 0; i < attributes.length; i+=2) {
				if((i+1) >= attributes.length) {
					throw new Exception("Incorrect number of arugments");
				}
				nameToType.put(attributes[i], attributes[i+1]);
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
		
	private String tableName;
	private Attributes tableAttributes;
	private List<SQLStatement> sqlBuffer = new ArrayList<SQLStatement>();
	
	/**
	 * Constructor.
	 * @param tableName
	 * @param attributes
	 * @throws Exception
	 */
	public Table(String tableName, String[] attributes) throws Exception {
		this.tableName = tableName;
		tableAttributes = new Attributes(attributes);
	}
	
	/**
	 * @return
	 */
	public String getName() {
		return tableName;
	};
	
	/**
	 * Create Table.  This call will prepare the create table sql statement
	 * and submit it to the buffer, flush() will still need to be called.
	 */
	public void create() {
		
		String sqlStatement = "CREATE TABLE \"" + tableName + "\" (";
		for(String columnName : tableAttributes.nameSet()) {
			sqlStatement += columnName + " " + tableAttributes.get(columnName) + ",";
		}
		sqlStatement = sqlStatement.substring(0, sqlStatement.length()-1);
		sqlStatement += ")";
		sqlBuffer.add(new SQLStatement(sqlStatement, null));
	}
	
	/**
	 * This method must be implemented by any subclass of Table.
	 * It allows customization of inserting of values into the table.
	 * Inside the implementation of this method should be a call
	 * to generateInsert()
	 * 
	 * @param values
	 */
	public abstract void insert(String values) throws Exception;
	
	/**
	 * Adds an sql insert statement to the sqlBuffer.
	 * The set of values must follow the convention "name;value;...;name;value".
	 * @param values
	 * @throws Exception
	 */
	protected void generateInsert(String values) throws Exception {
		
		String[] namesAndValues = values.split(";");
		List<String> valueBag = new ArrayList<String>();
		
        // !!!!! StringBuffer is more efficient for this kind of string construction.
		String sqlStatement = "INSERT INTO \"" + tableName + "\" (";
			
		for(int i = 0; i < namesAndValues.length; i+=2) {
			if((i+1) >= namesAndValues.length) {
				throw new Exception("Incorrect number of arugments");
			}
			if(!tableAttributes.validName(namesAndValues[i])) {
				throw new Exception("Not a valid table column: " + namesAndValues[i]);
			}
			sqlStatement += namesAndValues[i] + ",";
			valueBag.add(namesAndValues[i+1]);; // !!!!! Double semi-colons...sloppy...
		}
		sqlStatement = sqlStatement.substring(0, sqlStatement.length()-1);
		sqlStatement += ") VALUES (";
		
		for(int i = 0; i < valueBag.size(); i++) {
			sqlStatement += "?,";
		}
		sqlStatement = sqlStatement.substring(0, sqlStatement.length()-1);
		sqlStatement += ")";
		
		sqlBuffer.add(new SQLStatement(sqlStatement, valueBag.toArray(new String[0])));
	}

	/**
	 * FLush the sqlBuffer to the database given by the connection.
	 * @param connection
	 * @throws SQLException
	 */
	public void flush(Connection connection) throws SQLException {
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
	
