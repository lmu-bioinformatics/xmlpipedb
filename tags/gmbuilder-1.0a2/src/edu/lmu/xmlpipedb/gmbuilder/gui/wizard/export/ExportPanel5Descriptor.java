/********************************************************
 * Filename: ExportPanel5Description.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: A subclass of WizardPanelDescriptor which
 * outlines provides the nessecary requirements for 
 * creating a wizard panel.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export;

import java.awt.Cursor;

import com.nexes.wizard.WizardPanelDescriptor;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;


/**
 * @author Joey J. Barrett
 * Class: ExportPanel5Descriptor
 */
public class ExportPanel5Descriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "EXPORT_PANEL5";
    
    ExportPanel5 panel5;
    
    /**
     * Constructor.
     */
    public ExportPanel5Descriptor() {
        
        panel5 = new ExportPanel5();
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel5);
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getNextPanelDescriptor()
     */
    public Object getNextPanelDescriptor() {
        return FINISH;
    }
    
    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getBackPanelDescriptor()
     */
    public Object getBackPanelDescriptor() {
        return null;
    }
    
    
    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToDisplayPanel()
     */
    public void aboutToDisplayPanel() {
        
        ExportPanel5.setProgress(0, "Preparing for export...");
        

        getWizard().setNextFinishButtonEnabled(false);
        getWizard().setBackButtonEnabled(false);
        
    }
    
    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#displayingPanel()
     */
    public void displayingPanel() {
    	
            Thread t = new Thread() {

            public void run() {

                try {
                	getWizard().getDialog().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    ExportToGenMAPP.export();     
                    ExportPanel5.setProgress(100, "Export completed successfully.");
                    getWizard().setNextFinishButtonEnabled(true);                   
  
                } catch (Exception e) {
                	//ExportPanel5.setProgress(0, "An Error Has Occurred.");
                	e.printStackTrace();

				} finally {
					getWizard().getDialog().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}

            }
        };

        t.start();
    }          
}
