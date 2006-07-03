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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import shag.LayoutConstants;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile.DisplayOrderPreset;
import edu.lmu.xmlpipedb.gmbuilder.gui.util.CheckBoxList;

/**
 * @author Joey J. Barrett Class: ExportPanel4
 */
public class ExportPanel4 extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextArea systemTablesTextArea;
    private CheckBoxList relationshipTableList;
    private DefaultListModel relationshipTableListModel = new DefaultListModel();
    private JComboBox displayOrderComboBox;
    private JTextField displayOrderTF;

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
     * 
     * @return
     */
    private JPanel getContentPanel() {
        systemTablesTextArea = new JTextArea();
        systemTablesTextArea.setLineWrap(true);
        systemTablesTextArea.setWrapStyleWord(true);
        systemTablesTextArea.setEditable(false);
        systemTablesTextArea.setBackground(new Color(238, 238, 238));
        systemTablesTextArea.setMargin(new Insets(10, 5, 0, 0));

        // Display Order | JComboBox | getDisplayOrders
        displayOrderComboBox = new JComboBox();
        displayOrderTF = new JTextField();
        for (DisplayOrderPreset displayOrderPreset : ExportToGenMAPP.getDatabaseProfile().getAvailableDisplayOrderPresets()) {
            displayOrderComboBox.addItem(displayOrderPreset);
        }

        relationshipTableList = new CheckBoxList();
        JScrollPane listScroller1 = new JScrollPane(relationshipTableList);
        listScroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel leftPanel = new JPanel(new BorderLayout(LayoutConstants.SPACE, LayoutConstants.SPACE));
        leftPanel.add(new JLabel("Verify System Tables:"), BorderLayout.NORTH);
        leftPanel.add(systemTablesTextArea, BorderLayout.CENTER);
        leftPanel.add(createDisplayOrderPanel(), BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout(LayoutConstants.SPACE, LayoutConstants.SPACE));
        rightPanel.add(new JLabel("Verify Relationship Tables:"), BorderLayout.NORTH);
        rightPanel.add(listScroller1, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new BorderLayout(LayoutConstants.SPACE * 4, LayoutConstants.SPACE));
        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.CENTER);

        return contentPanel;
    }

    /**
     * Creates the user interface for entering the desired DisplayOrder.
     * 
     * @return The user interface for entering the desired DisplayOrder
     */
    private JPanel createDisplayOrderPanel() {
        JPanel result = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.right = LayoutConstants.SPACE;
        gbc.insets.bottom = LayoutConstants.SPACE;

        result.add(new JLabel("Display Order:"), gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // TODO Add this back when it is implemented properly.
//        result.add(displayOrderComboBox, gbc);
//        gbc.gridx = 1;
        result.add(displayOrderTF, gbc);

        return result;
    }

    /**
     * Displays any available dynamic information.
     */
    protected void displayAvailableInformation() {
        relationshipTableListModel = new DefaultListModel();

        DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();

        if (databaseProfile.getSystemTables().size() == 0) {
            systemTablesTextArea.setText("NONE");
        } else {
            StringBuffer systemTableText = new StringBuffer();
            for (String systemTable : databaseProfile.getSystemTables().keySet()) {
                systemTableText.append(systemTable).append("\n");
            }
            systemTablesTextArea.setText(systemTableText.toString());
        }

        JCheckBox relationshipTableListItem;
        for (String relationshipTable : databaseProfile.getAvailableRelationshipTables()) {
            relationshipTableListItem = new JCheckBox(relationshipTable);
            relationshipTableListItem.setSelected(true);
            relationshipTableListModel.addElement(relationshipTableListItem);
        }

        relationshipTableList.setModel(relationshipTableListModel);
        displayOrderTF.setText(databaseProfile.getDefaultDisplayOrder());
    }

    /**
     * Submits all relavent information.
     */
    public void submitInformationEntered() {
        DatabaseProfile databaseProfile = ExportToGenMAPP.getDatabaseProfile();

        List<String> relationshipTables = new ArrayList<String>();

        Object[] relationshipTableList = relationshipTableListModel.toArray();
        JCheckBox relationshipTableCheckBox;
        for (Object relationshipTableListItem : relationshipTableList) {
            if (relationshipTableListItem instanceof JCheckBox) {
                relationshipTableCheckBox = (JCheckBox)relationshipTableListItem;
                if (relationshipTableCheckBox.isSelected()) {
                    relationshipTables.add(relationshipTableCheckBox.getText());
                }
            }
        }

        databaseProfile.setRelationshipTableProperties(relationshipTables.toArray(new String[0]));
        databaseProfile.setDisplayOrder(displayOrderTF.getText());
        ExportToGenMAPP.setDatabaseProfile(databaseProfile);
    }
}
