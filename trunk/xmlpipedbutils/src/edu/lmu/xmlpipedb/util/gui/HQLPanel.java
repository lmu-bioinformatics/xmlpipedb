package edu.lmu.xmlpipedb.util.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;
import java.util.Vector;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Iterator;

import edu.lmu.xmlpipedb.util.app.HibernateUtil;
import generated.BookType;


public class HQLPanel extends JPanel{

	private JTextArea _hqlArea;
	private JTable _results;
	private DefaultTableModel _tableModel;
	private JSplitPane _split;
	private JPanel _buttonPanel;
	private Box _box = new Box(BoxLayout.Y_AXIS);

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
		_results = new JTable( _tableModel );
		_results.setVisible( true );
		
		_split = new JSplitPane( JSplitPane.VERTICAL_SPLIT, _hqlArea, new JScrollPane( _results) );
		_split.setDividerLocation( 0.25 );

		setSize( 25, 10 );
		initButtonPanel();
	}

	private void initButtonPanel(){

		_buttonPanel = new JPanel();
		_buttonPanel.setLayout( new FlowLayout( FlowLayout.CENTER ) );

		LinkedList<JButton> buttons = new LinkedList<JButton>();

		JButton execute = new JButton( "Execute Query" );

		buttons.add( execute );

		execute.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent ae ){
				Iterator iter = HibernateUtil.executeHQL( _hqlArea.getText().trim() );
				populateTable( iter );
				HibernateUtil.closeSession();
			}

		} );

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

		//_buttonPanel.setLayout( new GridLayout( buttons.size(), 1 ) );


		for( JButton jb: buttons ){
			_box.add(Box.createVerticalStrut(10));
			_box.add(Box.createHorizontalGlue());
			_box.add(jb);

		}

		_buttonPanel.add( _box );
	}
	
	private void populateTable( Iterator iter )
	{
		//try{
		Object temp = null;
		Map map = null;
		Vector data = null;
		
		while( iter.hasNext() ){
			temp = iter.next();
			
			try{
				map = BeanUtils.describe( temp );
				data = new Vector();
				for( Object o: map.values() ){
					data.addElement( o );
				}
			}
			catch( Exception e){
				System.err.println( e.getMessage() );
			}
			
			
			
			
			/*Vector data = new Vector();
			Class c = bt.getClass();
			for( java.lang.reflect.Field f: c.getFields() )
				data.addElement( f.get( bt ) );*/
			
			//Object[] data = {bt};//new Object[]{ bt.getAuthor(), bt.getGenre(), bt.getPrice(), bt.getTitle() };
			
			_tableModel.setColumnCount( map.size() );
			_tableModel.addRow( data  );
		}
		
		//_results.setVisible(true);
		//_results.validate();
	}
//	public static void main( String args[] ){
//
//		JFrame f = new JFrame( "HQL GUI Test" );
//		f.setSize( 800, 600 );
//		f.getContentPane().add( new HQLPanel() );
//		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//		f.setVisible(true);
//
//	}

}