/** 
 * Viewer.java
 * 
 * Description:  This class is the main application window.
 * 
 * Revision History
 *  04/06/06 Joey J Barrett. File orignaly created. 
 * 
 * Note: Please follow commenting convetions already in this file!
 */

package edu.lmu.xmlpipedb.gmbuilder.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;



/**
 * @author Joey J. Barrett
 *
 */
public class Viewer extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8178384920599185114L;
	
	//Locally accessible compenents.
	private JLabel infoLabel;
    private JFileChooser chooser = new JFileChooser(".");
	

	private static final String IMAGE1 = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/images/xmltodb.png";
	private static final String IMAGE2 = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/images/dbtogenmapp.png";
	private static final String IMAGE3 = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/images/xmltogenmapp.png";
	
	private static final String IMAGE4 = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/images/dbinspect.png";
	private static final String IMAGE5 = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/images/dbsettings.png";
	
	private static final String IMAGE6 = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/images/quit.png";
	private static final String IMAGE7 = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/images/preferences.png";
	
	//private static final String WELCOME_FILE = System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/resource/welcome/welcome.txt";
	
	public Viewer() {
		
		super("GMBuilder - Loyola Marymount University");
		
		SplashWindow splashWindow = new SplashWindow(System.getProperty("user.dir")+ "/src/edu/lmu/xmlpipedb/gmbuilder/resource/images/splash.jpg", this, 3000);

        // Setup the main window.
        JFrame.setDefaultLookAndFeelDecorated(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        // Register a window listener to allow clean up before exit
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                // Clean up and exit
                exit();
            }
        });

        //
        //	ACTIONS
        //
        Action quitAction = new AbstractAction("Quit") {
			private static final long serialVersionUID = 3215450361259127899L;

			{
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_Q));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control Q"));
            }

            public void actionPerformed(ActionEvent e) {
                exit();
            }
        };
        
        Action xmlToDBAction = new AbstractAction("XML to DB...") {
			private static final long serialVersionUID = -4744054778055564804L;

			{
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control O"));
            }

            public void actionPerformed(ActionEvent e) {
                xmlToDB();
            }
        };
        
        Action dbToGenMaPPAction = new AbstractAction("DB to GenMaPP...") {
			private static final long serialVersionUID = 7280335757790554745L;

			{
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control O"));
            }

            public void actionPerformed(ActionEvent e) {
                dbToGenMapp();
            }
        };
        
        Action xmlToGenMaPPAction = new AbstractAction("XML to GenMapp...") {
			private static final long serialVersionUID = 8463628960751531076L;

			{
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control O"));
            }

            public void actionPerformed(ActionEvent e) {
                xmlToGenMapp();
            }
        };
        
        Action dbInspectAction = new AbstractAction("DB Inspect...") {
			private static final long serialVersionUID = -697855802432697080L;

			{
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control O"));
            }

            public void actionPerformed(ActionEvent e) {
                dbInspect();
            }
        };
        
        Action dbSettingsAction = new AbstractAction("DB Settings...") {
			private static final long serialVersionUID = -9096442525329157638L;

			{
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control O"));
            }

            public void actionPerformed(ActionEvent e) {
                dbSettings();
            }
        };
        
        Action preferencesAction = new AbstractAction("Preferences") {
			private static final long serialVersionUID = 2168270378767322046L;

			{
                putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_P));
                putValue(Action.ACCELERATOR_KEY, KeyStroke
                        .getKeyStroke("control P"));
            }

            public void actionPerformed(ActionEvent e) {
                preferences();
            }
        };

        //
        //	RIBBON
        //
        JTabbedPane ribbon = new JTabbedPane();
        
        //
        //	FILE TAB
        //
        JToolBar fileRibbon = new JToolBar();
        fileRibbon.setLayout(new FlowLayout());
        fileRibbon.setFloatable(false);
        
        JPanel builderPanel2 = new JPanel(new BorderLayout());
        builderPanel2.add(new JButton(preferencesAction), BorderLayout.NORTH);
        builderPanel2.add(new JLabel(new ImageIcon(IMAGE7)), BorderLayout.SOUTH);     
        fileRibbon.add(builderPanel2);
        fileRibbon.addSeparator();
        
        JPanel builderPanel1 = new JPanel(new BorderLayout());
        builderPanel1.add(new JButton(quitAction), BorderLayout.NORTH);
        builderPanel1.add(new JLabel(new ImageIcon(IMAGE6)), BorderLayout.SOUTH);     
        fileRibbon.add(builderPanel1);
        fileRibbon.addSeparator();
        
        ribbon.addTab("File", null, fileRibbon, "File");

        //
        //	IMPORT TAB
        //
        JToolBar importRibbon = new JToolBar();
        importRibbon.setLayout(new FlowLayout());
        importRibbon.setFloatable(false);
       
        JPanel builderPanel3 = new JPanel(new BorderLayout());
        builderPanel3.add(new JButton(xmlToDBAction), BorderLayout.NORTH);
        builderPanel3.add(new JLabel(new ImageIcon(IMAGE1)), BorderLayout.SOUTH);     
        importRibbon.add(builderPanel3);
        importRibbon.addSeparator();
        
        JPanel builderPanel4 = new JPanel(new BorderLayout());
        builderPanel4.add(new JButton(dbToGenMaPPAction), BorderLayout.NORTH);
        builderPanel4.add(new JLabel(new ImageIcon(IMAGE2)), BorderLayout.SOUTH);     
        importRibbon.add(builderPanel4);
        importRibbon.addSeparator();
        
        JPanel builderPanel5 = new JPanel(new BorderLayout());
        builderPanel5.add(new JButton(xmlToGenMaPPAction), BorderLayout.NORTH);
        builderPanel5.add(new JLabel(new ImageIcon(IMAGE3)), BorderLayout.SOUTH);     
        importRibbon.add(builderPanel5);
        
        ribbon.addTab("Import", null, importRibbon, "Import");

        //
        //	DATABASE TAB
        //
        JToolBar databaseRibbon = new JToolBar();
        databaseRibbon.setLayout(new FlowLayout());
        databaseRibbon.setFloatable(false);
        
        JPanel builderPanel6 = new JPanel(new BorderLayout());
        builderPanel6.add(new JButton(dbInspectAction), BorderLayout.NORTH);
        builderPanel6.add(new JLabel(new ImageIcon(IMAGE4)), BorderLayout.SOUTH);     
        databaseRibbon.add(builderPanel6);
        databaseRibbon.addSeparator();
        
        JPanel builderPanel7 = new JPanel(new BorderLayout());
        builderPanel7.add(new JButton(dbSettingsAction), BorderLayout.NORTH);
        builderPanel7.add(new JLabel(new ImageIcon(IMAGE5)), BorderLayout.SOUTH);     
        databaseRibbon.add(builderPanel7);
        
        ribbon.addTab("Database", null, databaseRibbon, "Database");

        //
        //	CONSOLE
        //
        Console console = null;
		try {
			console = new Console();
		} catch (IOException e1) {		
			e1.printStackTrace();
			System.exit(0);
		}

        //
		//	INFO TOOLBAR
		//
        JToolBar infoToolBar = new JToolBar();
        infoLabel = new JLabel("Welcome to XMLPipeDB");
        infoToolBar.add(infoLabel);

        // 
        //	ADD EVERYTHING TO CONTENT PANE
        //
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(ribbon, BorderLayout.NORTH);
        getContentPane().add(console, BorderLayout.CENTER);
        getContentPane().add(infoToolBar, BorderLayout.SOUTH);

        pack();
        setVisible(true);
	}

	protected void dbSettings() {
		System.out.println("Loading Database Settings...");
	}
    
	protected void dbInspect() {
		// TODO Auto-generated method stub
		
	}
	
	protected void xmlToGenMapp() {
		// TODO Auto-generated method stub
		
	}
	
	protected void dbToGenMapp() {
		// TODO Auto-generated method stub
		
	}
	
	protected void xmlToDB() {
//        Wizard wizard = new Wizard();
//        wizard.getDialog().setTitle("XML to Database Wizard");
//        
//        WizardPanelDescriptor descriptor1 = new XMLToDBPanel1Descriptor();
//        wizard.registerWizardPanel(XMLToDBPanel1Descriptor.IDENTIFIER, descriptor1);
//
//        WizardPanelDescriptor descriptor2 = new XMLToDBPanel2Descriptor();
//        wizard.registerWizardPanel(XMLToDBPanel2Descriptor.IDENTIFIER, descriptor2);
//
//        WizardPanelDescriptor descriptor3 = new XMLToDBPanel3Descriptor();
//        wizard.registerWizardPanel(XMLToDBPanel3Descriptor.IDENTIFIER, descriptor3);
//        
//        wizard.setCurrentPanel(XMLToDBPanel1Descriptor.IDENTIFIER);
//        
//        int ret = wizard.showModalDialog();
        
//        System.out.println("Dialog return code is (0=Finish,1=Cancel,2=Error): " + ret);
      //  System.out.println("Second panel selection is: " + 
      //      (((TestPanel2)descriptor2.getPanelComponent()).getRadioButtonSelected()));
		
	}

    /**
     * Currently not used.
     */
    private void preferences() {
        //Add whatever is needed.

    }

    /**
     * Exit the application.
     */
    private void exit() {
        // TODO: Clean up anything before closing here

        // exit
        System.exit(0);
    }
	
	public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Viewer();
            }
        });
	}
	
}
