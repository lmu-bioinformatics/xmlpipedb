// Created by dondi, Nov 24, 2008.
// modified by rbrousla, April 30, 2011
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

/**
 * @author   dondi
 */
public class PlasmodiumFalciparumUniProtSpeciesProfile extends UniProtSpeciesProfile {

    /**
     * The species profile gets selected by a name match.
     * 
     * TODO As of this writing, the P. falciparum XML file actually has two
     * names: "Plasmodium falciparum" and "Plasmodium falciparum (isolate 3D7)."
     * For now, we choose the shorter of those strings, but in the long run,
     * the code needs a better way to map a species to a species profile. 
     */
    public PlasmodiumFalciparumUniProtSpeciesProfile() {
        super("Plasmodium falciparum", 36329, "This profile defines the requirements for "
                + "a Plasmodium falciparum species within a UniProt database.");
    }

    /**
     * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.UniProtSpeciesProfile#getSystemsTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile)
     */
    @Override
    public TableManager getSystemsTableManagerCustomizations(TableManager tableManager, DatabaseProfile dbProfile) {
        super.getSystemsTableManagerCustomizations(tableManager, dbProfile);
        tableManager.submit("Systems", QueryType.update, new String[][] {
            { "SystemCode", "N" },
            { "Species", "|" + getSpeciesName() + "|" }
        });

        tableManager.submit("Systems", QueryType.update, new String[][] {
            { "SystemCode", "N" },
            { "Link", "http://plasmodb.org/plasmo/showRecord.do?name=GeneRecordClasses.GeneRecordClass&project_id=PlasmoDB&source_id=~" }
        });

        return tableManager;
    }

    /**
     * @see edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.UniProtSpeciesProfile#getSystemTableManagerCustomizations(edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.tables.TableManager, java.util.Date)
     */
    @Override
    public TableManager getSystemTableManagerCustomizations(TableManager tableManager, TableManager primarySystemTableManager, Date version) throws SQLException, InvalidParameterException {
        // Start with the default OrderedLocusNames behavior.
        TableManager result = super.getSystemTableManagerCustomizations(tableManager, primarySystemTableManager, version);
        // query searches for all ids of type = 'ORF'

        String sqlQuery = "select d.entrytype_gene_hjid as hjid, c.value " +
            "from genenametype c inner join genetype d " +
            "on (c.genetype_name_hjid = d.hjid) " +
            "where c.type = 'ORF'" +
            "group by d.entrytype_gene_hjid, c.value";

        Connection c = ConnectionManager.getRelationalDBConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            // Query, iterate, add to table manager.
            ps = c.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String hjid = Long.valueOf(rs.getLong("hjid")).toString();

                // capture unmodified value in string variable id
                // We want to remove the '_' here but also add id's with the '_'
                String id = rs.getString("value");
                
                // condition if id matched pattern which indicates it contains a '_'
                if (id.matches("PFA_.*")) {
                String[] substrings = id.split("/");
                String new_id = null;
                String old_id = id;
                for (int i = 0; i < substrings.length; i++) {

                    new_id = substrings[i].replace("_", "");

                    _Log.debug("Remove '_' from " + id + " to create: " + new_id + " for surrogate " + hjid);
                    result.submit("OrderedLocusNames", QueryType.insert, new Object[][] { { "ID", new_id }, { "Species", "|" + getSpeciesName() + "|" }, { "Date", version }, { "UID", hjid } });
                    
                    _Log.debug("Keep '_' from " + id + " to create: " + old_id + " for surrogate " + hjid);
                    result.submit("OrderedLocusNames", QueryType.insert, new Object[][] { { "ID", old_id }, { "Species", "|" + getSpeciesName() + "|" }, { "Date", version }, { "UID", hjid } });
                }
                }
                // otherwise process as normal
                    else {
                    _Log.debug("Processing raw ID: " + id + " for surrogate " + hjid);
                    tableManager.submit("OrderedLocusNames", QueryType.insert, new Object[][] { { "ID", id }, { "Species", "|" + getSpeciesName() + "|" }, { "Date", version }, { "UID", hjid } });
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

    private static final Log _Log = LogFactory.getLog(PlasmodiumFalciparumUniProtSpeciesProfile.class);
}
