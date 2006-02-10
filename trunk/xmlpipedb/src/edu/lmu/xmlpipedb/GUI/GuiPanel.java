/*
 * GuiPanel.java
 *
 * Created on February 8, 2006, 9:29 AM
 */

package edu.lmu.xmlpipedb.GUI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import edu.lmu.xmlpipedb.GUI.util.JLabelComponentFactory;

/**
 * A panel with a form to invoke the main operation, whatever that is.
 *
 * @author David Hoffman
 */
public class GuiPanel extends JPanel{

    private AbstractAction _startButtonAction;
    private JButton _loadXmlToDatabaseButton;
    private JTextField _xmlFileBox;
    private JTextField _dataBaseNameBox;
    private JTextField _dataBaseUserBox;
    private JPasswordField _dataBasePasswordBox;
    private JProgressBar _xmlLoadProgress;
    private Gui _parent;

    /**
     * Creates the panel.
     *
     * @param parent the Gui-based application object hosting this
     * panel.
     */
    public GuiPanel(Gui parent) {
        _parent = parent;

        createActions();
        createComponents();
        layoutComponents();
    }

    /**
     * Sets the path for the xml file.
     */
    public void setXmlFilePath(String path) {
        _xmlFileBox.setText(path);
    }

    private void createActions() {
        createStartButtonAction();
    }

    private void createStartButtonAction() {
        final GuiPanel me = this;

        _startButtonAction = new AbstractAction(){
            public void actionPerformed(ActionEvent event) {
                if (!isThereDataInTheFields()) {
                    JOptionPane.showMessageDialog(me, "All fields are required",
                            "Input Needed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    _parent.startXmlToDataBase(_xmlFileBox.getText());
                } catch(Exception e){
                    JOptionPane.showMessageDialog(me, e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    private void createComponents() {
        _loadXmlToDatabaseButton = new JButton(_startButtonAction);
        _loadXmlToDatabaseButton.setText("Start");
        _dataBaseNameBox = new JTextField(20);
        _dataBaseUserBox = new JTextField(20);
        _dataBasePasswordBox = new JPasswordField(20);
        _xmlFileBox = new JTextField(20);
        _xmlLoadProgress = new JProgressBar();
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        // Global panel properties
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Subpanels
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(_loadXmlToDatabaseButton);

        // Text field labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(2, 0, 0, 0);
        add(new JLabel("XML File Path: "), gbc);
        gbc.gridy++;
        add(new JLabel("Database Name: "), gbc);
        gbc.gridy++;
        add(new JLabel("Database User: "), gbc);
        gbc.gridy++;
        add(new JLabel("Database Password: "), gbc);

        // Text fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(2, 3, 0, 0);
        add(_xmlFileBox, gbc);
        gbc.gridy++;
        add(_dataBaseNameBox, gbc);
        gbc.gridy++;
        add(_dataBaseUserBox, gbc);
        gbc.gridy++;
        add(_dataBasePasswordBox, gbc);

        // Progress bar
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 0, 0, 0);
        add(_xmlLoadProgress, gbc);

        // Button panel
        gbc.gridy++;
        add(buttonPanel, gbc);
    }

    private boolean isThereDataInTheFields() {
        //BN - the command line entry point only takes an XML file, no DB information
        return _xmlFileBox.getText().length() > 0;

        //return (_xmlFileBox.getText().length()>0)&&(_dataBaseNameBox.getText().length()>0)&&(_dataBaseUserBox.getText().length()>0)&& (_dataBasePasswordBox.getPassword().length>0);
    }
}
