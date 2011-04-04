/********************************************************
 * Filename: ExportPanel4Descriptor.java
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
 * Class: ExportPanel4Descriptor
 */
public class ExportPanel4Descriptor extends WizardPanelDescriptor {

    public static final String IDENTIFIER = "EXPORT_PANEL4";

    ExportPanel4 panel4;

    /**
     * Constructor.
     */
    public ExportPanel4Descriptor() {

        panel4 = new ExportPanel4();
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel4);

    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getNextPanelDescriptor()
     */
    public Object getNextPanelDescriptor() {
        return ExportPanel5Descriptor.IDENTIFIER;
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getBackPanelDescriptor()
     */
    public Object getBackPanelDescriptor() {
        return ExportPanel3Descriptor.IDENTIFIER;
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToDisplayPanel()
     */
    public void aboutToDisplayPanel() {
        panel4.displayAvailableInformation();
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToHidePanel()
     */
    public void aboutToHidePanel() {
        panel4.submitInformationEntered();
    }

}
