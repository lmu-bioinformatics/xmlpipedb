package edu.lmu.xmlpipedb.util.gui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.util.engines.ImportEngine;
import edu.lmu.xmlpipedb.util.resources.AppResources;

import shag.LayoutConstants;
import shag.dialog.ActionCommand;
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
    public static boolean performImportWithProgressBar(ImportEngine importEngine, File xmlFile) {
        // Perform the import task.
        Importer importer = new Importer(importEngine, xmlFile);
        performWithProgressBar(importer,
            AppResources.messageString("import.title"),
            AppResources.messageString("import.message")
                .replaceAll("\\$FILE", xmlFile.toString()));
        
        // When we get here, the Importer will be finished, and we can return
        // its results.
        try {
            return importer.get().booleanValue();
        } catch(InterruptedException iexc) {
            _Log.error("Import interrupted.", iexc);
            return false;
        } catch(ExecutionException eexc) {
            _Log.error("Import execution problem.", eexc);
            return false;
        }
    }

    /**
     * Helper method for bracketing any lengthy task with a progress dialog.
     */
    public static void performWithProgressBar(@SuppressWarnings("rawtypes") SwingWorker swingWorker,
      String progressTitle, String progressMessage) {
        // Set up the blocking UI.
        ModalDialog dialog = ModalDialog.createCustomDialog(progressTitle, new ActionCommand[0], false);

        // Build the dialog panel.
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JPanel messagePanel = new JPanel(new BorderLayout(0, LayoutConstants.SPACE));
        messagePanel.add(progressBar, BorderLayout.NORTH);
        messagePanel.add(new JLabel(progressMessage), BorderLayout.SOUTH);
        dialog.setComponent(messagePanel);
        swingWorker.addPropertyChangeListener(new SwingWorkerDialogWaiter(dialog));
        swingWorker.execute();

        // The dialog will be visible until GO processing is done.
        dialog.setVisible(true);
    }

    /**
     * Listener that monitors SwingWorker progress.
     */
    public static class SwingWorkerDialogWaiter implements PropertyChangeListener {

        private ModalDialog dialog;
    
        public SwingWorkerDialogWaiter(ModalDialog dialog) {
            this.dialog = dialog;
        }
    
        /**
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent event) {
            if ("state".equals(event.getPropertyName())
              && SwingWorker.StateValue.DONE == event.getNewValue()) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        }
    }

    /**
     * SwingWorker that performs the actual import.
     */
    private static class Importer extends SwingWorker<Boolean, Object> {

        private ImportEngine importEngine;
        private File xmlFile;
        private long startTime, endTime;
        private Exception exc;
        private OutOfMemoryError oome;

        public Importer(ImportEngine importEngine, File xmlFile) {
            this.importEngine = importEngine;
            this.xmlFile = xmlFile;
            exc = null;
            oome = null;
        }
        
        /**
         * @see javax.swing.SwingWorker#doInBackground()
         */
        @Override
        public Boolean doInBackground() {
            try {
                startTime = System.currentTimeMillis();
                _Log.info("Import Started at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(startTime));
                InputStream in = new BufferedInputStream(new FileInputStream(xmlFile));
                importEngine.loadToDB(in);
                endTime = System.currentTimeMillis();
                _Log.info("Import Finished at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(endTime));
                return true;
            } catch(Exception exc) {
                this.exc = exc;
                _Log.error(exc);
                return false;
            } catch(OutOfMemoryError oome) {
                this.oome = oome;
                _Log.error(oome);
                return false;
            }
        }

        @Override
        protected void done() {
            if ((exc == null) && (oome == null)) {
                // Notify user when import is complete.
                ModalDialog.showInformationDialog(AppResources.messageString("import.complete.title"),
                    AppResources.messageString("import.complete.message")
                        .replaceAll("\\$FILE", xmlFile.toString())
                        .replaceAll("\\$MINUTES", String.format("%.2f", (endTime - startTime) / 1000.0 / 60.0)));
            } else if (exc != null) {
                ModalDialog.showErrorDialog(AppResources.messageString("error.import.unknown.title"),
                    AppResources.messageString("error.import.unknown.prefix") +
                    exc.getMessage() + AppResources.messageString("error.import.footer"));
            } else if (oome != null) {
                ModalDialog.showErrorDialog(AppResources.messageString("error.import.outofmemory.title"),
                    AppResources.messageString("error.import.outofmemory.prefix") +
                    AppResources.messageString("error.import.footer"));
            }
        }
    }

    /**
     * The GUI utilities log.
     */
    private static final Log _Log = LogFactory.getLog(XMLPipeDBGUIUtils.class);

}
