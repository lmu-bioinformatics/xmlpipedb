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
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;

/**
 * 
 * @author dsmith
 *
 */
public class VibrioCholeraeUniprotSpeciesProfile extends UniProtSpeciesProfile {

	public VibrioCholeraeUniprotSpeciesProfile() {
		super("Vibrio cholerae", "This profile defines the requirements for "
                + "a Vibrio cholerae species within a UniProt database.");
	}
	
	  /**
     * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.UniProtSpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, java.util.Date)
     */
    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager, TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        // Start with the default OrderedLocusNames behavior.
        TableManager result = super.getSystemTableManagerCustomizations(tableManager, primarySystemTableManager, version);
        
        // We want to grab all of the legal OrderedLocusNames Ids and 
        // remove the '_', adding them to the OrderedLocusNames table
        final String vcID = "VC_*";
        String sqlQuery = "select d.entrytype_gene_hjid as hjid, c.value " +
            "from genenametype c inner join entrytype_genetype d " +
            "on (c.entrytype_genetype_name_hjid = d.hjid) " +
            "where (c.value similar to ?)" +
            "and type <> 'ordered locus names' " +
            "group by d.entrytype_gene_hjid, c.value";

        String dateToday = GenMAPPBuilderUtilities.getSystemsDateString(version);
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
                
                // We want to remove the '_' here
                String id = rs.getString("value");
                id = id.replace("_", "");
                
                _Log.debug("Remove '_' to create: " + id + " for surrogate " + hjid);
                tableManager.submit("OrderedLocusNames", QueryType.insert, new String[][] { { "ID", id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", dateToday }, { "UID", hjid } });
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

    private static final Log _Log = LogFactory.getLog(PlasmodiumFalciparumUniProtSpeciesProfile.class);
}
