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

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nexes.wizard.WizardPanelDescriptor;

/**
 * @author Joey J. Barrett
 * Class: ExportPanel2Descriptor
 */
public class ExportPanel2Descriptor extends WizardPanelDescriptor implements DocumentListener {

    public static final String IDENTIFIER = "EXPORT_PANEL2";

    private ExportPanel2 panel2;

    /**
     * Constructor
     */
    public ExportPanel2Descriptor() {
        panel2 = new ExportPanel2();
        panel2.addDocumentListener(this);

        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel2);
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getNextPanelDescriptor()
     */
    public Object getNextPanelDescriptor() {
        return ExportPanel3Descriptor.IDENTIFIER;
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getBackPanelDescriptor()
     */
    public Object getBackPanelDescriptor() {
        return ExportPanel1Descriptor.IDENTIFIER;
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
    private void setNextButton() {

        if (panel2.isAllInformationEntered())
           getWizard().setNextFinishButtonEnabled(true);
        else
           getWizard().setNextFinishButtonEnabled(false);
    }

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent arg0) {
		setNextButton();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	public void removeUpdate(DocumentEvent arg0) {
		setNextButton();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent arg0) {
		setNextButton();
	}
}
