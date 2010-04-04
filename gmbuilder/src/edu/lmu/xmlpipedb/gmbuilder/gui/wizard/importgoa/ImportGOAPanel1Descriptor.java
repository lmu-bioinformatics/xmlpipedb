/********************************************************
 * Filename: ImportGOAPanel1Descriptor.java
 * Author: Joey J. Barrett
 * Adaptation: Don Murphy
 * Program: gmBuilder
 * Description: A subclass of WizardPanelDescriptor which
 * outlines provides the necessary requirements for
 * creating a wizard panel.
 *
 * Revision History:
 * 20100402: Initial Revision, based upon ExportPanel2Descriptor.java.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.gui.wizard.importgoa;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nexes.wizard.WizardPanelDescriptor;

/**
 * @author Joey J. Barrett
 * Class: ExportPanel2Descriptor
 */
public class ImportGOAPanel1Descriptor extends WizardPanelDescriptor implements DocumentListener {

    public static final String IDENTIFIER = "EXPORT_PANEL2";

    private ImportGOAPanel1 panel1;

    /**
     * Constructor
     */
    public ImportGOAPanel1Descriptor() {
        panel1 = new ImportGOAPanel1();
        panel1.addDocumentListener(this);

        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel1);
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getNextPanelDescriptor()
     */
    public Object getNextPanelDescriptor() {
        return ImportGOAPanel2Descriptor.IDENTIFIER;
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
    	panel1.displayAvailableInformation();
        setNextButton();
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToHidePanel()
     */
    public void aboutToHidePanel() {
        panel1.submitInformationEntered();
     }

    /**
     * Sets the next button accordingly.
     */
    private void setNextButton() {

        if (panel1.isAllInformationEntered())
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
