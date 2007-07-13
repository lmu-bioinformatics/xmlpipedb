package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The ArabidopsisTAIRIDCollector "harvests" TAIR IDs from a UniProt Arabidopsis
 * database. The utility is separated for execution outside of GenMAPP Builder
 * as well as easier unit testing.
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

        PreparedStatement ps;
        ResultSet result;
        int splits = -1;
        String sqlQuery;
        
        //step 1 - put the dbreference values for TAIR into a temp table, at the
        // same time "cleaning out" any prefixes and suffixes (via the substring
        // function)
        sqlQuery = "create table " + (temporary ? "temporary" : "") +
            " temp_tair as " +
            "select a.entrytype_dbreference_hjid as hjid, substring(a.id from ?) as id " +
            "from dbreferencetype a where a.id similar to ? " +
            "group by a.id, a.entrytype_dbreference_hjid";
        try {
            ps = c.prepareStatement(sqlQuery);
            ps.setString(1, tairID);
            ps.setString(2, "%" + tairID + "%");
            ps.executeUpdate();
        } catch(SQLException e) {
            logSQLException(e, sqlQuery);
        }
        
        // step 1a - TAIR IDs can also be found in propertytype
        sqlQuery = "insert into temp_tair " +
            "select d.entrytype_dbreference_hjid as hjid, p.value " +
            "from propertytype p inner join dbreferencetype d on (p.dbreferencetype_property_hjid = d.hjid) " +
            "where p.value similar to ? " +
            "group by d.entrytype_dbreference_hjid, p.value";
        try {
            ps = c.prepareStatement(sqlQuery);
            // We don't accommodate prefixes nor suffixes in propertytype.
            ps.setString(1, tairID);
            ps.executeUpdate();
        } catch (SQLException e) {
            logSQLException(e, sqlQuery);
        }

        //step 2 - put the genename values that match the TAIR pattern exactly* into the temp table
        //* some genename records have more than one TAIR ID per cell and they 
        //   must have their values split out in step 3 below.
        sqlQuery =
        "insert into temp_tair " +
        "select d.entrytype_gene_hjid as hjid, c.value " +
        "from genenametype c INNER JOIN entrytype_genetype d ON (c.entrytype_genetype_name_hjid = d.hjid) " +
        "where c.value SIMILAR TO ? " +
        "group by d.entrytype_gene_hjid, c.value";
        try {
            ps = c.prepareStatement(sqlQuery);
            ps.setString(1, tairID);
            ps.executeUpdate();
        } catch (SQLException e) {
            logSQLException(e, sqlQuery);
        }
            
        //step 3 - get the genename values for the records with multiple TAIR IDs
        
        /* JD: I see what Jeffrey is trying to do here, but the logic breaks
         * somewhat when we consider that:
         * 
         * (a) the "separator" isn't just a slash; it may also be a colon,
         *     a semicolon, or a space
         *
         * (b) many composite IDs consist of more than just TAIR IDs; i.e., they
         *     may use other formats that aren't necessarily 9 characters long
         * 
         * (c) see below for the proposed alternative
         */
        // 3a. - determine the largest number of values in one cell
        sqlQuery =
        " select max(char_length(c.value)) as maxLength " +
        " from genenametype c " + 
        " where c.value SIMILAR TO ?";
        try {
            ps = c.prepareStatement(sqlQuery);
            ps.setString(1, "%/" + tairID + "/*A%");
            result = ps.executeQuery();
            // get result MOD 9 (number of characters in ID) = number of "/" characters in longest value
            result.next();
            int maxLength = result.getInt("maxLength");
            splits = maxLength / 9; // 9 is the length of the TAIR ID (At1g12345)
        } catch (SQLException e) {
            logSQLException(e, sqlQuery);
        }
        
            //step 4 - generate the query that will put values from each column of splits into the temp table
            for( int i = 1; i <= splits; i++){
                try {
                    sqlQuery = 
                    "insert into temp_tair " +
                    "select hjid, value from ( " +
                    "select d.entrytype_gene_hjid as hjid, split_part(c.value, '/', " + i + ") as value " +
                    "from genenametype c INNER JOIN entrytype_genetype d ON (c.entrytype_genetype_name_hjid = d.hjid) " +
                    "where c.value SIMILAR TO ?) as split_gene " +
                    "where value SIMILAR TO ? ";
                    ps = c.prepareStatement(sqlQuery);
                    ps.setString(1, "%" + tairID + "/%");
                    ps.setString(2, tairID);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    logSQLException(e, sqlQuery);
                }
            }

        // Step 3: extract the genenametype IDs that are likely to be lists of
        // further IDs, with at least one TAIR ID among them.  For each of these
        // potential lists, extract the TAIR IDs, then insert a new individual
        // record for each of them.
        //
        // FIXME Yes, this is nasty slow.  But more accurate and general.
//        sqlQuery =
//            "select d.entrytype_gene_hjid as hjid, c.value " +
//            "from genenametype c INNER JOIN entrytype_genetype d ON (c.entrytype_genetype_name_hjid = d.hjid) " +
//            "where c.value SIMILAR TO ? and char_length(c.value) > ? " +
//            "group by d.entrytype_gene_hjid, c.value";

        // step 5: Convert all IDs to upper case.
        sqlQuery = "update temp_tair set id = upper(id)";
        try {
            ps = c.prepareStatement(sqlQuery);
            ps.executeUpdate();
        } catch (SQLException e) {
            logSQLException(e, sqlQuery);
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

    private static final Log _Log = LogFactory.getLog(ArabidopsisTAIRIDCollector.class);
}
