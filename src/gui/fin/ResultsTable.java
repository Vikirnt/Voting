package gui.fin;

import gui.main.Main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * The results content table for votes.
 * 
 * @author admin
 *
 */
public class ResultsTable extends JTable {

	private final String[] columnData = { "Name", "Surname", "Post", "Class", "Votes" };
	private Object [][] getRowData() {
		Main.getDB().getFields().load();
		return Main.getDB().getFields().getVoteBasedData();
	}

	private final MainTableModel tableModel = new MainTableModel();
	
	@Override
	public TableModel getModel() {
		return tableModel;
	}
	

	public ResultsTable() {
		// Properties.
		setModel(tableModel);
		setToolTipText("Candidates.");
		setShowGrid(false);
		setName("Candidates.");
		setOpaque(true);
		setAutoCreateRowSorter(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(true);
		
		// Filter ny votes.
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(getModel());
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		 
		sortKeys.add(new RowSorter.SortKey(4, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		 
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

}
