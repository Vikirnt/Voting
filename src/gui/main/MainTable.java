package gui.main;

import gui.core.Action;
import gui.core.Command;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import core.DB;

/**
 * The main content table.
 * 
 * @author admin
 *
 */

public class MainTable extends JTable {

	private String[] columnData = { "Name", "Surname", "Post", "Class" };
	private Object [][] getRowData() {
		DB.getFields().load();
		return DB.getFields().contentTableData ();
	}

	private MainTableModel tableModel = new MainTableModel();
	
	@Override
	public TableModel getModel() {
		return tableModel;
	}
	
	public TableRowSorter <TableModel> sorter;

	public MainTable() {
		// Properties.
		setModel(tableModel);
		addMouseListener(new TableMouseListener());
		setToolTipText("Candidates.");
		setShowGrid(false);
		setName("Candidates.");
		setOpaque(true);
		setAutoCreateRowSorter(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(false);
		
		// Filter.
		sorter = new TableRowSorter <TableModel> (getModel());
		setRowSorter(sorter);
	}
	
	public class MainTableModel extends AbstractTableModel {
		@Override
	    public String getColumnName(int col) {
	        return columnData [col];
	    }
	    @Override
	    public int getRowCount() {
	    	return getRowData().length;
	    }
	    @Override
	    public int getColumnCount() {
	    	return columnData.length;
	    }
	    @Override
	    public Object getValueAt(int row, int col) {
	        return getRowData ()[row][col];
	    }
	    @Override
	    public boolean isCellEditable(int row, int col) {
	    	return false;
	    }
	    @Override
	    public void setValueAt(Object value, int row, int col) {
	    	super.setValueAt(value, row, col);
	    	fireTableDataChanged();
	    }
	}
	
	public void changeSorter (String reg) {
		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + reg));
	}

	private class TableMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {

			switch (e.getClickCount()) {

			case 2:
				if (isEnabled()) {
					Action.execute(Command.VOTE);
				}
			}

		}
	}

}
