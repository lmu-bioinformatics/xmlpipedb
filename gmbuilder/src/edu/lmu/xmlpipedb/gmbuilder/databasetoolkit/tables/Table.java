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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.healthmarketscience.jackcess.Database;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
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
            nameToType = GenMAPPBuilderUtilities.string2DArrayToMap(attributes);
        }

        protected boolean validName(String name) {
            return nameToType.containsKey(name);
        }

        protected Set<String> nameSet() {
            return nameToType.keySet();
        }

        protected String get(String name) {
            return nameToType.get(name);
        }
    }

    // TODO This is somewhat redundant with the information in TableManager itself.
    // We may be able to go directly to TableManager instead, simplifying the code.
    public class InsertSpecification {
        String tableName;
        Map<String, Object> values;
        
        public InsertSpecification(String tableName, Map<String, Object> values) {
            this.tableName = tableName;
            this.values = values;
        }
    }

    public Table(TableManager tableManager) {
        this.tableManager = tableManager;

        if (tableManager.getTableDefinition() != null) {
            tableAttributes = new Attributes(tableManager.getTableDefinition());
        } else {
            tableAttributes = null;
        }
    }

    public void export() throws SQLException, IOException, ClassNotFoundException {
        // First Create any objects requiring creation
        if (tableAttributes != null) {
            Database exportDatabase = ConnectionManager.getGenMAPPDB();
            for (String tableName: tableManager.getTableNames()) {
                LOG.info("Create Table in GDB. Table Name: [" + tableName + "]");
                create(tableName, exportDatabase);
            }
            exportDatabase.flush();
            exportDatabase.close();
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
        
        Database exportDatabase = ConnectionManager.getGenMAPPDB();
        flushInserts(exportDatabase);
        exportDatabase.flush();
        exportDatabase.close();
        
        Connection exportConnection = ConnectionManager.getGenMAPPDBConnection();
        flushUpdates(exportConnection);
        exportConnection.close();
    }

    private void create(String tableName, Database exportDatabase) throws IOException {
        GenMAPPBuilderUtilities.createAccessTableDirectly(exportDatabase, tableName, tableAttributes.nameToType);
    }

    private void insert(String tableName, Map<String, Object> namesAndValues) {
        // TODO Very possibly, this method can be eliminated, when the insert specification
        // is replaced by the TableManager's own Row objects.
        insertSpecifications.add(new InsertSpecification(tableName, namesAndValues));
        insertCount++;
    }

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

    private void flushInserts(Database exportDatabase) throws IOException {
        int forLoopPasses = 0;

        if (insertSpecifications.isEmpty()) {
            return;
        }
        
        LOG.info("Number of records to insert: insertSpecifications.size():: " + insertSpecifications.size());
        for (InsertSpecification insertSpecification: insertSpecifications) {
            GenMAPPBuilderUtilities.insertAccessRowDirectly(exportDatabase,
                    insertSpecification.tableName, insertSpecification.values);
            forLoopPasses++;
        }

        // Always print out the number of successful passes through the for loop
        LOG.info("Number of successful inserts: [" + forLoopPasses + "]");
    }

    private void flushUpdates(Connection connection) {
        int errorCounter = 0;
        int forLoopPasses = 0;

        if (sqlBuffer.isEmpty()) {
            return;
        }
        
        PreparedStatement ps = null;
        LOG.info("Number of records to update: sqlBuffer.size():: " + sqlBuffer.size());
        for (SQLStatement sqlStatement: sqlBuffer) {
            try {
                ps = connection.prepareStatement(sqlStatement.getSQL());
                if (sqlStatement.getValues() != null) {
                    Object[] values = sqlStatement.getValues();
                    for (int i = 0; i < values.length; i++) {
                        // TODO Unfortunate, necessary evil due to the need to extend the prior design
                        // so that it can accommodate proper setting of dates. This entire Table/TableManager
                        // framework really needs to be redesigned for greater type-awareness.
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
                StringBuffer errText = new StringBuffer("An SQLException occurred while writing to the database. ");
                errText.append(" Error Code: " + e.getErrorCode());
                errText.append(" Message: " + e.getMessage());
                LOG.error(errText);

                for (StackTraceElement ste: e.getStackTrace()) {
                    System.out.println("    " + ste);
                }

                LOG.error(sqlStatement.getSQL());
                if (sqlStatement.getValues() != null) {
                    for (Object value: sqlStatement.getValues()) {
                        LOG.error(" - " + value);
                    }
                }

                errorCounter++;
            } finally {
                try {
                    ps.close();
                } catch (Exception exc) {
                    LOG.warn("Problem closing PreparedStatement");
                }
            }
        }

        if (errorCounter > 0) {
            LOG.error(errorCounter + " number of erroneous records were captured.");
        }

        LOG.info("Number of successful updates: [" + forLoopPasses + "]");
    }

    private static final Log LOG = LogFactory.getLog(Table.class);

    private TableManager tableManager;
    private Attributes tableAttributes;
    private List<SQLStatement> sqlBuffer = new ArrayList<SQLStatement>();
    private List<InsertSpecification> insertSpecifications = new ArrayList<InsertSpecification>();
    private int insertCount = 0;
    private int updateCount = 0;
}
