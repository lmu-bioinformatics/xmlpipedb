package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ConnectionManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.QueryType;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager.Row;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

public class MycobacteriumTuberculosisUniProtSpeciesProfile extends UniProtSpeciesProfile {

    public MycobacteriumTuberculosisUniProtSpeciesProfile() {
        super("Mycobacterium tuberculosis",
            "This profile customizes the GenMAPP Builder export for Genus species data loaded from a UniProt XML file.");
    }

    @Override
    public TableManager getSystemsTableManagerCustomizations(TableManager tableManager, DatabaseProfile dbProfile) {
        super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
        tableManager.submit("Systems", QueryType.update, new String[][] {
            { "SystemCode", "N" }, { "Species", "|" + getSpeciesName() + "|" }
        });

        tableManager.submit("Systems", QueryType.update, new String[][] {
            { "SystemCode", "N" }, { "Link", "http://www.genome.jp/dbget-bin/www_bget?mtu+~" }
        });

        return tableManager;
    }

    /**
     * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.UniProtSpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, java.util.Date)
     */
    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager, TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        // Build the base query; we only use "ordered locus" and we only want
        // IDs that begin with "Rv."
        PreparedStatement ps = ConnectionManager.getRelationalDBConnection().prepareStatement("SELECT value, type " +
            "FROM genenametype INNER JOIN entrytype_genetype " +
            "ON (entrytype_genetype_name_hjid = entrytype_genetype.hjid) " +
            "WHERE type = 'ordered locus' and value like 'Rv%' and entrytype_gene_hjid = ?");
        ResultSet result;

        for (Row row : primarySystemTableManager.getRows()) {
            ps.setInt(1, Integer.parseInt(row.getValue("UID")));
            result = ps.executeQuery();

            // We actually want to keep the case where multiple ordered locus
            // names appear.
            while (result.next()) {
                // We want this name to appear in the OrderedLocusNames
                // system table.
                for (String id : result.getString("value").split("/")) {
                    tableManager.submit("OrderedLocusNames", QueryType.insert, new String[][] { { "ID", id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", GenMAPPBuilderUtilities.getSystemsDateString(version) }, { "UID", row.getValue("UID") } });
                }
            }
        }

        return tableManager;
    }
}
