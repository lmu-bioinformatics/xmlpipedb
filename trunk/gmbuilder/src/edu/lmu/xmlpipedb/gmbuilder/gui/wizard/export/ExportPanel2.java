/********************************************************
 * Filename: ExportPanel2.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: The second panel displayed in the export
 * wizard.
 *
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;


/**
 * @author Joey J. Barrett
 * Class: ExportPanel2
 */
public class ExportPanel2 extends JPanel {

	private static final long serialVersionUID = -2257129989592002163L;

	private ExportPanel2Descriptor descriptor;

	private File genmappDatabaseFile = null;
	private JButton chooseGenMAPPDatabaseButton;
	private JLabel genmappDatabaseFileLabel;

	private JCheckBox onlyFAspectCheckBox;
	private JCheckBox onlyCAspectCheckBox;
	private JCheckBox onlyPAspectCheckBox;

	/**
	 * Constructor.
	 */
	protected ExportPanel2(ExportPanel2Descriptor descriptor) {
        super();
        this.descriptor = descriptor;

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.gray);

        JLabel textLabel = new JLabel();
        textLabel.setBackground(Color.gray);
        textLabel.setFont(new Font("MS Sans Serif", Font.BOLD, 14));
        textLabel.setText("Export to GenMAPP - Database Settings");
        textLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        textLabel.setOpaque(true);

        titlePanel.add(textLabel, BorderLayout.CENTER);
        titlePanel.add(new JSeparator(), BorderLayout.SOUTH);

        JPanel contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Provides the content for the panel.
     * @return
     */
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
    	    public void actionPerformed(ActionEvent actionEvent) {
    	        onlyFAspectCheckBox.setSelected(true);
                onlyCAspectCheckBox.setSelected(true);
                onlyPAspectCheckBox.setSelected(true);
    	    }
    	}));

    	JPanel contentPanel = new JPanel(new BorderLayout(0, 5));
    	contentPanel.add(genmappFilePanel, BorderLayout.NORTH);
    	contentPanel.add(goaAspectPanel, BorderLayout.SOUTH);

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

	/**
	 * Displays a file chooser for creating
	 * a GenMAPP database file.
	 */
	protected void chooseGenMAPPDatabase() {

		String defaultFileName = GenMAPPBuilderUtilities.getDefaultGDBFilename(ExportToGenMAPP.getDatabaseProfile().getSelectedSpeciesProfile().getSpeciesName(), new Date());
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

	/**
     * Verifies if all information is entered.
     *
     * @return
     */
    protected boolean isAllInformationEntered() {
        return (genmappDatabaseFile != null) &&            // A file must be chosen.
                (onlyCAspectCheckBox.isSelected() ||
                        onlyFAspectCheckBox.isSelected() ||
                        onlyPAspectCheckBox.isSelected()); // At least one aspect must be included.
    }

	/**
	 * Submits all information collected on the panel.
	 */
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

	    DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();
		/* JN 7/15/2006 -- I eliminated most (many?) of the uses of File in the
		 * DatabaseProfile and ConnectionManager classes. The following line
		 * now needs to pass a String, not a File (like it used to).
		*/
		databaseProfile.setDatabaseProperties(
				genmappDatabaseFile.getAbsolutePath(),
				null,
				aspects);
		ExportToGenMAPP.setDatabaseProfile(databaseProfile);
	}
	
}
