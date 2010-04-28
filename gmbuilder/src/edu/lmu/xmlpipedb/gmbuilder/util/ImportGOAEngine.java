package edu.lmu.xmlpipedb.gmbuilder.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.text.DateFormat;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go.ExportGoData;
import edu.lmu.xmlpipedb.gmbuilder.gui.util.ImportGOAPanel;
//import edu.lmu.xmlpipedb.util.engines.HibernateException;
import edu.lmu.xmlpipedb.util.gui.UtilityDialogue;
import edu.lmu.xmlpipedb.util.resources.AppResources;

public class ImportGOAEngine {

	/**
	 * Creates a new instance of ImportGOAENgine
	 * @param hibernateCongfiguration A hibernate configuration as the configuration to import
     * the goa to the database
	 * @throws IOException for all IO exceptions
	 * @throws HibernateException for all hibernate exceptions
	 */
	public ImportGOAEngine(Configuration hibernateConfiguration) throws IOException, HibernateException {
		_hibernateConfiguration = hibernateConfiguration;
		_sessionFactory = hibernateConfiguration.buildSessionFactory();
	}

	public void importToSQL(File goaFile) throws Exception {
		_goaFile = goaFile;
		Session session = null;
        PreparedStatement query = null;
        String insert = "INSERT INTO goa VALUES "
        	+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
    		// Establishes connection to PostgreSQL database
            session = _sessionFactory.openSession();
            Connection conn = session.connection();

            // Creates BufferedReader for selected GOA file
        	BufferedReader in = new BufferedReader(new FileReader(_goaFile));

        	_Log.info("Import Started at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(System.currentTimeMillis()));
            String inputLine;
            String[] goaColumns = null;
            String[] goaColumnsTemp = null;
            Date tempdate = new Date(0000-00-00);
            int primarykeyid = 1;
            int linesRead = 0;
            int totalLines = getNumberOfLinesInGOA();
            int percentCrossedMultiplier = 1;
            double percentRead = 0.0;


            query = conn.prepareStatement(insert);
            while ((inputLine = in.readLine()) != null) {

            	// Prevents tag at beginning of GAF 2.0 from being imported
            	if (!(inputLine.startsWith("!"))) {

            		// Reports line imports at a set interval of GOA imported
            		if (primarykeyid % LINE_IMPORT_REPORT_INTERVAL == 0) {
            			_Log.info("Importing Line # " + primarykeyid + "...");
            		}


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
            					query.setDate(k+2, tempdate.valueOf(goaColumns[k].substring(0,4) + "-" + goaColumns[k].substring(4,6) + "-" + goaColumns[k].substring(6,8)));
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
            		_Log.info((PERCENT_LINES_READ * (double)percentCrossedMultiplier) + "% of GOA read...");
            		_Log.debug("Actual percent imported: " + percentRead +"%");
            		percentCrossedMultiplier++;
            	}

            }
            conn.commit();
            _Log.info("Imported " + primarykeyid + " lines from GOA file.");
            _Log.info("Import Finished at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(System.currentTimeMillis()));
            _success = true;
            // notify user when import is complete
            //JOptionPane.showMessageDialog(this, "Import Complete: " + _goaFile, "Import Complete", JOptionPane.INFORMATION_MESSAGE);
        /*} catch(IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An I/O exception occured while trying to read the file " + _goaFile, "Error", JOptionPane.ERROR_MESSAGE);
        } catch(SQLException sqle) {
            JOptionPane.showMessageDialog(this, sqle.getMessage());
            //Need to clean up connection after SQL exceptions*/
        /*} catch(Exception e) {*/
            /*e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch(OutOfMemoryError e) {
        	_Log.info("Error occurred at : " + DateFormat.getTimeInstance(DateFormat.LONG).format(System.currentTimeMillis()));
            e.printStackTrace();
            _Log.info("SystemOutOfMemoryError. Message = " + e.getMessage() + "LocalizedMessage = " + e.getLocalizedMessage());*/
        }
        finally {
            try { query.close(); } catch(Exception exc) { }
            try { session.close(); } catch(Exception exc) { }
        }
	}


    private int getNumberOfLinesInGOA(){
    	int lineCounter = 0;
    	String inputLine = null;

    	try {
    		// Creates BufferedReader for selected GOA file
            BufferedReader in = new BufferedReader(new FileReader(_goaFile));

            while ((inputLine = in.readLine()) != null) {
            	lineCounter++;
            }

            return lineCounter;

        } catch(IOException e) {
        	e.printStackTrace();
        	_Log.debug("An I/O exception occured while trying to read the file " + _goaFile + " for line counting.");
        	//JOptionPane.showMessageDialog(this, "An I/O exception occured while trying to read the file " + _goaFile, "Error", JOptionPane.ERROR_MESSAGE);
        } catch(Exception e) {
        	e.printStackTrace();
        	_Log.debug("Error during line count");
            //JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return lineCounter;
    }

    private static final Log _Log = LogFactory.getLog(ImportGOAEngine.class);

    private static final int GAF10NUMOFCOLUMNS = 15;
    private static final int GAF20NUMOFCOLUMNS = 17;
    private static final int DATECOLUMN = 13;
    private static final int LINE_IMPORT_REPORT_INTERVAL = 5000;
    private static final double PERCENT_LINES_READ = 10.0;

	private SessionFactory _sessionFactory;
	private boolean _success;
    private Configuration _hibernateConfiguration;
    private File _goaFile;
}