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
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.util.GenMAPPBuilderUtilities;


/**
 * @author Joey J. Barrett
 * Class: ExportPanel2
 */
public class ExportPanel2 extends JPanel {

	private static final long serialVersionUID = -2257129989592002163L;
	private JFileChooser chooser = new JFileChooser(".");

	private JRadioButton genmappRadioButton;
	private JRadioButton otherRadioButton;

	private File genmappDatabaseFile = new File("");
	private JTextField genmappDatabaseTextField;
	private JButton chooseGenMAPPDatabaseButton;
	private JLabel chooseGenMAPPDatabaseLabel;

	private JLabel otherDatabaseSettings;

	private JRadioButton allAspectRadioButton;
	private JRadioButton onlyFAspectRadioButton;
	private JRadioButton onlyCAspectRadioButton;
	private JRadioButton onlyPAspectRadioButton;

	private File goAssociationsFile = new File("");
	private JTextField goAssocationsTextField;

	private char chosenAspect;

	/**
	 * Constructor.
	 */
	protected ExportPanel2() {

        super();

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
        add(contentPanel, BorderLayout.WEST);

    }

    /**
     * Provides the content for the panel.
     * @return
     */
    private JPanel getContentPanel() {

    	JPanel genmappPanel = new JPanel();
    	genmappDatabaseTextField = new JTextField(15);
    	genmappDatabaseTextField.setEditable(false);
	    Action chooseGenMAPPDatabaseAction = new AbstractAction("Specify File...") {
			private static final long serialVersionUID = 3215450361259127899L;

	        public void actionPerformed(ActionEvent e) {
	            chooseGenMAPPDatabase();
	        }
	    };
	    chooseGenMAPPDatabaseButton = new JButton(chooseGenMAPPDatabaseAction);
	    chooseGenMAPPDatabaseLabel = new JLabel("Create GenMAPP Database:");
    	genmappPanel.add(chooseGenMAPPDatabaseLabel);
    	genmappPanel.add(genmappDatabaseTextField);
    	genmappPanel.add(chooseGenMAPPDatabaseButton);

    	JPanel otherPanel = new JPanel();
    	otherDatabaseSettings = new JLabel("Choose other database settings here:");
    	otherPanel.add(otherDatabaseSettings);


    	JPanel goPanel = new JPanel();
    	goAssocationsTextField = new JTextField(15);
    	goAssocationsTextField.setEditable(false);
	    Action chooseGOAssociationsAction = new AbstractAction("Choose File...") {
			private static final long serialVersionUID = 3215450361259127899L;

	        public void actionPerformed(ActionEvent e) {
	            chooseGOAssociationFile();
	        }
	    };
	    JButton chooseGoAssociationsFile = new JButton(chooseGOAssociationsAction);
	    goPanel.add(new JLabel("GeneOntology Associations File:"));
	    goPanel.add(goAssocationsTextField);
	    goPanel.add(chooseGoAssociationsFile);


    	genmappRadioButton = new JRadioButton();
    	genmappRadioButton.setText("GenMAPP Gene Database File");
    	genmappRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				genmappRadioButtonSelected();
			}

    	});

    	otherRadioButton = new JRadioButton();
    	otherRadioButton.setText("Other Relational Database");
    	otherRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				otherRadioButtonSelected();
			}

    	});
    	//TODO Implement optional database connection settings.
    	//Don't allow this selection until implemented.
    	otherRadioButton.setEnabled(false);

    	ButtonGroup buttonGroup = new ButtonGroup();
    	buttonGroup.add(genmappRadioButton);
    	buttonGroup.add(otherRadioButton);

    	allAspectRadioButton = new JRadioButton();
    	allAspectRadioButton.setText("Full Database");
    	allAspectRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				allAspectRadioButtonSelected();
			}

    	});

    	onlyFAspectRadioButton = new JRadioButton();
    	onlyFAspectRadioButton.setText("Molecular Function (F_only) Database");
    	onlyFAspectRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onlyFAspectRadioButtonSelected();
			}

    	});

    	onlyCAspectRadioButton = new JRadioButton();
    	onlyCAspectRadioButton.setText("Cellular Component (C_only) Database");
    	onlyCAspectRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onlyCAspectRadioButtonSelected();
			}

    	});

    	onlyPAspectRadioButton = new JRadioButton();
    	onlyPAspectRadioButton.setText("Biological Process (P_only) Database");
    	onlyPAspectRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onlyPAspectRadioButtonSelected();
			}

    	});

    	ButtonGroup aspectButtonGroup = new ButtonGroup();
    	aspectButtonGroup.add(allAspectRadioButton);
    	aspectButtonGroup.add(onlyFAspectRadioButton);
    	aspectButtonGroup.add(onlyCAspectRadioButton);
    	aspectButtonGroup.add(onlyPAspectRadioButton);

    	JPanel goaAspectPanel = new JPanel(new GridLayout(4,1));
    	goaAspectPanel.add(allAspectRadioButton);
    	goaAspectPanel.add(onlyFAspectRadioButton);
    	goaAspectPanel.add(onlyCAspectRadioButton);
    	goaAspectPanel.add(onlyPAspectRadioButton);

    	JPanel centerPanel = new JPanel(new GridLayout(0,1));
    	centerPanel.add(genmappRadioButton);
    	centerPanel.add(genmappPanel);
    	centerPanel.add(otherRadioButton);
    	centerPanel.add(otherPanel);

    	JPanel contentPanel = new JPanel(new BorderLayout());
    	contentPanel.add(centerPanel, BorderLayout.CENTER);
    	contentPanel.add(goaAspectPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

	/**
	 * Displays any available dynamic information.
	 */
	protected void displayAvailableInformation() {

		genmappDatabaseFile = new File("");
		genmappDatabaseTextField.setText("");
		goAssociationsFile = new File("");
		goAssocationsTextField.setText("");

		genmappRadioButton.setSelected(true);
		otherRadioButton.setSelected(false);

		allAspectRadioButton.setSelected(true);
		onlyFAspectRadioButton.setSelected(false);
		onlyCAspectRadioButton.setSelected(false);
		onlyPAspectRadioButton.setSelected(false);

		DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();

		if(databaseProfile.getGenMAPPDatabase() != null) {
			// DEV NOTE: JN 7/15/2006 -- I changed the next line from receiving the file directly
			// to receiving a string and converting it to a file.
			// I've been cleaning up the use of File references, by changing them
			// to pass strings instead. All the previous changes were made in
			// DatabaseProfile and ConnectionManager. I figure this is far enough
			// for the present.
			genmappDatabaseFile = new File(databaseProfile.getGenMAPPDatabase());
			genmappDatabaseTextField.setText(genmappDatabaseFile.getName());
			genmappDatabaseTextField.setEnabled(true);
			chooseGenMAPPDatabaseButton.setEnabled(true);
			chooseGenMAPPDatabaseLabel.setEnabled(true);
			genmappRadioButton.setSelected(true);
		}

		if(databaseProfile.getAssociationsFile() != null) {
			goAssociationsFile = databaseProfile.getAssociationsFile();
			goAssocationsTextField.setText(goAssociationsFile.getName());
		}
	}

	/**
	 * Adds a document listener where required.
	 * @param documentListener
	 */
	protected void addDocumentListener(DocumentListener documentListener) {
		genmappDatabaseTextField.getDocument().addDocumentListener(documentListener);
		goAssocationsTextField.getDocument().addDocumentListener(documentListener);
	}

	/**
	 * Sets dynamic content based on a button
	 * selection.
	 */
	protected void otherRadioButtonSelected() {
		genmappDatabaseTextField.setEnabled(false);
		chooseGenMAPPDatabaseButton.setEnabled(false);
		chooseGenMAPPDatabaseLabel.setEnabled(false);
		otherDatabaseSettings.setEnabled(true);
	}

	/**
	 * Sets dynamic content based on a button
	 * selection.
	 */
	protected void genmappRadioButtonSelected() {
		genmappDatabaseTextField.setEnabled(true);
		chooseGenMAPPDatabaseButton.setEnabled(true);
		chooseGenMAPPDatabaseLabel.setEnabled(true);
		otherDatabaseSettings.setEnabled(false);
	}

	/**
	 * Sets aspect type to be exported to all.
	 */
	protected void allAspectRadioButtonSelected() {
		chosenAspect = 'A';
	}

	/**
	 * Sets aspect type to be exported to molecular
	 * function, or F.
	 */
	protected void onlyFAspectRadioButtonSelected() {
		chosenAspect = 'F';
	}

	/**
	 * Sets aspect type to be exported to cellular
	 * component, or C.
	 */
	protected void onlyCAspectRadioButtonSelected() {
		chosenAspect = 'C';
	}

	/**
	 * Sets aspect type to be exported to biological
	 * process, or P.
	 */
	protected void onlyPAspectRadioButtonSelected() {
		chosenAspect = 'P';
	}

	/**
	 * Displays a file chooser for creating
	 * a GenMAPP database file.
	 */
	protected void chooseGenMAPPDatabase() {

		String defaultFileName = GenMAPPBuilderUtilities.getDefaultGDBFilename(ExportToGenMAPP.getDatabaseProfile().getSelectedSpeciesProfile().getSpeciesName(), new Date());
		File exportFolder = new File("./export/");
		if( !exportFolder.exists() );
			exportFolder.mkdir();
		chooser = new JFileChooser("./export");
		File defaultFile = new File(defaultFileName);
        chooser.setSelectedFile(defaultFile);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            genmappDatabaseFile = chooser.getSelectedFile();
            if (genmappDatabaseFile == null) {
                genmappDatabaseFile = new File("");
            } else if(!genmappDatabaseFile.getName().endsWith(".gdb")) {
                genmappDatabaseFile = new File(genmappDatabaseFile.getAbsoluteFile() + ".gdb");
            }
            genmappDatabaseTextField.setText(genmappDatabaseFile.getName());
        }
	}

	/**
	 * Displays a file chooser for selecting
	 * the GeneOntology associations file.
	 */
	protected void chooseGOAssociationFile() {
		chooser = new JFileChooser(".");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            goAssociationsFile = chooser.getSelectedFile();
            if (goAssociationsFile == null) {
                goAssociationsFile = new File("");
            }
            goAssocationsTextField.setText(goAssociationsFile.getName());
        }
	}

	/**
     * Verifies if all information is entered.
     *
     * @return
     */
    protected boolean isAllInformationEntered() {
        if ("".equals(genmappDatabaseFile.getName()) /*|| "".equals(goAssociationsFile.getName())*/ ) {
            return false;
        }
        return true;
    }

	/**
	 * Submits all information collected on the panel.
	 */
	protected void submitInformationEntered() {
		DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();
		/* JN 7/15/2006 -- I eliminated most (many?) of the uses of File in the
		 * DatabaseProfile and ConnectionManager classes. The following line
		 * now needs to pass a String, not a File (like it used to).
		*/
		databaseProfile.setDatabaseProperties(
				genmappDatabaseFile.getAbsolutePath(),
				null,
				chosenAspect);
		ExportToGenMAPP.setDatabaseProfile(databaseProfile);
	}
}
