package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

public class BordetellaPertussisUniProtSpeciesProfile extends UniProtSpeciesProfile {

    public BordetellaPertussisUniProtSpeciesProfile() {
        super("Bordetella pertussis", 257313, "This profile customizes the GenMAPP Builder export for " +
                "Bordetella pertussis" +
                " data loaded from a UniProt XML file.");
    }

    @Override
    public TableManager getSystemsTableManagerCustomizations(TableManager tableManager, DatabaseProfile dbProfile) {
        super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
        tableManager.submit("Systems", QueryType.update, new String[][] {
            { "SystemCode", "N" },
            { "Species", "|" + getSpeciesName() + "|" }
        });

        tableManager.submit("Systems", QueryType.update, new String[][] {
            { "SystemCode", "N" },
            { "Link", "http://www.genedb.org/gene/~;jsessionid=A06A0EFE93C64E476380393D4CBEFA69?actionName=%2FQuery%2FquickSearch&resultsSize=1&taxonNodeName=Bpertussis" }
        });

        return tableManager;
    }

    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager,
            TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        // Start with the default OrderedLocusNames behavior.
        TableManager result = super.getSystemTableManagerCustomizations(tableManager, primarySystemTableManager,
                version);

        String sqlQuery = "select dbreferencetype.entrytype_dbreference_hjid as hjid, propertytype.value from propertytype inner join dbreferencetype on " +
                "(propertytype.dbreferencetype_property_hjid = dbreferencetype.hjid) " +
                "where dbreferencetype.type = 'EnsemblBacteria' and propertytype.type = 'gene ID' " +
                "and propertytype.value ~ 'BP[0-9][0-9][0-9][0-9](A|B)' order by propertytype.value";

        Connection c = ConnectionManager.getRelationalDBConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            // Query, iterate, add to table manager.
            ps = c.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            while (rs.next()) {
                String hjid = Long.valueOf(rs.getLong("hjid")).toString();
                String id = rs.getString("value");
                result.submit("OrderedLocusNames", QueryType.insert, new Object[][] {
                    { "ID", id },
                    { "Species", "|" + getSpeciesName() + "|" },
                    { "Date", version },
                    { "UID", hjid }
                });
            }
        } catch(SQLException sqlexc) {
            logSQLException(sqlexc, sqlQuery);
        }

        return result;
    }

    private void logSQLException(SQLException sqlexc, String sqlQuery) {
        LOG.error("Exception trying to execute query: " + sqlQuery);
        while (sqlexc != null) {
            LOG.error("Error code: [" + sqlexc.getErrorCode() + "]");
            LOG.error("Error message: [" + sqlexc.getMessage() + "]");
            LOG.error("Error SQL State: [" + sqlexc.getSQLState() + "]");
            sqlexc = sqlexc.getNextException();
        }
    }

    private static final Log LOG = LogFactory.getLog(BordetellaPertussisUniProtSpeciesProfile.class);

}
