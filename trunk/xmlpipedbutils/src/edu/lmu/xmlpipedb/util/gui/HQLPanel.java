package edu.lmu.xmlpipedb.util.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Map;
import java.util.Iterator;

import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.beanutils.BeanUtils;

import edu.lmu.xmlpipedb.util.app.HibernateUtil;
import edu.lmu.xmlpipedb.util.utilities.PipeDBBeanUtils;

/**
 * This panel displays a text area for the user to input an HQL or SQL query and view the results.
 * As of version 1.1, the UI displays all data in a table.
 * As of version 1.2, a tree has been added. All data will be displayed in both the tree and the table fow now.
 * 
 * @version	1.2
 * @author Babak Naffas
 *
 */
public class HQLPanel extends JPanel{

	private JTextArea _queryTextArea;
	private JTable _resultsTable;
	private HQLResultTree _resultsTree;
	
	private DefaultTableModel _tableModel;
	private JSplitPane _split;
	private JPanel _buttonPanel;
	private JPanel _dataViewPanel;
	private Box _box = new Box(BoxLayout.Y_AXIS);
	
	private JRadioButton _sql;
	private JRadioButton _hql;
	private ButtonGroup _radioGroup;
	private String _queryActionCommand;
	
	private final static String 
		HQL_COMMAND = "HQL",
		SQL_COMMAND = "SQL";
	

	/**
	 * Default constructor. Adds the query area and the results table below it. All buttons will be displayed along the right edge of the UI.
	 *
	 */
	public HQLPanel(){
		super();

		initComponents();
		setLayout( new 	BorderLayout() );

		add( _buttonPanel, BorderLayout.EAST );
		add( _split, BorderLayout.CENTER );
	}

	private void initComponents(){
		_queryTextArea = new JTextArea();
		
		_tableModel = new DefaultTableModel(1, 10);
		
		_resultsTable = new JTable( _tableModel );
		_resultsTable.setVisible( true );
		
		_resultsTree = new HQLResultTree( new HQLTreeModel( "Our Tree" ) );
		
		_dataViewPanel = new JPanel( new BorderLayout() );
		JScrollPane tableScroll = new JScrollPane( _resultsTable);
		JScrollPane treeScroll = new JScrollPane( _resultsTree );
		_dataViewPanel.add( new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, tableScroll, treeScroll ), BorderLayout.CENTER );
		
		_split = new JSplitPane( JSplitPane.VERTICAL_SPLIT, _queryTextArea, _dataViewPanel );
		_split.setDividerLocation( 0.25 );

		initQueryChooser();
		initButtonPanel();
	}
	
	/**
	 * Initializes the set of radio buttons. One button is created for each query type. 
	 * Currently these buttons are as follow:
	 * 	SQL
	 * 	HQL
	 *
	 */
	private void initQueryChooser(){
		_sql = new JRadioButton( SQL_COMMAND );
		
		_sql.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent ae ){
				_queryActionCommand = SQL_COMMAND;
			}
		} );
		
		
		_hql = new JRadioButton( HQL_COMMAND, true );
		_hql.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent ae ){
				_queryActionCommand = HQL_COMMAND;
			}
		} );
		
		//We will initially set the action command to 'HQL'
		_queryActionCommand = HQL_COMMAND;
		
		//We need to add each set of mutually exclusive Radio Buttons to a ButtonGroup instance.
		_radioGroup = new ButtonGroup();
		_radioGroup.add( _sql );
		_radioGroup.add( _hql );
		
		//...then add the actual buttons to the UI.
		_box.add( _sql );
		_box.add( _hql );
	}

	/**
	 * Initializes the button panel. The following buttons are added along the right edge of the panel:
	 * 		Execute Query
	 * 		Clear 
	 *
	 */
	private void initButtonPanel(){

		_buttonPanel = new JPanel();
		_buttonPanel.setLayout( new FlowLayout( FlowLayout.CENTER ) );

		LinkedList<JButton> buttons = new LinkedList<JButton>();

		//Execute Query Button
		JButton execute = initExecuteQueryButton();
		buttons.add( execute );

		//Clear Button.
		JButton clear = new JButton( "Clear" );
		clear.setMinimumSize(new Dimension(100, 25));
		clear.setPreferredSize(new Dimension(100, 25));
		clear.setMaximumSize(new Dimension(Short.MAX_VALUE, 25));
		
		buttons.add( clear );
		clear.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent ae ){
				_queryTextArea.setText( "" );
			}
		} );
		
		//Eases the process of adding new buttons.
		for( JButton jb: buttons ){
			_box.add(Box.createVerticalStrut(10));
			_box.add(Box.createHorizontalGlue());
			_box.add(jb);

		}

		_buttonPanel.add( _box );
	}
	
	private void reportException( Exception e ){
		JOptionPane.showMessageDialog( this, e.getMessage() );
	}
	
	/**
	 * Executes the query in text area at the top of the panel. The results (if any) are displayed in the data view panel.
	 *
	 */
	private void runSQL(){
		Connection conn = HibernateUtil.currentSession().connection();
		PreparedStatement query = null;
		ResultSet results = null;

		try{
			query = conn.prepareStatement( _queryTextArea.getText() );
			results = query.executeQuery();
			
			LinkedList<Object[]> list = new LinkedList<Object[]>();
			
			populateTable( results );
			//results.beforeFirst(); 
			//populateTree( results );
		}
		catch( SQLException sqle ){ 
			JOptionPane.showMessageDialog( this, sqle.getMessage() );
		}
		catch( Exception e ){
			reportException( e );
		}
		
		finally{
			try{
				results.close();
				query.close();
				conn.close();
				//HibernateUtil.closeSession();
			}
			catch( Exception e ){
				reportException( e );
			}	//Ignore the errors here, nothing we can do anyways.
			
		}
	}
	
	private void runHQL(){
		
		try{
			Iterator iter = HibernateUtil.executeHQL( _queryTextArea.getText().trim() );
			final LinkedList data = new LinkedList();
			while( iter.hasNext() ){
				data.add( iter.next() );
			}
			HibernateUtil.closeSession();
			
			populateTable( data );
			
			Runnable thread = new Runnable(){
				public void run(){
					_resultsTree.populateTree( data );
				}
			};
			new Thread(thread).start();
			
			
		}
		catch( Exception e ){
			reportException( e );
		}
		
	}
	
	private JButton initExecuteQueryButton(){
		
		JButton execute = new JButton( "Execute Query" );
		execute.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent ae ){
				
				if( "SQL".equals(_queryActionCommand) ){
					runSQL();					
				}
				else if( "HQL".equals(_queryActionCommand) ){
					runHQL();
				}
			}

		} );
		
		return execute;
	}
	
	/*private void populateTree( ResultSet results ) throws SQLException{
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)_resultsTree.getModel().getRoot();
		root.removeAllChildren();
	}*/
	
	/**
	 * Populates the table on the bottom of the panel with the results of the executed SQL query. 
	 * @param results
	 * @throws SQLException
	 */
	private void populateTable( ResultSet results ) throws SQLException{
		ResultSetMetaData meta = null;
		meta = results.getMetaData();
		
		int numColumns = meta.getColumnCount();
		String[] columnNames = new String[numColumns];
		for( int i = 0; i < numColumns; i++ ){
			columnNames[i] = meta.getColumnLabel(i+1);
		}
		_tableModel.setColumnIdentifiers( columnNames );
		
		_tableModel.setRowCount(0);		
		while( results.next() ){
			Vector data = new Vector();
			for( int i = 1; i <= numColumns; i++ ){
				data.addElement( results.getObject(i) );	
			}
			_tableModel.addRow( data );
		}
	}
	
	/**
	 * Populates the table based on the contents of the list. 
	 * @param list	The list of objects to we are populating onto the table.
	 */
	private void populateTable( LinkedList<Object> list )
	{
		Object temp = null;	
		Map map = null;
		Vector data = null;
		
		//Iterate over the set of object generated from our query.
		
		_tableModel.setRowCount(0);
		for( Object object: list )
			
			try{
				//Get each field for the object...
				map = BeanUtils.describe( object );
				data = new Vector();
				
				//...and add it's value to our table.
				for( Object o: map.values() ){
					data.addElement( o );
				}
			}
			catch( Exception e){
				JOptionPane.showMessageDialog( this, e.getMessage(), "Error in Query", JOptionPane.ERROR_MESSAGE );
				System.err.println( e.getMessage() );
			}
			
			_tableModel.setColumnCount( map.size() );
			_tableModel.addRow( data  );
		}
	}
