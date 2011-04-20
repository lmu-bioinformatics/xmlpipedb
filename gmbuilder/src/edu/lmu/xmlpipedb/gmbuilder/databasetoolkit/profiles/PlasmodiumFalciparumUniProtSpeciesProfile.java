// Created by dondi, Nov 24, 2008.
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
    // disabled pattern match to just look for type = 'ORF'
        // Next, we add IDs from the other gene/name tags, but ONLY if they match
        // the pattern PF[A-Z][0-9]{4}[a-z].
        //final String pfID = "PF[A-Z][0-9][0-9][0-9][0-9][a-z]";
        //final String pfID2 = "PF[0-9][0-9]_[0-9][0-9][0-9][0-9]";
        //final String pfID3 = "MAL[0-9]*P1.[0-9]*";
        String sqlQuery = "select d.entrytype_gene_hjid as hjid, c.value " +
            "from genenametype c inner join entrytype_genetype d " +
            "on (c.entrytype_genetype_name_hjid = d.hjid) " +
            //"where (c.value similar to ? " +
            "where c.type = 'ORF'" +
            //"or c.value similar to ? " +
            //"or c.value similar to ?) " +
            //"and type <> 'ordered locus names' " +
            //"and type <> 'ORF' " +
            "group by d.entrytype_gene_hjid, c.value";

        String dateToday = GenMAPPBuilderUtilities.getSystemsDateString(version);
        Connection c = ConnectionManager.getRelationalDBConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            // Query, iterate, add to table manager.
            ps = c.prepareStatement(sqlQuery);
            //ps.setString(1, pfID);
            //ps.setString(2, pfID2);
            //ps.setString(3, pfID3);
            rs = ps.executeQuery();
        // old code prior to processing id's with '_'    
            //while (rs.next()) {
            //    String hjid = Long.valueOf(rs.getLong("hjid")).toString();
            //    String id = rs.getString("value");
            //    _Log.debug("Processing raw ID: " + id + " for surrogate " + hjid);
            //    tableManager.submit("OrderedLocusNames", QueryType.insert, new String[][] { { "ID", id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", dateToday }, { "UID", hjid } });
            //}
            
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
                    result.submit("OrderedLocusNames", QueryType.insert, new String[][] { { "ID", new_id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", dateToday }, { "UID", hjid } });
                    
                    _Log.debug("Keep '_' from " + id + " to create: " + old_id + " for surrogate " + hjid);
                    result.submit("OrderedLocusNames", QueryType.insert, new String[][] { { "ID", old_id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", dateToday }, { "UID", hjid } });
                }
                }
                // otherwise process as normal
                    else {
                    _Log.debug("Processing raw ID: " + id + " for surrogate " + hjid);
                    tableManager.submit("OrderedLocusNames", QueryType.insert, new String[][] { { "ID", id }, { "Species", "|" + getSpeciesName() + "|" }, { "\"Date\"", dateToday }, { "UID", hjid } });
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
