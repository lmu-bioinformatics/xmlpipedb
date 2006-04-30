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
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ProgressMonitorInputStream;

import org.hibernate.cfg.Configuration;

import edu.lmu.xmlpipedb.util.engines.ImportEngine;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Dave Hoffman
 */
public class ImportPanel extends JPanel {
    /**
     * Creates a new instance of ImportPanel
     * @param hibernateConfiguration The hibernate configuration to save to
     * @param jaxbContextPath The context path for the jaxb
     */
    public ImportPanel(String jaxbContextPath, Configuration hibernateConfiguration) {
        _jaxbContextPath = jaxbContextPath;
        _hibernateConfiguration = hibernateConfiguration;
        createComponents();
        createActions();
        layoutComponents();
    }
    
    private void createComponents() {
        _previewButton = new JButton("preview");
        _previewButton.setEnabled(false);
        _importButton = new JButton("import");
        _importButton.setEnabled(false);
        _openButton = new JButton("open");
        _textFieldPath = new JTextField();
        _xmlView = new JTextArea(10, 80);
        _xmlScrollArea = new JScrollPane(_xmlView);
        _progressBar = new JProgressBar();
    }

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
    }

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
        importBox.add(_previewButton);
        importBox.add(Box.createHorizontalGlue());
        importBox.add(_importButton);
        southBox.add(importBox);

        this.add(southBox, BorderLayout.SOUTH);

        this.add(_xmlScrollArea, BorderLayout.CENTER);
    }

    private void previewButtonActionPerformed(ActionEvent evt) {
        boolean proceedWithPreview = (_xmlFile != null);
        if (!proceedWithPreview) {
            if (_textFieldPath.getText().length() > 0) {
                _xmlFile = new File(_textFieldPath.getText());
                proceedWithPreview = true;
            }
        }
        //see Java API to understand why this is ok to use a thread and not a swing worker
        if (proceedWithPreview) {
            (new Thread(new FilePreview(_xmlFile, _xmlView))).start();
        } else {
            JOptionPane.showMessageDialog(this, "Please Open a Valid XML File", "Missing or Invalid File", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void TextFieldPathKeyTyped(java.awt.event.KeyEvent evt) {
        _previewButton.setEnabled(true);
        _importButton.setEnabled(true);
    }

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {
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
                ImportEngine importEngine = new ImportEngine(_jaxbContextPath, _hibernateConfiguration);
                importEngine.loadToDB(in);
            } catch(Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    private class FilePreview implements Runnable {
        File myFile;
        JTextArea myArea;

        FilePreview(File file, JTextArea area) {
            myArea = area;
            myFile = file;
        }

        public void run() {
            try {
                //does the progress monitor popup
                InputStream in = new BufferedInputStream(new ProgressMonitorInputStream(myArea.getParent(), "Reading " + myFile, new FileInputStream(myFile)));

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    myArea.append(line + "\n");
                    myArea.setRows(myArea.getRows() + 1);
                    if (line.length() > myArea.getColumns())
                        myArea.setColumns(line.length());
                }
            } catch(Exception e) {

            }
        }
    }
    /*
     *
     * A filter for xml files
     *
     */
    private class XMLFilter extends FileFilter
    {

        public boolean accept(File f)
        {
            //if it is a directory -- we want to show it so return true.
            if (f.isDirectory())
                return true;
            //get the extension of the file
            String extension = getExtension(f);
            if ((extension.equals("xml")) || (extension.equals("XML")))
            return true;

            return false;
        }
        public String getDescription()
        {
            return "XML files";
        }
        private String getExtension(File f)
        {
            String s = f.getName();
            int i = s.lastIndexOf('.');
            if (i > 0 &&  i < s.length() - 1)
                return s.substring(i+1).toLowerCase();
            return "";
        }        
    }
    private String _jaxbContextPath;
    private Configuration _hibernateConfiguration;
    private JButton _previewButton;
    private JTextField _textFieldPath;
    private JScrollPane _xmlScrollArea;
    private JTextArea _xmlView;
    private JButton _importButton;
    private JButton _openButton;
    private JProgressBar _progressBar;
    private File _xmlFile;
}
