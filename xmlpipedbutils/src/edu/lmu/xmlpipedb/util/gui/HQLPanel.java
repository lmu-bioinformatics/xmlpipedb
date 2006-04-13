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

import org.apache.commons.beanutils.BeanUtils;

import edu.lmu.xmlpipedb.util.app.HibernateUtil;

/**
 * This panel displays a text area for the user to input an HQL or SQL query and view the results.
 * 
 * @version	1.1
 * @author Babak Naffas
 *
 */
public class HQLPanel extends JPanel{

	private JTextArea _hqlArea;
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
		_hqlArea = new JTextArea();
		
		_tableModel = new DefaultTableModel(1, 10);
		
		_resultsTable = new JTable( _tableModel );
		_resultsTable.setVisible( true );
		
		_resultsTree = new HQLResultTree();
		
		_dataViewPanel = new JPanel( new BorderLayout() );
		JScrollPane tableScroll = new JScrollPane( _resultsTable);
		JScrollPane treeScroll = new JScrollPane( _resultsTree );
		_dataViewPanel.add( new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, tableScroll, treeScroll ) );
		
		_split = new JSplitPane( JSplitPane.VERTICAL_SPLIT, _hqlArea, _dataViewPanel );
		_split.setDividerLocation( 0.25 );

		setSize( 25, 10 );
		
		initQueryChooser();
		initButtonPanel();
	}
	
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
				_hqlArea.setText( "" );
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
	
	private JButton initExecuteQueryButton(){
		
		JButton execute = new JButton( "Execute Query" );
		final JComponent _this = this;
		execute.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent ae ){
				
				if( "SQL".equals(_queryActionCommand) ){
					Connection conn = HibernateUtil.currentSession().connection();
					PreparedStatement query = null;
					ResultSet results = null;
					ResultSetMetaData meta = null;
					
					try{
						query = conn.prepareStatement( _hqlArea.getText() );
						results = query.executeQuery();
						meta = results.getMetaData();
						
						int numColumns = meta.getColumnCount();
						String[] columnNames = new String[numColumns];
						for( int i = 0; i < numColumns; i++ ){
							columnNames[i] = meta.getColumnLabel(i+1);
						}
						_tableModel.setColumnIdentifiers( columnNames );
						
						while( results.next() ){
							Vector data = new Vector();
							for( int i = 1; i <= numColumns; i++ ){
								data.addElement( results.getObject(i) );	
							}

							
							_tableModel.addRow( data );
						}
						
					}
					catch( SQLException sqle ){ 
						JOptionPane.showMessageDialog( _this, sqle.getMessage() );
					}
					finally{
						try{
							results.close();
							query.close();
							conn.close();
						}
						catch( SQLException sqle ){}	//Ignore the errors here, nothing we can do anyways.
						
					}
										
					
				}
				else if( "HQL".equals(_queryActionCommand) ){
					Iterator iter = HibernateUtil.executeHQL( _hqlArea.getText().trim() );
					populateTable( iter );
					HibernateUtil.closeSession();
				}
			}

		} );
		
		return execute;
	}
	
	private void populateTable( Iterator iter )
	{
		Object temp = null;	
		Map map = null;
		Vector data = null;
		
		//Iterate over the set of object generated from our query.
		while( iter.hasNext() ){
			temp = iter.next();
			
			try{
				//Get each field for the object...
				
				map = BeanUtils.describe( temp );
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
}