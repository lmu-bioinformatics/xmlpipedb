package gui.wizard.xmltodb;

import gui.wizard.WizardPanelDescriptor;


public class XMLToDBPanel1Descriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "INTRODUCTION_PANEL";
    
    public XMLToDBPanel1Descriptor() {
        super(IDENTIFIER, new XMLToDBPanel1());
    }
    
    public Object getNextPanelDescriptor() {
        return XMLToDBPanel2Descriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
        return null;
    }  
    
}
