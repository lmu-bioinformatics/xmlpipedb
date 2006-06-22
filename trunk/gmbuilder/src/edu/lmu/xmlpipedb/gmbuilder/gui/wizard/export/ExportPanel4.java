/********************************************************
 * Filename: ExportPanel4.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: The fourth panel displayed in the export
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
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.gui.util.CheckBoxList;

/**
 * @author Joey J. Barrett
 * Class: ExportPanel4
 */
public class ExportPanel4 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea systemTablesTextArea;
	private CheckBoxList relationshipTableList;
	private DefaultListModel relationshipTableListModel = new DefaultListModel();
	
    /**
     * Constructor.
     */
    protected ExportPanel4() {
        
        super();
                
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.gray);
        
        JLabel textLabel = new JLabel();
        textLabel.setBackground(Color.gray);
        textLabel.setFont(new Font("MS Sans Serif", Font.BOLD, 14));
        textLabel.setText("Export to GenMAPP - Verify Database Settings");
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
     * The content for the panel.
     * @return
     */
    private JPanel getContentPanel() {            
        
    	JPanel leftPanel = new JPanel(new BorderLayout());
    	systemTablesTextArea = new JTextArea();
    	systemTablesTextArea.setLineWrap(true);
    	systemTablesTextArea.setWrapStyleWord(true);
    	systemTablesTextArea.setEditable(false);
    	systemTablesTextArea.setBackground(new Color(238, 238, 238));
    	systemTablesTextArea.setMargin(new Insets(10, 5, 0, 0));
    	
    	leftPanel.add(new JLabel("Verify System Tables:"), BorderLayout.NORTH);
    	leftPanel.add(systemTablesTextArea, BorderLayout.CENTER);
    	leftPanel.setBorder(new EmptyBorder(new Insets(0, 30, 0, 70)));
    	
    	JPanel rightPanel = new JPanel(new BorderLayout());
    	relationshipTableList = new CheckBoxList();
    	JScrollPane listScroller1 = new JScrollPane(relationshipTableList);
    	listScroller1.setPreferredSize(new Dimension(200, 10));
    	listScroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	rightPanel.add(new JLabel("Verify Relationship Tables:"), BorderLayout.NORTH);
    	rightPanel.add(listScroller1, BorderLayout.CENTER);
    	rightPanel.setBorder(new EmptyBorder(new Insets(0, 70, 0, 10)));
    	
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.EAST);
        
        return contentPanel;
    }
    
    
	/**
	 * Displays any available dynamic information.
	 */
	protected void displayAvailableInformation() {
		
		relationshipTableListModel = new DefaultListModel();
		
		DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();
		
		
		if(databaseProfile.getSystemTables().size() == 0) {
			systemTablesTextArea.setText("NONE");
		} else {
			StringBuffer systemTableText = new StringBuffer();
			for(String systemTable : databaseProfile.getSystemTables().keySet()) {
				systemTableText.append(systemTable).append("\n");
			}
			systemTablesTextArea.setText(systemTableText.toString());
		}
	
		JCheckBox relationshipTableListItem;
		for(String relationshipTable : databaseProfile.getAvailableRelationshipTables()) {
			relationshipTableListItem = new JCheckBox(relationshipTable);
			relationshipTableListItem.setSelected(true);
			relationshipTableListModel.addElement(relationshipTableListItem);
		}
		
		relationshipTableList.setModel(relationshipTableListModel);
	}

	/**
	 * Submits all relavent information.
	 */
	public void submitInformationEntered() {
		DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();
		
		List<String> relationshipTables = new ArrayList<String>();
		
		Object[] relationshipTableList = relationshipTableListModel.toArray();
		JCheckBox relationshipTableCheckBox;
		for(Object relationshipTableListItem : relationshipTableList) {
			if(relationshipTableListItem instanceof JCheckBox) {
				relationshipTableCheckBox = (JCheckBox) relationshipTableListItem;
				if(relationshipTableCheckBox.isSelected()) {
					relationshipTables.add(relationshipTableCheckBox.getText());
				}
			}
		}
		
		databaseProfile.setRelationshipTableProperties(relationshipTables.toArray(new String[0]));
		ExportToGenMAPP.setDatabaseProfile(databaseProfile);
		
	}
}
