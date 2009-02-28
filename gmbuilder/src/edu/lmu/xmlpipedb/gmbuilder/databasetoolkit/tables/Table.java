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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;

/**
 * @author Joey J. Barrett, Jeffrey Nicholas
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
		/**
		 * The getQuery method is an attempt to print out the query with the 
		 * values in the correct spots (in place of the question marks). 
		 * However, when doing this, we must also put quotation marks in
		 * all the right places and leave them out of the wrong places.
		 * That is: single quotes around strings, possibly around dates,
		 * but never around numbers.
		 * Therefore, the data produced by this method is highly suspect.
		 * In this case, no quotes are put around any values and the
		 * user of such queries must insert their own quotes.
		 * @return
		 */
		protected String getQuery(){
			String query = this.sqlStatement;
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                	query.indexOf("?");
                	query = query.substring(0, query.indexOf("?")) 
                		+ GenMAPPBuilderUtilities.straightToCurly(values[i])
                		+ query.substring( query.indexOf("?")+1 );
                }
            }
            return query;
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
		public Attributes(String[][] attributes) {
			
			nameToType = new LinkedHashMap<String, String>();
			
			for(int i = 0; i < attributes.length; i++) {
				if (attributes[i].length != 2) {
					_Log.error("Incorrect number of arguments");
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
	
	/**
	 * Constructor.
	 * @param tableManager
	 * @throws Exception
	 */
	public Table(TableManager tableManager) {
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
    public void export(Connection exportConnection) throws SQLException {
        // First Create any objects requiring creation
    	if (tableAttributes != null) {
//            Set<String> tableNames = new HashSet<String>();
//            for (Row row : tableManager.getRows()) {
//                if (row.getValue(TableManager.QUERY_TYPE_COLUMN).equals(QueryType.insert.name())) {
//                    tableNames.add(row.getValue(TableManager.TABLE_NAME_COLUMN));
//                }
//            }
            // Create the submitted tables.
        	
            for (String tableName : tableManager.getTableNames()) {
            	_Log.info("Create Table in GDB. Table Name: [" + tableName + "]" );
                create(tableName);
            }
        }

    	// Then create all insert and update statements
        String previousTableName = "";
        Row[] rowsToProcess = tableManager.getRows();
        _Log.info("Processing " + rowsToProcess.length + " rows");
        for (Row row : rowsToProcess) {
            if (!row.getValue(TableManager.TABLE_NAME_COLUMN).equals(previousTableName)) {
                previousTableName = row.getValue(TableManager.TABLE_NAME_COLUMN);
            }

            if (row.getValue(TableManager.QUERY_TYPE_COLUMN).equals(QueryType.insert.name())) {
                insert(row.getValue(TableManager.TABLE_NAME_COLUMN), row.getRowAsMap());
            } else if (row.getValue(TableManager.QUERY_TYPE_COLUMN).equals(QueryType.update.name())) {
                update(row.getValue(TableManager.TABLE_NAME_COLUMN), row.getRowAsMap());
            }
        }

        _Log.info("Insert Count: [" + insertCount + "]");
        _Log.info("Update Count: [" + updateCount + "]");
        _Log.info("Flushing tables...");
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
	private void insert(String tableName, Map<String, String> namesAndValues) {
		
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
		insertCount++;
	}
	

	/**
	 * Creates an SQL update query given a row
	 * in a TableManager and a table name.
	 * 
	 * @param tableName
	 * @param namesAndValues
	 * @throws Exception
	 */
	private void update(String tableName, Map<String, String> namesAndValues) {
		
		if (tableAttributes == null && tableManager.getPrimaryKeys().size() == 0) {
			_Log.error("Cannot do an update query with out a " +
					"table definition or primary keys for table: " + tableName);
            return;
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
		updateCount++;
	} // end update

	/**
     * Flush the sqlBuffer to the database given by the connection.
     * 
     * @param connection
     * @throws SQLException
     */
    private void flush(Connection connection) {
    	/* The code was not written to submit and commit batches of records. 
    	 * Rather it goes record by record. This is a possible area of 
    	 * performance enhancement later on.
    	 */
    	int errorCounter = 0; // used for counting SQLExceptions
    	int forLoopPasses = 0; // used for counting # of passes through for loop that executed without exception
    	
    	// If there are no records to process, just bail!
        if (sqlBuffer.size() <= 0)
        	return;
        
        PreparedStatement ps = null;
        _Log.info("Number of records to process: sqlBuffer.size():: " + sqlBuffer.size());
        _Log.info("Number of records to process: sqlBuffer.toArray().length:: " + sqlBuffer.toArray().length);
        for (SQLStatement sqlStatement : sqlBuffer) {
            try {
                ps = connection.prepareStatement(sqlStatement.getSQL());
                if (sqlStatement.getValues() != null) {
                    for (int i = 0; i < sqlStatement.getValues().length; i++) {
                        ps.setString(i + 1, GenMAPPBuilderUtilities.straightToCurly(sqlStatement.getValues()[i]));
                    }
                }
               
                ps.executeUpdate();
                forLoopPasses++;
            } catch( SQLException e ){
            	StringBuffer errText = new StringBuffer("An SQLException occurred while writing to the database. ");
            	//errText.append(" sqlStatement getSQL: " + sqlStatement.getSQL());
            	//errText.append(" sqlStatement getValues: " + sqlStatement.getValues());
            	errText.append(" Error Code: " + e.getErrorCode());
            	errText.append(" Message: " + e.getMessage());
            	_Log.error(errText);
            	
            	// start - rebuild the query that failed
            	String values = "";
            	if (sqlStatement.getValues() != null) {
                    for (int i = 0; i < sqlStatement.getValues().length; i++) {
                        values += "\'" + GenMAPPBuilderUtilities.straightToCurly(sqlStatement.getValues()[i] + "\', ");
                    }
                }
            	// remove the last comma
            	if(values.lastIndexOf(",") >= 0 )
            		values = values.substring(0, values.lastIndexOf(","));
            	
            	String sql = sqlStatement.getSQL();
            	sql = sql.substring(0, sql.indexOf("VALUES"));
            	sql += "VALUES( " + values + " )";
            	// end  - rebuild

            	_Log.error(sql);
            	errorCounter++;
            } finally {
                try {
                    ps.close();
                } catch(Exception exc) {
                    _Log.warn("Problem closing PreparedStatement");
                }
            } // end finally
        } // end for loop
        
        // print a log entry if any errors were encountered.
        if( errorCounter > 0 ){
        	_Log.error(errorCounter + " number of eroneous records were captured." );
        }
        
        // Always print out the number of successful passes through the for loop
        _Log.info("Number of successful passes through for loop: [" + forLoopPasses + "]");
    } // end flush()
    
    /**
     * Log object for this class.
     */
    private static final Log _Log = LogFactory.getLog(Table.class);

    /*
     * Class variables
     */
    private TableManager tableManager;
	private Attributes tableAttributes;
	private List<SQLStatement> sqlBuffer = new ArrayList<SQLStatement>();
	private int insertCount = 0;
	private int updateCount = 0;

    
} 
	
