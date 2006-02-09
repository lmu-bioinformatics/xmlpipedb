/*
 * GuiMenuCreator.java
 *
 * Created on February 8, 2006, 9:39 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.lmu.xmlpipedb.GUI;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Administrator
 */
public class GuiMenuCreator {
    
    /** Creates a new instance of GuiMenuCreator */
    public GuiMenuCreator() {
    }

    public static JMenuBar createMenu (final JFrame parentFrame, final Gui parentApp)
    {
        AbstractAction xmlLoad = new AbstractAction(){
            public void actionPerformed(ActionEvent event)
            {
                File file  = filePopup(parentFrame); 
                parentApp.loadXmlFile(file); 
            }
	}; 
		
	JMenuBar menuBar = new JMenuBar(); 
	JMenu file = new JMenu("File"); 
	JMenuItem loadXmlItem= new JMenuItem(xmlLoad); 
	loadXmlItem.setText("Load XML ...");
	file.add(loadXmlItem); 
	menuBar.add(file); 
	return menuBar; 
    }
    private static File filePopup(JFrame parentFrame)
    {
	JFileChooser filePopup = new JFileChooser(); 
        FileFilter xmlFilter = new FileFilter(){
            public boolean accept(File f)
            {
                if(f.isDirectory())
                    return true; 
                String name = f.getName(); 
                String ext = ""; 
                int i = name.indexOf('.'); 
                if(i>0 && i < name.length()-1)
                {
                    ext = name.substring(i+1).toLowerCase(); 
                }
                if(ext.compareTo("xml") == 0)
                    return true;
                return false; 
                    
            }
            public String getDescription()
            {
                return "XML files"; 
            }
        }; 
        filePopup.addChoosableFileFilter(xmlFilter); 
	if(filePopup.showOpenDialog(parentFrame) == JFileChooser.APPROVE_OPTION)
	{
		return filePopup.getSelectedFile(); 
	}
	return null; 
	
    }
}
