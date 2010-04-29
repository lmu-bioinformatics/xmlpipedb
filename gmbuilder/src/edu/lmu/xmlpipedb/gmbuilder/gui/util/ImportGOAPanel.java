/*
 * ImportGOAPanel.java
 *
 * Created on April 4, 2010, 4:03 PM
 * Based on ImportPanel.java
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package edu.lmu.xmlpipedb.gmbuilder.gui.util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go.ExportGoData;
import edu.lmu.xmlpipedb.gmbuilder.util.ImportGOAEngine;
import edu.lmu.xmlpipedb.util.gui.UtilityDialogue;
import edu.lmu.xmlpipedb.util.resources.AppResources;

/**
 *
 *
 * @author Don Murphy
 */
public class ImportGOAPanel extends UtilityDialogue {
    /**
     * Creates a new instance of ImportPanel
     *
     * @param hibernateConfiguration
     *            The hibernate configuration to save to
     * @param jaxbContextPath
     *            The context path for the jaxb
     */
    public ImportGOAPanel(Configuration hibernateConfiguration) {
        this(hibernateConfiguration, "", null);
    }

    /**
     * Creates a new instance of ImportPanel
     *
     * @param hibernateConfiguration
     *            The hibernate configuration to save to
     * @param entryElement
     *            Used to parse each record from the XML input file. For a
     *            uniprot XML file, this would normally be "uniprot/entry"
     * @param rootElementName
     *            Map<String, String> containing a "head" and a "tail". This is
     *            used to surround the extracted record for processing The
     *            "head" must have the complete beginning tag (inclusive all
     *            namespace delcarations, attributes, etc.), e.g.: <bookstore
     *            xlsns:http://mybookstore.org/bookstore> The "tail" need only
     *            have the correct closing tag, e.g.: </bookstore>
     */
    public ImportGOAPanel(Configuration hibernateConfiguration, String entryElement, HashMap<String, String> rootElementName) {
        _success = false;
        _hibernateConfiguration = hibernateConfiguration;
        createComponents();
        createActions();
        layoutComponents();
    }

    public boolean wasImportSuccessful() {
        return _success;
    }

    /**
     * Create all swing components
     */
    private void createComponents() {
        _cancelButton = new JButton(AppResources.messageString("import_done"));
        _previewButton = new JButton(AppResources.messageString("import_preview"));
        _previewButton.setEnabled(false);
        _importButton = new JButton(AppResources.messageString("import_import"));
        _importButton.setEnabled(false);
        _openButton = new JButton(AppResources.messageString("import_open"));
        _textFieldPath = new JTextField();
        _goaView = new JTextArea(10, 80);
        _goaScrollArea = new JScrollPane(_goaView);
        _progressBar = new JProgressBar();
    }

    /**
     * Create actions for the panel
     */
    private void createActions() {
        _textFieldPath.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                TextFieldPathKeyTyped(evt);
            }
        });
        _openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
        _importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });
        _previewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                previewButtonActionPerformed(evt);
            }
        });
        _cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancel();
            }
        });

    }

    /**
     * Layout the swing components
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        Box openBox = Box.createHorizontalBox();
        openBox.add(_textFieldPath);
        openBox.add(Box.createHorizontalStrut(5));
        openBox.add(_openButton);
        this.add(openBox, BorderLayout.NORTH);

        Box southBox = Box.createVerticalBox();
        _progressBar.setVisible(false);
        southBox.add(_progressBar);
        southBox.add(Box.createVerticalStrut(5));

        Box importBox = Box.createHorizontalBox();
        importBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        importBox.add(_previewButton);
        importBox.add(Box.createHorizontalStrut(10));
        importBox.add(Box.createHorizontalGlue());
        importBox.add(_importButton);
        importBox.add(Box.createHorizontalStrut(10));
        importBox.add(_cancelButton);
        southBox.add(importBox);

        this.add(southBox, BorderLayout.SOUTH);

        this.add(_goaScrollArea, BorderLayout.CENTER);

    }

    /**
     * The preview action when the button is pushed
     */
    private void previewButtonActionPerformed(ActionEvent evt) {
        boolean proceedWithPreview = (_goaFile != null);
        if (!proceedWithPreview) {
            if (_textFieldPath.getText().length() > 0) {
                _goaFile = new File(_textFieldPath.getText());
                proceedWithPreview = true;
            }
        }
        // see Java API to understand why this is ok to use a thread and not a
        // swing worker
        if (proceedWithPreview) {
            (new Thread(new FilePreview(_goaFile, _goaView))).start();
        } else {
            JOptionPane.showMessageDialog(this, "Please Open a Valid GOA File", "Missing or Invalid File", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * When something is typed in the text field the buttons are enabled.
     */
    private void TextFieldPathKeyTyped(java.awt.event.KeyEvent evt) {
        _previewButton.setEnabled(true);
        _importButton.setEnabled(true);
    }

    /**
     * Imports the opened file GOA file
     */
    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {
        boolean proceedWithImport = (_goaFile != null);
        if (!proceedWithImport) {
            if (_textFieldPath.getText().length() > 0) {
                _goaFile = new File(_textFieldPath.getText());
                proceedWithImport = true;
            }
        }

        if (proceedWithImport) {
        	try {
        		ImportGOAEngine importGOAEngine = new ImportGOAEngine(_hibernateConfiguration);
                importGOAEngine.importToSQL(_goaFile);
                JOptionPane.showMessageDialog(this, "Import Complete: " + _goaFile, "Import Complete", JOptionPane.INFORMATION_MESSAGE);
        	} catch(IOException e) {
        		e.printStackTrace();
        		JOptionPane.showMessageDialog(this, "An I/O exception occured while trying to read the file " + _goaFile, "Error", JOptionPane.ERROR_MESSAGE);
        	} catch(SQLException sqle) {
        		JOptionPane.showMessageDialog(this, sqle.getMessage());
        		//Need to clean up connection after SQL exceptions
        	} catch(Exception e) {
        		e.printStackTrace();
        		JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        	} catch(OutOfMemoryError e) {
        		_Log.info("Error occurred at : " + DateFormat.getTimeInstance(DateFormat.LONG).format(System.currentTimeMillis()));
        		e.printStackTrace();
        		_Log.info("SystemOutOfMemoryError. Message = " + e.getMessage() + "LocalizedMessage = " + e.getLocalizedMessage());
        	}
        } else {
            JOptionPane.showMessageDialog(this, "Please Open a Valid GOA File", "Missing or Invalid File", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * When a file is selected with the filechooser, the filepath is place in the text field
     * of the panel while the GOA file is set for import
     */
    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new GOAFilter());
        fc.setCurrentDirectory(new File("."));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            _goaFile = fc.getSelectedFile();
            _textFieldPath.setText(_goaFile.getAbsolutePath());
            _previewButton.setEnabled(true);
            _importButton.setEnabled(true);
        }
    }

    /*
     *
     * This class is for displaying text in the text area in a different thread.
     */
    private class FilePreview implements Runnable {
        File myFile;
        JTextArea myArea;

        FilePreview(File file, JTextArea area) {
            myArea = area;
            myFile = file;
        }

        public void run() {
            try {
                // does the progress monitor popup
                InputStream in = new BufferedInputStream(new ProgressMonitorInputStream(myArea.getParent(), "Reading " + myFile, new FileInputStream(myFile)));

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    myArea.append(line + "\n");
                    myArea.setRows(myArea.getRows() + 1);
                    if (line.length() > myArea.getColumns()) {
                        myArea.setColumns(line.length());
                    }
                }
            } catch(Exception e) {

            }
        }
    }

    /*
     *
     * A filter for goa files
     */
    private class GOAFilter extends FileFilter {

        public boolean accept(File f) {
            // if it is a directory -- we want to show it so return true.
            if (f.isDirectory()) {
                return true;
            }

            // get the extension of the file
            return "goa".equalsIgnoreCase(getExtension(f));
        }

        public String getDescription() {
            return "GOA files";
        }

        private String getExtension(File f) {
            String s = f.getName();
            int i = s.lastIndexOf('.');
            if (i > 0 && i < s.length() - 1) {
                return s.substring(i + 1).toLowerCase();
            }
            return "";
        }
    }

    /**
     * Log object for ImportGOAPanel.
     */
    private static final Log _Log = LogFactory.getLog(ImportGOAPanel.class);

    private static final int GAF10NUMOFCOLUMNS = 15;
    private static final int GAF20NUMOFCOLUMNS = 17;
    private static final int DATECOLUMN = 13;
    private static final int LINE_IMPORT_REPORT_INTERVAL = 5000;
    private static final double PERCENT_LINES_READ = 10.0;


    private boolean _success;
    private Configuration _hibernateConfiguration;
    private JButton _previewButton;
    private JTextField _textFieldPath;
    private JScrollPane _goaScrollArea;
    private JTextArea _goaView;
    private JButton _importButton;
    private JButton _openButton;
    private JButton _cancelButton;
    private JProgressBar _progressBar;
    private File _goaFile;
}
