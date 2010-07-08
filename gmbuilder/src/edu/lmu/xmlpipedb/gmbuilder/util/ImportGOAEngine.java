/**
 * ImportGOAEngine.java
 * Purpose: Imports the contents of a GOA tab-delimited file to a table in a PostgreSQL database
 * Author: Don Murphy
 * Description: The engine is used with ImportGOAPanel to import a GOA. A BufferedReader reads the
 *     GOA in line by line, parsing each line by the tabs separating the "columns" of the file into
 *     a String array. After any necessary format adjustments are done based upon the format
 *     version of the GOA file, the contents of the string array set as parameters of a pre-written
 *     SQL insert PreparedStatement. After one line has been completely added to the PreparedStatement,
 *     it is queried to insert the data as a row in the goa table of the PostgreSQL database. This
 *     process is repeated until the end of the GOA file is reached.
 */

package edu.lmu.xmlpipedb.gmbuilder.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ImportGOAEngine {

	/**
	 * Creates a new instance of ImportGOAEngine
	 * @param hibernateCongfiguration A hibernate configuration as the configuration to import
     * the goa to the database
	 * @throws IOException for all IO exceptions
	 * @throws HibernateException for all hibernate exceptions
	 */
	public ImportGOAEngine(Configuration hibernateConfiguration) throws HibernateException {
		_sessionFactory = hibernateConfiguration.buildSessionFactory();

        // Test the configuration by trying a transaction.
        Session session = _sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        tx.rollback();
        session.close();
	}

	/**
	 * Imports a GOA File based upon the hibernateConfiguration's database configuration
	 * @param goaFile The GOA file to parsed and upload to the PostgreSQL database
	 * @throws Exception
	 */
	public void importToSQL(File goaFile) throws IOException, SQLException {
		Session session = null;
        PreparedStatement query = null;
        String insert = "INSERT INTO goa VALUES "
        	+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
    		// Establishes connection to PostgreSQL database
            session = _sessionFactory.openSession();
            Connection conn = session.connection();

            // Creates BufferedReader for selected GOA file
        	BufferedReader in = new BufferedReader(new FileReader(goaFile));

        	_Log.warn("Import Started at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(System.currentTimeMillis()));
            String inputLine;
            String[] goaColumns = null;
            String[] goaColumnsTemp = null;
            int primarykeyid = 1;
            int linesRead = 0;
            int totalLines = getNumberOfLinesInGOA(goaFile);
            int percentCrossedMultiplier = 1;
            double percentRead = 0.0;
            query = conn.prepareStatement(insert);
            while ((inputLine = in.readLine()) != null) {

            	// Prevents tag at beginning of GAF 2.0 from being imported
            	if (!inputLine.startsWith("!")) {
            		// Splits line into an array of strings based upon tab-delimited format
            		goaColumns = inputLine.split("\t");


            		if (goaColumns.length == GAF10NUMOFCOLUMNS || goaColumns.length == GAF20NUMOFCOLUMNS) {

            			// Detects if file is in GAF 1.0 and converts the table to GAF 2.0
            			// (see http://www.geneontology.org/GO.format.gaf-2_0.shtml)
            			if (goaColumns.length == GAF10NUMOFCOLUMNS) {
            				goaColumnsTemp = new String[GAF20NUMOFCOLUMNS];
            				System.arraycopy(goaColumns, 0, goaColumnsTemp, 0, GAF10NUMOFCOLUMNS);
            				goaColumnsTemp[15] = "";
            				goaColumnsTemp[16] = "";
            				goaColumns = new String[GAF20NUMOFCOLUMNS];
            				System.arraycopy(goaColumnsTemp, 0, goaColumns, 0, GAF20NUMOFCOLUMNS);
            				goaColumnsTemp = null;
            			}

            			// Inserts parameters into query to be placed in IN parameter placeholders (?) when executed
            			query.setInt(1, primarykeyid);
            			for (int k = 0; k < GAF20NUMOFCOLUMNS; k++){
            				if (k == DATECOLUMN) {
            					query.setDate(k+2, Date.valueOf(goaColumns[k].substring(0,4) + "-" + goaColumns[k].substring(4,6) + "-" + goaColumns[k].substring(6,8)));
            				} else {
            					query.setString(k+2, goaColumns[k]);
            				}
            			}

            			// Executes insert statement
            			query.executeUpdate();
            			goaColumns = null;
            			primarykeyid++;
            		} else {
            			_Log.debug("Line not imported, improper number of columns");
            		}
            	} else {
            		_Log.debug("Line not imported, identified as a tag: " + inputLine);
            	}

            	linesRead++;
            	percentRead = (100.0 * ((double)linesRead / (double)totalLines));
            	if (percentRead >= (PERCENT_LINES_READ * (double)percentCrossedMultiplier)) {
            		_Log.warn((PERCENT_LINES_READ * (double)percentCrossedMultiplier) + "% of GOA read...");
            		_Log.info("Actual percent imported: " + percentRead +"%");
            		percentCrossedMultiplier++;
            	}

            }
            conn.commit();
            _Log.warn("Imported " + (primarykeyid - 1) + " lines from GOA file.");
            _Log.warn("Import Finished at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(System.currentTimeMillis()));
        } finally {
            try { query.close(); } catch(Exception exc) { }
            try { session.close(); } catch(Exception exc) { }
        }
	}

	/**
	 * Returns the number of lines in the GOA file being imported; used for progress indications
	 * @return The number of lines in the GOA file
	 */
	public int getNumberOfLinesInGOA(File goaFile) throws IOException {
	    int lineCounter = 0;

	    // Creates BufferedReader for selected GOA file
	    BufferedReader in = new BufferedReader(new FileReader(goaFile));

	    while (in.readLine() != null) {
	        lineCounter++;
	    }

	    return lineCounter;
	}

    private static final Log _Log = LogFactory.getLog(ImportGOAEngine.class);

    private static final int GAF10NUMOFCOLUMNS = 15;
    private static final int GAF20NUMOFCOLUMNS = 17;
    private static final int DATECOLUMN = 13;
    private static final double PERCENT_LINES_READ = 20.0;

	private SessionFactory _sessionFactory;

}