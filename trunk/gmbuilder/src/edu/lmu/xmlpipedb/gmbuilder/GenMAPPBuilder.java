// Created by dondi, Apr 26, 2006.
package edu.lmu.xmlpipedb.gmbuilder;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.hibernate.cfg.Configuration;

import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.gui.ConfigurationPanel;
import edu.lmu.xmlpipedb.util.gui.HQLPanel;
import edu.lmu.xmlpipedb.util.gui.ImportPanel;

import shag.App;
import shag.dialog.ModalDialog;
import shag.menu.WindowMenu;

/**
 * GenMAPPBuilder is a GUI application for loading, querying, and exporting data
 * used by GenMAPP.
 * 
 * @author dondi
 */
public class GenMAPPBuilder extends App {
    /**
     * Starts the application.
     */
    public static void main(String[] args) {
        (new GenMAPPBuilder()).run();
    }

    /**
     * @see shag.App#getAppName()
     */
    @Override
    public String getAppName() {
        return "GenMAPP Builder";
    }

    /**
     * @see shag.App#run()
     */
    @Override
    public void run() {
        super.run();
        
        Configuration hc = createHibernateConfiguration();
        if (hc == null) {
            doConfigureDatabase();
        } else {
            _queryPanel.setHibernateConfiguration(hc);
        }
    }

    /**
     * Initializes the application.
     */
    private GenMAPPBuilder() {
        createActions();
        createComponents();
    }

    /**
     * @see shag.App#getStartupFrame()
     */
    @Override
    protected JFrame getStartupFrame() {
        JFrame result = new JFrame(getAppName());
        result.setJMenuBar(createJMenuBar());
        result.setContentPane(_queryPanel);
        result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        result.setSize(720, 450);
        result.setLocationRelativeTo(null);
        return result;
    }
    
    /**
     * Creates the main window's menu bar.
     */
    private JMenuBar createJMenuBar() {
        JMenuBar mb = new JMenuBar();
        
        JMenu m = new JMenu("File");
        m.add(_configureDBAction);
        m.addSeparator();
        m.add(_importUniprotAction);
        m.add(_importGOAction);
        mb.add(m);

        mb.add(new WindowMenu(this));
        return mb;
    }

    /**
     * Creates the actions performed by the application.
     */
    private void createActions() {
        _configureDBAction = new AbstractAction("Configure Database...") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                doConfigureDatabase();
            }
        };
        
        _importUniprotAction = new AbstractAction("Import UniProtDB XML File...") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                doImport("org.uniprot.uniprot", "Import UniProtDB XML File");
            }
        };
        
        _importGOAction = new AbstractAction("Import GO XML File...") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                doImport("generated", "Import GO XML File");
            }
        };
    }

    /**
     * Creates the persistent components in the application.
     */
    private void createComponents() {
        _queryPanel = new HQLPanel();
    }

    /**
     * Configures the underlying database.
     */
    private void doConfigureDatabase() {
        try {
            ConfigurationPanel configPanel = new ConfigurationPanel();
            ModalDialog.showPlainDialog("Configure Database", configPanel);
            
            // Update components that rely on the configuration.
            _queryPanel.setHibernateConfiguration(createHibernateConfiguration());
        } catch(Exception exc) {
            ModalDialog.showErrorDialog("Unable to Configure Database", "Sorry, database configuration was unable to proceed.  This is most likely an error relating to file creation or modification on the system on which you are running.");
            exc.printStackTrace();
        }
    }

    private void doImport(String jaxbContextPath, String title) {
        Configuration hibernateConfiguration = createHibernateConfiguration();
        if (hibernateConfiguration != null) {
            ImportPanel importPanel = new ImportPanel(jaxbContextPath, hibernateConfiguration);
            importPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            ModalDialog.showPlainDialog(title, importPanel);
        }
    }

    /**
     * Builds the current Hibernate configuration.
     */
    private Configuration createHibernateConfiguration() {
        Configuration hibernateConfiguration = null;
        try {
            hibernateConfiguration = (new ConfigurationEngine()).getHibernateConfiguration();
            hibernateConfiguration.addJar(new File("lib/uniprotdb.jar"));
            hibernateConfiguration.addJar(new File("lib/godb.jar"));
        } catch(Exception exc) {
            // This may be a normal occurrence (particularly when starting up
            // for the first time), so we don't do anything in this case.
        }
        
        return hibernateConfiguration;
    }

    /**
     * Action object for configuring the database.
     */
    private Action _configureDBAction;
    
    /**
     * Action object for importing a uniprot file into the database.
     */
    private Action _importUniprotAction;

    /**
     * Action object for importing a go file into the database.
     */
    private Action _importGOAction;

    /**
     * Component for issuing queries to the database.
     */
    private HQLPanel _queryPanel;
}
