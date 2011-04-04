/********************************************************
 * Filename: ExportPanel1Descriptor.java
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

import java.text.ParseException;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nexes.wizard.WizardPanelDescriptor;

/**
 * @author Joey J. Barrett
 * Class: ExportPanel1Descriptor
 */
public class ExportPanel1Descriptor extends WizardPanelDescriptor implements DocumentListener {

    public static final String IDENTIFIER = "EXPORT_PANEL1";

    private ExportPanel1 panel1;

    /**
     * Constructor
     */
    public ExportPanel1Descriptor() {
        panel1 = new ExportPanel1();
        panel1.addDocumentListener(this);

        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel1);

    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getNextPanelDescriptor()
     */
    public Object getNextPanelDescriptor() {
        return ExportPanel2Descriptor.IDENTIFIER;
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
        setNextButton();
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToHidePanel()
     */
    public void aboutToHidePanel() {
       try {
		panel1.submitInformationEntered();
       } catch (ParseException ignored) {}
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
	public void changedUpdate(DocumentEvent arg0) {}
}
