/********************************************************
 * Filename: ExportPanel3.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: The third panel displayed in the export
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.SystemType;


/**
 * @author Joey J. Barrett
 * Class: ExportPanel3
 */
public class ExportPanel3 extends JPanel {
	
	private static final long serialVersionUID = 3826794595867823934L;
	private static final String MOVE_SELECTED = "Move Selected >>";
	private static final String MOVE_ALL = "Move All >>";
	private static final String REMOVE_ALL = "<< Remove All";
	
	private JList properTableList;
	private JList chosenProperTableList;
	private JList improperTableList;
	private JList chosenImproperTableList;
	
	private DefaultListModel properTableListModel = new DefaultListModel();
	private DefaultListModel chosenProperTableListModel = new DefaultListModel();
	private DefaultListModel improperTableListModel = new DefaultListModel();
	private DefaultListModel chosenImproperTableListModel = new DefaultListModel();
        
    /**
     * Constructor.
     */
    protected ExportPanel3() {
        
        super();
                
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.gray);
        
        JLabel textLabel = new JLabel();
        textLabel.setBackground(Color.gray);
        textLabel.setFont(new Font("MS Sans Serif", Font.BOLD, 14));
        textLabel.setText("Export to GenMAPP - Table Settings");
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
     * Provides all the content for the panel.
     * @return
     */
    private JPanel getContentPanel() {  
    	
    	properTableList = new JList(properTableListModel);
    	chosenProperTableList = new JList(chosenProperTableListModel);
    	improperTableList = new JList(improperTableListModel);
    	chosenImproperTableList = new JList(chosenImproperTableListModel);
    	
     	properTableList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	chosenProperTableList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	improperTableList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	chosenImproperTableList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	
    	JButton moveSelected1 = new JButton(MOVE_SELECTED);
    	moveSelected1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				moveSelectedProper();
			}	
    	});
    	JButton moveAll1 = new JButton(MOVE_ALL);
    	moveAll1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				moveAllProper();
			}
    	});
    	JButton removeAll1 = new JButton(REMOVE_ALL);
    	removeAll1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				removeAllProper();
			}
    	});
    	JPanel buttonPanel1 = new JPanel(new BorderLayout());
    	buttonPanel1.add(moveSelected1, BorderLayout.NORTH);
    	buttonPanel1.add(moveAll1, BorderLayout.CENTER);
    	buttonPanel1.add(removeAll1, BorderLayout.SOUTH);
    	buttonPanel1.setBorder(new EmptyBorder(new Insets(10, 30, 10, 30)));
    	
    	
    	JButton moveSelected2 = new JButton(MOVE_SELECTED);
    	moveSelected2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				moveSelectedImproper();
			}	
    	});
    	JButton moveAll2 = new JButton(MOVE_ALL);
    	moveAll2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				moveAllImproper();
			}
    	});
    	JButton removeAll2 = new JButton(REMOVE_ALL);
    	removeAll2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				removeAllImproper();
			}
    	});
    	JPanel buttonPanel2 = new JPanel(new BorderLayout());
    	buttonPanel2.add(moveSelected2, BorderLayout.NORTH);
    	buttonPanel2.add(moveAll2, BorderLayout.CENTER);
    	buttonPanel2.add(removeAll2, BorderLayout.SOUTH);
    	buttonPanel2.setBorder(new EmptyBorder(new Insets(10, 30, 10, 30)));
    	
    	JScrollPane listScroller1 = new JScrollPane(properTableList);
    	listScroller1.setPreferredSize(new Dimension(150, 80));
    	listScroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
 
    	JScrollPane listScroller2 = new JScrollPane(chosenProperTableList);
    	listScroller2.setPreferredSize(new Dimension(150, 80));
    	listScroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	JScrollPane listScroller3 = new JScrollPane(improperTableList);
    	listScroller3.setPreferredSize(new Dimension(150, 80));
    	listScroller3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	JScrollPane listScroller4 = new JScrollPane(chosenImproperTableList);
    	listScroller4.setPreferredSize(new Dimension(150, 80));
    	listScroller4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	JPanel topPanel = new JPanel(new BorderLayout());
    	topPanel.add(new JLabel("Select Proper System Tables"), BorderLayout.NORTH);
    	topPanel.add(listScroller1, BorderLayout.WEST);
    	topPanel.add(buttonPanel1, BorderLayout.CENTER);
    	topPanel.add(listScroller2, BorderLayout.EAST);
    	
    	JPanel bottomPanel = new JPanel(new BorderLayout());
    	bottomPanel.add(new JLabel("Select Improper System Tables"), BorderLayout.NORTH);
    	bottomPanel.add(listScroller3, BorderLayout.WEST);
    	bottomPanel.add(buttonPanel2, BorderLayout.CENTER);
    	bottomPanel.add(listScroller4, BorderLayout.EAST);
    	
    	
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        
        return contentPanel;
    }
    
    /**
     * Displays any available dynamic information.
     */
    protected void displayAvailableInformation() {
    	
    	properTableListModel = new DefaultListModel();
    	chosenProperTableListModel = new DefaultListModel();
    	improperTableListModel = new DefaultListModel();
    	chosenImproperTableListModel = new DefaultListModel();
    	
    	DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();
    	
    	if(databaseProfile.getSystemTables() != null) {
    		for(Entry<String, SystemType> systemTable : databaseProfile.getSystemTables().entrySet()) {
    			if(systemTable.getValue() == SystemType.Proper ||
    					systemTable.getValue() == SystemType.Primary) {
    				chosenProperTableListModel.addElement(systemTable.getKey());
    			} else {
    				chosenImproperTableListModel.addElement(systemTable.getKey());
    			}
    		}
    	}
    	
		for(Entry<String, SystemType> systemTable : databaseProfile.getAvailableSystemTables().entrySet()) {
			if((systemTable.getValue() == SystemType.Proper) && 
					!chosenProperTableListModel.contains(systemTable.getKey())) {
				properTableListModel.addElement(systemTable.getKey());
			} else if((systemTable.getValue() == SystemType.Primary) && 
					!chosenProperTableListModel.contains(systemTable.getKey())) {
				chosenProperTableListModel.addElement(systemTable.getKey());
			} else if(systemTable.getValue() == SystemType.Improper &&
					!chosenImproperTableListModel.contains(systemTable.getKey())){
				improperTableListModel.addElement(systemTable.getKey());
			}
		}
		
    	properTableList.setModel(properTableListModel);
    	chosenProperTableList.setModel(chosenProperTableListModel);
    	improperTableList.setModel(improperTableListModel);
    	chosenImproperTableList.setModel(chosenImproperTableListModel);
    }
    
	/**
	 * Moves a selection in the proper systems table list 
	 * to the chosen proper systems table list.
	 */
	protected void moveSelectedProper() {
		
		int[] selectedIndices = properTableList.getSelectedIndices();
		
		for(int selectedIndex : selectedIndices) {
			chosenProperTableListModel.addElement(properTableListModel.elementAt(selectedIndex));
		}
		
		for(int selectedIndex : selectedIndices) {
			properTableListModel.removeElementAt(selectedIndex);
		}
		
		properTableList.setModel(properTableListModel);
		chosenProperTableList.setModel(chosenProperTableListModel);
	}
    
    /**
     * Moves all items in the proper systems table list
     * to the chosen proper systems table list.
     */
    protected void moveAllProper() {
		for(int i = 0; i < properTableListModel.getSize(); i++) {
			chosenProperTableListModel.addElement(properTableListModel.get(i));	
		}
		properTableListModel = new DefaultListModel();
			
		properTableList.setModel(properTableListModel);
		chosenProperTableList.setModel(chosenProperTableListModel);
	}
    
    /**
     * Removes all items from the chosen proper system tables
     * list except for the primary system table.
     */
    protected void removeAllProper() {
    	DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();
    	
		for(int i = 0; i < chosenProperTableListModel.getSize(); i++) {
			if(!chosenProperTableListModel.get(i).equals(databaseProfile.getPrimarySystemTable())) {
				properTableListModel.addElement(chosenProperTableListModel.get(i));
			}
		}
    	chosenProperTableListModel = new DefaultListModel();
    	chosenProperTableListModel.addElement(databaseProfile.getPrimarySystemTable());
    	
		properTableList.setModel(properTableListModel);
		chosenProperTableList.setModel(chosenProperTableListModel);
	}

	/**
	 * Moves a selected item from the improper system tables
	 * list to the chosen improper system tables list.
	 */
	protected void moveSelectedImproper() {

		int[] selectedIndices = improperTableList.getSelectedIndices();
		
		for(int selectedIndex : selectedIndices) {
			chosenImproperTableListModel.addElement(improperTableListModel.elementAt(selectedIndex));
		}
		
		for(int selectedIndex : selectedIndices) {
			improperTableListModel.removeElementAt(selectedIndex);
		}
		
		improperTableList.setModel(improperTableListModel);
		chosenImproperTableList.setModel(chosenImproperTableListModel);
	}

	/**
	 * Moves all items from the improper system tables list
	 * to the chosen improper system tables list.
	 */
	protected void moveAllImproper() {
		for(int i = 0; i < improperTableListModel.getSize(); i++) {
			chosenImproperTableListModel.addElement(improperTableListModel.get(i));
		}
		improperTableListModel = new DefaultListModel();
		
		improperTableList.setModel(improperTableListModel);
		chosenImproperTableList.setModel(chosenImproperTableListModel);
	}

	/**
	 * Removes all tables from the chosen improper
	 * tables list to the improper chosen tables list.
	 */
	protected void removeAllImproper() {
		for(int i = 0; i < chosenImproperTableListModel.getSize(); i++) {
			improperTableListModel.addElement(chosenImproperTableListModel.get(i));
		}
		chosenImproperTableListModel = new DefaultListModel();
		
		improperTableList.setModel(improperTableListModel);
		chosenImproperTableList.setModel(chosenImproperTableListModel);
	}


	/**
	 * Submits all relavent information.
	 */
	protected void submitInformationEntered() {
		DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();

		databaseProfile.setTableProperties(chosenProperTableListModel.toArray(), 
				chosenImproperTableListModel.toArray());
		
		ExportToGenMAPP.setDatabaseProfile(databaseProfile);
	}
}
