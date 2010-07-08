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
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.go.ExportGoData;
import edu.lmu.xmlpipedb.gmbuilder.gui.wizard.export.ExportWizard;
import edu.lmu.xmlpipedb.gmbuilder.resource.properties.AppResources;
import edu.lmu.xmlpipedb.gmbuilder.util.ImportGOAEngine;
import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.engines.Criterion;
import edu.lmu.xmlpipedb.util.engines.CriterionList;
import edu.lmu.xmlpipedb.util.engines.ImportEngine;
import edu.lmu.xmlpipedb.util.engines.QueryEngine;
import edu.lmu.xmlpipedb.util.engines.RuleType;
import edu.lmu.xmlpipedb.util.engines.TallyEngine;
import edu.lmu.xmlpipedb.util.engines.TallyEngineDelegate;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;
import edu.lmu.xmlpipedb.util.exceptions.XpdException;
import edu.lmu.xmlpipedb.util.gui.ConfigurationPanel;
import edu.lmu.xmlpipedb.util.gui.HQLPanel;
import edu.lmu.xmlpipedb.util.gui.XMLPipeDBGUIUtils;

import shag.App;
import shag.dialog.ModalDialog;
import shag.menu.WindowMenu;
import shag.table.BeanColumn;
import shag.table.BeanTableModel;
import shag.table.UsefulTable;
import shag.util.PlatformIdentifier;

/**
 * GenMAPPBuilder is a GUI application for loading, querying, and exporting data
 * used by GenMAPP.
 *
 * @author dondi
 */
public class GenMAPPBuilder extends App implements TallyEngineDelegate {
    /**
     * Version string.
     */
    public static final String VERSION = "2.0b47";

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
        _Log.info("***** GenMAPP Builder started at: " + DateFormat.getTimeInstance(DateFormat.LONG).format(System.currentTimeMillis()));

        Configuration hc = createHibernateConfiguration();
        if (hc == null) {
            handleMissingHibernateConfiguration();
        } else {
            _queryPanel.setHibernateConfiguration(hc);
        }
    }

    /**
     * @see shag.App#showAboutBox()
     */
    @Override
    public void showAboutBox() {
        ModalDialog.showPlainDialog("About " + getAppName(), new JLabel(
            "<html><center>" +
            "<h1>&nbsp;&nbsp;&nbsp;" + getAppName() + " " + VERSION + "&nbsp;&nbsp;&nbsp;</h1>" +
            "<h3>Part of the XMLPipeDB Software Suite</h3>" +
            "<i>A project of the LMU Bioinformatics Group</i>" +
            "</center></html>"));
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
        JFrame result = new JFrame(getAppName() + " " + VERSION);
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
        fileMenu.add(_importGOAssociationAction);
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

        // JMenu dbMenu = new JMenu("DB Actions");
        // dbMenu.add(_doResetDbAction);
        // mb.add(dbMenu);

        mb.add(new WindowMenu(this));

        if (!PlatformIdentifier.isMac()) {
            JMenu helpMenu = new JMenu("Help");
            helpMenu.add(new AbstractAction("About " + getAppName()) {

                /**
                 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
                 */
                public void actionPerformed(ActionEvent aevt) {
                    showAboutBox();
                }

            });
            mb.add(helpMenu);
        }

        return mb;
    }

    /**
     * A species name found within an XML file needs to have some special
     * parsing done to it in order to match it with the proper species name in
     * the gmbuilder.properties file for the TallyEngine.
     *
     */
    private String getSpeciesNameFromString(String species) {
        String speciesString = "";
        if (species != null) {
            speciesString = species.toLowerCase();
            String[] substrings = speciesString.replaceAll(" ", "").split("\\(");
            if (substrings.length > 0) {
                speciesString = substrings[0];
            }
        }

        return speciesString;
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
                if (doGoImport("generated", "Import GO XML File") && ModalDialog.showQuestionDialog("Process GO Data?", "Some processing of the raw Gene Ontology data needs to be performed.\nThis may take a few minutes.  Proceed?")) {
                    doProcessGO();
                }
            }
        };

        _importGOAssociationAction = new AbstractAction("Import GOA File...") {
        	/**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
        	public void actionPerformed(ActionEvent aevt) {
        		doGoAssociationImport("Import GOA File"); // Purpose of first string
        	}
        };

        _runTalliesAction = new AbstractAction("Run XML and Database Tallies for UniProt and GO (The Full Monty)") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                doTallies();
            }
        };

        _gdbTallyAction = new AbstractAction("Run Tallies for Data Exported to GenMAPP Database") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                ModalDialog.showErrorDialog("Function not yet implemented.");
                // doTallies();
            }
        };

        _importedDataTallyAction = new AbstractAction("Run Tallies for Data Imported into Database") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                Configuration hibernateConfiguration = getCurrentHibernateConfiguration();
                if (hibernateConfiguration != null) {
                    try {
                        validateDatabaseSettings(hibernateConfiguration);

                        CriterionList uniprotCriteria = new CriterionList();
                        setTallyCriterion(uniprotCriteria, TallyType.UNIPROT);
        
                        CriterionList goCriteria = new CriterionList();
                        setTallyCriterion(goCriteria, TallyType.GO);
        
                        getTallyResultsDatabase(uniprotCriteria, hibernateConfiguration);
                        getTallyResultsDatabase(goCriteria, hibernateConfiguration);
        
                        // Gather the criteria into a list so that we can display them
                        // in a UsefulTable.
                        /**
                         * Columns used for displaying tally results.
                         */
                        final BeanColumn[] TallyColumns = { BeanColumn.create("Database Table", "table", String.class), BeanColumn.create("Database Count", "dbCount", Integer.class) };
                        BeanTableModel btm = new BeanTableModel(TallyColumns);
                        uniprotCriteria.addCriteria(goCriteria.getAllCriteria());
                        btm.setData(uniprotCriteria.getAllCriteria().toArray());
                        UsefulTable t = new UsefulTable(btm);
                        ModalDialog.showPlainDialog("Tally Results", new JScrollPane(t));
                    } catch(HibernateException hexc) {
                        handleErroneousHibernateConfiguration();
                    }
                } else {
                    handleMissingHibernateConfiguration();
                }
            }
        };

        _oboTallyAction = new AbstractAction("Run Tallies for GO XML File") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                CriterionList goCriteria = new CriterionList();
                setTallyCriterion(goCriteria, TallyType.GO);

                // Create a file chooser and setup the GO input stream
                File goFile = chooseXMLFile("Select GO XML file");
                if (goFile != null) {
                    getTallyResultsXml(goCriteria, goFile);
    
                    // Gather the criteria into a list so that we can display them
                    // in a UsefulTable.
                    /**
                     * Columns used for displaying tally results.
                     */
                    final BeanColumn[] TallyColumns = { BeanColumn.create("XML Path", "table", String.class), BeanColumn.create("XML Count", "xmlCount", Integer.class), };
    
                    BeanTableModel btm = new BeanTableModel(TallyColumns);
                    btm.setData(goCriteria.getAllCriteria().toArray());
                    UsefulTable t = new UsefulTable(btm);
                    ModalDialog.showPlainDialog("Tally Results", new JScrollPane(t));
                }
            }
        };

        _xmlTallyAction = new AbstractAction("Run Tallies for UniProt XML File") {
            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent aevt) {
                CriterionList uniprotCriteria = new CriterionList();
                setTallyCriterion(uniprotCriteria, TallyType.UNIPROT);

                // Create a file chooser and setup the UniProt input stream
                File uniprotFile = chooseXMLFile("Select UniProt XML file");
                if (uniprotFile != null) {
                    getTallyResultsXml(uniprotCriteria, uniprotFile);
    
                    // Gather the criteria into a list so that we can display them
                    // in a UsefulTable.
                    /**
                     * Columns used for displaying tally results.
                     */
                    final BeanColumn[] TallyColumns = { BeanColumn.create("XML Path", "table", String.class), BeanColumn.create("XML Count", "xmlCount", Integer.class), };
                    BeanTableModel btm = new BeanTableModel(TallyColumns);
                    btm.setData(uniprotCriteria.getAllCriteria().toArray());
                    UsefulTable t = new UsefulTable(btm);
                    ModalDialog.showPlainDialog("Tally Results", new JScrollPane(t));
                }
            }
        };

        _processGOAction = new AbstractAction("Process GO Data...") {
            public void actionPerformed(ActionEvent aevt) {
                doProcessGO();
            }
        };

        // TODO Work-in-progress: reset is not yet completely implemented.
        // _doResetDbAction = new
        // AbstractAction("Reset the database (WARNING: deletes all data)") {
        // public void actionPerformed(ActionEvent aevt) {
        // Configuration hibernateConfiguration =
        // getCurrentHibernateConfiguration();
        // if (hibernateConfiguration == null) {
        // showConfigurationError();
        // return;
        // }
        // boolean reset =
        // ModalDialog.showQuestionDialog("WARNING: This will delete all data loaded in database. This action cannot be undone. Are you sure you wish to do this?");
        // if( reset )
        // doResetUniprotAndGoDb(new QueryEngine(hibernateConfiguration));
        // }
        // };

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
            if (ModalDialog.showOKDialog("Configure Database", configPanel)) {
                // Update components that rely on the configuration.
                configPanel.saveConfiguration();
                _queryPanel.setHibernateConfiguration(createHibernateConfiguration());
            }
        } catch(Exception exc) {
            _Log.error(exc);
            ModalDialog.showErrorDialog("Unable to Configure Database", "Sorry, database configuration was unable to proceed.  This is most likely an error relating to file creation or modification on the system on which you are running.");
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
            HashMap<String, String> rootElement = new HashMap<String, String>(5);
            String head = "<uniprot xmlns=\"http://uniprot.org/uniprot\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://uniprot.org/uniprot http://www.uniprot.org/support/docs/uniprot.xsd\">";
            rootElement.put("head", head);
            rootElement.put("tail", "</uniprot>");
            try {
                final String importTitle = "Import UniProt XML File";
                ImportEngine importEngine = new ImportEngine(jaxbContextPath, hibernateConfiguration, "uniprot/entry", rootElement);
                File file = chooseXMLFile(importTitle);
                if (file != null) {
                    XMLPipeDBGUIUtils.performImportWithProgressBar(importEngine, file,
                        (ModalDialog.getTopDialog() != null) ? ModalDialog.getTopDialog() : getFrontmostWindow());
                }
            } catch(HibernateException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            } catch(JAXBException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            } catch(SAXException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            } catch(IOException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            }
        } else {
            handleMissingHibernateConfiguration();
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
    private boolean doGoImport(String jaxbContextPath, String title) {
        Configuration hibernateConfiguration = getCurrentHibernateConfiguration();
        if (hibernateConfiguration != null) {
            try {
                final String importTitle = "Import GO OBO XML File";
                ImportEngine importEngine = new ImportEngine(jaxbContextPath, hibernateConfiguration, "", null);
                File file = chooseXMLFile(importTitle);
                if (file != null) {
                    return XMLPipeDBGUIUtils.performImportWithProgressBar(importEngine, file,
                        (ModalDialog.getTopDialog() != null) ? ModalDialog.getTopDialog() : getFrontmostWindow());
                } else {
                    return false;
                }
            } catch(HibernateException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            } catch(JAXBException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            } catch(SAXException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            } catch(IOException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            }
        } else {
            handleMissingHibernateConfiguration();
        }

        // If we get here, we did not succeed.
        return false;
    }

    /**
     * Imports an GOA file into the database.
     *
     * @param jaxbContextPath
     *            The context path under which to store the object
     * @param title
     *            The title of the dialog (helps prompt the user on what file to
     *            import)
     */
    private void doGoAssociationImport(String title) {
        Configuration hibernateConfiguration = getCurrentHibernateConfiguration();
        if (hibernateConfiguration != null) {
            try {
                final String importTitle = "Import GOA File";
                ImportGOAEngine importGOAEngine = new ImportGOAEngine(hibernateConfiguration);
                File file = chooseImportFile(importTitle, new FilenameFilter() {
                    /**
                     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
                     */
                    public boolean accept(File dir, String name) {
                        return name.endsWith("goa") || name.endsWith("goa.txt");
                    }
                });
                if (file != null) {
                    importGOAEngine.importToSQL(file);
                }
            } catch(HibernateException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            } catch(IOException e) {
                _Log.error(e);
                ModalDialog.showErrorDialog("GOA I/O Error",
                    "<html><p>An I/O error has occured while importing the file.  Please make sure</p>" +
                    "<p>that the file you chose exists and is readable.</p></html>");
            } catch(SQLException e) {
                _Log.error(e);
                ModalDialog.showErrorDialog("Database Error",
                    "<html><p>A database error has occured while importing the file.  Please double-check</p>" +
                    "<p>your database server status and settings, then try again.</p></html>");
                handleMissingHibernateConfiguration();
            }
        } else {
            handleMissingHibernateConfiguration();
        }
    }

    /**
     * Helper method for import dialogs.
     */
    private File chooseImportFile(String importTitle, FilenameFilter filenameFilter) {
        FileDialog fileDialog = (ModalDialog.getTopDialog() != null) ?
            new FileDialog(ModalDialog.getTopDialog(), importTitle) :
            new FileDialog(getFrontmostWindow(), importTitle);
        fileDialog.setFilenameFilter(filenameFilter);
        fileDialog.setVisible(true);
        
        if (fileDialog.getFile() != null) {
            return new File(fileDialog.getDirectory(), fileDialog.getFile());
        } else {
            return null;
        }
    }

    /**
     * Helper method for importing/choosing XML files.
     */
    private File chooseXMLFile(String importTitle) {
        final FilenameFilter xmlFilenameFilter = new FilenameFilter() {
            /**
             * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
             */
            public boolean accept(File dir, String name) {
                return name.endsWith("xml");
            }
        };
        return chooseImportFile(importTitle, xmlFilenameFilter);
    }

    /**
     * Helper method for checking on database settings.
     */
    private void validateDatabaseSettings(Configuration hibernateConfiguration) {
        SessionFactory sessionFactory = hibernateConfiguration.buildSessionFactory();

        // Test the configuration by trying a transaction.
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        tx.rollback();
        session.close();
    }

    /**
     * Builds the criteria HashMap with the proper data to all the TallyEngine
     * to run properly.
     *
     * @param criteria
     *            The HashMap to load.
     * @param type
     *            The type of tallys to grab from the properties file.
     */
    private void setTallyCriterion(CriterionList criteria, TallyType type) {
        // We need to grab the correct strings to access
        // the resource file
        String mainPropertyString = null;

        switch (type) {
            case GO:
                mainPropertyString = "Go";
                break;

            case UNIPROT:
                mainPropertyString = "Uniprot";
                break;

            default:
                _Log.error("Unknown property attribute.");
        }

        setTallyCriterion(criteria, mainPropertyString);

        // A species criteiron must be using a different rule
        List<Criterion> speciesCrit = criteria.getBucket(AppResources.optionString("species_element_level").trim());
        if (speciesCrit != null) {
            speciesCrit.get(0).setRuleType(RuleType.FINDBODY);
            criteria.firstCriterion = speciesCrit.get(0);
        }

    }

    /**
     * Creates the species specific Criterion.
     *
     * @param criteria
     *            The list of criteria
     * @param species
     *            The name of the species (must be a known name)
     */
    private void setTallyCriterion(CriterionList criteria, String species) {

        if ("".equals(species)) {
            return;
        }

        // Remove all spaces between the species name, along with
        // any specific strain details. The first element in the
        // split array corresponds with the proper species name.
        species = getSpeciesNameFromString(species);
        _Log.debug("Setting tally criterion for: " + species);

        String speciesAmount = AppResources.optionString(species + "_level_amount");
        if (speciesAmount == null || "".equals(speciesAmount)) {
            return;
        }

        int levelAmount = Integer.parseInt(speciesAmount);

        String element = null;
        String query = null;
        String name = null;

        try {

            Criterion criterion;
            for (int i = 0; i < levelAmount; i++) {

                query = AppResources.optionString(species + "_query_level" + i).trim();
                name = AppResources.optionString(species + "_table_name_level" + i).trim();
                element = AppResources.optionString(species + "_element_level" + i).trim();

                // Only add a new criterion if an equivalent criterion is
                // not already there.
                criterion = new Criterion(name, element, query);
                criterion.setRuleType(RuleType.ENDOFRECORD);

                // It takes a little bit more finesse to pull out the
                // element path for the criterion in the properties file
                setXMLPathCriterion(element, criterion);
                if (!criteria.containsCriterion(criterion)) {
                    criteria.addCriteria(criterion);
                }

            }

        } catch(InvalidParameterException e) {
            _Log.error(e);
        }

    }

    /**
     * In charge of parsing the properties string into an element path and
     * attributes.
     *
     * @param xmlElement
     *            The string found in the properties file
     * @param criteria
     *            The object to update
     */
    private void setXMLPathCriterion(String xmlElement, Criterion criteria) {

        // The attributes are separated by a space
        String[] subStrings = xmlElement.split("&");

        // The beginning of the first attribute lives after
        // the first argument
        int attributeLength = subStrings.length;

        if (attributeLength > 1) {

            HashMap<String, String> attributes = new HashMap<String, String>();
            for (int i = 1; i < attributeLength; i += 2) {

                // The first argument we come across is the name,
                // the second is the value
                attributes.put(subStrings[i], subStrings[i + 1]);
            }

            criteria.setAttributes(attributes);

        }

        criteria.setDigesterPath(subStrings[0]);

    }

    private void getTallyResultsXml(CriterionList criteria, File xmlFile) {
        _currentCriteria = criteria;
        TallyEngine te = new TallyEngine(criteria);
        te.setDelegate(this);
        try {
            te.getXmlFileCounts(xmlFile);
        } catch(InvalidParameterException e) {
            _Log.error(e);
            ModalDialog.showErrorDialog(e.getClass().getName(), e.getMessage());
        } catch(XpdException e) {
            _Log.error(e);
            ModalDialog.showErrorDialog(e.getClass().getName(), e.getMessage());
        } catch(Exception e) {
            _Log.error(e);
            ModalDialog.showErrorDialog(e.getClass().getName(), "An unexpected Exception was caught. Exception text: " + e.getMessage());
        }
    }

    private void getTallyResultsDatabase(CriterionList criteria, Configuration hibernateConfiguration) {

        _currentCriteria = criteria;
        TallyEngine te = new TallyEngine(criteria);
        te.setDelegate(this);

        try {
            /*
             * Here I am explicitly catching the HibernateException, which is a
             * pretty clear indication that the configuration was not done.
             */
            te.getDbCounts(new QueryEngine(hibernateConfiguration));
        } catch(InvalidParameterException e) {
            _Log.error(e);
            ModalDialog.showErrorDialog(e.getClass().getName(), e.getMessage());
        } catch(XpdException e) {
            _Log.error(e);
            ModalDialog.showErrorDialog(e.getClass().getName(), e.getMessage());
        } catch(HibernateException e) {
            // TODO Well, strictly speaking, this command should never have
            // been selectable in the first place, if there is no valid
            // Hibernate configuration.
            ModalDialog.showErrorDialog("Problem with Hibernate", "A Hibernate exception was caught. If you have not configured your Hibernate properties, Do so now! Exception text: " + e.getMessage());
        } catch(Exception e) {
            _Log.error(e);
            ModalDialog.showErrorDialog(e.getClass().getName(), "An unexpected Exception was caught. Exception text: " + e.getMessage());
        }
    }

    /**
     * @see edu.lmu.xmlpipedb.util.engines.TallyEngineDelegate
     *      :processXMLBody(String)
     */
    public CriterionList processXMLBody(String body) {

        // This is where we will search for the species specific
        // id systems
        if (body == null) {
            return _currentCriteria;
        }

        setTallyCriterion(_currentCriteria, body);

        // we want to remove the species Criterion from
        // this list
        _currentCriteria.removeBucket(AppResources.optionString("uniprot_element_level0"));
        _Log.info("Setting XML body " + body);
        return _currentCriteria;
    }

    /**
     * @see edu.lmu.xmlpipedb.util.engines.TallyEngineDelegate
     *      :processDBColumn(String)
     */
    public void processDBColumn(String column) {

        // This is where we will search for the species specific
        // id systems
        if (column == null || "".equals(column)) {
            return;
        }

        _Log.info("Getting DB column " + column);
        setTallyCriterion(_currentCriteria, column);
    }

    /**
     * Runs XML file and database tallies for UniProt and GO. The user is
     * prompted for the 2 XML files, then all the processing is done. The
     * results are presented in a dialog box from which they can be copied.
     */
    private void doTallies() {
        Configuration hibernateConfiguration = getCurrentHibernateConfiguration();
        if (hibernateConfiguration != null) {
            try {
                validateDatabaseSettings(hibernateConfiguration);

                // Just getting the criterion
                CriterionList uniprotCriteria = new CriterionList();
                setTallyCriterion(uniprotCriteria, TallyType.UNIPROT);

                CriterionList goCriteria = new CriterionList();
                setTallyCriterion(goCriteria, TallyType.GO);

                // Create a file chooser and setup the UniProt and GO input streams
                File uniprotFile = chooseXMLFile("Select UniProt XML file");
                if (uniprotFile != null) {
                    File goFile = chooseXMLFile("Select GO XML file");
                    if (goFile != null) {
                        getTallyResultsXml(uniprotCriteria, uniprotFile);
                        getTallyResultsDatabase(uniprotCriteria, hibernateConfiguration);

                        getTallyResultsXml(goCriteria, goFile);
                        getTallyResultsDatabase(goCriteria, hibernateConfiguration);

                        // Gather the criteria into a list so that we can display them
                        // in a UsefulTable.
                        BeanTableModel btm = new BeanTableModel(TALLY_COLUMNS);
                        List<Criterion> criteria = new ArrayList<Criterion>();

                        criteria.addAll(uniprotCriteria.getAllCriteria());
                        criteria.addAll(goCriteria.getAllCriteria());
                        btm.setData(criteria.toArray());
                        UsefulTable t = new UsefulTable(btm);
                        ModalDialog.showPlainDialog("Tally Results", new JScrollPane(t));
                    }
                }
            } catch(HibernateException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            }
        } else {
            handleMissingHibernateConfiguration();
        }
    }

    // TODO Work-in-progress: reset is not yet completely implemented.
    // private void doResetUniprotAndGoDb(QueryEngine qe){
    // Connection conn = qe.currentSession().connection();
    // PreparedStatement query = null;
    // ResultSet results = null;
    // String sql = "";
    //
    // try {
    // // // try to find the file in the jar file first
    // // InputStream iStream =
    // getClass().getResourceAsStream(_defaultPropertiesUrl);
    // // if (iStream != null) {
    // // // iStream will be null if the file was not found
    // // _defaultProperties.load(iStream);
    // // } else {
    // // // since iStream WAS null, we'll try to find the properties
    // // // as a file in the file system
    // // File f = new File(_defaultPropertiesUrl);
    // // if (!f.exists()) {
    // // throw new FileNotFoundException(AppResources
    // // .messageString("exception.filenotfound.default")
    // // + _defaultPropertiesUrl);
    // // }
    // // FileInputStream fis = new FileInputStream(_defaultPropertiesUrl);
    // // _defaultProperties.load(fis);
    //
    // //getClass().getResourceAsStream(_defaultPropertiesUrl);
    // FileInputStream fis = new
    // FileInputStream("./sql/reset db for gmbuilder.sql");
    //
    // // Here BufferedInputStream is added for fast reading.
    // BufferedInputStream bis = new BufferedInputStream(fis);
    // DataInputStream dis = new DataInputStream(bis);
    //
    // // dis.available() returns 0 if the file does not have more lines.
    // while (dis.available() != 0) {
    //
    // // this statement reads the line from the file and print it to
    // // the console.
    // sql += dis.readLine();
    // }
    //
    // query = conn.prepareStatement( sql );
    // query.executeQuery();
    //
    // } catch(SQLException sqle) {
    // _Log.error("Caught exception in doResetUniprotAndGoDb() while trying to execute SQL statements.");
    // sqle.printStackTrace();
    // //came from HQLPanel -- probably not needed here
    // qe.currentSession().reconnect();
    // //throw new HibernateQueryException( sqle.getMessage() );
    // //Need to clean up connection after SQL exceptions
    // } catch(Exception e) {
    // // TODO: Log exception
    // //throw new XpdException(e.getMessage());
    // } finally {
    // try {
    // results.close();
    // query.close();
    //
    // //We need to be sure to NOT close the connection or the session here.
    // Leave it open!
    // } catch(Exception e) {
    // // TODO: Log exception
    //
    // } // Ignore the errors here, nothing we can do anyways.
    // }
    //
    // }

    /**
     * Columns used for displaying tally results.
     */
    private static final BeanColumn[] TALLY_COLUMNS = { BeanColumn.create("XML Path", "table", String.class), BeanColumn.create("XML Count", "xmlCount", Integer.class), BeanColumn.create("Database Table", "table", String.class), BeanColumn.create("Database Count", "dbCount", Integer.class) };

    /**
     * Processes the current GO data into staging and cached tables. These
     * tables are identical for every GenMAPP Gene Database export, so they can
     * be built once and just read directly later.
     */
    private void doProcessGO() {
        Configuration hibernateConfiguration = getCurrentHibernateConfiguration();
        if (hibernateConfiguration != null) {
            try {
                SessionFactory sessionFactory = hibernateConfiguration.buildSessionFactory();

                // Test the configuration by trying a transaction.
                Session session = sessionFactory.openSession();
                Transaction tx = session.beginTransaction();
                tx.rollback();
                session.close();

                // OK, go.
                session = sessionFactory.openSession();
                (new ExportGoData(session.connection())).populateGeneOntologyStage(hibernateConfiguration);
                session.close();
                ModalDialog.showInformationDialog("GO Processing Complete", "GO processing completed successfully.");
            } catch(HibernateException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            } catch(SQLException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            }
        } else {
            handleMissingHibernateConfiguration();
        }
    }

    /**
     * Exports the content of the current database to a GenMAPP database file.
     * For the moment, this runs only on Windows due to a dependence on the
     * native Access Jet engine.
     */
    private void doExportToGenMAPP() {
        getFrontmostWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Configuration hibernateConfiguration = GenMAPPBuilder.createHibernateConfiguration();
        if (hibernateConfiguration != null) {
            try {
                validateDatabaseSettings(hibernateConfiguration);

                /*
                 * ExportToGenMAPP is initialized
                 */
                ExportToGenMAPP.init(hibernateConfiguration);
                getFrontmostWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                new ExportWizard(this.getFrontmostWindow());
            } catch(HibernateException e) {
                _Log.error(e);
                handleErroneousHibernateConfiguration();
            } catch(Exception e) {
                _Log.error(e);
                ModalDialog.showErrorDialog("Unexpected Export Error",
                    "An unexpected error has occurred during export.\n" +
                    "Please consult the log for technical details.");
            } finally {
                try { ExportToGenMAPP.cleanup(); } catch(SQLException e) { /* ignored */ }
            }
        } else {
            handleMissingHibernateConfiguration();
        }
        
        getFrontmostWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Displays a message reporting a non-existent database configuration.
     */
    private void handleMissingHibernateConfiguration() {
        // FIXME Get text strings from an English resources file: i.e. i18n
        ModalDialog.showInformationDialog("No Database Configuration Found",
            "<html><p>No database configuration has been found.</p><br/><p>This is normal if you are starting GenMAPP Builder for the first time,</p><p>and may otherwise happen if the <tt>hibernate.properties</tt> file in the</p><p>GenMAPP Builder folder has been deleted or corrupted.</p><br/><p>The configuration dialog will now open so that proper setup can take place.</p></html>");
        doConfigureDatabase();
    }

    /**
     * Displays a message reporting an erroneous database configuration.
     */
    private void handleErroneousHibernateConfiguration() {
        // FIXME Get text strings from an English resources file: i.e. i18n
        ModalDialog.showErrorDialog("Database Connection Problem",
            "<html><p>GenMAPP Builder is unable to connect to the database.</p><br/>" +

            "<p>The most likely problem is either a database server that is not running</p>" +
            "<p>or an erroneous configuration setting.  If your database server is confirmed</p>" +
            "<p>to be available, double-check the database server address, port, username,</p>" + 
            "<p>and password.</p><br/>" +

            "<p>The configuration dialog will now open so that you can verify your settings.</p>" +
            "<p>Meanwhile, please check on whether your database server is running.</p><br/>" +

            "<p>If all settings check out and your database server is running, advanced users</p>" +
            "<p>can check the error log for additional technical details.</p></html>");
        doConfigureDatabase();
    }

    /**
     * Builds the current Hibernate configuration.
     */
    public static Configuration createHibernateConfiguration() {
        // TODO Fix this to update it to the new xpdutils stuff.
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
            // Thus, we report this via DEBUG level only.
            _Log.debug(exc);
        }

        return hibernateConfiguration;
    }

    /**
     * Determines whether the species specific criteria has already been loaded.
     */
    boolean _speciesCriterionLoaded;

    /**
     * The current criteria mapping that is being processed.
     */
    private CriterionList _currentCriteria;

    /**
     * The types that the TallyEngine can deal with.
     */
    private enum TallyType {
        UNIPROT, GO
    };

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
     * Action object for importing a goa file into the database
     */
    private Action _importGOAssociationAction;

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
    // TODO Work-in-progress: reset is not yet completely implemented.
    // private Action _doResetDbAction;

    /**
     * Stores the path last used in a file chooser
     */
    // This is really neat -- System.getProperty("user.home"); -- but I don't
    // want to be there!
    String _lastFilePath = ".";
}
