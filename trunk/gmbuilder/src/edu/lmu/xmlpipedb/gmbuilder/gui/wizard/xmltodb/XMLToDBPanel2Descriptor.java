package gui.wizard.xmltodb;

import gui.wizard.WizardPanelDescriptor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class XMLToDBPanel2Descriptor extends WizardPanelDescriptor implements ActionListener {
    
    public static final String IDENTIFIER = "CONNECTOR_CHOOSE_PANEL";
    
    XMLToDBPanel2 panel2;
    
    public XMLToDBPanel2Descriptor() {
        
        panel2 = new XMLToDBPanel2();
        panel2.addCheckBoxActionListener(this);
        
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel2);
        
    }
    
    public Object getNextPanelDescriptor() {
        return XMLToDBPanel3Descriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
        return XMLToDBPanel1Descriptor.IDENTIFIER;
    }
    
    
    public void aboutToDisplayPanel() {
        setNextButtonAccordingToCheckBox();
    }    

    public void actionPerformed(ActionEvent e) {
        setNextButtonAccordingToCheckBox();
    }
            
    
    private void setNextButtonAccordingToCheckBox() {
         if (panel2.isCheckBoxSelected())
            getWizard().setNextFinishButtonEnabled(true);
         else
            getWizard().setNextFinishButtonEnabled(false);           
    
    }
}
