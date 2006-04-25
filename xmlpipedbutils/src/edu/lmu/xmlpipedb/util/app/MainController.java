/*
 * Created on May 29, 2005
 *
 */
package edu.lmu.xmlpipedb.util.app;

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
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import edu.lmu.xmlpipedb.util.gui.ConfigurationPanel;
import edu.lmu.xmlpipedb.util.gui.HQLPanel;
import edu.lmu.xmlpipedb.util.gui.ImportPanel;
import edu.lmu.xmlpipedb.util.resources.AppResources;
import edu.lmu.xmlpipedb.util.utilities.ImportEngine;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;

/**
 * @author J. Nicholas
 * 
 */
public class MainController implements ActionListener {

    Configuration hibernateConfiguration;
    
	public MainController() {
		_initialFrame = null;

	} // end no-arg constructor


	/**
	 * Having a distinct start() method allows us to separate initialization
	 * from execution cleanly
	 */
	public void start() {
		SwingUtilities.invokeLater(new UIStarter());
	} // end start

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
			_initialFrame.setBounds(inset, inset, screenSize.width - inset,
					screenSize.height - inset);
			_initialFrame.setLocation(inset / 2, inset / 2);

			_initialFrame.setIconImage(createImage());
			_initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			_initialFrame.setVisible(true);

		} // end run

	} // end inner classs UIStarter

	/*public void importXml(File xmlFile) throws Exception {
		String context = AppResources.optionString("jaxbContextPath");
		String hibernateProp = AppResources.optionString("hibernateProperties");

		String hibernateConfig = AppResources
				.optionString("hibernateMappingDir");
                
                setHibernateConfig(hibernateConfig, hibernateProp);
		ImportEngine importEngine = new ImportEngine(context, hibernateConfiguration);
		importEngine.loadToDB(xmlFile);
	}*/

	public void importXml(InputStream xml) throws Exception {
		String context = AppResources.optionString("jaxbContextPath");
		String hibernateProp = AppResources.optionString("hibernateProperties");

		String hibernateConfig = AppResources
				.optionString("hibernateMappingDir");
                
                setHibernateConfig(hibernateConfig, hibernateProp);
		ImportEngine importEngine = new ImportEngine(context, hibernateConfiguration);
		importEngine.loadToDB(xml);
	}        
        private void setHibernateConfig(String hibernateMappingDirectory, String hibernatePropertiesFileName) throws IOException, HibernateException {
            hibernateConfiguration = new Configuration();
            hibernateConfiguration.addJar(new File(hibernateMappingDirectory));
            Properties hibernateProperties = new Properties();
            hibernateProperties.load(new FileInputStream(hibernatePropertiesFileName));
            hibernateConfiguration.setProperties(hibernateProperties);

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
	} // end createFDImage

	protected JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenuItem menuItem;
		int accelMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

		// Set up the tools menu.
		JMenu menuTools = new JMenu(AppResources.messageString("menu_tools"));
		menuTools.setMnemonic(KeyEvent.VK_T);
		menuBar.add(menuTools);

		// Set up the import menu item.
		menuItem = new JMenuItem(AppResources
				.messageString("menu_tools_import"));
		menuItem.setMnemonic(KeyEvent.VK_I);
		menuItem.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_I, accelMask));
		menuItem.setActionCommand("import");
		menuItem.addActionListener(this);
		menuTools.add(menuItem);

		// Set up the query menu item.
		menuItem = new JMenuItem(AppResources.messageString("menu_tools_query"));
		menuItem.setMnemonic(KeyEvent.VK_U);
		menuItem.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_U, accelMask));
		menuItem.setActionCommand("query");
		menuItem.addActionListener(this);
		menuTools.add(menuItem);


		// Set up the tools menu.
//		JMenu menuConfig = new JMenu(AppResources.messageString("menu_config"));
//		menuConfig.setMnemonic(KeyEvent.VK_T);
//		menuBar.add(menuConfig);

		// Set up menu item.
		menuItem = new JMenuItem(AppResources
				.messageString("menu_tools_config"));
		menuItem.setMnemonic(KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_C, accelMask));
		menuItem.setActionCommand("config_platform");
		menuItem.addActionListener(this);
		menuTools.add(menuItem);
		
		// Set up the last menu item.
		menuItem = new JMenuItem(AppResources.messageString("menu_tools_quit"));
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_Q, accelMask));
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
			_importPanel = new ImportPanel(this);
			_importPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			_initialFrame.setContentPane(_importPanel);
		} else if ("query".equals(e.getActionCommand())) { // new
			_importPanel = null;
			_configPanel = null;
			_queryPanel = new HQLPanel();
			_queryPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			_initialFrame.setContentPane(_queryPanel);
		} else if ("config_platform".equals(e.getActionCommand())) { 
			_importPanel = null;
			_queryPanel = null;
			doConfigPanel();
		}else { // quit
			System.exit(0);
		}
		_initialFrame.validate();
	}

	public void validate() {
		_initialFrame.validate();
	}

	public void cancelAction() {
		_initialFrame.setContentPane(new JPanel());
		_initialFrame.validate();
	}
	
	private void doConfigPanel(){
		ConfigurationController cc = new ConfigurationController(AppResources
				.optionString("hibernateProperties"), AppResources
				.optionString("hibernate_properties_default_url"));
		
		try {
			_configPanel = new ConfigurationPanel(cc.getConfigurationModel(),
					new Properties(), cc);
		} catch (FileNotFoundException e1) {
			// a proper handling of this exeption is left to the implementor
			e1.printStackTrace();
		}
		_configPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		_initialFrame.setContentPane(_configPanel);
	}

	// ### DEFINE VARS ###
	JFrame _initialFrame;
	ImportPanel _importPanel;
	ConfigurationPanel _configPanel;
	HQLPanel _queryPanel;

} // end Main
