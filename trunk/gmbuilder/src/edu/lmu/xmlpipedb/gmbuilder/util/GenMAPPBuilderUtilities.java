// Created by xmlpipedb, Jul 3, 2006.
package edu.lmu.xmlpipedb.gmbuilder.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * GenMAPPBuilderUtilities is a general placeholder for standalone utility
 * methods needed by GenMAPP Builder.
 * 
 * @author xmlpipedb
 * @version $Revision$ $Date$
 */
public class GenMAPPBuilderUtilities {
    /**
     * Returns the default filename for the given information.
     * 
     * @param fullSpeciesName
     *            The full species name to use
     * @param creationDate
     *            The date to include
     * @return The default filename for the Gene Database to be generated with
     *         the given parameters
     */
    public static String getDefaultGDBFilename(String fullSpeciesName, Date creationDate) {
        String genusName = getGenusName(fullSpeciesName);
        String speciesName = getSpeciesName(fullSpeciesName);

        StringBuffer sb = new StringBuffer();
        sb.append("".equals(genusName) ? "" : genusName.substring(0, 1).toUpperCase());
        sb.append("".equals(speciesName) ? "" : speciesName.substring(0, 1).toLowerCase());
        sb.append("-Std_");
        sb.append(GDB_DF.format(creationDate));
        sb.append(".gdb");
        return sb.toString();
    }
    
    /**
     * Extracts the genus name of the given species name. If a genus name is not
     * found, then an empty string is returned.
     * 
     * @param speciesName
     *            The full species name from which to extract the genus
     * @return The name of the genus
     */
    public static String getGenusName(String speciesName) {
        // This current implementation only returns the first token in the string.
        StringTokenizer st = new StringTokenizer(speciesName);
        if (st.countTokens() > 0) {
            return st.nextToken();
        } else {
            return "";
        }
    }

    /**
     * Extracts the (specific) species name of the given (full) species name. If
     * a species name is not found, then an empty string is returned.
     * 
     * @param fullSpeciesName
     *            The full species name from which to extract the species
     * @return The (specific) name of the species
     */
    public static String getSpeciesName(String fullSpeciesName) {
        // This current implementation only returns the second token in the string.
        StringTokenizer st = new StringTokenizer(fullSpeciesName);
        if (st.countTokens() > 1) {
            st.nextToken();
            return st.nextToken();
        } else {
            return "";
        }
    }

    /**
     * Date format used for default GDB filenames.
     */
    private static final DateFormat GDB_DF = new SimpleDateFormat("yyyyMMdd");
}
