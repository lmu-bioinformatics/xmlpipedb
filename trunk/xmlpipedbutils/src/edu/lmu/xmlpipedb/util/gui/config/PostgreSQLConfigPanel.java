package edu.lmu.xmlpipedb.util.gui.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;

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
     * @see edu.lmu.xmlpipedb.util.gui.config.DatabaseConfigPanel#writeSettings(edu.lmu.xmlpipedb.util.engines.HibernatePropertiesModel)
     */
    @Override
    public void writeSettings(HibernatePropertiesModel hibernatePropertiesModel) {
        final String category = "Platforms";
        final String type = "PostgreSQL";

        // Standard PostgreSQL stuff.
        HibernateProperty hp = new HibernateProperty(category, type, "hibernate.connection.driver_class", "org.postgresql.Driver", true);
        hibernatePropertiesModel.add(hp);
        hp = new HibernateProperty(category, type, "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect", true);
        hibernatePropertiesModel.add(hp);
        hp = new HibernateProperty(category, type, "hibernate.query.substitutions", "Y", true);
        hibernatePropertiesModel.add(hp);
        
        // Stuff from the user interface.  The URL needs to be built.
        hp = new HibernateProperty(category, type, "hibernate.connection.url", "jdbc:postgresql://" +
            serverTextField.getText() + ":" +
            portTextField.getValue() + "/" +
            databaseTextField.getText(), true);
        hibernatePropertiesModel.add(hp);
        hp = new HibernateProperty(category, type, "hibernate.connection.username", usernameTextField.getText(), true);
        hibernatePropertiesModel.add(hp);
        hp = new HibernateProperty(category, type, "hibernate.connection.password", new String(passwordField.getPassword()), true);
        hibernatePropertiesModel.add(hp);
    }

    /**
     * Creates the persistent parts of the user interface, and includes default
     * values for them.
     */
    private void createComponents() {
        serverTextField = new JTextField("localhost");
        serverTextField.setToolTipText(AppResources.messageString("config.postgresql.server.tooltip"));

        portTextField = new JFormattedTextField(new DecimalFormat("#"));
        portTextField.setValue(new Integer(5432));
        portTextField.setToolTipText(AppResources.messageString("config.postgresql.port.tooltip"));

        databaseTextField = new JTextField();
        databaseTextField.setToolTipText(AppResources.messageString("config.postgresql.database.tooltip"));

        usernameTextField = new JTextField();
        usernameTextField.setToolTipText(AppResources.messageString("config.postgresql.username.tooltip"));

        passwordField = new JPasswordField();
        passwordField.setToolTipText(AppResources.messageString("config.postgresql.password.tooltip"));
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
    
    private JTextField serverTextField;
    private JFormattedTextField portTextField;
    private JTextField databaseTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordField;

}
