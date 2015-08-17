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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;

/**
 * @author Joey J. Barrett, Jeffrey Nicholas
 */
public class Table {
    /**
     * @author Joey J. Barrett SQLStatement. An sql statement and its
     *         corrisponding values.
     */
    public class SQLStatement {
        private final String sqlStatement;
        private final Object[] values;

        protected SQLStatement(String sqlStatement, Object[] values) {
            this.sqlStatement = sqlStatement;
            this.values = values;
        }

        protected String getSQL() {
            return sqlStatement;
        }

        protected Object[] getValues() {
            return values;
        }

        @Override
        public String toString() {
            return getSQL();
        }
    }

    /**
     * @author Joey J. Barrett Attributes of a table. They are the column names
     *         and types.
     */
    public class Attributes {

        private final Map<String, String> nameToType;

        /**
         * Create a new Attribute Set. The param must follow the convention
         * {{name,type},{...},{name,type}}.
         * 
         * @param attributes
         * @throws Exception
         */
        public Attributes(String[][] attributes) {

            nameToType = new LinkedHashMap<String, String>();

            for (int i = 0; i < attributes.length; i++) {
                if (attributes[i].length != 2) {
                    LOG.error("Incorrect number of arguments");
                }
                nameToType.put(attributes[i][0], attributes[i][1]);
            }
        }

        /**
         * Returns whether a name is an attribute.
         * 
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
     * 
     * @param tableManager
     * @throws Exception
     */
    public Table(TableManager tableManager) {
        this.tableManager = tableManager;

        if (tableManager.getTableDefinition() != null) {
            tableAttributes = new Attributes(tableManager.getTableDefinition());
        } else {
            tableAttributes = null;
        }
    }

    public void export(Connection exportConnection) throws SQLException {
        // First Create any objects requiring creation
        if (tableAttributes != null) {

            for (String tableName: tableManager.getTableNames()) {
                LOG.info("Create Table in GDB. Table Name: [" + tableName + "]");
                create(tableName);
            }
        }

        // Then create all insert and update statements
        String previousTableName = "";
        Row[] rowsToProcess = tableManager.getRows();
        LOG.info("Processing " + rowsToProcess.length + " rows");
        for (Row row: rowsToProcess) {
            if (!row.getValue(TableManager.TABLE_NAME_COLUMN).equals(previousTableName)) {
                previousTableName = (String)row.getValue(TableManager.TABLE_NAME_COLUMN);
            }

            if (row.getValue(TableManager.QUERY_TYPE_COLUMN).equals(QueryType.insert.name())) {
                insert((String)row.getValue(TableManager.TABLE_NAME_COLUMN), row.getRowAsMap());
            } else if (row.getValue(TableManager.QUERY_TYPE_COLUMN).equals(QueryType.update.name())) {
                update((String)row.getValue(TableManager.TABLE_NAME_COLUMN), row.getRowAsMap());
            }
        }

        LOG.info("Insert Count: [" + insertCount + "]");
        LOG.info("Update Count: [" + updateCount + "]");
        LOG.info("Flushing tables...");
        flush(exportConnection);
    }

    /**
     * Create Table. This call will prepare the create table sql statement and
     * submit it to the buffer, flush() will still need to be called.
     * 
     * @param tableName
     */
    private void create(String tableName) {

        StringBuffer sqlStatement = new StringBuffer("CREATE TABLE [" + tableName + "] (");
        for (String columnName: tableAttributes.nameSet()) {
            sqlStatement.append(columnName + " " + tableAttributes.get(columnName) + ",");
        }
        sqlStatement = new StringBuffer(sqlStatement.substring(0, sqlStatement.length() - 1));
        sqlStatement.append(")");
        sqlBuffer.add(new SQLStatement(sqlStatement.toString(), null));
    }

    /**
     * Adds an sql insert statement to the sqlBuffer given a row in a
     * TableManager and a table name.
     * 
     * @param tableName
     * @param namesAndValues
     * @throws Exception
     */
    private void insert(String tableName, Map<String, Object> namesAndValues) {
        List<Object> valueBag = new ArrayList<Object>();
        StringBuffer sqlStatement = new StringBuffer("INSERT INTO [" + tableName + "] (");

        for (Entry<String, Object> nameAndValue: namesAndValues.entrySet()) {
            if (!nameAndValue.getKey().equals(TableManager.QUERY_TYPE_COLUMN) &&
                    !nameAndValue.getKey().equals(TableManager.TABLE_NAME_COLUMN)) {
                if (tableAttributes == null || tableAttributes.validName(nameAndValue.getKey())) {
                    sqlStatement.append(nameAndValue.getKey() + ",");
                    valueBag.add(nameAndValue.getValue());
                }
            }
        }

        sqlStatement = new StringBuffer(sqlStatement.substring(0, sqlStatement.length() - 1));
        sqlStatement.append(") VALUES (");

        for (int i = 0; i < valueBag.size(); i++) {
            sqlStatement.append("?,");
        }

        sqlStatement = new StringBuffer(sqlStatement.substring(0, sqlStatement.length() - 1));
        sqlStatement.append(")");

        sqlBuffer.add(new SQLStatement(sqlStatement.toString(), valueBag.toArray(new Object[0])));
        insertCount++;
    }

    /**
     * Creates an SQL update query given a row in a TableManager and a table
     * name.
     * 
     * @param tableName
     * @param namesAndValues
     * @throws Exception
     */
    private void update(String tableName, Map<String, Object> namesAndValues) {
        if (tableAttributes == null && tableManager.getPrimaryKeys().size() == 0) {
            LOG.error("Cannot do an update query with out a table definition or primary keys for table: " + tableName);
            return;
        }

        List<Object> valueBag = new ArrayList<Object>();
        StringBuffer sqlStatement = new StringBuffer("UPDATE [" + tableName + "] SET ");
        for (Entry<String, Object> nameAndValue: namesAndValues.entrySet()) {
            if (!nameAndValue.getKey().equals(TableManager.QUERY_TYPE_COLUMN) &&
                    !nameAndValue.getKey().equals(TableManager.TABLE_NAME_COLUMN) &&
                    !tableManager.getPrimaryKeys().contains(nameAndValue.getKey())) {
                if (!"".equals(nameAndValue.getValue())) {
                    if (tableAttributes == null || tableAttributes.validName(nameAndValue.getKey())) {
                        sqlStatement.append(nameAndValue.getKey() + "= ?,");
                        valueBag.add(nameAndValue.getValue());
                    }
                }
            }
        }

        sqlStatement = new StringBuffer(sqlStatement.substring(0, sqlStatement.length() - 1));
        if (tableAttributes != null || tableManager.getPrimaryKeys().size() > 0) {
            sqlStatement.append(" WHERE ");
            for (String primaryKey: tableManager.getPrimaryKeys()) {
                sqlStatement.append(primaryKey).append("= ?").append(",");
                valueBag.add(namesAndValues.get(primaryKey));
            }
        }

        sqlStatement = new StringBuffer(sqlStatement.substring(0, sqlStatement.length() - 1));
        sqlBuffer.add(new SQLStatement(sqlStatement.toString(), valueBag.toArray(new Object[0])));
        updateCount++;
    }

    /**
     * Flush the sqlBuffer to the database given by the connection.
     * 
     * @param connection
     * @throws SQLException
     */
    private void flush(Connection connection) {
        /*
         * The code was not written to submit and commit batches of records.
         * Rather it goes record by record. This is a possible area of
         * performance enhancement later on.
         */
        int errorCounter = 0; // used for counting SQLExceptions
        int forLoopPasses = 0; // used for counting # of passes through for loop
                               // that executed without exception

        // If there are no records to process, just bail!
        if (sqlBuffer.size() <= 0)
            return;

        PreparedStatement ps = null;
        LOG.info("Number of records to process: sqlBuffer.size():: " + sqlBuffer.size());
        LOG.info("Number of records to process: sqlBuffer.toArray().length:: " + sqlBuffer.toArray().length);
        for (SQLStatement sqlStatement: sqlBuffer) {
            try {
                ps = connection.prepareStatement(sqlStatement.getSQL());
                if (sqlStatement.getSQL().startsWith("CREATE")) {
                    System.out.println(sqlStatement.getSQL());
                }
                if (sqlStatement.getValues() != null) {
                    Object[] values = sqlStatement.getValues();
                    for (int i = 0; i < values.length; i++) {
                        // TODO Unfortunate, necessary evil due to the need to
                        // extend the prior design
                        // so that it can accommodate proper setting of dates.
                        // This entire Table/TableManager
                        // framework really needs to be rewritten, almost
                        // entirely from the ground up.
                        Object value = values[i];
                        if (value instanceof String) {
                            ps.setString(i + 1, GenMAPPBuilderUtilities.straightToCurly((String)value));
                        } else if (value instanceof Date) {
                            ps.setTimestamp(i + 1, new Timestamp(((Date)value).getTime()));
                        }
                    }
                }

                ps.executeUpdate();
                forLoopPasses++;
            } catch (SQLException e) {
                System.out.println(ps);
                StringBuffer errText = new StringBuffer("An SQLException occurred while writing to the database. ");
                errText.append(" Error Code: " + e.getErrorCode());
                errText.append(" Message: " + e.getMessage());
                LOG.error(errText);

                for (StackTraceElement ste: e.getStackTrace()) {
                    System.out.println("    " + ste);
                }

                // start - rebuild the query that failed
                String values = "";
                if (sqlStatement.getValues() != null) {
                    for (int i = 0; i < sqlStatement.getValues().length; i++) {
                        values += "\'" + GenMAPPBuilderUtilities.straightToCurly(sqlStatement.getValues()[i] + "\', ");
                    }
                }
                // remove the last comma
                if (values.lastIndexOf(",") >= 0)
                    values = values.substring(0, values.lastIndexOf(","));

                String sql = sqlStatement.getSQL();
                sql = sql.substring(0, sql.indexOf("VALUES"));
                sql += "VALUES( " + values + " )";
                // end - rebuild

                LOG.error(sql);
                errorCounter++;
            } finally {
                try {
                    ps.close();
                } catch (Exception exc) {
                    LOG.warn("Problem closing PreparedStatement");
                }
            }
        }

        // print a log entry if any errors were encountered.
        if (errorCounter > 0) {
            LOG.error(errorCounter + " number of eroneous records were captured.");
        }

        // Always print out the number of successful passes through the for loop
        LOG.info("Number of successful passes through for loop: [" + forLoopPasses + "]");
    }

    private static final Log LOG = LogFactory.getLog(Table.class);

    private TableManager tableManager;
    private Attributes tableAttributes;
    private List<SQLStatement> sqlBuffer = new ArrayList<SQLStatement>();
    private int insertCount = 0;
    private int updateCount = 0;
}
