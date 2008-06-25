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
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
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
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import shag.App;
import shag.dialog.ModalDialog;
import shag.menu.WindowMenu;
import shag.table.BeanColumn;
import shag.table.BeanTableModel;
import shag.table.UsefulTable;
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
        
        // We will use logging properties set in a file. This will give us
        // greater flexibility and control over our logging
        // Set up logging. next line just uses basic logging
        BasicConfigurator.configure();
        _Log.warn("\n\n\n***** GenMapp Builder Started at: " + DateFormat.getTimeInstance(DateFormat.LONG).format( System.currentTimeMillis()) );

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
        fileMenu.add(_exportToGenMAPPAction);
        mb.add(fileMenu);

        JMenu tallyMenu = new JMenu("Tallies");
        tallyMenu.add(_xmlTallyAction);
        tallyMenu.add(_oboTallyAction);
        tallyMenu.add(_importedDataTallyAction);
        tallyMenu.add(_gdbTallyAction);
        tallyMenu.addSeparator();
        tallyMenu.add(_runTalliesAction);
        mb.add(tallyMenu);
        
//        JMenu dbMenu = new JMenu("DB Actions");
//        dbMenu.add(_doResetDbAction);
//        mb.add(dbMenu);
        
        mb.add(new WindowMenu(this));
        return mb;
    }

    /**
     * Creates the actions performed by the application.
     * 
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
            	doUniprotImport("org.uniprot.uniprot", "Import UniProtDB XML File");
            }
        };
        
        _importGOAction = new AbstractAction("Import GO XML File...") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
            	doGoImport("generated", "Import GO XML File");
                doProcessGO();
            }
        };
        
        _runTalliesAction = new AbstractAction("Run Xml and Db Tallies for Uniprot and Go (The Full Monty)") {
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
            	ModalDialog.showErrorDialog("Function not yet implemented.");
            	//doTallies();
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
            	getXmlTallyElementsDb(uniprotCriteria);
            	HashMap<String, Criterion> goCriteria = new HashMap<String, Criterion>();
            	getOboTallyElementsDb(goCriteria);

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
             	HashMap<String, Criterion> goCriteria = new HashMap<String, Criterion>();
            	getOboTallyElements(goCriteria);

                // Create a file chooser and setup the GO input stream
                InputStream goInputStream = getXmlFile("Select GO XML file");
                if( goInputStream == null ){
                	ModalDialog.showWarningDialog("No File Chosen", "No file chosen. Command aborted.");
                	return;
                }

                getTallyResultsXml(goCriteria, goInputStream);
                
                // Gather the criteria into a list so that we can display them
                // in a UsefulTable.
                /**
                 * Columns used for displaying tally results.
                 */
                final BeanColumn[] TallyColumns = {
                		BeanColumn.create("XML Path", "digesterPath", String.class),
                	    BeanColumn.create("XML Count", "xmlCount", Integer.class),
                };
                BeanTableModel btm = new BeanTableModel(TallyColumns);
                List<Criterion> criteria = new ArrayList<Criterion>(goCriteria.size() );
                criteria.addAll(goCriteria.values());
                btm.setData(criteria.toArray());
                UsefulTable t = new UsefulTable(btm);
                ModalDialog.showPlainDialog("Tally Results", new JScrollPane(t));
            }
        };
        
        _xmlTallyAction = new AbstractAction("Run Tallies for Uniprot XML File") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
            	HashMap<String, Criterion> uniprotCriteria = new HashMap<String, Criterion>();
            	getXmlTallyElements(uniprotCriteria);

                // Create a file chooser and setup the UniProt input stream
                InputStream uniprotInputStream = getXmlFile("Select UniProt XML file");
                if( uniprotInputStream == null ){
                	ModalDialog.showWarningDialog("No File Chosen", "No file chosen. Command aborted.");
                	return;
                }

                getTallyResultsXml(uniprotCriteria, uniprotInputStream );
                
                // Gather the criteria into a list so that we can display them
                // in a UsefulTable.
                /**
                 * Columns used for displaying tally results.
                 */
                final BeanColumn[] TallyColumns = {
                		BeanColumn.create("XML Path", "digesterPath", String.class),
                	    BeanColumn.create("XML Count", "xmlCount", Integer.class),
                };
                BeanTableModel btm = new BeanTableModel(TallyColumns);
                List<Criterion> criteria = new ArrayList<Criterion>(uniprotCriteria.size() );
                criteria.addAll(uniprotCriteria.values());
                btm.setData(criteria.toArray());
                UsefulTable t = new UsefulTable(btm);
                ModalDialog.showPlainDialog("Tally Results", new JScrollPane(t));
            }
        };
        
        
        _processGOAction = new AbstractAction("Process GO Data...") {
            public void actionPerformed(ActionEvent aevt) {
                doProcessGO();
            }
        };
        
        _doResetDbAction = new AbstractAction("Reset the database (WARNING: deletes all data)") {
            public void actionPerformed(ActionEvent aevt) {
            	Configuration hibernateConfiguration = getCurrentHibernateConfiguration();
                if (hibernateConfiguration == null) {
                    showConfigurationError();
                    return;
                }
                boolean reset = ModalDialog.showQuestionDialog("WARNING: This will delete all data loaded in database. This action cannot be undone. Are you sure you wish to do this?");
            	if( reset )
            		doResetUniprotAndGoDb(new QueryEngine(hibernateConfiguration));
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
    private void doUniprotImport(String jaxbContextPath, String title) {
        Configuration hibernateConfiguration = getCurrentHibernateConfiguration();
        if (hibernateConfiguration != null) {
        	HashMap<String,String> rootElement = new HashMap<String,String>(5);
            String head = "<uniprot xmlns=\"http://uniprot.org/uniprot\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://uniprot.org/uniprot http://www.uniprot.org/support/docs/uniprot.xsd\">";
            rootElement.put("head",head );
            rootElement.put("tail","</uniprot>" );
            ImportPanel importPanel = new ImportPanel(jaxbContextPath, hibernateConfiguration, "uniprot/entry", rootElement);
            importPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            ModalDialog.showPlainDialog(title, importPanel);
        } else {
            showConfigurationError();
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
    private void doGoImport(String jaxbContextPath, String title) {
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
    	String element = null;

    	try{
	    	element = AppResources.optionString("UniprotElementLevel1").trim();
	    	setElementInCriterion(criteria, element);
	        
	    	element = AppResources.optionString("UniprotElementLevel2").trim();
	    	setElementInCriterion(criteria, element);
	    	
	        element = AppResources.optionString("UniprotElementLevel3").trim();
	    	setElementInCriterion(criteria, element);
	
	        element = AppResources.optionString("UniprotElementLevel4").trim();
	    	setElementInCriterion(criteria, element);
	    	
    	} catch( InvalidParameterException e ){
    		//TODO: print to log file
    	}
    }
    
    /**
     * Gets the queries used to get import data counts from the properties file. 
     * The HashMap passed in is populated.
     *
     */
    private void getXmlTallyElementsDb(HashMap<String, Criterion> criteria){
    	String element = null;
    	String query = null;

    	try{
	    	element = AppResources.optionString("UniprotElementLevel1").trim();
	    	query = AppResources.optionString("UniprotQueryLevel1").trim();
	    	setQueryInCriterion(criteria, element, query);
	        
	    	element = AppResources.optionString("UniprotElementLevel2").trim();
	    	query = AppResources.optionString("UniprotQueryLevel2").trim();
	    	setQueryInCriterion(criteria, element, query);
	
	        element = AppResources.optionString("UniprotElementLevel3").trim();
	        query = AppResources.optionString("UniprotQueryLevel3").trim();
	    	setQueryInCriterion(criteria, element, query);
	
	        element = AppResources.optionString("UniprotElementLevel4").trim();
	        query = AppResources.optionString("UniprotQueryLevel4").trim();
	    	setQueryInCriterion(criteria, element, query);
	        
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
    	String element = null;

    	try{
			// *** GO ***
			element = AppResources.optionString("GoElementLevel1").trim();
			setElementInCriterion(criteria, element);

	        element = AppResources.optionString("GoElementLevel2").trim();
	        setElementInCriterion(criteria, element);

	        element = AppResources.optionString("GoElementLevel3").trim();
	        setElementInCriterion(criteria, element);

	        element = AppResources.optionString("GoElementLevel4").trim();
	        setElementInCriterion(criteria, element);
	        
            element = AppResources.optionString("GoElementLevel5").trim();
            setElementInCriterion(criteria, element);
            
            element = AppResources.optionString("GoElementLevel6").trim();
            setElementInCriterion(criteria, element);
            
    	} catch( InvalidParameterException e ){
    		//TODO: print to log file
    	}

    }
    
    /**
     * Gets the elements from the properties file for reading in
     * XML data (import files)
     *
     */
    private void getOboTallyElementsDb(HashMap<String, Criterion> criteria){
    	String element = null;
    	String query = null;

    	try{
			// *** GO ***
			element = AppResources.optionString("GoElementLevel1").trim();
			query = AppResources.optionString("GoQueryLevel1").trim();
	        setQueryInCriterion(criteria, element, query);

	        element = AppResources.optionString("GoElementLevel2").trim();
	        query = AppResources.optionString("GoQueryLevel2").trim();
	        setQueryInCriterion(criteria, element, query);

	        element = AppResources.optionString("GoElementLevel3").trim();
	        query = AppResources.optionString("GoQueryLevel3").trim();
	        setQueryInCriterion(criteria, element, query);

	        element = AppResources.optionString("GoElementLevel4").trim();
	        query = AppResources.optionString("GoQueryLevel4").trim();
	        setQueryInCriterion(criteria, element, query);
	        
            element = AppResources.optionString("GoElementLevel5").trim();
            query = AppResources.optionString("GoQueryLevel4").trim();
            setQueryInCriterion(criteria, element, query);
            
            element = AppResources.optionString("GoElementLevel6").trim();
            query = AppResources.optionString("GoQueryLevel4").trim();
            setQueryInCriterion(criteria, element, query);
            
    	} catch( InvalidParameterException e ){
    		//TODO: print to log file
    	}

    }
   
    /**
     * Determine if a Criteria object already exists in the HashMap and 
     * update it (if exists) or add a new object (if not exists).
     * 
     * @param criteria HashMap of Criterion objects
     * @param element String with the element (path that Digester will look for in the xml file)
     * @throws InvalidParameterException 
     */
    private void setQueryInCriterion(HashMap<String, Criterion> criteria, String element, String query) throws InvalidParameterException{
    	if (!element.equals("") && element != null ){
    		if( criteria.containsKey(element) ){
    			Criterion tmpCriteria = criteria.get(element);
    			tmpCriteria.setQuery( query );
    		}else{
    			criteria.put(element, new Criterion("", null, query));
    		}
    	}
    }
    
    /**
     * Determine if a Criteria object already exists in the HashMap and 
     * update it (if exists) or add a new object (if not exists).
     * 
     * @param criteria HashMap of Criterion objects
     * @param element String with the element (path that Digester will look for in the xml file)
     * @throws InvalidParameterException 
     */
    private void setElementInCriterion(HashMap<String, Criterion> criteria, String element) throws InvalidParameterException{
    	if (!element.equals("") && element != null ){
    		if( criteria.containsKey(element) ){
    			Criterion tmpCriteria = criteria.get(element);
    			tmpCriteria.setDigesterPath(element);
    		}else{
    			criteria.put(element, new Criterion("", element, null) );
    		}
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
			te.getDbCounts(new QueryEngine(hibernateConfiguration));
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
    	getXmlTallyElementsDb(uniprotCriteria);
    	HashMap<String, Criterion> goCriteria = new HashMap<String, Criterion>();
    	getOboTallyElements(goCriteria);
    	getOboTallyElementsDb(goCriteria);

    	
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

    private void doResetUniprotAndGoDb(QueryEngine qe){
		Connection conn = qe.currentSession().connection();
        PreparedStatement query = null;
        ResultSet results = null;
        String sql = "";

		try {
//        	// try to find the file in the jar file first
//            InputStream iStream = getClass().getResourceAsStream(_defaultPropertiesUrl);
//            if (iStream != null) { 
//            	// iStream will be null if the file was not found
//                _defaultProperties.load(iStream);
//            } else {
//            	// since iStream WAS null, we'll try to find the properties
//            	// as a file in the file system
//            	File f = new File(_defaultPropertiesUrl);
//                if (!f.exists()) {
//                    throw new FileNotFoundException(AppResources
//							.messageString("exception.filenotfound.default")
//							+ _defaultPropertiesUrl);
//                }
//                FileInputStream fis = new FileInputStream(_defaultPropertiesUrl);
//                _defaultProperties.load(fis);
			
			//getClass().getResourceAsStream(_defaultPropertiesUrl);
	        FileInputStream fis = new FileInputStream("./sql/reset db for gmbuilder.sql");

	        // Here BufferedInputStream is added for fast reading.
	        BufferedInputStream bis = new BufferedInputStream(fis);
	        DataInputStream dis = new DataInputStream(bis);
	        
			// dis.available() returns 0 if the file does not have more lines.
	        while (dis.available() != 0) {

	        // this statement reads the line from the file and print it to
	          // the console.
	          sql += dis.readLine();
	        }
			
            query = conn.prepareStatement( sql  );
            query.executeQuery();

		} catch(SQLException sqle) {
			_Log.error("Caught exception in doResetUniprotAndGoDb() while trying to execute SQL statements.");
			sqle.printStackTrace();
			//came from HQLPanel -- probably not needed here qe.currentSession().reconnect();
			//throw new HibernateQueryException(  sqle.getMessage() );
			//Need to clean up connection after SQL exceptions
        } catch(Exception e) {
//        	TODO: Log exception
            //throw new XpdException(e.getMessage());
        } finally {
            try {
                results.close();
                query.close();

               //We need to be sure to NOT close the connection or the session here. Leave it open!
            } catch(Exception e) {
//            	TODO: Log exception
                
            } // Ignore the errors here, nothing we can do anyways.
        }

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
     * Runs tallies from xml and db for uniprot and go 
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
     * Drops and recreates all database objects
     */
    private Action _doResetDbAction;
    
    /**
     * Stores the path last used in a file chooser
     */
    // This is really neat -- System.getProperty("user.home"); -- but I don't want to be there!
    String _lastFilePath = ".";
}
