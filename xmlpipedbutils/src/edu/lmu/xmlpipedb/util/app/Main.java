/*
 * Created on May 29, 2005
 *
 */
package edu.lmu.xmlpipedb.util.app;

import edu.lmu.xmlpipedb.util.gui.ConfigurationPanel;
import edu.lmu.xmlpipedb.util.gui.HQLPanel;
import edu.lmu.xmlpipedb.util.gui.ImportPanel;
import edu.lmu.xmlpipedb.util.resources.AppResources;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;


/**
 * @author J. Nicholas
 *
 */
public class Main implements ActionListener {
	
	public Main(){
		_initialFrame = null;
		createComponents();
		_cc = new ConfigurationController();
				 
	} // end no-arg constructor
	
	private void createComponents() {
		

		
	}

	/**
	 * Having a distinct start() method allows us to separate
	 * initialization from execution cleanly
	 */
	public void start(){
		SwingUtilities.invokeLater(new UIStarter());
	} // end start
	
	
	/**
	 * Wrap the UI-related startup sequence in a Runnable
	 * so that we can use it within SwingUtilities.invokelater().
	 * 
	 * NOTE: don't know why, yet. took this code from Dondi.
	 */
	private class UIStarter implements Runnable{
		/**
		 * @see java.lang.Runnable#run() 
		 */
		public void run(){
			_initialFrame = new JFrame(AppResources.messageString("str_title"));
			_initialFrame.setJMenuBar(createMenuBar());
			JFrame.setDefaultLookAndFeelDecorated(true);
			
			//Set initial size and location
	        int inset = 400;
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        _initialFrame.setBounds(inset, inset,
	                  screenSize.width  - inset,
	                  screenSize.height - inset);
	        _initialFrame.setLocation( inset/2, inset/2);
	        
			_initialFrame.setIconImage(createImage());
			_initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			_initialFrame.setVisible(true);
			

		} // end run
		
	} // end inner classs UIStarter

	
	
	//	Creates an icon-worthy Image from scratch.
    protected static Image createImage() {
        //Create a 16x16 pixel image.
        BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);

        //Draw into it.
        Graphics g = bi.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 15, 15);
        g.setColor(Color.YELLOW);
        g.fillOval(2, 2, 14, 14);
//        g.drawLine(1, 1, 13, 13 );
//        g.drawLine(1, 13, 13, 1 );
//        g.drawLine(5, 10, 5, 12 );
//        g.drawLine(7, 10, 7, 12 );
//        g.drawLine(9, 10, 9, 12 );
        g.setColor(Color.RED);
        g.fillRect(6, 6, 1, 2 );
        g.fillRect(9, 6, 1, 2 );
        g.drawLine(4, 10, 12, 10 );

        //Clean up.
        g.dispose();

        //Return it.
        return bi;
    } // end createFDImage
    
	protected JMenuBar createMenuBar() {
	    JMenuBar menuBar = new JMenuBar();
        int accelMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
	
	    //Set up the lone menu.
	    JMenu menu = new JMenu(AppResources.messageString("menu_tools"));
	    menu.setMnemonic(KeyEvent.VK_T);
	    menuBar.add(menu);
	
	    //Set up the first menu item.
	    JMenuItem menuItem = new JMenuItem(AppResources.messageString("menu_tools_config"));
	    menuItem.setMnemonic(KeyEvent.VK_C);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(
	            KeyEvent.VK_C, accelMask));
	    menuItem.setActionCommand("configure");
	    menuItem.addActionListener(this);
	    menu.add(menuItem);
	    
	    
	    //Set up the import menu item.
	    menuItem = new JMenuItem(AppResources.messageString("menu_tools_import"));
	    menuItem.setMnemonic(KeyEvent.VK_I);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(
	            KeyEvent.VK_I, accelMask));
	    menuItem.setActionCommand("import");
	    menuItem.addActionListener(this);
	    menu.add(menuItem);	
	  
	    //Set up the query menu item.
	    menuItem = new JMenuItem(AppResources.messageString("menu_tools_query"));
	    menuItem.setMnemonic(KeyEvent.VK_U);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(
	            KeyEvent.VK_U, accelMask));
	    menuItem.setActionCommand("query");
	    menuItem.addActionListener(this);
	    menu.add(menuItem);	
	    
	    //Set up the last menu item.
	    menuItem = new JMenuItem(AppResources.messageString("menu_tools_quit"));
	    menuItem.setMnemonic(KeyEvent.VK_Q);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(
	            KeyEvent.VK_Q, accelMask));
	    menuItem.setActionCommand("quit");
	    menuItem.addActionListener(this);
	    menu.add(menuItem);
	
	    return menuBar;
	} // end createMenuBar


	//React to menu selections.
	public void actionPerformed(ActionEvent e) {
	    if ("configure".equals(e.getActionCommand())) { //new
			_configPanel = new ConfigurationPanel(_cc, this);
			_configPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			_initialFrame.setContentPane(_configPanel);
			_initialFrame.validate();
	    } else if ("import".equals(e.getActionCommand())) { //new
			_importPanel = new ImportPanel();
			_importPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			_initialFrame.setContentPane(_importPanel);
			_initialFrame.validate();
	    } else if ("query".equals(e.getActionCommand())) { //new
			_queryPanel = new HQLPanel();
			_queryPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			_initialFrame.setContentPane(_queryPanel);
			_initialFrame.validate();
	    } else { //quit
	        System.exit(0);
	    }
	}
	
	public void cancelAction(){
		_initialFrame.setContentPane(new JPanel());
		_initialFrame.validate();
	}
	

    
    
    // ### DEFINE VARS ###
    JFrame _initialFrame;
    ImportPanel _importPanel;
    ConfigurationPanel _configPanel;
    HQLPanel _queryPanel;
    ConfigurationController _cc; 
    
} // end Main
