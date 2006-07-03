/********************************************************
 * Filename: ExportPanel1.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: The first panel displayed in the export
 * wizard.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentListener;

import shag.LayoutConstants;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.DisplayOrder;
import edu.lmu.xmlpipedb.gmbuilder.gui.util.SpringUtilities;

/**
 * @author Joey J. Barrett
 * Class: ExportPanel1
 */
public class ExportPanel1 extends JPanel {
 
	private static final long serialVersionUID = -4815677619866768960L;
	private JComboBox profileComboBox;
    private JTextArea profileDescriptionTextArea;
    private JTextField ownerTextField;
    private JFormattedTextField versionFormattedTextField;
    private JTextField modSystemTextField;
    private JComboBox speciesComboBox;
    private JTextArea speciesDescriptionTextArea;
    private JTextField speciesCustomizeTextField; 
    private JFormattedTextField modifyFormattedTextField;
    private JComboBox displayOrderComboBox;
    private JTextArea notesTextArea;
    
    /**
     * Constructor.
     */
    protected ExportPanel1() {
        
        super();  
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.gray);
        
        
        JLabel textLabel = new JLabel();
        textLabel.setBackground(Color.gray);
        textLabel.setFont(new Font("MS Sans Serif", Font.BOLD, 14));
        textLabel.setText("Export to GenMAPP - Basic Setup");
        textLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        textLabel.setOpaque(true);
        
        
        titlePanel.add(textLabel, BorderLayout.CENTER);
        titlePanel.add(new JSeparator(), BorderLayout.SOUTH);
        

        JPanel contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        
        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.WEST);
        
        init();
    }
    
    /**
     * Init is called from constructor.  Does basic
     * pre-setup work.
     */
    private void init() {
        for(DatabaseProfile profile : ExportToGenMAPP.getAvailableDatabaseProfiles()) {
        	profileComboBox.addItem(profile);
        }     
    }
    
    /**
     * Sets up the content of the panel.
     * @return
     */
    private JPanel getContentPanel() {
        
        //Profile 	| JComboBox 	| availableDatabaseProfiles	| JLabel	| Description
        profileComboBox = new JComboBox();
        profileComboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				databaseProfileSelected(profileComboBox.getSelectedItem());
			}
        });
        
        profileDescriptionTextArea = new JTextArea(3,15);
        profileDescriptionTextArea.setLineWrap(true);
        profileDescriptionTextArea.setWrapStyleWord(true);
        profileDescriptionTextArea.setEditable(false);
        profileDescriptionTextArea.setBackground(new Color(238, 238, 238));
        
        
        //Owner		| JTextField	| empty     
        ownerTextField = new JTextField(10);
        
        //Version 	| Calendar		| set to today
        versionFormattedTextField = new JFormattedTextField(new SimpleDateFormat("MM/dd/yyyy"));
        versionFormattedTextField.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        
        //MODSystem	| JTextfield 	| set to database profile
        modSystemTextField = new JTextField(10);
        modSystemTextField.setEditable(false);
        
        //Species 	| JComboBox		| speciesFound				| JLabel	| Description
        //		  					| JTextField	| customizable name
        speciesComboBox = new JComboBox();
        speciesComboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				speciesProfileSelected(speciesComboBox.getSelectedItem());
			}
        });
        speciesDescriptionTextArea = new JTextArea(3,15);
        speciesDescriptionTextArea.setLineWrap(true);
        speciesDescriptionTextArea.setWrapStyleWord(true);
        speciesDescriptionTextArea.setEditable(false);
        speciesDescriptionTextArea.setBackground(new Color(238, 238, 238));
        
        speciesCustomizeTextField = new JTextField(10);
        
        //Modify			| Calendar		| set to today
        modifyFormattedTextField = new JFormattedTextField(new SimpleDateFormat("MM/dd/yyyy"));
        modifyFormattedTextField.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        
        //Display Order	| JComboBox		| getDisplayOrders
        displayOrderComboBox = new JComboBox();
     
        //Notes			| JTextArea		| empty
        notesTextArea = new JTextArea(2, 40);
        notesTextArea.setBorder(new EtchedBorder());
        
        JPanel leftPanel = new JPanel(new SpringLayout());
        leftPanel.add(new JLabel("Profile:"));
        leftPanel.add(profileComboBox);
        leftPanel.add(new JLabel("Owner:"));
        leftPanel.add(ownerTextField);
        leftPanel.add(new JLabel("Version (MM/dd/yyyy):"));
        leftPanel.add(versionFormattedTextField);
        leftPanel.add(new JLabel("MODSystem:"));
        leftPanel.add(modSystemTextField);
        leftPanel.add(new JLabel("Species:"));
        leftPanel.add(speciesComboBox);
        leftPanel.add(new JLabel("Customize Name:"));
        leftPanel.add(speciesCustomizeTextField);
        leftPanel.add(new JLabel("Modify (MM/dd/yyyy):"));
        leftPanel.add(modifyFormattedTextField);
        // We're moving this to panel 4.
//        leftPanel.add(new JLabel("DisplayOrder:"));
//        leftPanel.add(displayOrderComboBox);
       
        SpringUtilities.makeCompactGrid(leftPanel,
                                        leftPanel.getComponentCount()/2, 2, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad
        
        JPanel rightPanel = new JPanel(new GridLayout(0, 1));
        rightPanel.add(profileDescriptionTextArea);
        rightPanel.add(speciesDescriptionTextArea);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets.left = LayoutConstants.SPACE;
        gbc.insets.top = LayoutConstants.SPACE * 2;
        bottomPanel.add(new JLabel("Notes:"), gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        bottomPanel.add(notesTextArea, gbc);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.EAST);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        return contentPanel;
    }

	/**
	 * Adjusts the dynamic content when a database
	 * profile is selected.
	 * @param selectedItem
	 */
	protected void databaseProfileSelected(Object selectedItem) {
		if(selectedItem instanceof DatabaseProfile) {
			DatabaseProfile selectedProfile = (DatabaseProfile) selectedItem;
			ExportToGenMAPP.setDatabaseProfile(selectedProfile);
			profileDescriptionTextArea.setText(selectedProfile.getDescription());
			modSystemTextField.setText(selectedProfile.getMODSystem());
			
			speciesComboBox.removeAllItems();
			
			for(SpeciesProfile speciesProfile : selectedProfile.getSpeciesProfilesFound()) {
				speciesComboBox.addItem(speciesProfile);
			}
			
			displayOrderComboBox.removeAllItems();
			for(DisplayOrder displayOrder : selectedProfile.getAvailableDisplayOrders()) {
				displayOrderComboBox.addItem(displayOrder);
			}
		}
		
	}
	
	/**
	 * Adjusts the dynamic content when a species 
	 * profile is selected.
	 * @param selectedItem
	 */
	protected void speciesProfileSelected(Object selectedItem) {
		if(selectedItem instanceof SpeciesProfile) {
			SpeciesProfile selectedProfile = (SpeciesProfile) selectedItem;
			
			DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();
			databaseProfile.setSelectedSpeciesProfile(selectedProfile);
			ExportToGenMAPP.setDatabaseProfile(databaseProfile);
			
			speciesDescriptionTextArea.setText(selectedProfile.getDescription());
			speciesCustomizeTextField.setText(selectedProfile.getName());
		}	
	}
	
	/**
	 * Checks the required fields for the panel.
	 * @return
	 */
	protected boolean isAllInformationEntered() {
		return ownerTextField.getText().equals("") ? false : true;	
	}
	
	/**
	 * Submits all information collected on the panel.
	 * @throws ParseException
	 */
	protected void submitInformationEntered() throws ParseException {
		DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();
		databaseProfile.setProfileProperties(
				ownerTextField.getText(),
				new SimpleDateFormat("MM/dd/yyyy").parse(versionFormattedTextField.getText()),
				modSystemTextField.getText(),
				speciesCustomizeTextField.getText(),
				new SimpleDateFormat("MM/dd/yyyy").parse(modifyFormattedTextField.getText()),
				(DisplayOrder) displayOrderComboBox.getSelectedItem(),
				notesTextArea.getText());
		ExportToGenMAPP.setDatabaseProfile(databaseProfile);
	}
	
	/**
	 * Adds a document listener.
	 * @param documentListener
	 */
	protected void addDocumentListener(DocumentListener documentListener) {
		ownerTextField.getDocument().addDocumentListener(documentListener);
	}
}
