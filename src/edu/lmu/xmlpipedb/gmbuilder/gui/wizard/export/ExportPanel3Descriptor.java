/********************************************************
 * Filename: ExportPanel3Descriptor.java
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
 * Class: ExportPanel3Descriptor
 */
public class ExportPanel3Descriptor extends WizardPanelDescriptor {

    public static final String IDENTIFIER = "EXPORT_PANEL3";

    private ExportPanel3 panel3;

    /**
     * Constructor.
     */
    public ExportPanel3Descriptor() {

    	panel3 = new ExportPanel3();

        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel3);
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getNextPanelDescriptor()
     */
    public Object getNextPanelDescriptor() {
        return ExportPanel4Descriptor.IDENTIFIER;
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getBackPanelDescriptor()
     */
    public Object getBackPanelDescriptor() {
        return ExportPanel2Descriptor.IDENTIFIER;
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToDisplayPanel()
     */
    public void aboutToDisplayPanel() {
    	panel3.displayAvailableInformation();
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToHidePanel()
     */
    public void aboutToHidePanel() {
        panel3.submitInformationEntered();
     }
}
