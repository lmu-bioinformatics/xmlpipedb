/*
 * Main.java
 *
 * Created on February 7, 2006, 1:20 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package edu.lmu.xmlpipedb.GUI;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Administrator
 */
public class Gui implements Runnable{
    private GuiPanel _mainPanel;
    private File _xmlFile; 
    /** Creates a new instance of Main */
    public Gui() {
        _mainPanel = new GuiPanel(this); 
    }
    
    /**
     * @param args the command line arguments
     */
    public void startGui(){
        
        SwingUtilities.invokeLater(this); 
    }
    public static void main(String[] args) {
        (new Gui()).startGui(); 
        
    }
    
    public void run(){
        JFrame frame = new JFrame("Xml Loader"); 
        frame.setJMenuBar(GuiMenuCreator.createMenu(frame, this)); 
        frame.setContentPane(_mainPanel); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.pack(); 
        frame.setVisible(true); 
    }
    
    public void loadXmlFile(File file)
    {
        _xmlFile = file; 
        _mainPanel.setXmlFilePath(file.getPath()); 
    }
    public void startXmlToDataBase() throws Exception
    {
            
    }
   
}
