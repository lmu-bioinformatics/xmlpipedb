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

public class VibrioCholeraeUniprotSpeciesProfile extends UniProtSpeciesProfile {

    public VibrioCholeraeUniprotSpeciesProfile() {
        super("Vibrio cholerae", 243277, "This profile defines the requirements for " +
                "a Vibrio cholerae species within a UniProt database.");
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
            { "Link", "http://bacteria.ensembl.org/Multi/Search/Results?species=all;idx=;q=~;site=ensemblunit" }
        });

        return tableManager;
    }

    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager,
            TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        // Start with the default OrderedLocusNames behavior.
        TableManager result = super.getSystemTableManagerCustomizations(tableManager, primarySystemTableManager,
                version);

        // We want to grab all of the legal OrderedLocusNames Ids and
        // remove the '_', adding them to the OrderedLocusNames table
        final String vcID = "VC_*";
        String sqlQuery = "select d.entrytype_gene_hjid as hjid, c.value " +
                "from genenametype c inner join genetype d " +
                "on (c.genetype_name_hjid = d.hjid) " +
                "where (c.value similar to ?)" +
                "and type <> 'ordered locus names' " +
                "group by d.entrytype_gene_hjid, c.value";

        Connection c = ConnectionManager.getRelationalDBConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            // Query, iterate, add to table manager.
            ps = c.prepareStatement(sqlQuery);
            ps.setString(1, vcID);
            rs = ps.executeQuery();
            while (rs.next()) {
                String hjid = Long.valueOf(rs.getLong("hjid")).toString();
                String id = rs.getString("value");
                String[] substrings = id.split("/");
                String newId = null;
                for (int i = 0; i < substrings.length; i++) {
                    newId = substrings[i].replace("_", "");
                    LOG.debug("Remove '_' from " + id + " to create: " + newId + " for surrogate " + hjid);
                    result.submit("OrderedLocusNames", QueryType.insert, new Object[][] {
                        { "ID", newId },
                        { "Species", "|" + getSpeciesName() + "|" },
                        { "Date", version },
                        { "UID", hjid }
                    });
                }
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

    private static final Log LOG = LogFactory.getLog(VibrioCholeraeUniprotSpeciesProfile.class);
}
