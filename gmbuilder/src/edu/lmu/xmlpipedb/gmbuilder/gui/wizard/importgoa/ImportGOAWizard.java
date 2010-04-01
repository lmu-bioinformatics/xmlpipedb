/**
 * ImportGOAWizard
 * Based on ExportWizard by Joey J. Barrett
 * Edited by Don Murphy
 */

package edu.lmu.xmlpipedb.gmbuilder.gui.wizard.importgoa;

import java.awt.Cursor;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nexes.wizard.Wizard;
import com.nexes.wizard.WizardPanelDescriptor;

/**
 * @author Joey J. Barrett
 * Class: ImportGOAWizard
 */
public class ImportGOAWizard {
	/**
	 * Constuctor.
	 */
	public ImportGOAWizard(JFrame owner) {
		Wizard wizard = new Wizard(owner);
		wizard.getDialog().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		wizard.getDialog().setTitle("Import GOA Wizard Dialog");

		WizardPanelDescriptor descriptor1 = new ImportGOAPanel1Descriptor();
		wizard.registerWizardPanel(ImportGOAPanel1Descriptor.IDENTIFIER, descriptor1);

		descriptor2 = new ImportGOAPanel2Descriptor();
		wizard.registerWizardPanel(ImportGOAPanel2Descriptor.IDENTIFIER, descriptor2);

		wizard.setCurrentPanel(ImportGOAPanel1Descriptor.IDENTIFIER);

		wizard.getDialog().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        wizard.getDialog().setLocationRelativeTo(owner);

		//0=Finish,1=Cancel,2=Error
		wizard.showModalDialog();
	}

	/**
	 * Provides a loop-through to the progress bar on
	 * ExportPanel5.  Sets the progress of the progress bar.
	 *
     * @param progressValue must be an integer between 0-100.
     * @param progressText some description string.
	 */
	public static void updateExportProgress(int progressValue, String progressText) {
        _Log.info(progressText);
		ImportGOAPanel2.setProgress(progressValue, progressText);
        // Why was this here?
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException ignored) {}
	}

    /**
     * Log object for this class.
     */
    private static final Log _Log = LogFactory.getLog(ImportGOAWizard.class);

    private WizardPanelDescriptor descriptor2;
}
