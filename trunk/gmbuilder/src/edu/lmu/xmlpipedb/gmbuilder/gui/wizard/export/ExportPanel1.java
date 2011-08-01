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

// Dondi - Use the Source > Organize Imports command to generate these automatically.
// You can even specify, in the preferences, your preferred order.
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.lmu.xmlpipedb.gmbuilder.GenMAPPBuilder;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.SpeciesProfile;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.UniProtSpeciesProfile;
import edu.lmu.xmlpipedb.gmbuilder.gui.util.SpringUtilities;

import shag.App;
import shag.LayoutConstants;

/**
 * @author Joey J. Barrett Class: ExportPanel1
 */
public class ExportPanel1 extends JPanel {

    private static final long serialVersionUID = -4815677619866768960L;
    private JComboBox profileComboBox;
    private JTextArea profileDescriptionTextArea;
    private JTextField ownerTextField;
    private JFormattedTextField versionFormattedTextField;
    private JTextField modSystemTextField;
    // RB - created private JList to be added
    // to the JPanel
    private JList speciesCheckList;
    private JTextArea speciesDescriptionTextArea;
    private JTextField speciesCustomizeTextField;
    private JFormattedTextField modifyFormattedTextField;
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

        // This initializes all the fields and puts them in a panel for display
        JPanel contentPanel = getContentPanel(); // method from below
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.WEST);

        init();
    }

    /**
     * Init is called from constructor. Does basic pre-setup work.
     * That is: it calls the <i>STATIC</i> ExportToGenMAPP.getAvailableDatabaseProfiles()
     * to add all available DbProfiles to the comboBox. One of these DbProfiles
     * (normally there is only one, I think --JN) will become the basis for 
     * all the export work about to be done.
     */
    // RB - prob rename this to something more descriptive. It is a method to 
    // populate Database ComboBox with all available database profiles of
    // which there is only one - UniProt
    private void init() {
        for (DatabaseProfile profile : ExportToGenMAPP.getAvailableDatabaseProfiles()) {
            profileComboBox.addItem(profile);
        }
    }

    /**
     * Sets up the content of the panel.
     * 
     * @return
     */
    private JPanel getContentPanel() {
        // Profile | JComboBox | availableDatabaseProfiles | JLabel |
        // Description
        profileComboBox = new JComboBox();
        profileComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                databaseProfileSelected(profileComboBox.getSelectedItem());
            }
        });

        profileDescriptionTextArea = new JTextArea(3, 15);
        profileDescriptionTextArea.setLineWrap(true);
        profileDescriptionTextArea.setWrapStyleWord(true);
        profileDescriptionTextArea.setEditable(false);
        profileDescriptionTextArea.setBackground(new Color(238, 238, 238));

        // Owner | JTextField | empty
        ownerTextField = new JTextField(10);

        // Version | Calendar | set to today
        versionFormattedTextField = new JFormattedTextField(new SimpleDateFormat("MM/dd/yyyy"));
        versionFormattedTextField.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));

        // MODSystem | JTextfield | set to database profile
        modSystemTextField = new JTextField(10);
        modSystemTextField.setEditable(false);

        // RB - get data to populate the JList speciesCheckList
        // data is in speciesProfilesFound which is an instanced
        // ArrayList<SpeciesProfile>
        
        SpeciesListModel speciesListModel = new SpeciesListModel();
        speciesCheckList = new JList(speciesListModel);
        
        speciesCheckList.setVisibleRowCount(5); // Dondi - This guides the scroll
        // pane and layout manager.


        // register listeners
        speciesCheckList.addListSelectionListener (new ListSelectionListener() {
            // Handle list selection
        	public void valueChanged(ListSelectionEvent listSelectionEvent) {
        	    handleSpeciesProfileSelection(listSelectionEvent);
            }
        });

        speciesDescriptionTextArea = new JTextArea(3, 15);
        speciesDescriptionTextArea.setLineWrap(true);
        speciesDescriptionTextArea.setWrapStyleWord(true);
        speciesDescriptionTextArea.setEditable(true); // RB - true to discover size on panel
        speciesDescriptionTextArea.setBackground(new Color(238, 238, 238));

        speciesCustomizeTextField = new JTextField(10);

        // Modify | Calendar | set to today
        modifyFormattedTextField = new JFormattedTextField(new SimpleDateFormat("MM/dd/yyyy"));
        modifyFormattedTextField.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));

        // Notes | JTextArea | GenMAPP Builder version
        notesTextArea = new JTextArea("Exported by " + App.get().getAppName() + " " + GenMAPPBuilder.VERSION, 2, 40);
        notesTextArea.setBorder(new EtchedBorder());

        // Dondi - Useful link for figuring out how SpringLayout works:
        //    http://download.oracle.com/javase/tutorial/uiswing/layout/spring.html
        //
        // (cosmetic note: since the list occupies more than one line, you'll want to
        //  find a way to top-align its corresponding "Species:" label)
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
        // RB - disable adding of the speciesComboBox to the JPanel
        // leftPanel.add(speciesComboBox);
        // RB - adding the JList speciesChecklist to JPanel
        leftPanel.add(new JScrollPane(speciesCheckList));
        leftPanel.add(new JLabel("Customize Name:"));
        leftPanel.add(speciesCustomizeTextField);
        leftPanel.add(new JLabel("Modify (MM/dd/yyyy):"));
        leftPanel.add(modifyFormattedTextField);

        SpringUtilities.makeCompactGrid(leftPanel, leftPanel.getComponentCount() / 2, 2, // rows,
                                                                                            // cols
        6, 6, // initX, initY
        6, 6); // xPad, yPad

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
     * Adjusts the dynamic content of speciesCheckBox when a database profile is selected.
     * 
     * @param selectedItem
     */
    protected void databaseProfileSelected(Object selectedItem) {
        if (selectedItem instanceof DatabaseProfile) {
            DatabaseProfile selectedProfile = (DatabaseProfile)selectedItem;
            ExportToGenMAPP.setDatabaseProfile(selectedProfile);
            profileDescriptionTextArea.setText(selectedProfile.getDescription());
            modSystemTextField.setText(selectedProfile.getMODSystem());

            // Dondi - Note how, with the list model properly coded, all of the above becomes
            // this single [nested] statement.
            ((SpeciesListModel)speciesCheckList.getModel())
                .setSpeciesProfiles(selectedProfile.getSpeciesProfilesFound());
        }
    }

    /**
     * Adjusts the dynamic content when a species profile is selected.
     * Since only UniProt is supported, this selection will always be UniProt.
     * However, the SpeciesProfile is also being set here. 
     * 
     * @param selectedItem
     */
    
    // RB disable method for rewrite using JList speciesCheckList
    
    // Dondi - You might not have remembered that one of the things you needed to do with
    // this method was to change the parameters.  The prior version took the selected item,
    // which, in our case, no longer applies.  Instead, I'm sending in the entire event.
    // This change also merited a renaming of the method. 
    protected void handleSpeciesProfileSelection(ListSelectionEvent listSelectionEvent) {
        DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();

        // RB - GROUND ZERO - This is the line to modify for a collection!!!!
        // databaseProfile.setSelectedSpeciesProfile(selectedProfile);

        ExportToGenMAPP.setDatabaseProfile(databaseProfile);

        // Dondi - Alternatively, you can go straight to speciesCheckList.
        JList list = (JList)listSelectionEvent.getSource();

        // Dondi - Suggestion: get the hang of iteration-style for loops.  With that
        // style, you would not need the indices at all:
        //
        // for (Object selection: list.getSelectedValues()) {
        //     .......
        // }
        // RB - tried the iteration style loop but didn't get the proper text strings from line 208.
        StringBuilder speciesTextBuilder = new StringBuilder("Selected Species info: ");
        for (Object selection: list.getSelectedValues()) {
            // Dondi - Use StringBuilder when incrementally building a string.
            // (e.g., stringBuilder.append(selection.toString()))
            speciesTextBuilder.append("\n").append(selection);

//          StringBuilder stringBuilder.append( selection.toString() );
            // Dondi - The line above, from your prior commit, is, I think,
            // instructive in triaging some of the roots of your recent coding struggles.
            // Fundamentally, I see in this line the need to improve your understanding of how
            // syntax (what you write) relates to semantics (what you want the code to do).
            //
            // When facing a compile error, first try to break down what that line is trying to say.
            // Overall, the line implies a variable declaration: there is a class at the beginning
            // (StringBuilder) followed by what seems to be a variable name (stringBuilder).
            // However, that *isn't* a variable name --- it's an expression, as noted by the
            // method call to append.  Thus, just stepping back and looking at the line like
            // that, the skill to hone here is to realize that this statement does not match
            // any typical programming language construct at all.  You can then work from there.
            // For instance, since the append method call is what fundamentally messes up the
            // statement, you may then realize that this line should be closer to:
            //
            //     StringBuilder stringBuilder;
            //
            // That would now be syntactically valid.  Then, having seen this, you would probably
            // realize that you need to assign a value to stringBuilder.  For objects, this is
            // frequently a constructor.  So you may then go:
            //
            //     StringBuilder stringBuilder = new StringBuilder();
            //
            // Finally, you know that you want to call the append method.  Now you're golden ---
            // you have the object you want, and so you can now call append:
            //
            //     StringBuilder stringBuilder = new StringBuilder();
            //     stringBuilder.append(selection.toString());
            //
            // Now, this is not necessarily the final code that comes out, but I hope it illustrates
            // how taking things really slowly, especially in a language for which you are still on
            // a learning curve, is crucial in not getting lost.  And, the starting point of pretty
            // much any coding is the syntax.  So, my advice is to sit back, take it slow, and always
            // start with the syntax.  Be very sure that what is written makes syntactic sense first
            // and foremost.  After that, you can worry about whether it's doing what you want.

            // Dondi - sample class comparison for Rich
            System.out.println(selection.getClass() == UniProtSpeciesProfile.class);
		}
		speciesDescriptionTextArea.setText(speciesTextBuilder.toString());
            
		// RB - should kill the speciesCustomizeTextField all together
        //speciesCustomizeTextField.setText(selectedProfile.getName());
    }

    /**
     * Checks the required fields for the panel.
     * 
     * @return
     */
    protected boolean isAllInformationEntered() {
        return ownerTextField.getText().equals("") ? false : true;
    }

    /**
     * Submits all information collected on the panel to the DatabaseProfile
     * object of the Database that was selected (Uniprot, TIGR, or whatever),
     * then stores this DatabaseProfile back in the ExportToGenMAPP object. 
     * 
     * @throws ParseException
     */
    //FIXME: All this config data should be in an ExportProperties model object
    protected void submitInformationEntered() throws ParseException {
    	/*
    	 * ExportToGenMAPP is not a static class but has some static members and
    	 * static methods. This is used to store the DatabaseProfile without
    	 * passing an instance of the class around.
    	 */
    	// get a reference to the DBProfile
        DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();
        databaseProfile.setOwner(ownerTextField.getText());
        databaseProfile.setVersion(new SimpleDateFormat("MM/dd/yyyy").parse(versionFormattedTextField.getText()));
        databaseProfile.setMODSystem(modSystemTextField.getText());
        // set the species name
        
        // RB - not sure if setSpeciesName needs to be modified??
        
        databaseProfile.setSpeciesName(speciesCustomizeTextField.getText());
        databaseProfile.setModify(new SimpleDateFormat("MM/dd/yyyy").parse(modifyFormattedTextField.getText()));
        databaseProfile.setNotes(notesTextArea.getText());
        ExportToGenMAPP.setDatabaseProfile(databaseProfile);
    }

    /**
     * Adds a document listener.
     * 
     * @param documentListener
     */
    protected void addDocumentListener(DocumentListener documentListener) {
        ownerTextField.getDocument().addDocumentListener(documentListener);
    }

    // RB - add nested class here.
    // Need this class to provide full control over the JList.
    
    // Dondi - You were fairly close here.  I think your misunderstanding was that you were
    // supposed to *extend* AbstractListModel, not redefine it.  Note how AbstractListModel
    // takes care of the listener stuff for you --- you only need to add on the specific
    // mechanism for supplying the list's data.  Plus, when the array of species profiles
    // changes, you need to inform your listeners via fireContentsChanged.
    private class SpeciesListModel extends AbstractListModel {

        // The source of the list.
        private SpeciesProfile[] speciesProfiles;
        
        public void setSpeciesProfiles(SpeciesProfile[] speciesProfiles) {
            this.speciesProfiles = speciesProfiles;
            
            // Update listeners.
            fireContentsChanged(this, 0, getSize());
        }

        @Override
		public Object getElementAt(int index) {
			return speciesProfiles[index];
		}

		@Override
		public int getSize() {
		    return (speciesProfiles != null) ? speciesProfiles.length : 0;
		}

    }
}