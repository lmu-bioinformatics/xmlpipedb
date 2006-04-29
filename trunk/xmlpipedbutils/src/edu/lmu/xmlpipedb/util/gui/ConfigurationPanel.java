/*
 * Created on May 29, 2005
 *
 */
package edu.lmu.xmlpipedb.util.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.hibernate.cfg.Configuration;

import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.engines.HibernatePropertiesModel;
import edu.lmu.xmlpipedb.util.engines.HibernateProperty;
import edu.lmu.xmlpipedb.util.exceptions.CouldNotLoadPropertiesException;
import edu.lmu.xmlpipedb.util.exceptions.NoHibernatePropertiesException;
import edu.lmu.xmlpipedb.util.resources.AppResources;

/**
 * @author J.Nicholas
 * 
 */
public class ConfigurationPanel extends JPanel implements ActionListener, ItemListener {
    /**
     * 
     */
    private static final long serialVersionUID = -3486506045107182287L;

    
    /**
     * Creates an instance of the ConfigurationPanel, which in turn creates an
     * instance of the ConfigurationEngine for its own private use.
     * 
     * @throws CouldNotLoadPropertiesException
     * @throws FileNotFoundException
     */
    public ConfigurationPanel() throws CouldNotLoadPropertiesException, FileNotFoundException {

        try {
            _configEngine = new ConfigurationEngine();
            _model = _configEngine.getConfigurationModel();
           
        } catch(CouldNotLoadPropertiesException e) {
            throw e;
        } catch(FileNotFoundException e) {
            throw e;
        }

        createComponents();
        layoutComponents();
        startListeningToUI();
    }

    /**
     * Returns a hibernate Configuration object, if the properties object is not
     * empty. If it is empty, a NoHibernatePropertiesException will be thrown.
     * 
     * @return Configuration - an org.hibernate.cfg.Configuration ojbect is
     *         populated with the currently configured properties and returned.
     * @throws NoHibernatePropertiesException
     */
    public Configuration getHibernateConfiguration() throws NoHibernatePropertiesException {
        Configuration config = null;

        try {
            config = _configEngine.getHibernateConfiguration();
        } catch(NoHibernatePropertiesException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            throw e;
        }

        return config;
    }

    /**
     * Lays out the components on the panel
     */
    private void layoutComponents() {
        this.setLayout(new BorderLayout());
        this.add(_topBox, BorderLayout.NORTH);

        // add fields
        this.add( new JScrollPane(_centerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);

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
    private void createComponents() {

        _promptGBC = new GridBagConstraints();
        _fieldGBC = new GridBagConstraints();
        _comboGBC = new GridBagConstraints();
        _panelGBC = new GridBagConstraints();

        setPromptConstraints(_promptGBC);
        setFieldConstraints(_fieldGBC);
        setComboConstraints(_comboGBC);
        setPanelConstraints(_panelGBC);

        _topBox = getTopBox();
        _centerPanel = new JPanel(new GridBagLayout());
        // add entries to combo
        getComboBox(/*STRCAT, */null);
        getFields(/*STRCAT,*/ (String)_typeCombo.getSelectedItem());

        _saveButton = new JButton(AppResources.messageString("config_save"));
        _cancelButton = new JButton(AppResources.messageString("config_cancel"));
        _revertButton = new JButton(AppResources.messageString("config_revert"));
        _revertButton.setToolTipText(AppResources.messageString("config_revert_tooltip"));
        _defaultButton = new JButton(AppResources.messageString("config_default"));
        _defaultButton.setToolTipText(AppResources.messageString("config_default_tooltip"));

        _revertButton.setEnabled(false);
        _defaultButton.setEnabled(false);

    } // end createComponents

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent iEvent) {
        if (iEvent.getStateChange() == ItemEvent.SELECTED) {
            _centerPanel.removeAll();
            getComboBox(/*STRCAT,*/ (String)iEvent.getItem());
            getFields(/*STRCAT,*/ (String)iEvent.getItem());
            this.validate();
            this.repaint();
        }
    }

    /**
     * @param folderUrl
     * @param currFile
     */
    private Box getTopBox() {
        Box topBox = new Box(BoxLayout.X_AXIS);
        String strCat = null;
        Set catSet = _model.getCategories();
        Iterator catIter = catSet.iterator();
        ButtonGroup bg = new ButtonGroup();
        int i = 0;

        while (catIter.hasNext()) {
            // add _catButton buttons
            strCat = (String)catIter.next();
            if (strCat == null)
                throw new NullPointerException("There are " + "no categories in the model.");

            // SET IT TO TRUE IF IT IS THE CURRENT CATEGORY
            if(  strCat.equalsIgnoreCase(_model.getCurrentCategory())){
            	_categoryRB = new JRadioButton(strCat, true );
            } else{
            	_categoryRB = new JRadioButton(strCat, false );
            }
            
            _categoryRB.addActionListener(this);
            bg.add(_categoryRB);
            topBox.add(_categoryRB, BorderLayout.NORTH);
            i++;
        }
        
        if( _model.getCurrentCategory() == null ){
        	_model.setCurrentCategory(strCat);
        }
        
        return topBox;
    }

    private void getComboBox(/*String category,*/ String selected) {
        if (_typeCombo != null)
            _typeCombo.removeItemListener(this);

        String[] types = _model.getTypes(_model.getCurrentCategory());
        _typeCombo = new JComboBox(types);

        if( selected != null)
			_typeCombo.setSelectedItem(selected);
        else{
        	String selectedType = _model.getSelectedType(_model.getCurrentCategory());
	        if (selectedType == null)
	            _typeCombo.setSelectedIndex(0);
	        else
	            _typeCombo.setSelectedItem(selectedType);
        }
        _typeCombo.addItemListener(this);
        _centerPanel.add(_typeCombo, _comboGBC);
    }

    /**
     * @param config
     * @param path
     */
    private void getFields(/*String category, */String type) {
        ArrayList propsArray = _model.getProperties(_model.getCurrentCategory(), type);
        Iterator props = propsArray.iterator();

        _propSelected = new JCheckBox[propsArray.size()];
        //_propName = new JLabel[propsArray.size()];
        _propValue = new JTextField[propsArray.size()];
        int i = 0;
        while (props.hasNext()) {
            HibernateProperty hp = (HibernateProperty)props.next();

            //Babak - Changed the implementation to use the JCheckBox's text label instead of a seperate 
            _propSelected[i] = new JCheckBox( hp.getName() );
            //_propName[i] = new JLabel(hp.getName());
            
            // check if the field is a password field
            if (hp.getName().indexOf("password") != -1)
                _propValue[i] = new JPasswordField(hp.getValue(), 30);
            else
                _propValue[i] = new JTextField(hp.getValue(), 30);
            
            final JCheckBox thisCheckBox =_propSelected[i]; 
            _propValue[i].getDocument().addDocumentListener(
            		new DocumentListener(){

						public void insertUpdate(DocumentEvent e) {
							thisCheckBox.setSelected(true);
						}

						public void removeUpdate(DocumentEvent e) {
							thisCheckBox.setSelected(true);
						}

						public void changedUpdate(DocumentEvent e) {
							thisCheckBox.setSelected(true);
						}
            			
            		}
            );
            
            if (hp.isSaved()) {
                _propSelected[i].setSelected(true);
            }

            _centerPanel.add(_propSelected[i], _promptGBC);
            //_centerPanel.add(_propName[i], _promptGBC);
            _centerPanel.add(_propValue[i], _fieldGBC);

            i++;
        }
    } // end createConfigPanel

    private void setPromptConstraints(GridBagConstraints gbc) {
        gbc.insets = PROMPT_INSETS;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
    }

    private void setFieldConstraints(GridBagConstraints gbc) {
        gbc.insets = FIELD_INSETS;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
    }

    private void setComboConstraints(GridBagConstraints gbc) {
        gbc.insets = FIELD_INSETS;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
    }

    private void setPanelConstraints(GridBagConstraints gbc) {
        // gbc.insets = FIELD_INSETS;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
    }

    /**
     * Adds listeners to components of interest.
     */
    private void startListeningToUI() {
        // FIXME This is a very fragile binding...must redesign...
        _saveButton.addActionListener(this);
        _cancelButton.addActionListener(this);
        _revertButton.addActionListener(this);
        _defaultButton.addActionListener(this);
    }

    private void saveAction() {
        // get current info
        String type = (String)_typeCombo.getSelectedItem();
        //String logging = "";

        // Prepare a new model object, which will be used to save the
        // properties.
        // Add all the other saved properties to the saveModel, except the
        // properties
        // of this category, which will be overwritten.
        HibernatePropertiesModel saveModel = new HibernatePropertiesModel();
        Iterator modelIter = _model.getProperties();
        while (modelIter.hasNext()) {
            HibernateProperty hp = _model.getProperty((String)modelIter.next());
            // if it is this category, don't add it no matter what
            if (hp.getCategory().equals(_model.getCurrentCategory()))
                continue;
            if (hp.isSaved()){
                saveModel.add(hp);
                //logging += hp.toString() + "\n";
            }
        }
        //System.out.print(logging);
        //logging = "";

        for (int i = 0; i < _propValue.length; i++) {
            if (_propSelected[i].isSelected()) {
                // if it is checked, add, which will add a new or replace an
                // existing property

            	HibernateProperty hp = new HibernateProperty(_model.getCurrentCategory(), type, _propSelected[i].getText(), _propValue[i].getText(), true); 
                saveModel.add(hp);
                //logging += hp.toString() + "\n";

            	saveModel.add(hp);

            } 
            // else -- if not select, just move on to the next one
        }
        //System.out.print(logging);
        
        saveModel.setCurrentType(type);
        saveModel.setCurrentCategory(_model.getCurrentCategory());

        _configEngine.saveProperties(saveModel);

        // update the model to reflect what is now saved.
        try {
            _model = _configEngine.getConfigurationModel();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent aevt) {
        if (aevt.getSource() == _saveButton) {
            saveAction();

        } else if (aevt.getSource() == _cancelButton) {
            this.setVisible(false);
            this.validate();
        } else {

            _model.setCurrentCategory( aevt.getActionCommand() );
            _centerPanel.removeAll();
            getComboBox(/*STRCAT, */null);
            getFields(/*STRCAT,*/ (String)_typeCombo.getSelectedItem());
            this.validate();
            this.repaint();
        }
    }

    // ### DEFINE VARS ###
    private JButton _saveButton, _cancelButton, _revertButton, _defaultButton;
    private JComboBox _typeCombo;
    private HibernatePropertiesModel _model;

    // control arrays
    private JCheckBox[] _propSelected;
    //private JLabel[] _propName;
    private JTextField[] _propValue;
    private JRadioButton _categoryRB;

    //private Box _centerBox;
    private JPanel _centerPanel;
    private Box _topBox;
    private ConfigurationEngine _configEngine;

    // gridbag vars
    private static final Insets PROMPT_INSETS = new Insets(0, 0, 5, 5);
    private static final Insets FIELD_INSETS = new Insets(0, 0, 5, 0);

    private GridBagConstraints _promptGBC;
    private GridBagConstraints _fieldGBC;
    private GridBagConstraints _comboGBC;
    private GridBagConstraints _panelGBC;

} // end class ImportPanel
