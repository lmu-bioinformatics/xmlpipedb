/*
 * Created on May 29, 2005
 *
 */
package edu.lmu.xmlpipedb.util.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
public class ConfigurationPanel extends JPanel implements ActionListener, ItemListener {
    /**
     * 
     * 
     * @param cc
     * @param m
     * @param url
     * @param currFile
     */
    public ConfigurationPanel(ConfigurationController cc, Main m, String url, String currFile) {
        _configControl = cc;
        _main = m;
        _url = url;
        createComponents(url, currFile);
        layoutComponents();
        startListeningToUI();

        // cc.getHibernateConfigPanel(AppResources.optionString("hibernate_conf_properties_url"));
        /*
         * hibernate_general_properties_url hibernate_platforms_properties_url
         * hibernate_connection_pools_properties_url
         * hibernate_other_properties_url
         */
    }

    /**
     * @param m
     * @param url
     * @param currFile
     */
    public ConfigurationPanel(Main m, String url, String currFile) {
        _configControl = new ConfigurationController(AppResources.optionString("hibernate_properties_url"), this);
        _main = m;
        _url = url;
        createComponents(url, currFile);
        layoutComponents();
        startListeningToUI();

        // cc.getHibernateConfigPanel(AppResources.optionString("hibernate_conf_properties_url"));
        /*
         * hibernate_general_properties_url hibernate_platforms_properties_url
         * hibernate_connection_pools_properties_url
         * hibernate_other_properties_url
         */
    }

    /**
     * Lays out the components on the panel
     */
    private void layoutComponents() {
        this.setLayout(new BorderLayout());

        // Box bxMain = new Box(BoxLayout.X_AXIS);
        // this.add(bxMain, BorderLayout.CENTER);
        // bxMain.add(new JScrollPane(_panel));
        // bxMain.add(new JScrollPane(_propsTable));
        this.add(new JScrollPane(_panel), BorderLayout.CENTER);

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

        this.validate();
    }


    /**
     * @param url
     * @param currFile
     */
    private void createComponents(String url, String currFile) {
        _panel = null;
        _panel = _configControl.getHibernateConfigPanel(url, currFile);

        // _propsTable = new JTable(_configControl.loadCurrentHibProps());
        _saveButton = new JButton(AppResources.messageString("config_save"));
        _cancelButton = new JButton(AppResources.messageString("config_cancel"));
        _revertButton = new JButton(AppResources.messageString("config_revert"));
        _revertButton.setToolTipText(AppResources.messageString("config_revert_tooltip"));
        _defaultButton = new JButton(AppResources.messageString("config_default"));
        _defaultButton.setToolTipText(AppResources.messageString("config_default_tooltip"));
        
        _revertButton.setEnabled(false);
        _defaultButton.setEnabled(false);

    } // end createComponents


    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent iEvent) {
        if (iEvent.getStateChange() == ItemEvent.SELECTED) {
            reloadPanel((String)iEvent.getItem());
        }
    }

    /**
     * @param item
     */
    private void reloadPanel(String item) {
        stopListeningToUI();
        removeAll();
        _panel = _configControl.getHibernateConfigPanel(_url, item + ".properties");
        layoutComponents();
        revalidate();
        startListeningToUI();
    }

    /**
     * Adds listeners to components of interest.
     */
    private void startListeningToUI() {
        // FIXME This is a very fragile binding...must redesign...
        for (int i = 0; i < _panel.getComponentCount(); i++) {
            if (_panel.getComponent(i).getClass().getName().contains("JComboBox"))
                ((JComboBox)_panel.getComponent(i)).addItemListener(this);
        }
        _saveButton.addActionListener(this);
        _cancelButton.addActionListener(this);
        _revertButton.addActionListener(this);
        _defaultButton.addActionListener(this);
    }

    /**
     * Stops the GUI listners
     */
    private void stopListeningToUI() {
        for (int i = 0; i < _panel.getComponentCount(); i++) {
            if (_panel.getComponent(i).getClass().getName().contains("JComboBox"))
                ((JComboBox)_panel.getComponent(i)).removeItemListener(this);
        }
        _saveButton.removeActionListener(this);
        _cancelButton.removeActionListener(this);
        _revertButton.removeActionListener(this);
        _defaultButton.removeActionListener(this);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent aevt) {
        if (aevt.getSource() == _saveButton) {
            _configControl.saveProperties(_panel);
            // _configControl.storeHibProperties(_propsTable.getModel());
        } else if (aevt.getSource() == _cancelButton) {
            _main.cancelAction();
        } else if (aevt.getSource() == _revertButton) {
            _propsTable.setModel(_configControl.loadRevertedHibProps());
        } else if (aevt.getSource() == _defaultButton) {
            _propsTable.setModel(_configControl.loadOriginalHibProps());
        }
    }

    // ### DEFINE VARS ###
    private ConfigurationController _configControl;
    private JButton _saveButton, _cancelButton, _revertButton, _defaultButton;
    private JTable _propsTable;
    private Main _main;
    private JPanel _panel;
    private String _url;


} // end class ImportPanel
