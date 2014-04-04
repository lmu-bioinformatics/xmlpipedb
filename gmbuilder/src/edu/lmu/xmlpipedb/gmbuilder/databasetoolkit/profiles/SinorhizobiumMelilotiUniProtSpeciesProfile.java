package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;


public class SinorhizobiumMelilotiUniProtSpeciesProfile extends UniProtSpeciesProfile {
    
    public SinorhizobiumMelilotiUniProtSpeciesProfile() {
        super("Sinorhizobium meliloti",
            266834,
            "This profile customizes the GenMAPP Builder export for " +
                "Sinorhizobium meliloti" +
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
            { "Link", "http://bacteria.ensembl.org/sinorhizobium_meliloti_1021/Gene/Summary?g=~" }
        });
    
        return tableManager;
    }
    
    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager, TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        List<String> comparisonList = new ArrayList<String>();
        comparisonList.add("ordered locus");
        comparisonList.add("ORF");

        // First, we want to export the IDs without modification.
        TableManager result = systemTableManagerCustomizationsHelper(tableManager, primarySystemTableManager, version, "OrderedLocusNames", comparisonList);

        // We want to grab all of the legal OrderedLocusNames Ids and
        // remove the '_', adding them to the OrderedLocusNames table
        final String idPattern = "(SM_b|smb)%";
        String sqlQuery = "select d.entrytype_gene_hjid as hjid, c.value " +
                "from genenametype c inner join genetype d " +
                "on (c.genetype_name_hjid = d.hjid) " +
                "where (c.value similar to ?) " +
                "and (type = 'ORF' or type = 'ordered locus') " +
                "group by d.entrytype_gene_hjid, c.value";

        String dateToday = GenMAPPBuilderUtilities.getSystemsDateString(version);
        Connection c = ConnectionManager.getRelationalDBConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            // Query, iterate, add to table manager.
            ps = c.prepareStatement(sqlQuery);
            ps.setString(1, idPattern);
            rs = ps.executeQuery();
            while (rs.next()) {
                String hjid = Long.valueOf(rs.getLong("hjid")).toString();

                // We want to remove the '_' here
                String id = rs.getString("value");

                String[] substrings = id.split("/");
                for (int i = 0; i < substrings.length; i++) {
                    // Grab the digit sets---this is what we care about anyway.
                    int length = substrings[i].length();
                    
                    // Conditional is a sanity check guess.
                    String digits = (length >= 5) ? substrings[i].substring(length - 5, length) : substrings[i];

                    // Generate the variants.
                    List<String> variants = new ArrayList<String>(2);
                    variants.add("SM_b" + digits); // SM_b#####
                    variants.add("smb" + digits);  // smb#####
                    
                    for (String variant: variants) {
                        _Log.debug("Original ID: " + substrings[i] + " converted to: " + variant + " for surrogate " + hjid);
                        result.submit("OrderedLocusNames", QueryType.insert, new String[][] { { "ID", variant }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", dateToday }, { "UID", hjid } });
                    }
                }
            }
        } catch(SQLException sqlexc) {
            logSQLException(sqlexc, sqlQuery);
        }

        return result;
    }

    /**
     * Helper method for logging an SQL exception.
     */
    private void logSQLException(SQLException sqlexc, String sqlQuery) {
        _Log.error("Exception trying to execute query: " + sqlQuery);
        while (sqlexc != null) {
            _Log.error("Error code: [" + sqlexc.getErrorCode() + "]");
            _Log.error("Error message: [" + sqlexc.getMessage() + "]");
            _Log.error("Error SQL State: [" + sqlexc.getSQLState() + "]");
            sqlexc = sqlexc.getNextException();
        }
    }

    private static final Log _Log = LogFactory.getLog(SinorhizobiumMelilotiUniProtSpeciesProfile.class);
}
