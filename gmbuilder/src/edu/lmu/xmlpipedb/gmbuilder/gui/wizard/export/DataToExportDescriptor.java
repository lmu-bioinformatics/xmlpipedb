/********************************************************
 * Filename: ExportPanel2Descriptor.java
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

import com.nexes.wizard.WizardPanelDescriptor;

/**
 * @author Joey J. Barrett
 * Class: ExportPanel2Descriptor
 */
public class DataToExportDescriptor extends WizardPanelDescriptor {

    public static final String IDENTIFIER = "DATA_TO_EXPORT_PANEL";

    private DataToExportPanel panel2;

    /**
     * Constructor
     */
    public DataToExportDescriptor() {
        panel2 = new DataToExportPanel(this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel2);
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getNextPanelDescriptor()
     */
    public Object getNextPanelDescriptor() {
        return ExportInProgressDescriptor.IDENTIFIER;
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getBackPanelDescriptor()
     */
    public Object getBackPanelDescriptor() {
        return BasicExportInformationDescriptor.IDENTIFIER;
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToDisplayPanel()
     */
    public void aboutToDisplayPanel() {
    	panel2.displayAvailableInformation();
        setNextButton();
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToHidePanel()
     */
    public void aboutToHidePanel() {
        panel2.submitInformationEntered();
     }

    /**
     * Sets the next button accordingly.
     */
    protected void setNextButton() {
        getWizard().setNextFinishButtonEnabled(panel2.isAllInformationEntered());
    }

}
