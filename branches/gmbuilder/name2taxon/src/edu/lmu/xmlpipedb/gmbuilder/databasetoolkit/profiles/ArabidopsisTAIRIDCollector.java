package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The ArabidopsisTAIRIDCollector "harvests" TAIR IDs from a UniProt Arabidopsis
 * database. The utility is separated for execution outside of GenMAPP Builder
 * as well as easier unit testing.
 * 
 * Implementation note: This routine assumes support for "similar to" regexes in
 * addition to the substring(source from regex) and upper(string) functions.
 * PostgreSQL has them, but other databases may not.
 * 
 * @author dondi
 */
public class ArabidopsisTAIRIDCollector {

    /**
     * Convenience method for collectTAIRIDs() with temporary set to true.
     */
    public void collectTAIRIDs(Connection c) {
        collectTAIRIDs(c, true);
    }

    /**
     * Collects TAIR IDs from the database at the given Connection (assumed to
     * be an xsd2db-generated UniProt Arabidopsis database), and gathers them
     * into a temp_tair table.
     */
    public void collectTAIRIDs(Connection c, boolean temporary) {
        // All TAIR IDs follow this pattern.
        final String tairID = "[Aa][Tt][0-9][CcMmGg][0-9][0-9][0-9][0-9][0-9]";

        // Wrap this all in a transaction.
        boolean priorAutoCommit = true;
        try {
            priorAutoCommit = c.getAutoCommit();
            c.setAutoCommit(false);
        } catch(SQLException e) {
            logSQLException(e, "Setting autocommit to false");
        }

        PreparedStatement ps;
        String sqlQuery;

        // Step 1: Put the dbreference values for TAIR into a temp table, at
        // the same time "cleaning out" any prefixes and suffixes (via the
        // substring function)
        _Log.info("Collecting TAIR IDs from dbreference...");
        sqlQuery = "create " + (temporary ? "temporary" : "") + " table " +
            "temp_tair as " +
            "select a.entrytype_dbreference_hjid as hjid, " +
            "substring(a.id from ?) as id " +
            "from dbreferencetype a where a.id similar to ? " +
            "group by a.id, a.entrytype_dbreference_hjid";
        try {
            ps = c.prepareStatement(sqlQuery);
            // We want just the substring (no version numbers).
            ps.setString(1, tairID);
            // TAIR IDs in dbreference may have prefixes and postfixes.
            ps.setString(2, "%" + tairID + "%");
            ps.executeUpdate();
        } catch(SQLException e) {
            logSQLException(e, sqlQuery);
        }

        // Step 2: TAIR IDs can also be found in propertytype
        _Log.info("Collecting TAIR IDs from propertytype...");
        sqlQuery = "insert into temp_tair " +
            "select d.entrytype_dbreference_hjid as hjid, substring(p.value from ?) " +
            "from propertytype p inner join dbreferencetype d " +
            "on (p.dbreferencetype_property_hjid = d.hjid) " +
            "where p.value similar to ? " +
            "group by d.entrytype_dbreference_hjid, p.value";
        try {
            ps = c.prepareStatement(sqlQuery);
            // We want just the substring (no version numbers).
            ps.setString(1, tairID);
            // We don't accommodate prefixes nor suffixes in propertytype.
            ps.setString(2, tairID);
            ps.executeUpdate();
        } catch(SQLException e) {
            logSQLException(e, sqlQuery);
        }

        // Step 3: Extract the genenametype IDs; these may be lists of further
        // IDs, with at least one TAIR ID among them. For each of these
        // potential lists, extract the TAIR IDs, then insert a new individual
        // record for each of them.
        sqlQuery = "select d.entrytype_gene_hjid as hjid, c.value " +
            "from genenametype c inner join entrytype_genetype d " +
            "on (c.entrytype_genetype_name_hjid = d.hjid) " +
            "where c.value similar to ? " +
            "group by d.entrytype_gene_hjid, c.value";
        parseMatches(sqlQuery, tairID, c);

        // Step 4: Grab TAIR IDs from the proteinnametype table.
        // 20081031: There is no more proteinnametype table.
//        sqlQuery = "select e.hjid as hjid, p.value " +
//            "from entrytype e inner join proteinnametype p on " +
//            "(e.protein = p.proteintype_name_hjid) " +
//            "where p.value similar to ? " +
//            "group by e.hjid, value";
//        parseMatches(sqlQuery, tairID, c);
        
        // Step 5: Grab TAIR IDs from the text field of the comment table.
        sqlQuery = "select ec.entrytype_comment_hjid as hjid, c.text as value " +
            "from entrytype_comment ec inner join commenttype c on " +
            "(ec.entrytype_comment_hjchildid = c.hjid) " +
            "where c.text similar to ? " +
            "group by ec.entrytype_comment_hjid, c.text";
        parseMatches(sqlQuery, tairID, c);

        // Penultimate step: Convert all IDs to upper case.
        _Log.debug("Setting all TAIR IDs to uppercase...");
        sqlQuery = "update temp_tair set id = upper(id)";
        try {
            ps = c.prepareStatement(sqlQuery);
            ps.executeUpdate();
        } catch(SQLException e) {
            logSQLException(e, sqlQuery);
        }

        // At this point, temp_tair should be ready for further processing.
        // Commit and continue.
        try {
            c.commit();
            c.setAutoCommit(priorAutoCommit);
            _Log.debug("Committed temp TAIR table; all done.");
        } catch(SQLException e) {
            logSQLException(e, "Commit and restore prior autocommit");
        }
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

    /**
     * Helper method for extracting matches from a larger string.
     * 
     * Yes, this can be nasty slow on some computers, but it is accurate and
     * general; in particular, it will capture multiple occurrences of an ID in
     * the string. In any case, the computer used to develop this code had
     * satisfactory performance.
     */
    private void parseMatches(String sqlQuery, String tairID, Connection c) {
        // First, gather the extracted IDs into memory.
        List<TAIRPair> ids = new ArrayList<TAIRPair>();
        
        PreparedStatement ps;
        ResultSet result;
        try {
            ps = c.prepareStatement(sqlQuery);
            // Wildcards on both ends let us select everything that contains
            // a TAIR ID pattern.
            ps.setString(1, "%" + tairID + "%");
            result = ps.executeQuery();

            // genenametype includes "dot-number" TAIR IDs; take these into
            // account.
            Pattern p = Pattern.compile("(" + tairID + ")(\\.[0-9])?");
            while (result.next()) {
                Long hjid = result.getLong("hjid");
                String idList = result.getString("value");
                _Log.debug("Processing raw ID: " + idList + " for surrogate " + hjid);

                // Extract matching IDs, including "dot-number" versions.
                Matcher m = p.matcher(idList);
                while (m.find()) {
                    String extractedID = m.group(1);
                    _Log.debug("--- Got TAIR ID " + extractedID);
                    ids.add(new TAIRPair(hjid, extractedID));
                }
            }
        } catch(SQLException e) {
            logSQLException(e, sqlQuery);
        }

        // Place the IDs into temp_tair.
        sqlQuery = "insert into temp_tair(hjid, id) values(?, ?)";
        try {
            ps = c.prepareStatement(sqlQuery);
            for (TAIRPair tp : ids) {
                ps.setLong(1, tp.hjid);
                ps.setString(2, tp.tair);
                _Log.debug("inserting (" + tp.hjid + ", " + tp.tair + ")...");
                int count = ps.executeUpdate();
                _Log.debug("...inserted " + count);
            }
        } catch(SQLException e) {
            logSQLException(e, sqlQuery);
        }
    }

    private static final Log _Log = LogFactory.getLog(ArabidopsisTAIRIDCollector.class);

    /**
     * Helper class for holding a surrogate gene ID and a string equivalent.
     */
    private static class TAIRPair {
        public TAIRPair(Long hjid, String tair) {
            this.hjid = hjid;
            this.tair = tair;
        }

        public Long hjid;
        public String tair;
    }
}
