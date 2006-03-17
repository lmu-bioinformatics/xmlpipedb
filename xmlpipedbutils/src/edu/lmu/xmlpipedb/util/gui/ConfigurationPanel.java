/*
 * Created on May 29, 2005
 *
 */
package edu.lmu.xmlpipedb.util.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.lmu.xmlpipedb.util.app.ConfigurationController;
import edu.lmu.xmlpipedb.util.app.Main;
import edu.lmu.xmlpipedb.util.resources.AppResources;


/**
 * @author J.Nicholas
 *
 */
public class ConfigurationPanel extends JPanel implements ActionListener {
	   
	
	public ConfigurationPanel(ConfigurationController cc, Main m  ){
		_configControl = cc;
		_main = m;
        createComponents();
        layoutComponents();
        startListeningToUI();
    }
	
	
    private void layoutComponents() {
        this.setLayout(new BorderLayout());

        Box bxMain = new Box(BoxLayout.X_AXIS);
        this.add(bxMain, BorderLayout.CENTER);
        bxMain.add(new JScrollPane(_propsTable));
        
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(_defaultButton);
        buttonBox.add(Box.createHorizontalStrut(5));
        buttonBox.add(_revertButton);
        buttonBox.add(Box.createHorizontalStrut(5));
        buttonBox.add(_saveButton);
        buttonBox.add(Box.createHorizontalStrut(5));
        buttonBox.add(_cancelButton);
        add(buttonBox, BorderLayout.SOUTH);
        
    }

    /**
     * 
     */
    private void createComponents() {
    	_propsTable = new JTable(_configControl.loadCurrentHibProps());
        _saveButton = new JButton(AppResources.messageString("config_save"));
        _cancelButton = new JButton(AppResources.messageString("config_cancel"));
        _revertButton = new JButton(AppResources.messageString("config_revert"));
        _revertButton.setToolTipText(AppResources.messageString("config_revert_tooltip"));
        _defaultButton = new JButton(AppResources.messageString("config_default"));
        _defaultButton.setToolTipText(AppResources.messageString("config_default_tooltip"));
    } // end createComponents
    

    
    /**
     * Adds listeners to components of interest.
     */
    private void startListeningToUI() {
        _saveButton.addActionListener(this);
        _cancelButton.addActionListener(this);
        _revertButton.addActionListener(this);
        _defaultButton.addActionListener(this);
    }
    
    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent aevt) {
        if (aevt.getSource() == _saveButton) {
        	_configControl.storeHibProperties(_propsTable.getModel());
        } else if (aevt.getSource() == _cancelButton) {
        	_main.cancelAction();
        } else if (aevt.getSource() == _revertButton) {
            _propsTable.setModel(_configControl.loadRevertedHibProps());
        } else if (aevt.getSource() == _defaultButton) {
            _propsTable.setModel(_configControl.loadOriginalHibProps());
        } 
    }
    
    //### DEFINE VARS ###
    private ConfigurationController _configControl;
    private JButton _saveButton, _cancelButton, _revertButton, _defaultButton;
    private JTable _propsTable;
    private Main _main;

    /*
hibernate.dialect net.sf.hibernate.dialect.PostgreSQLDialect
hibernate.connection.driver_class org.postgresql.Driver
hibernate.connection.url jdbc:postgresql://localhost:5432/uniprot
hibernate.connection.username username
hibernate.connection.password password
hibernate.query.substitutions yes 'Y', no 'N'
    */
	
} // end class ImportPanel
