/**
 * 
 */
package edu.lmu.xmlpipedb.util.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * @author Babak Naffas
 * @version 0.5
 *
 */
public class HQLResultTree extends JTree {

	private HQLTreeModel _treeModel;
	
	/**
	 * 
	 */
	public HQLResultTree() {
		super();
		_treeModel = new HQLTreeModel( new DefaultMutableTreeNode() );
		treeModel = _treeModel;
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param newModel
	 */
	public HQLResultTree(HQLTreeModel newModel) {
		super(newModel);
		_treeModel = newModel;
		
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * The three implementations for addObject(...) were taken from the How to Use Trees tutorial 
	 *	 
	 */
	
	
	/**
	 * Add child to the currently selected node. 
	 * If no parent is explicitly stated, use the currently selected node
	 * 
	 * @param child The user object of the new DefaultMutableTreeNode being added
	 * @param allowsChildren	Whether or not this node can have children. For the purposes of Papercut, true means the node is a file group and false means it is a file node.
	 * 
	 * @return The newly added DEfaultMutableTreeNode object.
	 */
	public DefaultMutableTreeNode addObject(Object child, boolean allowsChildren) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = getSelectionPath();
		
		if (parentPath == null)
			parentNode = (DefaultMutableTreeNode)_treeModel.getRoot();
		else
			parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
		
		return addObject(parentNode, child, true, allowsChildren);
	}
	
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean allowsChildren ) {
		return addObject(parent, child, true, allowsChildren);
	}
	
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, 
			boolean shouldBeVisible, boolean allowsChildren) {
		DefaultMutableTreeNode childNode = 
			new DefaultMutableTreeNode(child, allowsChildren);
		
		if (parent == null) {
			parent = (DefaultMutableTreeNode)_treeModel.getRoot();
		}
		
		_treeModel.insertNodeInto(childNode, parent, 
				parent.getChildCount());
		
		//Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}
	


}
