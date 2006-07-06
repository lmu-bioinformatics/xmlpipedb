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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

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
public class ExportPanel5 extends JPanel {
 
	private static final long serialVersionUID = 7026526881028815907L;
	private JLabel anotherBlankSpace;
    private JLabel blankSpace;
    private JPanel jPanel1;
    private static JLabel progressDescription;
    private static JProgressBar progressSent;
    private JLabel yetAnotherBlankSpace1;
        
    /**
     * Constructor
     */
    public ExportPanel5() {
        
        super();
                
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.gray);
        
        JLabel textLabel = new JLabel();
        textLabel.setBackground(Color.gray);
        textLabel.setFont(new Font("MS Sans Serif", Font.BOLD, 14));
        textLabel.setText("Export to GenMAPP - Exporting");
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
     * Sets the progress of the progress bar.
     * 
     * @param i
     *            must be an integer between 0-100.
     * @param s
     *            some description string.
     */
    public static void setProgress(final int i, final String s) {
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
        
      
        jPanel1 = new JPanel();
        blankSpace = new JLabel();
        progressSent = new JProgressBar();
        progressDescription = new JLabel();
        anotherBlankSpace = new JLabel();
        yetAnotherBlankSpace1 = new JLabel();

        contentPanel.setLayout(new BorderLayout());  
        contentPanel.add(new JLabel("Exporting to GenMAPP database..."), BorderLayout.NORTH);

        jPanel1.setLayout(new GridLayout(0, 1));
        jPanel1.add(blankSpace);

        progressSent.setStringPainted(true);
        progressSent.setSize(new Dimension(20,150));
        jPanel1.add(progressSent);

        progressDescription.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        progressDescription.setText("Preparing for export...");
        jPanel1.add(progressDescription);

        jPanel1.add(anotherBlankSpace);
        jPanel1.add(yetAnotherBlankSpace1);

        jPanel1.add(new JLabel("When the export completes the \"finish\" button will hightlight below."));
        
        contentPanel.add(jPanel1, BorderLayout.CENTER);


    
        return contentPanel;
    }
    
}
