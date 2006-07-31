/********************************************************
 * Filename: CheckBoxList.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: Stolen off the internet.  Provides an 
 * implementation of a list with checkboxes.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.gui.util;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * @author Joey J. Barrett
 * Class: CheckBoxList
 */
public class CheckBoxList extends JList {

	private static final long serialVersionUID = -1258515306019916448L;

	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	/**
	 * Constructor.
	 */
	public CheckBoxList() {
		setCellRenderer(new CellRenderer());

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int index = locationToIndex(e.getPoint());

				if (index != -1) {
					JCheckBox checkbox = (JCheckBox) getModel().getElementAt(
							index);
					checkbox.setSelected(!checkbox.isSelected());
					repaint();
				}
			}
		});

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * @author Joey J. Barrett
	 * Class: CellRenderer
	 * Custom cell renderer.
	 */
	protected class CellRenderer implements ListCellRenderer {
		/* (non-Javadoc)
		 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			JCheckBox checkbox = (JCheckBox) value;
			checkbox.setBackground(isSelected ? getSelectionBackground()
					: getBackground());
			checkbox.setForeground(isSelected ? getSelectionForeground()
					: getForeground());
			checkbox.setEnabled(isEnabled());
			checkbox.setFont(getFont());
			checkbox.setFocusPainted(false);
			checkbox.setBorderPainted(true);
			checkbox
					.setBorder(isSelected ? UIManager
							.getBorder("List.focusCellHighlightBorder")
							: noFocusBorder);
			return checkbox;
		}
	}
}
