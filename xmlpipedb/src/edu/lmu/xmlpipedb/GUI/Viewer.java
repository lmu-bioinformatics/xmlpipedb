/*
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 Web: http://sourceforge.net/projects/xmlpipedb
 */

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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import edu.lmu.xmlpipedb.ImportUniprotXML;

public class Viewer extends JFrame {

	private static final long serialVersionUID = 3797151961981031648L;

	// Global definitions.
	public static final String PATH_TO_DB_SCHEMA = "lib"
			+ System.getProperty("file.separator") + "database"
			+ System.getProperty("file.separator") + "schema.sql";

	// Locally accessible compenents.
	private static JTextArea console;

	private static JLabel infoLabel;

	private static JButton importButton;

	private static JTextField xmlPathField;

	private static File xmlImportFile = null;

	private JFileChooser chooser = new JFileChooser(".");

	/**
	 * Constructor.
	 */
	public Viewer() {

		super("XMLPipeDB - Loyola Marymount University");

		// Setup the main window.
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(new Dimension(800, 600));
		this.setLocationRelativeTo(null);

		// take care of the window closing event.
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				// Call to exit() to safetly exit the application.
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

		// Create the menu bar.
		JMenuBar menuBar = new JMenuBar();

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
		setJMenuBar(menuBar);

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
		getContentPane().add(importXMLFilePanel, BorderLayout.NORTH);
		getContentPane().add(consolePanel, BorderLayout.CENTER);
		getContentPane().add(infoToolBar, BorderLayout.SOUTH);

		this.pack();
		this.setVisible(true);
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
	 * Choose an XML file for importing.
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
		// Clean up anything before closing here

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
