package edu.lmu.xmlpipedb.util.gui.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.lmu.xmlpipedb.util.engines.HibernatePropertiesModel;
import edu.lmu.xmlpipedb.util.engines.HibernateProperty;
import edu.lmu.xmlpipedb.util.resources.AppResources;

import shag.LayoutConstants;

/**
 * Custom user interface for configuring XMLPipeDB against a PostgreSQL
 * database server.
 * 
 * @author   dondi
 */
public class PostgreSQLConfigPanel extends DatabaseConfigPanel {

    public PostgreSQLConfigPanel() {
        createComponents();
        layoutComponents();
    }
    
    /**
     * @see edu.lmu.xmlpipedb.util.gui.config.DatabaseConfigPanel#readSettings(edu.lmu.xmlpipedb.util.engines.HibernatePropertiesModel)
     */
    @Override
    public void readSettings(HibernatePropertiesModel hibernatePropertiesModel) {
        // Grab the property objects.
        HibernateProperty urlProperty = hibernatePropertiesModel.getProperty(CATEGORY_TYPE + "." + PROPERTY_TYPE + "." + URL_PROPERTY);
        HibernateProperty usernameProperty = hibernatePropertiesModel.getProperty(CATEGORY_TYPE + "." + PROPERTY_TYPE + "." + USERNAME_PROPERTY);
        HibernateProperty passwordProperty = hibernatePropertiesModel.getProperty(CATEGORY_TYPE + "." + PROPERTY_TYPE + "." + PASSWORD_PROPERTY);
        
        // Parse the URL property.
        if (urlProperty != null) {
            String url = urlProperty.getValue();
            Pattern p = Pattern.compile("jdbc:postgresql://([^:]+):(\\d+)/(.+)");
            Matcher matcher = p.matcher(url);
            if (matcher.matches()) {
                serverTextField.setText(matcher.group(1));
                portTextField.setText(matcher.group(2));
                databaseTextField.setText(matcher.group(3));
            } else {
                setDefaultValues();
            }
        } else {
            setDefaultValues();
        }

        // Grab the remaining PostgreSQL properties from the model.
        usernameTextField.setText((usernameProperty != null) ? usernameProperty.getValue() : "");
        passwordField.setText((passwordProperty != null) ? passwordProperty.getValue() : "");
    }

    /**
     * @see edu.lmu.xmlpipedb.util.gui.config.DatabaseConfigPanel#writeSettings(edu.lmu.xmlpipedb.util.engines.HibernatePropertiesModel)
     */
    @Override
    public void writeSettings(HibernatePropertiesModel hibernatePropertiesModel) {
        // Standard PostgreSQL stuff.
        HibernateProperty hp = new HibernateProperty(CATEGORY_TYPE, PROPERTY_TYPE, "hibernate.connection.driver_class", "org.postgresql.Driver", true);
        hibernatePropertiesModel.add(hp);
        hp = new HibernateProperty(CATEGORY_TYPE, PROPERTY_TYPE, "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect", true);
        hibernatePropertiesModel.add(hp);
        hp = new HibernateProperty(CATEGORY_TYPE, PROPERTY_TYPE, "hibernate.query.substitutions", "Y", true);
        hibernatePropertiesModel.add(hp);
        
        // Stuff from the user interface.  The URL needs to be built.
        hp = new HibernateProperty(CATEGORY_TYPE, PROPERTY_TYPE, URL_PROPERTY, "jdbc:postgresql://" +
            serverTextField.getText() + ":" +
            portTextField.getValue() + "/" +
            databaseTextField.getText(), true);
        hibernatePropertiesModel.add(hp);
        hp = new HibernateProperty(CATEGORY_TYPE, PROPERTY_TYPE, USERNAME_PROPERTY, usernameTextField.getText(), true);
        hibernatePropertiesModel.add(hp);
        hp = new HibernateProperty(CATEGORY_TYPE, PROPERTY_TYPE, PASSWORD_PROPERTY, new String(passwordField.getPassword()), true);
        hibernatePropertiesModel.add(hp);
    }

    /**
     * Creates the persistent parts of the user interface, and includes default
     * values for them.
     */
    private void createComponents() {
        serverTextField = new JTextField();
        serverTextField.setToolTipText(AppResources.messageString("config.postgresql.server.tooltip"));

        portTextField = new JFormattedTextField(new DecimalFormat("#"));
        portTextField.setToolTipText(AppResources.messageString("config.postgresql.port.tooltip"));

        databaseTextField = new JTextField();
        databaseTextField.setToolTipText(AppResources.messageString("config.postgresql.database.tooltip"));

        usernameTextField = new JTextField();
        usernameTextField.setToolTipText(AppResources.messageString("config.postgresql.username.tooltip"));

        passwordField = new JPasswordField();
        passwordField.setToolTipText(AppResources.messageString("config.postgresql.password.tooltip"));
        
        setDefaultValues();
    }
    
    private void layoutComponents() {
        setLayout(new GridBagLayout());
        
        GridBagConstraints labelGBC = new GridBagConstraints();
        labelGBC.insets.right = LayoutConstants.SPACE;
        labelGBC.insets.bottom = LayoutConstants.SPACE;
        labelGBC.anchor = GridBagConstraints.EAST;

        GridBagConstraints fieldGBC = new GridBagConstraints();
        fieldGBC.gridwidth = GridBagConstraints.REMAINDER;
        fieldGBC.fill = GridBagConstraints.HORIZONTAL;
        fieldGBC.weightx = 1.0;
        
        add(new JLabel(AppResources.messageString("config.postgresql.server.label")), labelGBC);
        add(serverTextField, fieldGBC);
        
        add(new JLabel(AppResources.messageString("config.postgresql.port.label")), labelGBC);
        add(portTextField, fieldGBC);
        
        add(new JLabel(AppResources.messageString("config.postgresql.database.label")), labelGBC);
        add(databaseTextField, fieldGBC);
        
        add(new JLabel(AppResources.messageString("config.postgresql.username.label")), labelGBC);
        add(usernameTextField, fieldGBC);
        
        add(new JLabel(AppResources.messageString("config.postgresql.password.label")), labelGBC);
        add(passwordField, fieldGBC);
    }

    private void setDefaultValues() {
        serverTextField.setText("localhost");
        portTextField.setValue(new Integer(5432));
        databaseTextField.setText("");
    }

    private static final String CATEGORY_TYPE = "Platforms";
    private static final String PROPERTY_TYPE = "PostgreSQL";
    private static final String URL_PROPERTY = "hibernate.connection.url";
    private static final String USERNAME_PROPERTY = "hibernate.connection.username";
    private static final String PASSWORD_PROPERTY = "hibernate.connection.password";

    private JTextField serverTextField;
    private JFormattedTextField portTextField;
    private JTextField databaseTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordField;

}
