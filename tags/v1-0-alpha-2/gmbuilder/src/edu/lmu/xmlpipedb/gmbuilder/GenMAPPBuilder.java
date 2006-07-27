/********************************************************
 * Filename: GenMAPPBuilder.java
 * Author: LMU
 * Program: gmBuilder
 * Description: Main application.    
 * Revision History:
 * 20060426: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go.ExportGoData;
import edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export.ExportWizard;
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
        
        // Set up logging.
        BasicConfigurator.configure();
        if (System.getProperty("log.level") != null) {
            LogManager.getRootLogger().setLevel(Level.toLevel(System.getProperty("log.level")));
        } else {
            LogManager.getRootLogger().setLevel(Level.WARN);
        }

        Configuration hc = createHibernateConfiguration();
        if (hc == null) {
            doConfigureDatabase();
        } else {
            _queryPanel.setHibernateConfiguration(hc);
        }
    }

    /**
     * Returns the current Hibernate configuration.
     * 
     * @return The current Hibernate configuration
     */
    public Configuration getCurrentHibernateConfiguration() {
        return _queryPanel.getHibernateConfiguration();
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
        m.add(_processGOAction);
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
                doProcessGO();
            }
        };
        
        _processGOAction = new AbstractAction("Process GO Data...") {
            public void actionPerformed(ActionEvent aevt) {
                doProcessGO();
            }
        };
        
        _exportToGenMAPPAction = new AbstractAction("Export to GenMAPP...") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	doExportToGenMAPP();
                    }
                });
                
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
            _Log.error(exc);
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
        Configuration hibernateConfiguration = getCurrentHibernateConfiguration();
        if (hibernateConfiguration != null) {
            ImportPanel importPanel = new ImportPanel(jaxbContextPath, hibernateConfiguration);
            importPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            ModalDialog.showPlainDialog(title, importPanel);
        } else {
        	//FIXME Put in English resources file 
        	JOptionPane.showMessageDialog(this.getFrontmostWindow(), "No configuration was defined. Set the database configuration.", "No Configuration Defined", 0);
        }
    }

    /**
     * Processes the current GO data into staging and cached tables. These
     * tables are identical for every GenMAPP Gene Database export, so they can
     * be built once and just read directly later.
     */
    private void doProcessGO() {
        if (ModalDialog.showQuestionDialog("Process GO Data?", "Some processing of the raw Gene Ontology data needs to be performed.\nThis may take a few hours.  Proceed?")) {
            try {
                SessionFactory sessionFactory = getCurrentHibernateConfiguration().buildSessionFactory();
                Session session = sessionFactory.openSession();
                (new ExportGoData(session.connection())).populateGeneOntologyStage(getCurrentHibernateConfiguration());
                session.close();
            } catch(IOException e) {
                ModalDialog.showErrorDialog("I/O error.");
                _Log.error(e);
            } catch(Exception e) {
                ModalDialog.showErrorDialog("Unexpected error.");
                _Log.error(e);
            }
        }
    }

    /**
     * Exports the content of the current database to a GenMAPP database file.
     * For the moment, this runs only on Windows due to a dependence on the
     * native Access Jet engine.
     */
    private void doExportToGenMAPP() {
    	try {
    		getFrontmostWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    		Configuration hibernateConfiguration = GenMAPPBuilder.createHibernateConfiguration();
	        if (hibernateConfiguration != null) {
	        	ExportToGenMAPP.init(hibernateConfiguration);
				getFrontmostWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				new ExportWizard(this.getFrontmostWindow());
				ExportToGenMAPP.cleanup();
	        } else {
	        	//FIXME Get text strings from an English resources file: i.e. i18n 
	        	JOptionPane.showMessageDialog(this.getFrontmostWindow(), "No configuration was defined. Set the database configuration.", "No Configuration Defined", 0);
	        }

		} catch (HibernateException e) {
			ModalDialog.showErrorDialog("HIBERNATE error.");
            _Log.error(e);
		} catch (SAXException e) {
			ModalDialog.showErrorDialog("SAX error.");
            _Log.error(e);
		} catch (JAXBException e) {
			ModalDialog.showErrorDialog("JAXB error.");
            _Log.error(e);
		} catch (SQLException e) {			
            ModalDialog.showErrorDialog("SQL error.");
            _Log.error(e);
		} catch (IOException e) {
			ModalDialog.showErrorDialog("I/O error.");
            _Log.error(e);
		} catch (ClassNotFoundException e) {
			ModalDialog.showErrorDialog("Database driver error.");
            _Log.error(e);
		} catch (Exception e) {
			ModalDialog.showErrorDialog(e.toString());
            _Log.error(e);
		} finally {
			try {
				ExportToGenMAPP.cleanup();
			} catch (SQLException ignored) {}
		}
    }

    /**
     * Builds the current Hibernate configuration.
     */
    private static Configuration createHibernateConfiguration() {
//    	TODO Fix this to update it to the new xpdutils stuff.
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
     * The GenMAPPBuilder log.
     */
    private static final Log _Log = LogFactory.getLog(GenMAPPBuilder.class);

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
     * Action object for processing the currently stored GO data.
     */
    private Action _processGOAction;

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
