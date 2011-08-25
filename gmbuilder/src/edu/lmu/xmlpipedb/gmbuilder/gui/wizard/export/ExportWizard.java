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

import com.nexes.wizard.Wizard;

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
		
		wizard.registerWizardPanel(BasicExportInformationDescriptor.IDENTIFIER,
		        new BasicExportInformationDescriptor());

		wizard.registerWizardPanel(DataToExportDescriptor.IDENTIFIER,
		        new DataToExportDescriptor());

		wizard.registerWizardPanel(ExportInProgressDescriptor.IDENTIFIER,
		        new ExportInProgressDescriptor());
		
		wizard.setCurrentPanel(BasicExportInformationDescriptor.IDENTIFIER);
    
		wizard.getDialog().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        wizard.getDialog().setLocationRelativeTo(owner);
		
		//0=Finish,1=Cancel,2=Error
		wizard.showModalDialog();        
	}
}
