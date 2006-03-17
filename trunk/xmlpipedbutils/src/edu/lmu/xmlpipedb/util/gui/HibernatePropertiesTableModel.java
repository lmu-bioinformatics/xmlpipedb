package edu.lmu.xmlpipedb.util.gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import edu.lmu.xmlpipedb.util.app.Property;


public class HibernatePropertiesTableModel extends AbstractTableModel {

	public HibernatePropertiesTableModel(){
		_props = new ArrayList();
	}
	
	public int getRowCount() {
		return _props.size();
	}

	public int getColumnCount() {
		return (COLUMNS.length);
	}

    /**
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int column){ 
    	return(COLUMNS[column]); 
    }

    /**
     * Adds the given property to the list.
     */
    public void addProperty(Property p) {
        _props.add(p);
        fireTableRowsInserted(_props.size() - 1, _props.size() - 1);
    }
    
    /**
     * Removes the property at the given index from the list.
     */
    public void removeProperty(int propIndex) {
        if ((propIndex >= 0) && (propIndex < _props.size())) {
        	_props.remove(propIndex);
            fireTableRowsDeleted(propIndex, propIndex);
        }
    }
    
    
	
	
    /**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
//    public Class getColumnClass(int columnIndex) {
//        switch(columnIndex) {
//            case 1: return(Race.class);
//            case 3: return(Guild.class);
//            default: return(super.getColumnClass(columnIndex));
//        }
//    }

    /**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return((columnIndex == 1));
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Property p = (Property)_props.get(rowIndex);
        switch(columnIndex) {
            case 0: return(p.getName());
            case 1: return(p.getValue());
            default: return("");
        }
	}


    /**
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Property p = (Property)_props.get(rowIndex);
        switch(columnIndex) {
            case 0: p.setName((String)aValue); return;
            case 1: p.setValue((String)aValue); return;
           
            // Otherwise, we do nothing.
            default: return;
        }
    }

	//#### DEFINE VARS ####
    /**
     * The columns to display for a Property.
     */
    private static final String[] COLUMNS = { "Property", "Value" };

	ArrayList _props;
}
