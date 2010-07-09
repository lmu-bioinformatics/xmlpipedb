package edu.lmu.xmlpipedb.util.gui;

import javax.swing.JPanel;

/**
 * Defines the call back methods used for displaying the GUI
 * components of XMLPipeDB.
 * 
 * @author geocoso2
 *
 */
public interface DialogueDelegate {
	
	
	/**
	 * Called when a panel finishes its business and wants
	 * to close.
	 * 
	 * @param panel The panel to close.
	 */
	public void dialogueDidFinish(JPanel panel);
	
	/**
	 * Called when the cancel button for the panel was hit.
	 * 
	 * @param panel The panel that was cancelled.
	 */
	public void dialogueDidCancel(JPanel panel);
	
	/**
	 * Called when the panel finished cleaning up and exited.
	 * 
	 * @param panel
	 */
	public void dialogueDidExit(JPanel panel);

}
