/*
 * Created on May 21, 2005
 *
 */
package edu.lmu.xmlpipedb.util.app;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author J. Nicholas
 *
 * HCI0602 is the second assignment for LMU's Human Computer Interfaces class.
 * The purpose of this project is to develop skills with Swing GridBagLayout
 * as well as Swing event handling. 
 * 
 * The goal of this application is to produce an application with 4 fields,
 * which responds to 2 buttons, either setting or displaying the text in the 
 * fields.
 * 
 * Extra Credit:
 * Allowing fields to be dynamically added or removed.
 * 
 */
public class XmlPipeDbUtils {

	public static void main(String[] args) {
		
		//FIXME: the look and feel should come from the options file
		String crossPlatformLAF = UIManager.getCrossPlatformLookAndFeelClassName();
		
		try{
			UIManager.setLookAndFeel(crossPlatformLAF);
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(InstantiationException e){
			e.printStackTrace();
		}
		catch(IllegalAccessException e){
			e.printStackTrace();
		}
		catch(UnsupportedLookAndFeelException e){
			e.printStackTrace();
		}
		
		(new Main()).start();
		

	} // end main

	
	public XmlPipeDbUtils(){
		// do nothing
		
	} // end no-arg constructor



    
} // end class XmlPipeDbUtils
