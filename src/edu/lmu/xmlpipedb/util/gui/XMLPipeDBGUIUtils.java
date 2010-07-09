package edu.lmu.xmlpipedb.util.gui;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;

import javax.swing.ProgressMonitorInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.util.engines.ImportEngine;

import shag.dialog.ModalDialog;

/**
 * XMLPipeDBGUIUtils provides GUI helper methods that "wrap" various "engine"
 * activities within a GUI layer.
 * 
 * @author   dondi
 */
public class XMLPipeDBGUIUtils {

    /**
     * Wraps an ImportEngine import operation "within" a progress bar.  Returns
     * whether or not the import was successful.
     */
    public static boolean performImportWithProgressBar(ImportEngine importEngine, File xmlFile, Component parent) {
        try {
            // Does the progress monitor popup.
            long startTime = System.currentTimeMillis();
            _Log.info("Import Started at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(startTime));
            InputStream in = new BufferedInputStream(new ProgressMonitorInputStream(parent, "Reading " + xmlFile, new FileInputStream(xmlFile)));
            importEngine.loadToDB(in);
            long endTime = System.currentTimeMillis();
            _Log.info("Import Finished at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(endTime));

            // Notify user when import is complete.
            ModalDialog.showInformationDialog("Import Complete", "The import of " + xmlFile + " has completed successfully.\n\nThe import took " + ((endTime - startTime) / 1000 / 60) + " minutes.");
            return true;
        } catch(Exception e) {
            _Log.error(e);
            ModalDialog.showErrorDialog("Unexpected Error", "An unexpected error with the following message has occurred:\n\n" + e.getMessage() + CHECK_LOG_MESSAGE);
        } catch(OutOfMemoryError e) {
            _Log.error(e);
            ModalDialog.showErrorDialog("Out of Memory", "The import process ran out of memory." + CHECK_LOG_MESSAGE);
        }
        
        // If we get here, the import did not succeed.
        return false;
    }
    
    /**
     * The GUI utilities log.
     */
    private static final Log _Log = LogFactory.getLog(XMLPipeDBGUIUtils.class);

    /**
     * Standard message string for examining the log.  TODO Should be externalized.
     */
    private static final String CHECK_LOG_MESSAGE = "\n\nPlease examine the application log for further details.";

}
