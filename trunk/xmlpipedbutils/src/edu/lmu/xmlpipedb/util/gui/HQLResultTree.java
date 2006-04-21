/**
 * 
 */
package edu.lmu.xmlpipedb.util.gui;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

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
		getSelectionModel().setSelectionMode( javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION );
		putClientProperty("JTree.lineStyle", "Angled");
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
	 * 	 * Displays the list of object maintained in list in the tree.
	 * Primitive fields are rendered as lead nodes, objects are rendered as parent nodes.
	 * 
	 * @param list	The list of objects 
	 */
	public void populateTree( List<Object> list ){
		
		//First empty out the tree
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)_treeModel.getRoot();
		root.removeAllChildren();
		
		Map map = null;
		
		try{
			for( Object o: list ){
				//map = BeanUtils.describe(o);
				if( PipeDBBeanUtils.isPrimitive(o) ){
					addObject( root, o, false, true );
				}
				else if( PipeDBBeanUtils.isCollection(o) ){
					//TODO	Add code to populate Collections
				}
				else{
					map = PropertyUtils.describe(o);
					DefaultMutableTreeNode addedNode =  addObject( root, o, true, true );
					for( Object key: map.keySet() ){
						populateObjects( key, map.get(key), addedNode );
						//if field is a primitive, add it as a child node
					}
				}
			}
		}
		catch( Exception e ){
			reportException(e);
		}
		
	}
	
	private void populateObjects( Object property, Object value, DefaultMutableTreeNode root ){
		
System.out.println( value.getClass() + ":\t" + value );	
		//Primitives are designated as simple leaf nodes.
		String label = property.toString() + "[" + value + "]";
		if( PipeDBBeanUtils.isPrimitive(value) ){
			addObject( root, label, false, true );
		}
		//if field is a collection, add each of it's entries seperately.
		else if( PipeDBBeanUtils.isCollection(value) ){
			
		}
		//if field is an object, add it recursively
		else{
			try{
				DefaultMutableTreeNode addedNode = addObject( root, label, true, true );
				
				//Map map = BeanUtils.describe(o);
				Map map = PropertyUtils.describe(value);
				//java.util.Collection fields = map.values();
				for( Object key: map.keySet() ){
					String label2 = key + "[" + map.get(key) + "]";
					addObject( addedNode, label2, true, true );
					//populateObjects( field, addedNode );
				}
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
