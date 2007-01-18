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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
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
import edu.lmu.xmlpipedb.gmbuilder.resource.properties.AppResources;
import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.engines.Criterion;
import edu.lmu.xmlpipedb.util.engines.QueryEngine;
import edu.lmu.xmlpipedb.util.engines.TallyEngine;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;
import edu.lmu.xmlpipedb.util.exceptions.XpdException;
import edu.lmu.xmlpipedb.util.gui.ConfigurationPanel;
import edu.lmu.xmlpipedb.util.gui.HQLPanel;
import edu.lmu.xmlpipedb.util.gui.ImportPanel;

import shag.App;
import shag.dialog.ModalDialog;
import shag.menu.WindowMenu;
import shag.table.BeanColumn;
import shag.table.BeanTableModel;
import shag.table.UsefulTable;

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
        
        // Set up logging. next line just uses basic logging
        BasicConfigurator.configure();
        // We will use logging properties set in a file. This will give us
        // greater flexibility and control over our logging
        //FIXME: This is a complete kludge cuz I can't find how to set it to get the log4j.properties from the classpath. After this is working, we should use this in place of BasicConfigurator
//        PropertyConfigurator.configure("/eclipse projects/gmbuilder/log4j.properties");

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
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(_configureDBAction);
        fileMenu.addSeparator();
        fileMenu.add(_importUniprotAction);
        fileMenu.add(_importGOAction);
        fileMenu.addSeparator();
        fileMenu.add(_processGOAction);
        fileMenu.addSeparator();
        fileMenu.add(_runTalliesAction);
        fileMenu.addSeparator();
        fileMenu.add(_exportToGenMAPPAction);
        mb.add(fileMenu);

        JMenu tallyMenu = new JMenu("Tallies");
        tallyMenu.add(_xmlTallyAction);
        tallyMenu.add(_oboTallyAction);
        tallyMenu.add(_importedDataTallyAction);
        tallyMenu.add(_gdbTallyAction);
        mb.add(tallyMenu);
        
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
        
        _runTalliesAction = new AbstractAction("Run Tallies for Uniprot and Go...") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                doTallies();
            }
        };
        
        _gdbTallyAction  = new AbstractAction("Run Tallies for Data Exported to GenMapp Database") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                doTallies();
            }
        };
        
        _importedDataTallyAction = new AbstractAction("Run Tallies for Data Imported into Database") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                Configuration hibernateConfiguration = getCurrentHibernateConfiguration();
                if (hibernateConfiguration == null) {
                    showConfigurationError();
                    return;
                }
            	HashMap<String, Criterion> uniprotCriteria = new HashMap<String, Criterion>();
            	getXmlTallyElements(uniprotCriteria);
            	HashMap<String, Criterion> goCriteria = new HashMap<String, Criterion>();
            	getOboTallyElements(goCriteria);

                getTallyResultsDatabase(uniprotCriteria, hibernateConfiguration);
                getTallyResultsDatabase(goCriteria, hibernateConfiguration);
                
                // Gather the criteria into a list so that we can display them
                // in a UsefulTable.
                /**
                 * Columns used for displaying tally results.
                 */
                final BeanColumn[] TallyColumns = {
                     BeanColumn.create("Database Table", "table", String.class),
                    BeanColumn.create("Database Count", "dbCount", Integer.class)
                };
                BeanTableModel btm = new BeanTableModel(TallyColumns);
                List<Criterion> criteria = new ArrayList<Criterion>(uniprotCriteria.size() + goCriteria.size());
                criteria.addAll(uniprotCriteria.values());
                criteria.addAll(goCriteria.values());
                btm.setData(criteria.toArray());
                UsefulTable t = new UsefulTable(btm);
                ModalDialog.showPlainDialog("Tally Results", new JScrollPane(t));
            }
        };
        
        _oboTallyAction = new AbstractAction("Run Tallies for GO XML File") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                doTallies();
            }
        };
        
        _xmlTallyAction = new AbstractAction("Run Tallies for Uniprot XML File") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                doTallies();
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
            showConfigurationError();
        }
    }

    /**
     * Gets the elements from the properties file for reading in
     * XML data (import files)
     *
     */
    private HashMap<String, Criterion> getXmlTallyElements(){
    	
       	HashMap<String, Criterion> uniprotCriteria = new HashMap<String, Criterion>();
    	String element = null;
    	String query = null;

//    	 *** UniProt ***
    	try{
	    	element = AppResources.optionString("UniprotElementLevel1").trim();
	    	if (!element.equals("") && element != null )
	            uniprotCriteria.put(element, new Criterion("", element, query));
	        
	    	element = AppResources.optionString("UniprotElementLevel2").trim();
	        if (!element.equals("") && element != null )
	            uniprotCriteria.put(element, new Criterion("", element, query));
	
	        element = AppResources.optionString("UniprotElementLevel3").trim();
	        if (!element.equals("") && element != null )
	            uniprotCriteria.put(element, new Criterion("", element, query));
	
	        element = AppResources.optionString("UniprotElementLevel4").trim();
	        if (!element.equals("") && element != null )
	            uniprotCriteria.put(element, new Criterion("", element, query));
	        
    	} catch( InvalidParameterException e ){
    		//TODO: print to log file
    	}
    	
    	return uniprotCriteria;
    }
    
    /**
     * Gets the queries used to get import data counts from the properties file. 
     * The HashMap passed in is populated.
     *
     */
    private void getXmlTallyElements(HashMap<String, Criterion> criteria){
    	
       	//HashMap<String, Criterion> uniprotCriteria = new HashMap<String, Criterion>();
    	String element = null;
    	String query = null;

//    	 *** UniProt ***
    	try{
	    	element = AppResources.optionString("UniprotElementLevel1").trim();
	    	query = AppResources.optionString("UniprotQueryLevel1").trim();
	    	if (!element.equals("") && element != null )
	            criteria.put(element, new Criterion("", element, query));
	        
	    	element = AppResources.optionString("UniprotElementLevel2").trim();
	    	query = AppResources.optionString("UniprotQueryLevel2").trim();
	        if (!element.equals("") && element != null )
	            criteria.put(element, new Criterion("", element, query));
	
	        element = AppResources.optionString("UniprotElementLevel3").trim();
	        query = AppResources.optionString("UniprotQueryLevel3").trim();
	        if (!element.equals("") && element != null )
	            criteria.put(element, new Criterion("", element, query));
	
	        element = AppResources.optionString("UniprotElementLevel4").trim();
	        query = AppResources.optionString("UniprotQueryLevel4").trim();
	        if (!element.equals("") && element != null )
	            criteria.put(element, new Criterion("", element, query));
	        
    	} catch( InvalidParameterException e ){
    		//TODO: print to log file
    	}
    }
    
    /**
     * Gets the queries used to get import data counts from the properties file. 
     * The HashMap passed in is populated.
     *
     */
    private void getTallyElementsDb(HashMap<String, Criterion> criteria){
    	
       	//HashMap<String, Criterion> uniprotCriteria = new HashMap<String, Criterion>();
    	String element = null;
    	String query = null;

//    	 *** UniProt ***
    	try{
	    	element = AppResources.optionString("UniprotElementLevel1").trim();
	    	query = AppResources.optionString("UniprotQueryLevel1").trim();
	    	if (!element.equals("") && element != null )
	            criteria.put(element, new Criterion("", null, query));
	        
	    	element = AppResources.optionString("UniprotElementLevel2").trim();
	    	query = AppResources.optionString("UniprotQueryLevel2").trim();
	        if (!element.equals("") && element != null )
	            criteria.put(element, new Criterion("", null, query));
	
	        element = AppResources.optionString("UniprotElementLevel3").trim();
	        query = AppResources.optionString("UniprotQueryLevel3").trim();
	        if (!element.equals("") && element != null )
	            criteria.put(element, new Criterion("", null, query));
	
	        element = AppResources.optionString("UniprotElementLevel4").trim();
	        query = AppResources.optionString("UniprotQueryLevel4").trim();
	        if (!element.equals("") && element != null )
	            criteria.put(element, new Criterion("", null, query));
	        
    	} catch( InvalidParameterException e ){
    		//TODO: print to log file
    	}
    }
    
    
    /**
     * Gets the elements from the properties file for reading in
     * XML data (import files)
     *
     */
    private void getOboTallyElements(HashMap<String, Criterion> criteria){
    	
       	//HashMap<String, Criterion> criteria = new HashMap<String, Criterion>();
    	String element = null;
    	String query = null;

//    	 *** UniProt ***
    	try{
			// *** GO ***
			element = AppResources.optionString("GoElementLevel1").trim();
			query = AppResources.optionString("GoQueryLevel1").trim();
	        if (!element.equals("") && element != null)
	            criteria.put(element, new Criterion("", element, query));

	        element = AppResources.optionString("GoElementLevel2").trim();
	        query = AppResources.optionString("GoQueryLevel2").trim();
	        if (!element.equals("") && element != null)
	            criteria.put(element, new Criterion("", element, query));

	        element = AppResources.optionString("GoElementLevel3").trim();
	        query = AppResources.optionString("GoQueryLevel3").trim();
	        if (!element.equals("") && element != null)
	            criteria.put(element, new Criterion("", element, query));

	        element = AppResources.optionString("GoElementLevel4").trim();
	        query = AppResources.optionString("GoQueryLevel4").trim();
	        if (!element.equals("") && element != null)
	            criteria.put(element, new Criterion("", element, query));
	        
    	} catch( InvalidParameterException e ){
    		//TODO: print to log file
    	}

    }
    
    /**
     * Takes a String, which is used to set the text in the File Chooser.
     * Returns an InputStream with the file choosen or null if no file was
     * choosen.
     */
    private InputStream getXmlFile( String customText ){
        // Create a file chooser and setup the UniProt and GO input streams
        InputStream iStream = null;
        int returnVal; // used by FileChooser fc
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File( _lastFilePath ));
		
		// Get UniProt XML file
		returnVal = fc.showDialog(getFrontmostWindow(), customText);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
    		try {
    			_lastFilePath = fc.getSelectedFile().getAbsolutePath();
    			fc.setCurrentDirectory(new File(_lastFilePath) );
    			iStream = new FileInputStream(fc.getSelectedFile());
    			return iStream;
    			
    		} catch (FileNotFoundException e1) {
    			ModalDialog.showErrorDialog("File Not Found", "The chosen file was not accessible. Try again.");
                _Log.error(e1);
    			return null;
    		}
        } else {
        	return null;
        }
    }
    
    private void getTallyResultsXml( HashMap<String, Criterion> criteria, InputStream iStream ){
    	
    	TallyEngine te = new TallyEngine(criteria);
		try {
			criteria.putAll(te.getXmlFileCounts(iStream));

		} catch (InvalidParameterException e) {
			ModalDialog.showErrorDialog(e.getClass().getName(), e.getMessage());
            _Log.error(e);
		} catch (XpdException e) {
            ModalDialog.showErrorDialog(e.getClass().getName(), e.getMessage());
            _Log.error(e);
		} catch (Exception e) {
			ModalDialog.showErrorDialog(e.getClass().getName(),
					"An unexpected Exception was caught. Exception text: "
							+ e.getMessage());
            _Log.error(e);
		}
    }

    
    private void getTallyResultsDatabase( HashMap<String, Criterion> criteria, Configuration hibernateConfiguration ){
   	
    	TallyEngine te = new TallyEngine(criteria);
		try {
			/*
			 * Here I am explicitly catching the HibernateException, which is a
			 * pretty clear indication that the configuration was not done.
			 */
			criteria.putAll(te.getDbCounts(new QueryEngine(hibernateConfiguration)));
		} catch (InvalidParameterException e) {
			ModalDialog.showErrorDialog(e.getClass().getName(), e.getMessage());
            _Log.error(e);
		} catch (XpdException e) {
            ModalDialog.showErrorDialog(e.getClass().getName(), e.getMessage());
            _Log.error(e);
		} catch (HibernateException e){
            // TODO Well, strictly speaking, this command should never have
            // been selectable in the first place, if there is no valid
            // Hibernate configuration.
			ModalDialog.showErrorDialog("Problem with Hibernate", "A Hibernate exception was caught. If you have not configured your Hibernate properties, Do so now! Exception text: " + e.getMessage());
		} catch (Exception e) {
			ModalDialog.showErrorDialog(e.getClass().getName(),
					"An unexpected Exception was caught. Exception text: "
							+ e.getMessage());
            _Log.error(e);
		}
    }


    /**
     * Runs XML file and database tallies for UniProt and GO. The user is 
     * prompted for the 2 XML files, then all the processing is done. 
     * The results are presented in a dialog box from which they can be copied.
     */
    private void doTallies() {
        Configuration hibernateConfiguration = getCurrentHibernateConfiguration();
        if (hibernateConfiguration == null) {
            showConfigurationError();
            return;
        }
    	HashMap<String, Criterion> uniprotCriteria = new HashMap<String, Criterion>();
    	getXmlTallyElements(uniprotCriteria);
    	HashMap<String, Criterion> goCriteria = new HashMap<String, Criterion>();
    	getOboTallyElements(goCriteria);

    	
        // Create a file chooser and setup the UniProt and GO input streams
        InputStream uniprotInputStream = getXmlFile("Select UniProt XML file");
        if( uniprotInputStream == null ){
        	ModalDialog.showWarningDialog("No File Chosen", "No file chosen. Command aborted.");
        	return;
        }
        
        
        InputStream goInputStream = getXmlFile("Select GO XML file");
        if( goInputStream == null ){
        	ModalDialog.showWarningDialog("No File Chosen", "No file chosen. Command aborted.");
        	return;
        }

        getTallyResultsXml(uniprotCriteria, uniprotInputStream);
        getTallyResultsDatabase(uniprotCriteria, hibernateConfiguration);
		
        getTallyResultsXml(goCriteria, goInputStream);
        getTallyResultsDatabase(goCriteria, hibernateConfiguration);
        


        // Gather the criteria into a list so that we can display them
        // in a UsefulTable.
        BeanTableModel btm = new BeanTableModel(TALLY_COLUMNS);
        List<Criterion> criteria = new ArrayList<Criterion>(uniprotCriteria.size() + goCriteria.size());
        criteria.addAll(uniprotCriteria.values());
        criteria.addAll(goCriteria.values());
        btm.setData(criteria.toArray());
        UsefulTable t = new UsefulTable(btm);
        ModalDialog.showPlainDialog("Tally Results", new JScrollPane(t));

    }

    /**
     * Columns used for displaying tally results.
     */
    private static final BeanColumn[] TALLY_COLUMNS = {
        BeanColumn.create("XML Path", "digesterPath", String.class),
        BeanColumn.create("XML Count", "xmlCount", Integer.class),
        BeanColumn.create("Database Table", "table", String.class),
        BeanColumn.create("Database Count", "dbCount", Integer.class)
    };
    
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
                ModalDialog.showInformationDialog("GO Processing Complete", "GO processing completed successfully.");
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
	        	/*
	        	 * ExportToGenMAPP is initialized
	        	 */
	        	ExportToGenMAPP.init(hibernateConfiguration);
				getFrontmostWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				new ExportWizard(this.getFrontmostWindow());
				ExportToGenMAPP.cleanup();
	        } else {
                showConfigurationError();
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
     * Displays a message reporting a problem with database configuration.
     */
    private void showConfigurationError() {
        //FIXME Get text strings from an English resources file: i.e. i18n
        ModalDialog.showErrorDialog("No Configuration Defined", "No configuration was defined. Please set the database configuration.");
    }

    /**
     * Builds the current Hibernate configuration.
     */
    public static Configuration createHibernateConfiguration() {
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
    
    /**
     * @deprecated 
     */
    private Action _runTalliesAction;
    
    /**
     * These actions are for running the tallies from the Tallies menu.
     */
    private Action _xmlTallyAction;
    private Action _oboTallyAction;
    private Action _importedDataTallyAction;
    private Action _gdbTallyAction;
    
    /**
     * Stores the path last used in a file chooser
     */
    String _lastFilePath = System.getProperty("user.home");
}
