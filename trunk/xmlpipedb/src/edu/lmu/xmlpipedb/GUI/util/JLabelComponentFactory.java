/*
 * JLabelComponentFactory.java
 *
 * Created on February 8, 2006, 9:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.lmu.xmlpipedb.GUI.util;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author David Hoffman
 */
public class JLabelComponentFactory {
    
    /** Creates a new instance of JLabelComponentFactory */
    public JLabelComponentFactory() {
    }
    public static Box createHorizontalLabelComponent(String labelName, int strut, JComponent component)
    {
        Box box = Box.createHorizontalBox(); 
        box.add(new JLabel(labelName)); 
        box.add(Box.createHorizontalStrut(strut)); 
        box.add(component); 
        return box; 
    }
    public static Box createHorizontalLabelComponent(String labelName, JComponent component)
    {
        Box box = Box.createHorizontalBox(); 
        box.add(new JLabel(labelName)); 
        box.add(Box.createHorizontalGlue()); 
        box.add(component); 
        return box; 
    }
    public static Box createVerticalLabelComponent(String labelName, int strut, JComponent component)
    {
        Box box = Box.createVerticalBox(); 
        box.add(new JLabel(labelName)); 
        box.add(Box.createVerticalStrut(strut)); 
        box.add(component); 
        return box; 
    }
    public static Box createVerticalLabelComponent(String labelName, JComponent component)
    {
        Box box = Box.createHorizontalBox(); 
        box.add(new JLabel(labelName)); 
        box.add(Box.createVerticalGlue()); 
        box.add(component); 
        return box; 
    }
}
