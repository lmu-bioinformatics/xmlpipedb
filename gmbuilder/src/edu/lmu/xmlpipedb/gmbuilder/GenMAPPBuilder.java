// Created by dondi, Apr 26, 2006.
package edu.lmu.xmlpipedb.gmbuilder;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.xml.bind.JAXBException;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMaPP;
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
        m.addSeparator();
        m.add(_exportToGenMAPPAction);
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
        
        _exportToGenMAPPAction = new AbstractAction("Export to GenMAPP...") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                doExportToGenMAPP();
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

    /**
     * Imports an XML file into the database.
     * 
     * @param jaxbContextPath
     *            The context path under which to store the object
     * @param title
     *            The title of the dialog (helps prompt the user on what file to
     *            import)
     */
    private void doImport(String jaxbContextPath, String title) {
        Configuration hibernateConfiguration = createHibernateConfiguration();
        if (hibernateConfiguration != null) {
            ImportPanel importPanel = new ImportPanel(jaxbContextPath, hibernateConfiguration);
            importPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            ModalDialog.showPlainDialog(title, importPanel);
        }
    }

    /**
     * Exports the content of the current database to a GenMAPP database file.
     * For the moment, this runs only on Windows due to a dependence on the
     * native Access Jet engine.
     */
    private void doExportToGenMAPP() {
    	
        // Prompt the user for the output (destination) file.
    	JFileChooser chooser = new JFileChooser();
    	int returnVal = chooser.showSaveDialog(this.getFrontmostWindow());
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            // Now we have all the information we need; perform the actual export.
    		try {
				ExportToGenMaPP.exportToGenMaPP(chooser.getSelectedFile());
			} catch (HibernateException e) {
				ModalDialog.showErrorDialog("HIBERNATE error.");
				e.printStackTrace();
			} catch (SAXException e) {
				ModalDialog.showErrorDialog("SAX error.");
				e.printStackTrace();
			} catch (JAXBException e) {
				ModalDialog.showErrorDialog("JAXB error.");
				e.printStackTrace();
			} catch (SQLException e) {			
                ModalDialog.showErrorDialog("SQL error.");
                e.printStackTrace();
    		} catch (IOException e) {
    			ModalDialog.showErrorDialog("I/O error.");
    			e.printStackTrace();
    		} catch (ClassNotFoundException e) {
    			ModalDialog.showErrorDialog("Database driver error.");
    			e.printStackTrace();
			}
        }
     
        
        // In the future, we also want to prompt the user for the organism to
        // output for.
        
        // Prompt the user for the GO Associations file.
        

    	
    	
    }

    /**
     * Builds the current Hibernate configuration.
     */
    public static Configuration createHibernateConfiguration() {
        Configuration hibernateConfiguration = null;
        try {
            hibernateConfiguration = (new ConfigurationEngine()).getHibernateConfiguration();
            
            // TODO: Find a way to make this independent of the current
            // directory, or at least ensure that the working directory of this
            // program is indeed the top-level directory of the distribution.
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
     * Action object for exporting the current content of the database to a
     * GenMAPP database file.
     */
    private Action _exportToGenMAPPAction;

    /**
     * Component for issuing queries to the database.
     */
    private HQLPanel _queryPanel;
}
