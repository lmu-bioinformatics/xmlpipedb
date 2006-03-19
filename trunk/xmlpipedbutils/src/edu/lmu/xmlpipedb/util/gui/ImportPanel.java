/*
 * ImportPanel.java
 *
 * Created on March 12, 2006, 8:17 PM
 */

package edu.lmu.xmlpipedb.util.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import edu.lmu.xmlpipedb.util.app.Main;


/**
 *
 * @author  David Hoffman
 */
public class ImportPanel extends javax.swing.JPanel {
    private File _xmlFile; 
    private Main _main; 
    /** Creates new form ImportPanel */
    public ImportPanel(Main main) {
        initComponents();
        _xmlFile = null; 
        _main = main; 
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        TextFieldPath = new javax.swing.JTextField();
        openButton = new javax.swing.JButton();
        importButton = new javax.swing.JButton();
        XmlScrollArea = new javax.swing.JScrollPane();
        XmlView = new javax.swing.JTextArea();
        PreviewButton = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();

        setLayout(new java.awt.GridBagLayout());

        TextFieldPath.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldPathKeyTyped(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        add(TextFieldPath, gridBagConstraints);

        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        add(openButton, new java.awt.GridBagConstraints());

        importButton.setText("Import");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        add(importButton, gridBagConstraints);

        XmlScrollArea.setAutoscrolls(true);
        XmlScrollArea.setPreferredSize(new java.awt.Dimension(400, 200));
        XmlView.setColumns(20);
        XmlView.setEditable(false);
        XmlView.setRows(5);
        XmlView.setPreferredSize(new java.awt.Dimension(400, 200));
        XmlScrollArea.setViewportView(XmlView);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(XmlScrollArea, gridBagConstraints);

        PreviewButton.setText("Preview");
        PreviewButton.setEnabled(false);
        PreviewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreviewButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(PreviewButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(progressBar, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void PreviewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviewButtonActionPerformed
        if(_xmlFile == null)
        {
            if(TextFieldPath.getText().length()>0)
            {
                try{
                    _xmlFile = new File(TextFieldPath.getText());
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
        (new Thread(new filePreview(_xmlFile, XmlView))).start(); 
        //SwingUtilities.invokeLater(new filePreview(_xmlFile, XmlView)); 
        
    }//GEN-LAST:event_PreviewButtonActionPerformed

    private void TextFieldPathKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldPathKeyTyped
         PreviewButton.setEnabled(true); 
    }//GEN-LAST:event_TextFieldPathKeyTyped

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        if(_xmlFile == null)
        {
            if(TextFieldPath.getText().length()>0)
            {
                try{
                    _xmlFile = new File(TextFieldPath.getText());
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
            _main.importXml(_xmlFile);
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
        }
        
    }//GEN-LAST:event_importButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            _xmlFile = fc.getSelectedFile(); 
            TextFieldPath.setText(_xmlFile.getAbsolutePath()); 
            PreviewButton.setEnabled(true); 
        }
        
    }//GEN-LAST:event_openButtonActionPerformed
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
                BufferedReader reader = new BufferedReader(new FileReader(myFile)); 

               
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

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PreviewButton;
    private javax.swing.JTextField TextFieldPath;
    private javax.swing.JScrollPane XmlScrollArea;
    private javax.swing.JTextArea XmlView;
    private javax.swing.JButton importButton;
    private javax.swing.JButton openButton;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
    
}
