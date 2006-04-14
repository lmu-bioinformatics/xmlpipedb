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

import edu.lmu.xmlpipedb.util.app.MainController;
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
import javax.swing.SwingUtilities;

/**
 *
 * @author Dave
 */
public class ImportPanel extends JPanel{
    private JButton _previewButton;
    private JTextField _textFieldPath;
    private JScrollPane _xmlScrollArea;
    private JTextArea _xmlView;
    private JButton _importButton;
    private JButton _openButton;
    private JProgressBar _progressBar;
    private File _xmlFile; 
    private MainController _main; 
    /** Creates a new instance of ImportPanel2 */
    public ImportPanel(MainController main) {
        _main = main; 
        createComponents();
        createActions(); 
        layoutComponents();
    }
    private void createComponents()
    {
        _previewButton = new JButton("preview");
        _previewButton.setEnabled(false);
        _importButton = new JButton("import");
        _importButton.setEnabled(false);
        _openButton = new JButton("open");
        _textFieldPath = new JTextField();
        _xmlView = new JTextArea();
        _xmlScrollArea = new JScrollPane(_xmlView);
        _progressBar = new JProgressBar(); 
        
    }
    private void createActions()
    {
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
    private void layoutComponents()
    {
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
        if(_xmlFile == null)
        {
            if(_textFieldPath.getText().length()>0)
            {
                try{
                    _xmlFile = new File(_textFieldPath.getText());
                }
                finally{
                    JOptionPane.showMessageDialog(this, "Please Open a Valid XML File", "Missing or Invalid File", JOptionPane.ERROR_MESSAGE);                     
                    return;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please Open a Valid XML File", "Missing or Invalid File", JOptionPane.ERROR_MESSAGE); 
                return;
            }    

        }
        (new Thread(new filePreview(_xmlFile, _xmlView))).start(); 
        
    }
    
    private void TextFieldPathKeyTyped(java.awt.event.KeyEvent evt) {                                       
         _previewButton.setEnabled(true); 
         _importButton.setEnabled(true); 
    } 
    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        if(_xmlFile == null)
        {
            if(_textFieldPath.getText().length()>0)
            {
                try{
                    _xmlFile = new File(_textFieldPath.getText());
                }
                finally{
                    JOptionPane.showMessageDialog(this, "Please Open a Valid XML File", "Missing or Invalid File", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please Open a Valid XML File", "Missing or Invalid File", JOptionPane.ERROR_MESSAGE); 
                return;
            }    

        }
        try{
           //does the progress monitor popup
           InputStream in = new BufferedInputStream(
                                new ProgressMonitorInputStream(
                                  this,
                                  "Reading " + _xmlFile,
                                  new FileInputStream(_xmlFile)));
            _main.importXml(in);
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
        }
        
    }
    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(".")); 
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            _xmlFile = fc.getSelectedFile(); 
            _textFieldPath.setText(_xmlFile.getAbsolutePath()); 
            _previewButton.setEnabled(true); 
            _importButton.setEnabled(true); 
        }   
    }    
    private class filePreview implements Runnable
    {
        File myFile; 
        JTextArea myArea; 
        filePreview(File file, JTextArea area){
            myArea = area; 
            myFile = file; 
        }
        public void run()
        {
            try{

                //does the progress monitor popup
                InputStream in = new BufferedInputStream(
                                 new ProgressMonitorInputStream(
                                 myArea.getParent(),
                                  "Reading " + myFile,
                                  new FileInputStream(myFile)));
                 
                BufferedReader reader = new BufferedReader(new InputStreamReader(in)); 
                
                String line; 
                while((line = reader.readLine())!=null)
                {
                    myArea.append(line+"\n");
                    myArea.setRows(myArea.getRows()+1); 
                    if(line.length() > myArea.getColumns())
                        myArea.setColumns(line.length()); 
                }
            }
            catch(Exception e)
            {
            
            }
        }
    }
}
