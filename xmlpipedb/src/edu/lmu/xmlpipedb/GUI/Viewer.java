package edu.lmu.xmlpipedb.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import edu.lmu.xmlpipedb.ImportUniprotXML;

/**
 * A main frame window for hosting the XmlPipeDB application.
 *
 * @author Joey J Barrett
 */
public class Viewer extends JFrame {

    private static final long serialVersionUID = 3797151961981031648L;

    // Global definitions.
    public static final String PATH_TO_DB_SCHEMA = "lib"
            + System.getProperty("file.separator") + "database"
            + System.getProperty("file.separator") + "schema.sql";

    // Locally accessible compenents.
    private JTextArea console;
    private JLabel infoLabel;
    private JButton importButton;
    private JTextField xmlPathField;
    private JTextField connectionURLField;
    private JTextField usernameField;
    private JTextField passwordField;
    private File xmlImportFile = null;
    private JFileChooser chooser = new JFileChooser(".");

    /**
     * Creates a viewer, lays out components and registers listeners.
     */
    public Viewer() {

        super("XMLPipeDB - Loyola Marymount University");

        // Setup the main window.
        JFrame.setDefaultLookAndFeelDecorated(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        // Register a window listener to allow clean up before exit
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                // Clean up and exit
                exit();
            }
        });

        // Setup all actions.
        Action openXMLFileAction = new AbstractAction("Open XML File...") {
            {
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control O"));
            }

            public void actionPerformed(ActionEvent e) {
                openXMLFile();
            }
        };

        Action quitAction = new AbstractAction("Quit") {
            {
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_Q));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control Q"));
            }

            public void actionPerformed(ActionEvent e) {
                exit();
            }
        };

        Action preferencesAction = new AbstractAction("Preferences") {
            {
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_P));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control P"));
            }

            public void actionPerformed(ActionEvent e) {
                preferences();
            }
        };

        Action openSchemaAction = new AbstractAction("Open Database Schema") {
            {
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control S"));
            }

            public void actionPerformed(ActionEvent e) {
                openSchema();
            }
        };

        Action importXMLFileAction = new AbstractAction("Import XML File") {
            {
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_I));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control I"));
            }

            public void actionPerformed(ActionEvent e) {
                importXMLFile();
            }
        };

        JTabbedPane ribbon = new JTabbedPane();

        JToolBar fileRibbon = new JToolBar();
        fileRibbon.setFloatable(false);
        fileRibbon.add(quitAction);
        ribbon.addTab("File", null, fileRibbon, "File");

        JToolBar importRibbon = new JToolBar();
        importRibbon.setFloatable(false);
        importRibbon.add(openXMLFileAction);
        xmlPathField = new JTextField("Use \"Open\" to choose an XML File...");
        xmlPathField.setEditable(false);
        importRibbon.add(xmlPathField);
        importRibbon.add(importXMLFileAction);
        ribbon.addTab("Import", null, importRibbon, "Import");

        JToolBar databaseRibbon = new JToolBar();
        databaseRibbon.setFloatable(false);
        connectionURLField = new JTextField("jdbc:postgresql://localhost:5432/uniprot");
        databaseRibbon.add(connectionURLField);
        usernameField = new JTextField("username");
        databaseRibbon.add(usernameField);
        passwordField = new JTextField("password");
        databaseRibbon.add(passwordField);
        ribbon.addTab("Database", null, databaseRibbon, "Database");

        JToolBar debugRibbon = new JToolBar();
        debugRibbon.setFloatable(false);
        debugRibbon.add(openSchemaAction);
        ribbon.addTab("Debug", null, debugRibbon, "Debug");

        // Create the menu bar.
/*		JMenuBar menuBar = new JMenuBar();

        // Setup the "file" menu bar.
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openXMLFileAction);
        fileMenu.add(importXMLFileAction);
        fileMenu.addSeparator();
        fileMenu.add(preferencesAction);
        fileMenu.addSeparator();
        fileMenu.add(quitAction);
        menuBar.add(fileMenu);

        // Setup the "debug" menu bar.
        JMenu debugMenu = new JMenu("Debug");
        debugMenu.add(openSchemaAction);
        menuBar.add(debugMenu);

        // Add the menu bar to the frame.
        setJMenuBar(menuBar);*/

        // Create the "top" panel for importing a file.
        JPanel importXMLFilePanel = new JPanel(new BorderLayout());
        JButton openButton = new JButton("Open");
        openButton.setToolTipText("Open XML File");
        openButton.addActionListener(openXMLFileAction);
        importXMLFilePanel.add(openButton, BorderLayout.WEST);
        xmlPathField = new JTextField("Use \"Open\" to choose an XML File...");
        xmlPathField.setEditable(false);
        importXMLFilePanel.add(xmlPathField, BorderLayout.CENTER);
        importButton = new JButton("Import");
        importButton.setToolTipText("Parse XML File");
        importButton.addActionListener(importXMLFileAction);
        importButton.setEnabled(false);
        importXMLFilePanel.add(importButton, BorderLayout.EAST);

        // Create the "bottom" panel for the output console.
        JPanel consolePanel = new JPanel(new BorderLayout());
        console = new JTextArea(30, 60);
        console.setEditable(false);
        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);
        console.setFont(new Font("Serif", Font.PLAIN, 12));
        consolePanel.add(new JScrollPane(console), BorderLayout.CENTER);

        // Create the info toolbar
        JToolBar infoToolBar = new JToolBar();
        infoLabel = new JLabel("Welcome to XMLPipeDB");
        infoToolBar.add(infoLabel);

        // ADD EVERYTHING TO CONTENT PANE
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(ribbon, BorderLayout.NORTH);
        getContentPane().add(consolePanel, BorderLayout.CENTER);
        getContentPane().add(infoToolBar, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    /**
     * Requests importing of the specified XML file.
     */
    private void importXMLFile() {
        if (xmlImportFile != null) {
            try {
                console.append("Loading XML file: " + xmlImportFile);
                console.append(System.getProperty("line.separator"));

                ImportUniprotXML.loadXML(xmlImportFile);

                console.append("Loading Complete.");
                console.append(System.getProperty("line.separator"));
            } catch (Exception e) {
                console.append("Error: ");
                console.append(System.getProperty("line.separator"));
                console.append(e.toString());
            }
        }

    }

    /**
     * Opens the database schema file.
     */
    private void openSchema() {
        StringBuffer buffer = new StringBuffer();
        try {
            console
                    .append("Opening database schema file: "
                            + PATH_TO_DB_SCHEMA);
            console.append(System.getProperty("line.separator"));

            BufferedReader in = new BufferedReader(new FileReader(new File(
                    PATH_TO_DB_SCHEMA)));

            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line)
                        .append(System.getProperty("line.separator"));
            }
            in.close();
        } catch (IOException e) {
            console.append("Error: ");
            console.append(System.getProperty("line.separator"));
            console.append(e.toString());
        }
        console.append(buffer.toString());
    }


    /**
     * Chooses an XML file for importing.
     */
    private void openXMLFile() {
        chooser.showOpenDialog(this);
        File file = chooser.getSelectedFile();
        if (file == null) {
            if (xmlImportFile == null) {
                importButton.setEnabled(false);
            }
            return;
        } else {
            xmlImportFile = file;
            xmlPathField.setText(xmlImportFile.getAbsolutePath());
            importButton.setEnabled(true);
        }
    }

    /**
     * Currently not used.
     */
    private void preferences() {
        //Add whatever is needed.

    }

    /**
     * Exit the application.
     */
    private void exit() {
        // TODO: Clean up anything before closing here

        // exit
        System.exit(0);
    }

    /**
     * Runs the application.
     */
    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Viewer();
            }
        });
    }
}