/********************************************************
 * Filename: ExportPanel5.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: The fifth panel displayed in the export
 * wizard.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * @author Joey J. Barrett
 * Class: ExportPanel5
 */
public class ExportInProgressPanel extends JPanel {
 
	private static final long serialVersionUID = 7026526881028815907L;

    private JLabel progressDescription;
    private JProgressBar progressSent;
        
    /**
     * Constructor
     */
    public ExportInProgressPanel() {
        super();
                
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(new JLabel("<html><h3>&nbsp;Export to GenMAPP: Export in Progress</h3></html>"),
                BorderLayout.CENTER);
        titlePanel.add(new JSeparator(), BorderLayout.SOUTH);

        JPanel contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        
        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.WEST);
    }  
    
    /**
     * Sets the progress of the progress bar.
     * 
     * @param i
     *            must be an integer between 0-100.
     * @param s
     *            some description string.
     */
    public void setProgress(final int i, final String s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                progressSent.setValue(i);
                progressDescription.setText(s);
            }
        });
    }
    
    /**
     * The content of the panel.
     * @return
     */
    private JPanel getContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        progressSent = new JProgressBar();
        progressSent.setStringPainted(true);
        progressSent.setSize(new Dimension(20, 150));
        
        progressDescription = new JLabel();
        progressDescription.setFont(new Font("MS Sans Serif", 1, 11));
        progressDescription.setText("Preparing for export...");
        
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(progressSent);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(progressDescription);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(new JLabel("<html><h4>When the export completes the <i>Finish</i> button will hightlight below.</h4></html>"));
    
        return contentPanel;
    }
    
}
