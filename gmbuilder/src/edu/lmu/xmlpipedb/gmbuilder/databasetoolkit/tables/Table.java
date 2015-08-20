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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.PropertyMap;
import com.healthmarketscience.jackcess.TableBuilder;

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

            nameToType = new LinkedHashMap<String, String>();

            for (int i = 0; i < attributes.length; i++) {
                if (attributes[i].length != 2) {
                    LOG.error("Incorrect number of arguments");
                }
                nameToType.put(attributes[i][0], attributes[i][1]);
            }
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
        flush(exportDatabase);
        exportDatabase.flush();
        exportDatabase.close();
    }

    /**
     * Create Table. This call will prepare the create table sql statement and
     * submit it to the buffer, flush() will still need to be called.
     * 
     * @param tableName
     */
    private void create(String tableName, Database exportDatabase) throws IOException {
        TableBuilder tableBuilder = new TableBuilder(tableName);
        for (String columnName: tableAttributes.nameSet()) {
            String typeSpec = tableAttributes.get(columnName);
            ColumnBuilder columnBuilder = new ColumnBuilder(columnName, GenMAPPBuilderUtilities.getDataType(typeSpec));
            if (GenMAPPBuilderUtilities.getDataTypeLength(typeSpec) != null) {
                columnBuilder.setLengthInUnits(GenMAPPBuilderUtilities.getDataTypeLength(typeSpec).intValue());
            }
            tableBuilder.addColumn(columnBuilder);
        }

        // FIXME Jackcess property-setting appears to corrupt the table. Need to revisit sometime.
        com.healthmarketscience.jackcess.Table table = tableBuilder.toTable(exportDatabase);
        for (Column column: table.getColumns()) {
            if (GenMAPPBuilderUtilities.specifiesDataTypeNotNull(tableAttributes.get(column.getName()))) {
                PropertyMap propertyMap = column.getProperties();
                propertyMap.put(PropertyMap.REQUIRED_PROP, DataType.BOOLEAN, Boolean.TRUE);
                propertyMap.save();
            }
        }
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
        // TODO Very possibly, this method can be eliminated, when the insert specification
        // is replaced by the TableManager's own Row objects.
        insertSpecifications.add(new InsertSpecification(tableName, namesAndValues));
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
    private void flush(Database exportDatabase/*Connection connection*/) throws IOException {
        /*
         * The code was not written to submit and commit batches of records.
         * Rather it goes record by record. This is a possible area of
         * performance enhancement later on.
         */
        int errorCounter = 0; // used for counting SQLExceptions
        int forLoopPasses = 0; // used for counting # of passes through for loop
                               // that executed without exception

        if (insertSpecifications.isEmpty()) {
            return;
        }
        
        LOG.info("Number of records to process: insertSpecifications.size():: " + insertSpecifications.size());
        for (InsertSpecification insertSpecification: insertSpecifications) {
            com.healthmarketscience.jackcess.Table table = exportDatabase.getTable(insertSpecification.tableName);
            List<Object> valuesInOrder = new ArrayList<Object>();
            for (Column column: table.getColumns()) {
                valuesInOrder.add(insertSpecification.values.get(column.getName()));
            }
            table.addRow(valuesInOrder.toArray());
            forLoopPasses++;
        }

        // print a log entry if any errors were encountered.
        if (errorCounter > 0) {
            LOG.error(errorCounter + " number of erroneous records were captured.");
        }

        // Always print out the number of successful passes through the for loop
        LOG.info("Number of successful passes through for loop: [" + forLoopPasses + "]");
    }

    private static final Log LOG = LogFactory.getLog(Table.class);

    private TableManager tableManager;
    private Attributes tableAttributes;
    private List<SQLStatement> sqlBuffer = new ArrayList<SQLStatement>();
    private List<InsertSpecification> insertSpecifications = new ArrayList<InsertSpecification>();
    private int insertCount = 0;
    private int updateCount = 0;
}
