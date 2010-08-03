package edu.lmu.xmlpipedb.util.gui.config;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.lmu.xmlpipedb.util.engines.HibernatePropertiesModel;
import edu.lmu.xmlpipedb.util.resources.AppResources;

/**
 * This class manages the mapping from platform names (in
 * /edu/lmu/xmlpipedb/util/resources/hibernate.properties) to custom panels
 * for soliciting configuration information.  If a custom panel is not
 * available, a panel for using the "advanced" settings is returned.
 *
 * @author   dondi
 */
public class CustomDatabaseConfigPanelFactory {

    private static Map<String, Class<? extends DatabaseConfigPanel>> DatabaseToPanelClassMap;
    private static DatabaseConfigPanel NO_CUSTOM_PANEL;
    static {
        // The "no custom panel" panel.
        NO_CUSTOM_PANEL = new DatabaseConfigPanel() {

            @Override
            public void readSettings(HibernatePropertiesModel hibernatePropertiesModel) {
                
            }

            @Override
            public void writeSettings(HibernatePropertiesModel hibernatePropertiesModel) {
                // No-op.
            }
            
        };
        NO_CUSTOM_PANEL.setLayout(new BorderLayout());
        NO_CUSTOM_PANEL.add(new JLabel(AppResources.messageString("config.db.nocustom.message"), JLabel.CENTER), BorderLayout.CENTER);
        
        // The custom panels, mapped to their respective database names.
        DatabaseToPanelClassMap = new HashMap<String, Class<? extends DatabaseConfigPanel>>();
        DatabaseToPanelClassMap.put("PostgreSQL", PostgreSQLConfigPanel.class);
    }

    public static DatabaseConfigPanel getCustomDatabaseConfigPanel(String database) {
        if (DatabaseToPanelClassMap.containsKey(database)) {
            try {
                return DatabaseToPanelClassMap.get(database).newInstance();
            } catch(InstantiationException e) {
                _Log.error(e);
            } catch(IllegalAccessException e) {
                _Log.error(e);
            }
        }

        // If we fall to here then there is no custom panel available.
        return NO_CUSTOM_PANEL;
    }
    
    private static final Log _Log = LogFactory.getLog(CustomDatabaseConfigPanelFactory.class);

}
