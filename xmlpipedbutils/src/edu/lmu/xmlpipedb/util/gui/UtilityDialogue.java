package edu.lmu.xmlpipedb.util.gui;

import javax.swing.JPanel;
import javax.swing.JDialog;
import java.awt.event.WindowEvent;

/**
 * Any JPanel that wishes to turn into a dialogue box to be used
 * throughout the program should inherit this class.
 * 
 *  The class is only intended to create 
 * 
 * @author geocoso2
 *
 */
public class UtilityDialogue extends JPanel {

	public UtilityDialogue() {
		super();
	}
	
	protected void close() {
		if(_delegate != null) {
			_delegate.dispose();
		}
	}
	
	protected void cancel() {
		if(_delegate != null) {
			_delegate.dispose();
		}
	}
	
	protected void finish() {
		if(_delegate != null) {
			_delegate.dispose();
		}
	}
	
	public void setDelegate(JDialog del) {
		_delegate = del;
	}
	
	
	/**
	 * Delegate that recieve call back notifiactions about the
	 * panel.
	 */
	private JDialog _delegate;
}
