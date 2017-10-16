package gui.fin;

import gui.main.Main;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

/**
 * The results content table for votes.
 * 
 * @author vikirnt
 *
 */
class OverallTable extends JTable {
	
	/** Column names. */
	private final String[] columnData = { "Name", "Surname", "Post", "Class", "Votes" };

	/** Main constructor. */
	OverallTable () {
		// Properties.
		/* Table model. */
		TableModel tableModel = new MainTableModel ();
		setModel (tableModel);
		setToolTipText ("Candidates.");
		setShowGrid (false);
		setName ("Candidates.");
		setOpaque (true);
		setAutoCreateRowSorter (true);
		setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
		getTableHeader ().setReorderingAllowed (true);
		
		// Filter any votes.
		TableRowSorter<TableModel> sorter = new TableRowSorter<> (getModel ());
		List<RowSorter.SortKey> sortKeys = new ArrayList<> ();
		 
		sortKeys.add (new RowSorter.SortKey (4, SortOrder.DESCENDING));
		sorter.setSortKeys (sortKeys);
		sorter.sort ();
		 
		setRowSorter (sorter);
	}
	
	/** Table model class. */
	private class MainTableModel extends AbstractTableModel {
		@Override
	    public String getColumnName (int col) {
	        return columnData [col];
	    }
	    @Override
	    public int getRowCount () {
	    	return Main.getDB ().getCount ();
	    }
	    @Override
	    public int getColumnCount () {
	    	return columnData.length;
	    }
	    @Override
	    public Object getValueAt (int row, int col) {
	        return Main.getDB ().getTableContentArray () [row].get (col);
	    }
	    @Override
	    public boolean isCellEditable (int row, int col) {
	    	return false;
	    }
	    @Override
	    public void setValueAt (Object value, int row, int col) {
	    	super.setValueAt (value, row, col);
	    	fireTableDataChanged ();
	    }
	}

}
