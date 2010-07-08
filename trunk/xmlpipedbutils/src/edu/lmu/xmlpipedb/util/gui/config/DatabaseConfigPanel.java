package edu.lmu.xmlpipedb.util.gui.config;

import javax.swing.JPanel;

import edu.lmu.xmlpipedb.util.engines.HibernatePropertiesModel;

/**
 * DatabaseConfigPanel defines the required methods so that a panel can be used
 * to customize the configuration user interface for a particular database.
 * 
 * @author   dondi
 */
public abstract class DatabaseConfigPanel extends JPanel {

    public abstract void writeSettings(HibernatePropertiesModel hibernatePropertiesModel);
    
}
