/*
 * Created on May 29, 2005
 *
 */
package edu.lmu.xmlpipedb.util.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
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
import edu.lmu.xmlpipedb.util.engines.QueryEngine;
import edu.lmu.xmlpipedb.util.engines.TallyEngine;
import edu.lmu.xmlpipedb.util.exceptions.CouldNotLoadPropertiesException;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;
import edu.lmu.xmlpipedb.util.exceptions.XpdException;
import edu.lmu.xmlpipedb.util.gui.ConfigurationPanel;
import edu.lmu.xmlpipedb.util.gui.HQLPanel;
import edu.lmu.xmlpipedb.util.gui.ImportPanel;

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
        menuItem = new JMenuItem(AppResources.messageString("menu_tools_config"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, accelMask));
        menuItem.setActionCommand("config_platform");
        menuItem.addActionListener(this);
        menuTools.add(menuItem);
        
        // Set up menu item.
        menuItem = new JMenuItem(AppResources.messageString("menu_tools_tally"));
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, accelMask));
        menuItem.setActionCommand("tally");
        menuItem.addActionListener(this);
        menuTools.add(menuItem);

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
        if ("import".equals(e.getActionCommand())) { // new
            _queryPanel = null;
            _configPanel = null;
            Configuration hibernateConfiguration = getHibernateConfig();
            if (hibernateConfiguration != null) {
                _importPanel = new ImportPanel(AppResources
						.optionString("jaxbContextPath"),
						hibernateConfiguration, "");
                _importPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                _initialFrame.setContentPane(_importPanel);
            }
        } else if ("query".equals(e.getActionCommand())) { // new
            _importPanel = null;
            _configPanel = null;
            Configuration hibernateConfiguration = getHibernateConfig();
            if (hibernateConfiguration != null) {
                _queryPanel = new HQLPanel(hibernateConfiguration);
                _queryPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                _initialFrame.setContentPane(_queryPanel);
            }
        } else if ("config_platform".equals(e.getActionCommand())) {
            _importPanel = null;
            _queryPanel = null;
            doConfigPanel();
        } else if ("tally".equals(e.getActionCommand())) {
            _importPanel = null;
            _queryPanel = null;
            _configPanel = null;
            doGetTallies();
        } else { // quit
            System.exit(0);
        }
        _initialFrame.validate();
    }

    private void doGetTallies() {
    	HashMap<String, Criterion> criteria = new HashMap<String, Criterion>();
    	String element = null;
    	String table = null;
    	element = AppResources.optionString("ElementLevel1");
    	table = AppResources.optionString("TableLevel1");
		if(!element.equals("") && element != null)
			criteria.put(element, new Criterion("", element, table));
    	element = AppResources.optionString("ElementLevel2");
    	table = AppResources.optionString("TableLevel2");
    	if(!element.equals("") && element != null)
			criteria.put(element, new Criterion("", element, table));
		element = AppResources.optionString("ElementLevel3");
		table = AppResources.optionString("TableLevel3");
		if(!element.equals("") && element != null)
			criteria.put(element, new Criterion("", element, table));
		element = AppResources.optionString("ElementLevel4");
		table = AppResources.optionString("TableLevel4");
		if(!element.equals("") && element != null)
			criteria.put(element, new Criterion("", element, table));
		
//		Create a file chooser and setup the input stream
		InputStream is = null;
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int returnVal = fc.showOpenDialog(this._initialFrame);
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
    		try {
    			is = new FileInputStream(fc.getSelectedFile());
    		} catch (FileNotFoundException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        } else {
        	JOptionPane.showMessageDialog(null, "No file choosen. Command aborted.");
        	return;
        }
		
		TallyEngine te = new TallyEngine(criteria);
		try {
			criteria.putAll(te.getXmlFileCounts(is));
			/*
			 * Here I am explicitly catching the HibernateException, which is a
			 * pretty clear indication that the configuration was not done.
			 * 
			 * TODO: Add an isConfigured() method to the ConfigurationEngine.
			 * This could be checked before getting the config. Or could be used
			 * to disable menus before the config is done.
			 */
			criteria.putAll( te.getDbCounts( new QueryEngine(getHibernateConfig() ) ) );
		} catch (InvalidParameterException e) {
			JOptionPane.showMessageDialog(this._initialFrame, e.getMessage());
		} catch (XpdException e) {
			JOptionPane.showMessageDialog(this._initialFrame, e.getMessage());
		} catch (HibernateException e){
			JOptionPane
					.showMessageDialog(
							this._initialFrame,
							"A Hibernate exception was caught. If you have not configured your hibernate properties, Do so now! Exception text: "
									+ e.getMessage());
		} catch (Exception e){
			JOptionPane.showMessageDialog(this._initialFrame,
					"An unexpected Exception was caught. Exception text: "
							+ e.getMessage());
		}
		
		String display = "Path: \t XML Count   \t||   Table: \t DB Count";
		
		Set set = criteria.keySet();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			Criterion crit = criteria.get(iter.next());
			display += "\n" + crit.getDigesterPath() + ": \t " + crit.getXmlCount() + "   \t||   " +  crit.getTable() + ": \t " + crit.getDbCount();
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
        } catch(FileNotFoundException e1) {
            // a proper handling of this exeption is left to the implementor
            e1.printStackTrace();
        } catch( CouldNotLoadPropertiesException e ){
        	e.printStackTrace();
        }
        
        _configPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        _initialFrame.setContentPane(_configPanel);
    }

    JFrame _initialFrame;
    ImportPanel _importPanel;
    ConfigurationPanel _configPanel;
    HQLPanel _queryPanel;
}
