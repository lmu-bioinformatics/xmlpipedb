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
import java.util.Properties;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import edu.lmu.xmlpipedb.util.app.ConfigurationController;
import edu.lmu.xmlpipedb.util.app.HibernatePropertiesModel;
import edu.lmu.xmlpipedb.util.app.HibernateProperty;

/**
 * @author J.Nicholas
 * 
 */
public class ConfigurationPanel extends JPanel implements ActionListener, ItemListener {
	String STRCAT;
	
	/**
     * @param cc
     * @param m
     * @param url
     * @param currFile
     */
    public ConfigurationPanel(HibernatePropertiesModel model, Properties props, ConfigurationController configController) {
    	_model = model;
    	_configController = configController;
    	
        createComponents();
        layoutComponents();
        startListeningToUI();
    }

    /**
     * Lays out the components on the panel
     */
    private void layoutComponents() {
        this.setLayout(new BorderLayout());
        this.add(_topBox, BorderLayout.NORTH);
        
		// add fields
        this.add(_centerPanel, BorderLayout.CENTER);
        
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

		setPromptConstraints( _promptGBC );
		setFieldConstraints( _fieldGBC );
		setComboConstraints( _comboGBC );
		setPanelConstraints(_panelGBC);
		
        _topBox = getTopBox();
        _centerPanel = new JPanel(new GridBagLayout());
		// add entries to combo
		getComboBox(STRCAT, null);
		getFields(STRCAT, (String)_typeCombo.getSelectedItem());
		
		_saveButton = new JButton("Save");
        _cancelButton = new JButton("Cancel");
        _revertButton = new JButton("Revert");
        _revertButton.setToolTipText("Revert to values when app was started");
        _defaultButton = new JButton("Default");
        _defaultButton.setToolTipText("Default values from hibernate");
        
        
        /*  --- make an option to pass params via hashmap --- 
        _saveButton = new JButton(AppResources.messageString("config_save"));
        _cancelButton = new JButton(AppResources.messageString("config_cancel"));
        _revertButton = new JButton(AppResources.messageString("config_revert"));
        _revertButton.setToolTipText(AppResources.messageString("config_revert_tooltip"));
        _defaultButton = new JButton(AppResources.messageString("config_default"));
        _defaultButton.setToolTipText(AppResources.messageString("config_default_tooltip"));*/
        
        _revertButton.setEnabled(false);
        _defaultButton.setEnabled(false);

    } // end createComponents


    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent iEvent) {
        if (iEvent.getStateChange() == ItemEvent.SELECTED) {
            _centerPanel.removeAll();
            getComboBox(STRCAT, (String)iEvent.getItem());
            getFields(STRCAT, (String)iEvent.getItem());
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

		Set catSet = _model.getCategories();
		Iterator catIter = catSet.iterator();
		ButtonGroup bg = new ButtonGroup();
		int i = 0;

		while( catIter.hasNext() ){
			// add _catButton buttons
			STRCAT = (String)catIter.next();
			if( STRCAT == null )
				throw new NullPointerException("There are " +
						"no categories in the model.");
			
			_categoryRB = new JRadioButton(STRCAT);
			_categoryRB.addActionListener(this);
			bg.add(_categoryRB);
			_categoryRB.setSelected(true);
			topBox.add(_categoryRB, BorderLayout.NORTH);
			i++;
		}

		return topBox;
	}


	private void getComboBox( String category, String selected ){
		if(_typeCombo != null )
			_typeCombo.removeItemListener(this);
		
		String[] types = _model.getTypes(category);
		_typeCombo = new JComboBox( types );
		if( selected != null)
			_typeCombo.setSelectedItem(selected);
		else{
			String selectedType = _model.getSelectedType( category );
			if( selectedType == null )
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
	private void getFields(String category, String type) {
		ArrayList propsArray = _model.getProperties(category, type);
		Iterator props = propsArray.iterator();

		_propSelected = new JCheckBox[propsArray.size()];
		_propName  = new JLabel[propsArray.size()];
		_propValue  = new JTextField[propsArray.size()];
		int i = 0;
		while (props.hasNext()) {
			HibernateProperty hp = (HibernateProperty) props.next();
			
			_propSelected[i] = new JCheckBox();
			_propName[i] = new JLabel( hp.getName() );
			// check if the field is a password field
			if( hp.getName().indexOf("password") != -1 )
				_propValue[i] = new JPasswordField( hp.getValue(), 30 );
			else
				_propValue[i] = new JTextField( hp.getValue(), 30 );
			
			if( hp.isSaved() ){
				_propSelected[i].setSelected(true);
			}
			
			_centerPanel.add(_propSelected[i], _promptGBC);
			_centerPanel.add(_propName[i], _promptGBC);
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
	
	private void setButtonConstraints(GridBagConstraints gbc) {
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
	}
	
	private void setPanelConstraints(GridBagConstraints gbc) {
	//	gbc.insets = FIELD_INSETS;
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

    
    private void saveAction(){
//    	 get current info
    	String category = STRCAT;
    	String type = (String)_typeCombo.getSelectedItem();
    	
    	// Prepare a new model object, which will be used to save the properties.
    	// Add all the other saved properties to the saveModel, except the properties
    	// of this category, which will be overwritten.
        HibernatePropertiesModel saveModel = new HibernatePropertiesModel();
       	Iterator modelIter = _model.getProperties();
       	while( modelIter.hasNext() ){
       		HibernateProperty hp = _model.getProperty((String)modelIter.next());
       		// if it is this category, don't add it no matter what
       		if( hp.getCategory().equals(category) )
       			continue;
       		if( hp.isSaved() )
       			saveModel.add(hp);
       	}
       	
       	for(int i = 0; i < _propValue.length; i++ ){
       		if( _propSelected[i].isSelected() ){
//       		 if it is checked, add, which will add a new or replace an existing property
				saveModel.add(new HibernateProperty(category, type, _propName[i].getText(), _propValue[i].getText(), true ));
       		}else{
       			// not sure we need an else
       		}
       	}
       	saveModel.setCurrentType( type);
       	saveModel.setCurrentCategory(category);
       	
        _configController.saveProperties(saveModel);
        
        //update the model to reflect what is now saved.
        try {
			_model = _configController.getConfigurationModel();
		} catch (FileNotFoundException e) {
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
       	
        	STRCAT = aevt.getActionCommand(); 
            _centerPanel.removeAll();
            getComboBox(STRCAT, null);
            getFields(STRCAT, (String)_typeCombo.getSelectedItem());
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
    private JLabel[] _propName;
    private JTextField[] _propValue;
    private JRadioButton _categoryRB;

    //private Box _centerBox;
    private JPanel _centerPanel;
    private Box _topBox;
    private ConfigurationController _configController;
    
    // gridbag vars
	private static final Insets PROMPT_INSETS = new Insets(0,0,5,5);
	private static final Insets FIELD_INSETS = new Insets(0,0,5,0);
	
	private GridBagConstraints _promptGBC;
	private GridBagConstraints _fieldGBC;
	private GridBagConstraints _comboGBC;
	private GridBagConstraints _panelGBC;
	
} // end class ImportPanel
