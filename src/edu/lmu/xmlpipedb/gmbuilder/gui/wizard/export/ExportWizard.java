/********************************************************
 * Filename: ExportWizard.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: Creates the export wizard and adds the 
 * panels.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export;

import java.awt.Cursor;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nexes.wizard.Wizard;
import com.nexes.wizard.WizardPanelDescriptor;

/**
 * @author Joey J. Barrett
 * Class: ExportWizard
 */
public class ExportWizard {
	/**
	 * Constuctor.
	 */
	public ExportWizard(JFrame owner) {
		Wizard wizard = new Wizard(owner);
		wizard.getDialog().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		wizard.getDialog().setTitle("Export Wizard Dialog");
		
		WizardPanelDescriptor descriptor1 = new ExportPanel1Descriptor();
		wizard.registerWizardPanel(ExportPanel1Descriptor.IDENTIFIER, descriptor1);

		WizardPanelDescriptor descriptor2 = new ExportPanel2Descriptor();
		wizard.registerWizardPanel(ExportPanel2Descriptor.IDENTIFIER, descriptor2);

		WizardPanelDescriptor descriptor3 = new ExportPanel3Descriptor();
		wizard.registerWizardPanel(ExportPanel3Descriptor.IDENTIFIER, descriptor3);
		
		WizardPanelDescriptor descriptor4 = new ExportPanel4Descriptor();
		wizard.registerWizardPanel(ExportPanel4Descriptor.IDENTIFIER, descriptor4);
    
		descriptor5 = new ExportPanel5Descriptor();
		wizard.registerWizardPanel(ExportPanel5Descriptor.IDENTIFIER, descriptor5);
		
		wizard.setCurrentPanel(ExportPanel1Descriptor.IDENTIFIER);
    
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
		ExportPanel5.setProgress(progressValue, progressText);
        // Why was this here?
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException ignored) {}
	}

    /**
     * Log object for this class.
     */
    private static final Log _Log = LogFactory.getLog(ExportWizard.class);    
    
    private WizardPanelDescriptor descriptor5;
}
