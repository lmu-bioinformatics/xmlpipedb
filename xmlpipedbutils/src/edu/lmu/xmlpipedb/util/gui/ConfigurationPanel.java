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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;

import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.engines.HibernatePropertiesModel;
import edu.lmu.xmlpipedb.util.engines.HibernateProperty;
import edu.lmu.xmlpipedb.util.exceptions.CouldNotLoadPropertiesException;
import edu.lmu.xmlpipedb.util.exceptions.NoHibernatePropertiesException;
import edu.lmu.xmlpipedb.util.gui.config.CustomDatabaseConfigPanelFactory;
import edu.lmu.xmlpipedb.util.gui.config.DatabaseConfigPanel;
import edu.lmu.xmlpipedb.util.resources.AppResources;

import shag.LayoutConstants;

/**
 * ConfigurationPanel should be called to create a Hibernate configuration
 * panel. ConfigurationPanel will create instances of the ConfigurationEngine
 * and any other classes it needs.
 * 
 * @author J. Nicholas
 * 
 */
public class ConfigurationPanel extends JPanel implements ActionListener, ItemListener {

    private static final long serialVersionUID = -3486506045107182287L;

    /**
     * Creates an instance of the ConfigurationPanel, which in turn creates an
     * instance of the ConfigurationEngine for its own private use.
     * 
     * @throws CouldNotLoadPropertiesException
     * @throws FileNotFoundException
     */
    public ConfigurationPanel() throws CouldNotLoadPropertiesException, FileNotFoundException {
        // create an instance of the config engine
        _configEngine = new ConfigurationEngine();
        // use the config engine to get the config model.
        // the config model is a HibernatePropertiesModel and
        // contains both the default values and any saved values
        _model = _configEngine.getConfigurationModel();

        // create all gui components
        createComponents();
        // layout the components
        layoutComponents();
    }

    /**
     * Returns a hibernate Configuration object, if the properties object is not
     * empty. If it is empty, a message will be displayed to the user and a
     * NoHibernatePropertiesException will be thrown to the caller.
     * 
     * @return Configuration - an org.hibernate.cfg.Configuration ojbect is
     *         populated with the currently configured properties and returned.
     * @throws NoHibernatePropertiesException
     */
    public Configuration getHibernateConfiguration() throws NoHibernatePropertiesException {
        // pass through method that calls the _configEngine to get a
        // hibernate Configuration object
        return _configEngine.getHibernateConfiguration();
    }

    /**
     * Sets the preferred platform.  This does not change the data (yet); it
     * only displays the custom settings panel for that platform (if implemented).
     */
    public void setCurrentPlatform(String platform) {
        databaseCB.setSelectedItem(platform);
    }

    private void createComponents() {
        createDatabaseConnectionComponents();
        createAdvancedComponents();
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        JTabbedPane tp = new JTabbedPane();
        tp.addTab(AppResources.messageString("config.db.tab"), databaseSelectionPanel);
        tp.addTab(AppResources.messageString("config.advanced.tab"), createAdvancedPanel());
        add(tp, BorderLayout.CENTER);
    }

    private JPanel createAdvancedPanel() {
        JPanel result = new JPanel(new BorderLayout(0, LayoutConstants.BORDER));
        result.setBorder(BorderFactory.createEmptyBorder(
            LayoutConstants.BORDER, LayoutConstants.BORDER,
            LayoutConstants.BORDER, LayoutConstants.BORDER));

        result.add(_topBox, BorderLayout.NORTH);

        // add fields
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.add(_centerPanel, BorderLayout.NORTH);
        result.add(new JScrollPane(fieldPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        
        return result;
    }

    private void createDatabaseConnectionComponents() {
        databaseSelectionPanel = new JPanel(new BorderLayout(LayoutConstants.BORDER, LayoutConstants.BORDER));
        databaseSelectionPanel.setBorder(BorderFactory.createEmptyBorder(
            LayoutConstants.BORDER, LayoutConstants.BORDER,
            LayoutConstants.BORDER, LayoutConstants.BORDER));
        
        // This panel is keyed specifically to the "Platform" preference category.
        databaseCB = new JComboBox(_model.getTypes("Platforms"));
        databaseCB.addItemListener(this);
        
        JPanel topPanel = new JPanel(new BorderLayout(LayoutConstants.BORDER, 0));
        topPanel.add(new JLabel(AppResources.messageString("config.db.driver.label")), BorderLayout.WEST);
        topPanel.add(databaseCB, BorderLayout.CENTER);

        databaseSelectionPanel.add(topPanel, BorderLayout.NORTH);
        
        setCurrentCustomComponent(CustomDatabaseConfigPanelFactory.getCustomDatabaseConfigPanel((String)databaseCB.getSelectedItem()));
    }

    /**
     * Creates the components used on the "advanced" version of the panel.
     */
    private void createAdvancedComponents() {
        // *** prepare gridbag stuff ***
        _promptGBC = new GridBagConstraints();
        _fieldGBC = new GridBagConstraints();
        _comboGBC = new GridBagConstraints();
        _panelGBC = new GridBagConstraints();

        setPromptConstraints(_promptGBC);
        setFieldConstraints(_fieldGBC);
        setComboConstraints(_comboGBC);
        setPanelConstraints(_panelGBC);

        // *** get the dynamic components ***
        // gets the categories box, which goes on top
        _topBox = getTopBox();
        // instantiates the center panel
        _centerPanel = new JPanel(new GridBagLayout());
        _centerPanel.setBorder(BorderFactory.createEmptyBorder(
            LayoutConstants.BORDER, LayoutConstants.BORDER,
            LayoutConstants.BORDER, LayoutConstants.BORDER));
        // add entries for the selected category to the types combo
        // and adds the types combo to the _centerPanel
        getComboBox(null);
        // adds entries for the selected type to the _centerPanel
        getFields((String)_typeCombo.getSelectedItem());

        // *** these buttons may be enabled, but some changes need to be made
        // to the methods that support them ***

        // _revertButton = new
        // JButton(AppResources.messageString("config_revert"));
        // _revertButton.setToolTipText(AppResources.messageString("config_revert_tooltip"));
        // _defaultButton = new
        // JButton(AppResources.messageString("config_default"));
        // _defaultButton.setToolTipText(AppResources.messageString("config_default_tooltip"));

        // _revertButton.setEnabled(false);
        // _defaultButton.setEnabled(false);

    } // end createComponents

    /**
     * Listens to the combo box for changes in the which item is selected. When
     * the item changes, it rebuilds the _centerPanel with a new combo box and
     * new fields
     * 
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent iEvent) {
        if (iEvent.getStateChange() == ItemEvent.SELECTED) {
            if (iEvent.getSource() == databaseCB) {
                // Check for a custom panel for the selected database.
                setCurrentCustomComponent(CustomDatabaseConfigPanelFactory.getCustomDatabaseConfigPanel((String)databaseCB.getSelectedItem()));
            } else if (iEvent.getSource() == _typeCombo) {
                // clear out the _centerPanel
                _centerPanel.removeAll();
                // put a new ComboBox in
                getComboBox((String)iEvent.getItem());
                // put new fields in
                getFields((String)iEvent.getItem());

                // ensure that everything shows up right away
                validate();
                repaint();
            }
        }
    }

    private void setCurrentCustomComponent(DatabaseConfigPanel newCurrent) {
        if (currentCustomComponent != null) {
            databaseSelectionPanel.remove(currentCustomComponent);
        }
        currentCustomComponent = newCurrent;
        currentCustomComponent.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(LayoutConstants.BORDER, LayoutConstants.BORDER << 1,
                LayoutConstants.BORDER, LayoutConstants.BORDER << 1)));
        databaseSelectionPanel.add(currentCustomComponent, BorderLayout.CENTER);
        databaseSelectionPanel.validate();
        databaseSelectionPanel.repaint();
    }

    /**
     * Returns the categories box used on the top of the panel
     * 
     * @return Box - contains radio buttons for each category
     */
    private Box getTopBox() {
        // create the box that will be returned
        Box topBox = new Box(BoxLayout.X_AXIS);
        // strCat is a temporary var to store the category being worked with
        String strCat = null;
        // catSet is the Set of categories
        Set<String> catSet = _model.getCategories();
        // the catIter will be used to iterate over the categories
        Iterator<String> catIter = catSet.iterator();
        // bg is the buttonGroup the will contain the radio buttons
        // the buttonGroup provides single select functionality, which
        // disables other buttons if one is selected
        ButtonGroup bg = new ButtonGroup();
        while (catIter.hasNext()) {
            // get the category and ensure that it is not null, if so, throw an
            // exception
            strCat = (String)catIter.next();

            // If the working category is the same as the last category worked
            // with by the user (stored in the model as currentCategory)
            // then mark this radio button as true (selected) otherwise it is
            // false
            JRadioButton categoryRB = new JRadioButton(strCat, strCat.equalsIgnoreCase(_model.getCurrentCategory()));

            // add an action listener to the radio button
            categoryRB.addActionListener(this);
            // add the button to the button group
            bg.add(categoryRB);
            // add the button to the local topBox
            topBox.add(categoryRB);
            // The last selected category is, well, selected.
            categoryRB.setSelected(true);
        }

        // if there was no saved category, the currentCategory will be null
        // in this case, set it to be strCat, which is the last category added
        // since currentCategory is used by other methods, leaving it null
        // will be disasterous!!!
        if (_model.getCurrentCategory() == null) {
            _model.setCurrentCategory(strCat);
        }

        return topBox;
    }

    /**
     * Instantiates the class var _typeCombo, which contains the types for the
     * currently selected category.
     * 
     * @param selected
     *            - the currently selected item or null, if no item is currently
     *            selected.
     * @return void
     */
    private void getComboBox(String selected) {
        // _typeCombo will be to null the first time this method is called
        // and no item listener will exist, so we skip this step
        // All subsequent times, we remove the item listener
        if (_typeCombo != null) {
            _typeCombo.removeItemListener(this);
        }

        // get a String array of the types for this category
        // use the String array to create the _typeCombo
        String[] types = _model.getTypes(_model.getCurrentCategory());
        _typeCombo = new JComboBox(types);

        // if selected was passed in, then mark this item as selected
        // selected is not null when this method is called from the item
        // listener. In this case, selected is the item that was clicked
        if (selected != null) {
            _typeCombo.setSelectedItem(selected);
        } else {
            // try to get a selected type from the model. this is different
            // from the currentType stored in the model, because there can
            // be only one currentType, but the getSelectedType method will
            // look to see if anything is stored for the current category
            // and get that type - it takes the first type encountered
            String selectedType = _model.getSelectedType(_model.getCurrentCategory());
            if (selectedType == null) {
                // if there were no selectedType, set selected to the first
                // item in the list
                _typeCombo.setSelectedIndex(0);
            } else {
                _typeCombo.setSelectedItem(selectedType);
            }
        }

        // add the item listener and add the combo to the panel
        _typeCombo.addItemListener(this);
        _centerPanel.add(_typeCombo, _comboGBC);
    }

    /**
     * Creates all the fields for the currently selected category and type.
     * 
     * @param type
     *            - the currently selected type
     * @return void
     */
    private void getFields(String type) {
        // get the properties for the currentcategory and the type passed in
        List<HibernateProperty> propsArray = _model.getProperties(_model.getCurrentCategory(), type);
        // create an iterator for these props
        Iterator<HibernateProperty> props = propsArray.iterator();

        // initialize the checkbox and textfield arrays to the size of the
        // properties array
        _propSelected = new JCheckBox[propsArray.size()];
        _propValue = new JTextField[propsArray.size()];
        // i is required to access the correct element of the control array
        int i = 0;
        while (props.hasNext()) {
            // for each item:
            // - get a HibernateProperties object from the properties array
            // - set the name to the lable of the checkbox
            // - if the field is a password field, then initialize the field in
            // the textfield array as a password field otherwise initialize it
            // as textfield - in either case use the property's value
            // - create a document listener for each textfield, that checks
            // the check box if the text is changed.
            // - set the checkbox as selected the property is marked "saved"
            // - add the components to the _centerPanel
            // - increment i for the next iteration
            HibernateProperty hp = props.next();

            // Babak - Changed the implementation to use the JCheckBox's text
            // label instead of a seperate
            // JN - thank you, Babak
            _propSelected[i] = new JCheckBox(hp.getName());

            // check if the field is a password field
            if (hp.getName().indexOf("password") != -1) {
                _propValue[i] = new JPasswordField(hp.getValue(), 30);
            } else {
                _propValue[i] = new JTextField(hp.getValue(), 30);
            }

            final JCheckBox thisCheckBox = _propSelected[i];
            _propValue[i].getDocument().addDocumentListener(new DocumentListener() {

                public void insertUpdate(DocumentEvent e) {
                    thisCheckBox.setSelected(true);
                }

                public void removeUpdate(DocumentEvent e) {
                    thisCheckBox.setSelected(true);
                }

                public void changedUpdate(DocumentEvent e) {
                    thisCheckBox.setSelected(true);
                }

            });

            if (hp.isSaved()) {
                _propSelected[i].setSelected(true);
            }

            _centerPanel.add(_propSelected[i], _promptGBC);
            _centerPanel.add(_propValue[i], _fieldGBC);

            i++;
        }
    }

    // #### gridbag helper methods ####
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
     * Iterates through the current set of controls and populates a
     * HibernatePropertiesModel that is passed to the ConfigurationEnging to
     * save the properties.
     * 
     * @return void
     */
    public void saveConfiguration() {
        // Get currently selected type.
        String type = (String)_typeCombo.getSelectedItem();

        // Prepare a new model object, which will be used to save the properties.
        HibernatePropertiesModel saveModel = new HibernatePropertiesModel();
        
        // Hand the properties to the custom panel first --- the advanced
        // section overrides, because it has greater flexibility.
        if (currentCustomComponent != null) {
            currentCustomComponent.writeSettings(saveModel);
        }
        
        // Add all the other saved properties to the saveModel, except the
        // properties of this category, which will be overwritten.
        Iterator<String> modelIter = _model.getProperties();
        while (modelIter.hasNext()) {
            HibernateProperty hp = _model.getProperty((String)modelIter.next());
            // if it is this category, don't add it no matter what
            if (hp.getCategory().equals(_model.getCurrentCategory())) {
                continue;
            }
            
            // hp is not this category, so check if it is saved.
            if (hp.isSaved()) {
                saveModel.add(hp);
            }
        }

        for (int i = 0; i < _propValue.length; i++) {
            if (_propSelected[i].isSelected()) {
                // if it is checked, add it, which will add a new or replace an
                // existing property
                HibernateProperty hp = new HibernateProperty(_model.getCurrentCategory(), type, _propSelected[i].getText(), _propValue[i].getText(), true);
                saveModel.add(hp);
            }
        }

        // save the current type and category
        saveModel.setCurrentType(type);
        saveModel.setCurrentCategory(_model.getCurrentCategory());

        // save the properties in the model
        _configEngine.saveProperties(saveModel);

        // update the model to reflect what is now saved.
        try {
            _model = _configEngine.getConfigurationModel();
        } catch(FileNotFoundException e) {
            _Log.error(e);
        }
    }

    /**
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent aevt) {
        // this is a radio button click
        // update the currentCategory
        _model.setCurrentCategory(aevt.getActionCommand());
        // remove all components from the _centerPanel
        _centerPanel.removeAll();
        // create the combobox
        getComboBox(null);
        // create the fields for the selected type
        getFields((String)_typeCombo.getSelectedItem());
        // update the display
        this.validate();
        this.repaint();
    }

    private static final Log _Log = LogFactory.getLog(ConfigurationPanel.class);

    private HibernatePropertiesModel _model;

    /*
     * "Basic/database connection" controls and variables.
     */
    private JPanel databaseSelectionPanel;
    private JComboBox databaseCB;
    private DatabaseConfigPanel currentCustomComponent;

    /*
     * "Advanced" controls and variables.
     */
    // control arrays and controls
    private JCheckBox[] _propSelected;
    private JTextField[] _propValue;
    private JComboBox _typeCombo;

    // private Box _centerBox;
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

}
