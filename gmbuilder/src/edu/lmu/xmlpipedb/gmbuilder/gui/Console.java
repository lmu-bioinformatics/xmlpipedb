/** 
 * Console.java
 * 
 * Description:  This class functions as a console used in the main
 * window. 
 * 
 * Revision History
 *  04/06/06 Joey J Barrett. File orignaly created. 
 * 
 * Note: Please follow commenting convetions already in this file!
 */

package edu.lmu.xmlpipedb.gmbuilder.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Console extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3740516391173894774L;
	
	private PipedInputStream piOut;
	private PipedInputStream piErr;
	private PipedOutputStream poOut;
	private PipedOutputStream poErr;

	private JTextArea textArea = new JTextArea();

	public Console() throws IOException {
		// Set up System.out
		piOut = new PipedInputStream();
		poOut = new PipedOutputStream(piOut);
		System.setOut(new PrintStream(poOut, true));

		// Set up System.err
		piErr = new PipedInputStream();
		poErr = new PipedOutputStream(piErr);
		System.setErr(new PrintStream(poErr, true));

		// Add a scrolling text area
		textArea.setEditable(false);
		textArea.setRows(25);
		textArea.setColumns(80);
		textArea.setEditable(false);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Serif", Font.PLAIN, 12));
		
		setLayout(new BorderLayout());
		add(new JScrollPane(textArea), BorderLayout.CENTER);

		// Create reader threads
		new ReaderThread(piOut).start();
		new ReaderThread(piErr).start(); 
	}

	class ReaderThread extends Thread {
		PipedInputStream pi;

		ReaderThread(PipedInputStream pi) {
			this.pi = pi;
		}

		public void run() {
			final byte[] buf = new byte[1024];
			try {
				while (true) {
					final int len = pi.read(buf);
					if (len == -1) {
						break;
					}
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							textArea.append(new String(buf, 0, len));

							// Make sure the last line is always visible
							textArea.setCaretPosition(textArea.getDocument()
									.getLength());

							// Keep the text area down to a certain character size
							int idealSize = 1000;
							int maxExcess = 500;
							int excess = textArea.getDocument().getLength()
									- idealSize;
							if (excess >= maxExcess) {
								textArea.replaceRange("", 0, excess);
							}
						}
					});
				}
			} catch (IOException ignored) {
			}
		}
	}
}