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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.nexes.wizard.WizardPanelDescriptor;

/**
 * @author Joey J. Barrett
 */
public class ExportPanel1Descriptor extends WizardPanelDescriptor implements DocumentListener, ListSelectionListener {

    public static final String IDENTIFIER = "EXPORT_PANEL1";

    private ExportPanel1 panel1;

    /**
     * Constructor
     */
    public ExportPanel1Descriptor() {
        panel1 = new ExportPanel1();
        panel1.getOwnerTextField().getDocument().addDocumentListener(this);
        panel1.getSpeciesCheckList().addListSelectionListener(this);

        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nexes.wizard.WizardPanelDescriptor#getNextPanelDescriptor()
     */
    public Object getNextPanelDescriptor() {
        return ExportPanel2Descriptor.IDENTIFIER;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nexes.wizard.WizardPanelDescriptor#getBackPanelDescriptor()
     */
    public Object getBackPanelDescriptor() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToDisplayPanel()
     */
    public void aboutToDisplayPanel() {
        setNextButton();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToHidePanel()
     */
    public void aboutToHidePanel() {
        try {
            panel1.submitInformationEntered();
        } catch(ParseException ignored) {
        }
    }

    /**
     * Sets the next button accordingly.
     */
    private void setNextButton() {
        getWizard().setNextFinishButtonEnabled(panel1.isAllInformationEntered());
    }

    /**
     * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        setNextButton();
    }

    /**
     * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        setNextButton();
    }

    /**
     * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
    }

    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        setNextButton();
    }

}
