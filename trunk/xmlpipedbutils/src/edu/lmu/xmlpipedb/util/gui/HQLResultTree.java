/**
 * 
 */
package edu.lmu.xmlpipedb.util.gui;

import java.util.LinkedList;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.beanutils.BeanUtils;

import edu.lmu.xmlpipedb.util.utilities.PipeDBBeanUtils;

/**
 * HQLResultTree will servce as the object tree for the results of our HQL queries.
 * The three implementations for addObject(...) were taken from the How to Use Trees tutorial on Sun's JAva website.
 * @author Babak Naffas
 * @version 0.5
 *
 */
public class HQLResultTree extends JTree {

	private HQLTreeModel _treeModel;

	/**
	 * @param newModel
	 */
	public HQLResultTree(HQLTreeModel newModel) {
		super(newModel);
		_treeModel = newModel;
		_treeModel.setAsksAllowsChildren(true);
	}
	
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
	
	/**
	 * Add's a new subnode to the specified tree node.
	 * 
	 * @param parent	The node to which we are adding 
	 * @param child		The data we are adding to <b>parent</b>
	 * @param allowsChildren	
	 * @return	The added node
	 */
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean allowsChildren ) {
		return addObject(parent, child, true, allowsChildren);
	}
	
	/**
	 * Add's a new subnode to the specified tree node.
	 * 
	 * @param parent	The node to which we are adding 
	 * @param child		The data we are adding to <b>parent</b>
	 * @param allowsChildren
	 * @param shouldBeVisible
	 * @return	The added node
	 */
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, 
			 boolean allowsChildren, boolean shouldBeVisible ) {
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
	
	/**
	 * Displays the list of object maintained in list in the tree.
	 * Primitive fields are rendered as lead nodes, objects are rendered as parent nodes.
	 */
	public void populateTree( LinkedList<Object> list ){
		
		//First empty out the tree
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)_treeModel.getRoot();
		root.removeAllChildren();
		
		Map map = null;
		
		try{
			for( Object o: list ){
				map = BeanUtils.describe(o);
				DefaultMutableTreeNode addedNode =  addObject( root, o, true, true );
				for( Object field: map.values() ){
					populateObjects( field, addedNode );
					//if field is a primitive, add it as a child node
				}
				
			}
		}
		catch( Exception e ){
			reportException(e);
		}
		
	}
	
	private void populateObjects( Object o, DefaultMutableTreeNode root ){
		
System.out.println( o.getClass() + ":\t" + o );	
		//Primitives are designated as simple leaf nodes.
		if( PipeDBBeanUtils.isPrimitive(o) ){
			addObject( root, o, false );
		}
		//if field is a collection, add each of it's entries seperately.
		else if( PipeDBBeanUtils.isCollection(o) ){
			
		}
		//if field is an object, add it recursively
		else{
			try{
				DefaultMutableTreeNode addedNode = addObject( root, o, true, true );
				
				Map map = BeanUtils.describe(o);
				java.util.Collection fields = map.values();
				for( Object field: fields )
					addObject( addedNode, field, true, true );
					//populateObjects( field, addedNode );
			}
			catch( Exception e ){
				reportException( e );
			}
		}
	}
	
	private void reportException( Exception e ){
		JOptionPane.showMessageDialog( this, e.getMessage() );
	}
	
	


}
