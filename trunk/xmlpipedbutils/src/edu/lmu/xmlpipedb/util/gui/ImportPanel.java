/*
 * ImportPanel2.java
 *
 * Created on March 30, 2006, 6:59 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package edu.lmu.xmlpipedb.util.gui;

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
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.util.engines.ImportEngine;
import edu.lmu.xmlpipedb.util.resources.AppResources;

/**
 * 
 * @author Dave Hoffman
 */
public class ImportPanel extends UtilityDialogue {
    /**
     * Creates a new instance of ImportPanel
     * 
     * @param hibernateConfiguration
     *            The hibernate configuration to save to
     * @param jaxbContextPath
     *            The context path for the jaxb
     * @throws IOException 
     * @throws SAXException 
     * @throws JAXBException 
     * @throws HibernateException 
     */
    public ImportPanel(String jaxbContextPath, Configuration hibernateConfiguration)
      throws SQLException, HibernateException, JAXBException, SAXException, IOException {
        this(jaxbContextPath, hibernateConfiguration, "", null);
    }

    /**
     * Creates a new instance of ImportPanel
     * 
     * @param jaxbContextPath
     *            The context path for the jaxb
     * @param hibernateConfiguration
     *            The hibernate configuration to save to
     * @param entryElement
     *            Used to parse each record from the XML input file. For a
     *            uniprot XML file, this would normally be "uniprot/entry"
     * @param rootElementName
     *            Map<String, String> containing a "head" and a "tail". This is
     *            used to surround the extracted record for processing The
     *            "head" must have the complete beginning tag (inclusive all
     *            namespace declarations, attributes, etc.), e.g.: <bookstore
     *            xlsns:http://mybookstore.org/bookstore> The "tail" need only
     *            have the correct closing tag, e.g.: </bookstore>
     * @throws IOException 
     * @throws SAXException 
     * @throws JAXBException 
     * @throws HibernateException 
     */
    public ImportPanel(String jaxbContextPath, Configuration hibernateConfiguration, String entryElement, HashMap<String, String> rootElementName)
      throws SQLException, HibernateException, JAXBException, SAXException, IOException {
        _jaxbContextPath = jaxbContextPath;
        _success = false;
        _hibernateConfiguration = hibernateConfiguration;
        _entryElement = entryElement;
        _rootElementName = rootElementName;
        importEngine = new ImportEngine(_jaxbContextPath, _hibernateConfiguration, _entryElement, _rootElementName);
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
        _xmlView = new JTextArea(10, 80);
        _xmlScrollArea = new JScrollPane(_xmlView);
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
        this.add(_xmlScrollArea, BorderLayout.CENTER);
    }

    /**
     * The preview action when the button is pushed
     */
    private void previewButtonActionPerformed(ActionEvent evt) {
        boolean proceedWithPreview = (_xmlFile != null);
        if (!proceedWithPreview) {
            if (_textFieldPath.getText().length() > 0) {
                _xmlFile = new File(_textFieldPath.getText());
                proceedWithPreview = true;
            }
        }
        // see Java API to understand why this is ok to use a thread and not a
        // swing worker
        if (proceedWithPreview) {
            (new Thread(new FilePreview(_xmlFile, _xmlView))).start();
        } else {
            JOptionPane.showMessageDialog(this, "Please Open a Valid XML File", "Missing or Invalid File", JOptionPane.ERROR_MESSAGE);
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
     * Import the opened file
     */
    private void importButtonActionPerformed(ActionEvent evt) {
        boolean proceedWithImport = (_xmlFile != null);
        if (!proceedWithImport) {
            if (_textFieldPath.getText().length() > 0) {
                _xmlFile = new File(_textFieldPath.getText());
                proceedWithImport = true;
            }
        }

        if (proceedWithImport) {
            try {
                // does the progress monitor popup
                InputStream in = new BufferedInputStream(new ProgressMonitorInputStream(this, "Reading " + _xmlFile, new FileInputStream(_xmlFile)));
                _Log.info("Import Started at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(System.currentTimeMillis()));
                importEngine.loadToDB(in);
                _Log.info("Import Finished at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(System.currentTimeMillis()));
                _success = true;
                // notify user when import is complete
                JOptionPane.showMessageDialog(this, "Import Complete: " + _xmlFile, "Import Complete", JOptionPane.INFORMATION_MESSAGE);
            } catch(IOException e) {
                JOptionPane.showMessageDialog(this, "An I/O exception occured while trying to read the file " + _xmlFile, "Error", JOptionPane.ERROR_MESSAGE);
                _Log.error(e);
            } catch(Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                _Log.error(e);
            } catch(OutOfMemoryError e) {
                _Log.error("Error occurred at : " + DateFormat.getTimeInstance(DateFormat.LONG).format(System.currentTimeMillis()), e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please Open a Valid XML File", "Missing or Invalid File", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new XMLFilter());
        fc.setCurrentDirectory(new File("."));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            _xmlFile = fc.getSelectedFile();
            _textFieldPath.setText(_xmlFile.getAbsolutePath());
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
     * A filter for xml files
     */
    private class XMLFilter extends FileFilter {

        public boolean accept(File f) {
            // if it is a directory -- we want to show it so return true.
            if (f.isDirectory()) {
                return true;
            }

            // get the extension of the file
            return "xml".equalsIgnoreCase(getExtension(f));
        }

        public String getDescription() {
            return "XML files";
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
     * The ImportPanel log.
     */
    private static final Log _Log = LogFactory.getLog(ImportPanel.class);

    private ImportEngine importEngine;

    private boolean _success;
    private String _jaxbContextPath;
    private Configuration _hibernateConfiguration;
    private String _entryElement;
    private HashMap<String, String> _rootElementName;
    private JButton _previewButton;
    private JTextField _textFieldPath;
    private JScrollPane _xmlScrollArea;
    private JTextArea _xmlView;
    private JButton _importButton;
    private JButton _openButton;
    private JButton _cancelButton;
    private JProgressBar _progressBar;
    private File _xmlFile;
}
