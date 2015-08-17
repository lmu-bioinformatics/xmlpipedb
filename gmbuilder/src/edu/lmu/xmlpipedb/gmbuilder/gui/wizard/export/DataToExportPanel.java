package edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.GOAspect;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.SystemType;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;

/**
 * @author Joey J. Barrett
 */
public class DataToExportPanel extends JPanel {

	private static final long serialVersionUID = -2257129989592002163L;

	private DataToExportDescriptor descriptor;

	private File genmappDatabaseFile = null;
	private JButton chooseGenMAPPDatabaseButton;
	private JLabel genmappDatabaseFileLabel;

	private JCheckBox onlyFAspectCheckBox;
	private JCheckBox onlyCAspectCheckBox;
	private JCheckBox onlyPAspectCheckBox;

	protected DataToExportPanel(DataToExportDescriptor descriptor) {
        super();
        this.descriptor = descriptor;

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(new JLabel("<html><h3>&nbsp;Export to GenMAPP: Data to Export</h3></html>"),
                BorderLayout.CENTER);
        titlePanel.add(new JSeparator(), BorderLayout.SOUTH);

        JPanel contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel getContentPanel() {

    	JPanel genmappFilePanel = new JPanel();
    	genmappFilePanel.setBorder(BorderFactory.createCompoundBorder(
    	    BorderFactory.createTitledBorder("GenMAPP Database File"),
    	    BorderFactory.createEmptyBorder(10, 5, 10, 5)
    	));
	    Action chooseGenMAPPDatabaseAction = new AbstractAction("Save GenMAPP Database File As...") {
			private static final long serialVersionUID = 3215450361259127899L;

	        public void actionPerformed(ActionEvent actionEvent) {
	            chooseGenMAPPDatabase();
	        }
	    };
	    chooseGenMAPPDatabaseButton = new JButton(chooseGenMAPPDatabaseAction);
	    genmappDatabaseFileLabel = new JLabel();
	    genmappFilePanel.add(chooseGenMAPPDatabaseButton);
    	genmappFilePanel.add(genmappDatabaseFileLabel);

    	onlyFAspectCheckBox = new JCheckBox("Export Molecular Function (F) Terms");
    	onlyCAspectCheckBox = new JCheckBox("Export Cellular Component (C) Terms");
    	onlyPAspectCheckBox = new JCheckBox("Export Biological Process (P) Terms");
    	
    	ChangeListener changeListener = new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                descriptor.setNextButton();
            }
    	    
    	};
    	onlyFAspectCheckBox.addChangeListener(changeListener);
        onlyCAspectCheckBox.addChangeListener(changeListener);
        onlyPAspectCheckBox.addChangeListener(changeListener);

    	JPanel goaAspectPanel = new JPanel();
    	goaAspectPanel.setLayout(new BoxLayout(goaAspectPanel, BoxLayout.Y_AXIS));
        goaAspectPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Gene Ontology Terms"),
            BorderFactory.createEmptyBorder(0, 5, 5, 5)
        ));
    	goaAspectPanel.add(onlyFAspectCheckBox);
    	goaAspectPanel.add(onlyCAspectCheckBox);
    	goaAspectPanel.add(onlyPAspectCheckBox);
    	goaAspectPanel.add(Box.createVerticalStrut(10));
    	goaAspectPanel.add(new JButton(new AbstractAction("Export All Terms") {

            private static final long serialVersionUID = -7958956533563071206L;

            public void actionPerformed(ActionEvent actionEvent) {
    	        onlyFAspectCheckBox.setSelected(true);
                onlyCAspectCheckBox.setSelected(true);
                onlyPAspectCheckBox.setSelected(true);
    	    }
    	}));
    	goaAspectPanel.add(Box.createVerticalGlue());

    	JPanel contentPanel = new JPanel(new BorderLayout(0, 5));
    	contentPanel.add(genmappFilePanel, BorderLayout.NORTH);
    	contentPanel.add(goaAspectPanel, BorderLayout.CENTER);
    	contentPanel.add(new JLabel("<html><h4>Clicking <i>Next</i> will begin the export process.</h4></html>",
    	        JLabel.CENTER), BorderLayout.SOUTH);

        return contentPanel;
    }

	/**
	 * Displays any available dynamic information.
	 */
	protected void displayAvailableInformation() {

		genmappDatabaseFile = null;
		genmappDatabaseFileLabel.setText("");

		onlyFAspectCheckBox.setSelected(true);
		onlyCAspectCheckBox.setSelected(true);
		onlyPAspectCheckBox.setSelected(true);

		DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();

		if (databaseProfile.getGenMAPPDatabase() != null) {
			// DEV NOTE: JN 7/15/2006 -- I changed the next line from receiving the file directly
			// to receiving a string and converting it to a file.
			// I've been cleaning up the use of File references, by changing them
			// to pass strings instead. All the previous changes were made in
			// DatabaseProfile and ConnectionManager. I figure this is far enough
			// for the present.
			genmappDatabaseFile = new File(databaseProfile.getGenMAPPDatabase());
			genmappDatabaseFileLabel.setText(genmappDatabaseFile.getName());
		}

	}

	protected void chooseGenMAPPDatabase() {

		String defaultFileName = GenMAPPBuilderUtilities.getDefaultGDBFilename(ExportToGenMAPP.getDatabaseProfile()
		        .getSelectedSpeciesProfile().getSpeciesName(), new Date());
		JFileChooser chooser = new JFileChooser();
		File defaultFile = new File(defaultFileName);
        chooser.setSelectedFile(defaultFile);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            genmappDatabaseFile = chooser.getSelectedFile();
            if ((genmappDatabaseFile != null) && (!genmappDatabaseFile.getName().endsWith(".gdb"))) {
                genmappDatabaseFile = new File(genmappDatabaseFile.getAbsoluteFile() + ".gdb");
            }
            genmappDatabaseFileLabel.setText(genmappDatabaseFile.getName());
        }
        descriptor.setNextButton();

	}

    protected boolean isAllInformationEntered() {
        return (genmappDatabaseFile != null) &&            // A file must be chosen.
                (onlyCAspectCheckBox.isSelected() ||
                        onlyFAspectCheckBox.isSelected() ||
                        onlyPAspectCheckBox.isSelected()); // At least one aspect must be included.
    }

	protected void submitInformationEntered() {
	    // Build the list of chosen aspects.  With just 3 checkboxes, we go ahead and inline.
	    List<DatabaseProfile.GOAspect> aspects = new ArrayList<DatabaseProfile.GOAspect>();
	    if (onlyCAspectCheckBox.isSelected()) {
	        aspects.add(GOAspect.Component);
	    }
	    if (onlyFAspectCheckBox.isSelected()) {
	        aspects.add(GOAspect.Function);
	    }
	    if (onlyPAspectCheckBox.isSelected()) {
	        aspects.add(GOAspect.Process);
	    }

	    // Pass the settings on to the database profile.
	    DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();
	    
	    // Export file and GO "aspects."
		databaseProfile.setDatabaseProperties(genmappDatabaseFile.getAbsolutePath(), aspects);

        // System tables: This used to be customizable, but ultimately we always
        // want everything anyway, so the customization interface was removed.
		List<String> properSystemTables = new ArrayList<String>();
		List<String> improperSystemTables = new ArrayList<String>();
		
		// Give precedence to what is already there, if any.
        if (databaseProfile.getSystemTables() != null) {
            for (Entry<String, SystemType> systemTable: databaseProfile.getSystemTables().entrySet()) {
                if (systemTable.getValue() == SystemType.Proper ||
                        systemTable.getValue() == SystemType.Primary) {
                    properSystemTables.add(systemTable.getKey());
                } else {
                    improperSystemTables.add(systemTable.getKey());
                }
            }
        }
        
        for (Entry<String, SystemType> systemTable: databaseProfile.getAvailableSystemTables().entrySet()) {
            if ((systemTable.getValue() == SystemType.Proper ||
                    systemTable.getValue() == SystemType.Primary) &&
                    !properSystemTables.contains(systemTable.getKey())) {
                properSystemTables.add(systemTable.getKey());
            } else if (systemTable.getValue() == SystemType.Improper &&
                    !improperSystemTables.contains(systemTable.getKey())) {
                improperSystemTables.add(systemTable.getKey());
            }
        }
        
        // Set the tables.
        databaseProfile.setTableProperties(properSystemTables.toArray(new String[0]),
                improperSystemTables.toArray(new String[0]));

        // Relationship tables: Like system tables, this used to be
        // customizable, and like system tables, we ended up wanting everything
        // anyway.
        databaseProfile.setRelationshipTableProperties(databaseProfile.getAvailableRelationshipTables());
        
        // Display order: Ideally, this *is* customizable. We have chosen to
        // remove customization until a suitable user interface for it can be
        // implemented. The ideal user interface is a drag-and-drop system table
        // list, where the user can reorder the desired system tables at will.
        // The corresponding string, based on the system tables' codes, would
        // then be derived from this list.
        databaseProfile.setDisplayOrder(databaseProfile.getDefaultDisplayOrder());

        // Done setting up the database profile.
        ExportToGenMAPP.setDatabaseProfile(databaseProfile);
	}
	
}
