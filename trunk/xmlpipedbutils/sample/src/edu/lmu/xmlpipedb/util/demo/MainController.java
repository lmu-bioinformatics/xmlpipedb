/*
 * Created on May 29, 2005
 *
 */
package edu.lmu.xmlpipedb.util.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;

import edu.lmu.xmlpipedb.util.demo.resources.AppResources;
import edu.lmu.xmlpipedb.util.engines.ConfigurationEngine;
import edu.lmu.xmlpipedb.util.engines.Criterion;
import edu.lmu.xmlpipedb.util.engines.CriterionList;
import edu.lmu.xmlpipedb.util.engines.ImportEngine;
import edu.lmu.xmlpipedb.util.engines.QueryEngine;
import edu.lmu.xmlpipedb.util.engines.RuleType;
import edu.lmu.xmlpipedb.util.engines.TallyEngine;
import edu.lmu.xmlpipedb.util.exceptions.CouldNotLoadPropertiesException;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;
import edu.lmu.xmlpipedb.util.exceptions.XpdException;
import edu.lmu.xmlpipedb.util.gui.ConfigurationPanel;
import edu.lmu.xmlpipedb.util.gui.HQLPanel;
import edu.lmu.xmlpipedb.util.gui.XMLPipeDBGUIUtils;

/**
 * @author J. Nicholas
 * 
 */
public class MainController implements ActionListener {
    public MainController() {
        _initialFrame = null;
    }

    /**
     * Having a distinct start() method allows us to separate initialization
     * from execution cleanly
     */
    public void start() {
        SwingUtilities.invokeLater(new UIStarter());
    }

    /**
     * Wrap the UI-related startup sequence in a Runnable so that we can use it
     * within SwingUtilities.invokelater().
     * 
     * NOTE: don't know why, yet. took this code from Dondi.
     */
    private class UIStarter implements Runnable {
        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {
            _initialFrame = new JFrame(AppResources.messageString("str_title"));
            _initialFrame.setJMenuBar(createMenuBar());
            JFrame.setDefaultLookAndFeelDecorated(true);

            // Set initial size and location
            int inset = 400;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            _initialFrame.setBounds(inset, inset, screenSize.width - inset, screenSize.height - inset);
            _initialFrame.setLocation(inset / 2, inset / 2);

            _initialFrame.setIconImage(createImage());
            _initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            _initialFrame.setVisible(true);
        }
    }

    private Configuration getHibernateConfig() {
        String hibernateMappingLocation = AppResources.optionString("hibernateMappingLocation");
        Configuration hibernateConfiguration = null;
        try {
            hibernateConfiguration = (new ConfigurationEngine()).getHibernateConfiguration();
        } catch(Exception exc) {
            hibernateConfiguration = new Configuration();
        }
        hibernateConfiguration.addJar(new File(hibernateMappingLocation));
        return hibernateConfiguration;
    }

    // Creates an icon-worthy Image from scratch.
    protected static Image createImage() {
        // Create a 16x16 pixel image.
        BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);

        // Draw into it.
        Graphics g = bi.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 15, 15);
        g.setColor(Color.YELLOW);
        g.fillOval(2, 2, 14, 14);
        g.setColor(Color.RED);
        g.fillRect(6, 6, 1, 2);
        g.fillRect(9, 6, 1, 2);
        g.drawLine(4, 10, 12, 10);

        // Clean up.
        g.dispose();

        // Return it.
        return bi;
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem;
        int accelMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        // Set up the tools menu.
        JMenu menuTools = new JMenu(AppResources.messageString("menu_tools"));
        menuTools.setMnemonic(KeyEvent.VK_T);
        menuBar.add(menuTools);

        // Set up menu item.
        menuItem = new JMenuItem(AppResources.messageString("menu_tools_config"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, accelMask));
        menuItem.setActionCommand("config_platform");
        menuItem.addActionListener(this);
        menuTools.add(menuItem);
        menuTools.addSeparator();
        
        // Set up the import menu item.
        menuItem = new JMenuItem(AppResources.messageString("menu_tools_import"));
        menuItem.setMnemonic(KeyEvent.VK_I);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, accelMask));
        menuItem.setActionCommand("import");
        menuItem.addActionListener(this);
        menuTools.add(menuItem);

        // Set up the query menu item.
        menuItem = new JMenuItem(AppResources.messageString("menu_tools_query"));
        menuItem.setMnemonic(KeyEvent.VK_U);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, accelMask));
        menuItem.setActionCommand("query");
        menuItem.addActionListener(this);
        menuTools.add(menuItem);

        // Set up the tools menu.
        // JMenu menuConfig = new
        // JMenu(AppResources.messageString("menu_config"));
        // menuConfig.setMnemonic(KeyEvent.VK_T);
        // menuBar.add(menuConfig);

        // Set up menu item.
        menuItem = new JMenuItem(AppResources.messageString("menu_tools_tally"));
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, accelMask));
        menuItem.setActionCommand("tally");
        menuItem.addActionListener(this);
        menuTools.add(menuItem);
        menuTools.addSeparator();

        // Set up the last menu item.
        menuItem = new JMenuItem(AppResources.messageString("menu_tools_quit"));
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, accelMask));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menuTools.add(menuItem);

        return menuBar;
    } // end createMenuBar

    // React to menu selections.
    public void actionPerformed(ActionEvent e) {
        // Save the configuration if necessary.
        if (_configPanel != null) {
            _configPanel.saveConfiguration();
        }

        if ("import".equals(e.getActionCommand())) { // new
            _queryPanel = null;
            _configPanel = null;
            Configuration hibernateConfiguration = getHibernateConfig();
            Map<String,String> rootElement = new HashMap<String,String>(1);
            rootElement.put("head", "<bookstore>");
            rootElement.put("tail", "</bookstore>");
            if (hibernateConfiguration != null) {
                try {
                    ImportEngine importEngine = new ImportEngine(AppResources
                            .optionString("jaxbContextPath"), hibernateConfiguration,
                            "bookstore/book", rootElement);
                    File file = chooseXMLFile(AppResources.messageString("choose_book_xml"));
                    if (file != null) {
                        XMLPipeDBGUIUtils.performImportWithProgressBar(importEngine, file);
                    }
                } catch(Exception exception) {
                    JOptionPane.showMessageDialog(_initialFrame, exception.getMessage());
                }
            }
        } else if ("query".equals(e.getActionCommand())) { // new
            _configPanel = null;
            Configuration hibernateConfiguration = getHibernateConfig();
            if (hibernateConfiguration != null) {
                _queryPanel = new HQLPanel(hibernateConfiguration);
                _queryPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                _initialFrame.setContentPane(_queryPanel);
            }
        } else if ("config_platform".equals(e.getActionCommand())) {
            _queryPanel = null;
            doConfigPanel();
        } else if ("tally".equals(e.getActionCommand())) {
            _queryPanel = null;
            _configPanel = null;
            doGetTallies();
        } else { // quit
            System.exit(0);
        }
        _initialFrame.validate();
    }

    /**
     * Helper method for import dialogs.
     */
    private File chooseImportFile(String importTitle, FilenameFilter filenameFilter) {
        FileDialog fileDialog = new FileDialog(_initialFrame, importTitle);
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

    private void doGetTallies() {
    	CriterionList criteria = new CriterionList();
        try {
        	Criterion criterion = new Criterion("",
        	        AppResources.optionString("bookElement"),
        	        AppResources.optionString("bookTable"));
        	criterion.setRuleType(RuleType.ENDOFRECORD);
        	criteria.addCriteria(criterion);
    	
        	criterion = new Criterion("",
                    AppResources.optionString("authorElement"),
                    AppResources.optionString("authorTable"));
            criterion.setRuleType(RuleType.ENDOFRECORD);
            criteria.addCriteria(criterion);
    	} catch(InvalidParameterException e) {
    	    JOptionPane.showMessageDialog(_initialFrame, e.getMessage());
    	}
    	
    	// Determine the file.
        File f = chooseXMLFile(AppResources.messageString("choose_tally_xml"));
        if (f == null) {
        	JOptionPane.showMessageDialog(null, "No file chosen.  Command aborted.");
        	return;
        }
        
		TallyEngine te = new TallyEngine(criteria);
        try {
            te.getXmlFileCounts(f);
            /*
             * Here I am explicitly catching the HibernateException, which is a
             * pretty clear indication that the configuration was not done.
             * 
             * TODO: Add an isConfigured() method to the ConfigurationEngine.
             * This could be checked before getting the config. Or could be used
             * to disable menus before the config is done.
             */
            te.getDbCounts(new QueryEngine(getHibernateConfig()));
        } catch(InvalidParameterException e) {
            JOptionPane.showMessageDialog(this._initialFrame, e.getMessage());
        } catch(XpdException e) {
            JOptionPane.showMessageDialog(this._initialFrame, e.getMessage());
        } catch(HibernateException e) {
            JOptionPane.showMessageDialog(this._initialFrame,
                    "A Hibernate exception was caught. If you have not configured your hibernate properties, Do so now! Exception text: " +
                    e.getMessage());
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this._initialFrame,
                    "An unexpected Exception was caught. Exception text: " +
                    e.getMessage());
        }
		
		String display = "Path: \t XML Count   \t||   DB Count";
        for (String key: criteria.keySet()) {
            List<Criterion> critBucket = criteria.getBucket(key);
            for (Criterion crit : critBucket) {
                display += "\n" + crit.getDigesterPath() + ": \t " + crit.getXmlCount() + "   \t||   " + crit.getDbCount();
            }
        }
		JOptionPane.showMessageDialog(null, display);
	}

	public void validate() {
        _initialFrame.validate();
    }

    public void cancelAction() {
        _initialFrame.setContentPane(new JPanel());
        _initialFrame.validate();
    }

    private void doConfigPanel() {

        try {
            _configPanel = new ConfigurationPanel();
            _configPanel.setCurrentPlatform("PostgreSQL");
        } catch(FileNotFoundException fnfexc) {
            JOptionPane.showMessageDialog(this._initialFrame, fnfexc.getMessage());
        } catch(CouldNotLoadPropertiesException cnlpexc) {
            JOptionPane.showMessageDialog(this._initialFrame, cnlpexc.getMessage());
        }
        
        _configPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        _initialFrame.setContentPane(_configPanel);
    }

    JFrame _initialFrame;
    ConfigurationPanel _configPanel;
    HQLPanel _queryPanel;
}
