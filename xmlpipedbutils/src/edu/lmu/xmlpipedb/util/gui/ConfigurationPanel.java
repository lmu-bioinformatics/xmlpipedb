/*
 * Created on May 29, 2005
 *
 */
package edu.lmu.xmlpipedb.util.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import edu.lmu.xmlpipedb.util.resources.AppResources;

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
//    	_props = props;
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
        
        
		_promptGBC = new GridBagConstraints();
		_fieldGBC = new GridBagConstraints();
//		_buttonGBC = new GridBagConstraints();
		_panelGBC = new GridBagConstraints();

		setPromptConstraints( _promptGBC );
		setFieldConstraints( _fieldGBC );
//		setButtonConstraints( _buttonGBC );
		setPanelConstraints(_panelGBC);

        
        // Box bxMain = new Box(BoxLayout.X_AXIS);
        // this.add(bxMain, BorderLayout.CENTER);
        // bxMain.add(new JScrollPane(_panel));
        // bxMain.add(new JScrollPane(_propsTable));
        //this.add(new JScrollPane(_panel), BorderLayout.CENTER);
        
		
		
		
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
		
		//_centerBox = new Box(BoxLayout.Y_AXIS);
		//_centerBox.add(_typeCombo);
		// add fields
		//_centerBox.add(getFields(strCat, (String)_typeCombo.getSelectedItem()));
    	
/*    	_panel = null;
        _panel = _configControl.getHibernateConfigPanel(url, currFile);
*/
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
        	//FIXME make sure this is doing the right thing here
            _centerPanel.removeAll();
            getComboBox(STRCAT, (String)iEvent.getItem());
            getFields(STRCAT, (String)iEvent.getItem());
        	_centerPanel.validate();
            this.validate();
        }
    }

/*    *//**
     * @param item
     *//*
    private void reloadPanel(String item) {
        stopListeningToUI();
        removeAll();
        //_panel = _configControl.getHibernateConfigPanel(_url, item + ".properties");
        layoutComponents();
        revalidate();
        startListeningToUI();
    }*/
    
    
    
	/**
	 * @param folderUrl
	 * @param currFile
	 */
	private Box getTopBox() {
//		JPanel config = new JPanel();
//		config.setLayout(new BorderLayout());
		Box topBox = new Box(BoxLayout.X_AXIS);

//		File f = new File(folderUrl);
//		File[] files = f.listFiles(new PropertiesFileNameFilter());
		
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
		//this.add(centerBox, BorderLayout.CENTER);
		
		
		
		
/*		if (files.length > 1) {
			ArrayList fileList = new ArrayList();
			String selected = null;

			for (int i = 0; i < files.length; i++) {
				String filename = files[i].getName();
				fileList.add(filename.substring(0, filename.lastIndexOf(".")));
				if (filename.equals(currFile)) {
					createConfigPanel(config, files[i].getPath());
					selected = filename.substring(0, filename.lastIndexOf("."));
				}
			}// end for
			JComboBox head = new JComboBox(fileList.toArray());
			head.setSelectedItem(selected);
			// head.addItemListener(new MyItemListener());
			config.add(head, BorderLayout.NORTH);

		} else {
			if (files[0].isFile())
				createConfigPanel(config, files[0].getPath());
		}

		return config;*/
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
		//Properties props = loadProperties(path);
		//Box boxes = new Box(BoxLayout.Y_AXIS);

		ArrayList propsArray = _model.getProperties(category, type);
		Iterator props = propsArray.iterator();
		//Enumeration props = _model.getProperties(category, type);
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
			
			/*Box box = new Box(BoxLayout.X_AXIS);
			box.add(_propSelected[i]);
			box.add(_propName[i]);
			box.add(_propValue[i]);
			boxes.add(box);
			boxes.add(Box.createVerticalStrut(5));*/
			// Property p = new Property( key, props.getProperty(key));
			// hptm.addProperty(p);
			i++;
		}
		//boxes.add(Box.createVerticalGlue());
		//config.add(boxes, BorderLayout.CENTER);
		//return boxes;
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
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
	}
	
	private void setComboConstraints(GridBagConstraints gbc) {
		gbc.insets = FIELD_INSETS;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTHWEST;
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
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
	}
	
    /**
     * Adds listeners to components of interest.
     */
    private void startListeningToUI() {
        // FIXME This is a very fragile binding...must redesign...
     /*   for (int i = 0; i < _panel.getComponentCount(); i++) {
            if (_panel.getComponent(i).getClass().getName().contains("JComboBox"))
                ((JComboBox)_panel.getComponent(i)).addItemListener(this);
        }*/
        
        _saveButton.addActionListener(this);
        _cancelButton.addActionListener(this);
        _revertButton.addActionListener(this);
        _defaultButton.addActionListener(this);
    }

    /**
     * Stops the GUI listners
     */
    private void stopListeningToUI() {
/*        for (int i = 0; i < _panel.getComponentCount(); i++) {
            if (_panel.getComponent(i).getClass().getName().contains("JComboBox"))
                ((JComboBox)_panel.getComponent(i)).removeItemListener(this);
        }*/
        _saveButton.removeActionListener(this);
        _cancelButton.removeActionListener(this);
        _revertButton.removeActionListener(this);
        _defaultButton.removeActionListener(this);
    }
    
    private void saveAction(){
//    	 get current info

    	String category = STRCAT;
    	String type = (String)_typeCombo.getSelectedItem();

    	
    	//Box centerBox = (Box) this.getComponent(1);
        //Component[] fieldBoxes = ((Box)centerBox.getComponent(1)).getComponents();
    	
    	
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
       	
        /*
        for(int i = 0; i < fieldBoxes.length; i++ ){
        	// check to see if the component is a box
			if( !("javax.swing.Box".equals(fieldBoxes[i].getClass().getName())) )
				continue;
        	Box b = (Box) fieldBoxes[i];
			boolean cb = ((JCheckBox) b.getComponent(0)).isSelected();
			String name = ((JLabel) b.getComponent(1)).getText();
			if (cb) {
				// if it is checked, add, which will add a new or replace an existing property
				String value = ((JTextField) b.getComponent(2)).getText();
				saveModel.add(new HibernateProperty(category, type, name, value, true ));
			} else {
				// removes this property from the model - the value arguement is irrelevant for this purpose
				saveModel.remove(new HibernateProperty(category, type, name, "", false ));
			}
				
			}*/
        
        _configController.saveProperties(saveModel);

    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent aevt) {
        if (aevt.getSource() == _saveButton) {
        	saveAction();
        	
        } else if (aevt.getSource() == _cancelButton) {
            //_main.cancelAction();
        	this.setVisible(false);
        	this.validate();         	
        } else {
       	
        	STRCAT = aevt.getActionCommand(); 
            _centerPanel.removeAll();
            getComboBox(STRCAT, null);
            getFields(STRCAT, (String)_typeCombo.getSelectedItem());
        	this.validate();
        }
        
        /*else if (aevt.getSource() == _revertButton) {
            _propsTable.setModel(_configControl.loadRevertedHibProps());
        } else if (aevt.getSource() == _defaultButton) {
            _propsTable.setModel(_configControl.loadOriginalHibProps());
        }*/
    }

    // ### DEFINE VARS ###
    private JButton _saveButton, _cancelButton, _revertButton, _defaultButton;
    private JComboBox _typeCombo;
    private HibernatePropertiesModel _model;
    /*private JButton _catButton;*/
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
