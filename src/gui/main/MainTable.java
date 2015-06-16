package gui.main;

import gui.core.Action;
import gui.core.Command;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.Main;

/**
 * The main content table.
 * 
 * @author admin
 *
 */

public class MainTable extends JTable {

	private final String[] columnData = { "Name", "Surname", "Post", "Class" };
	private Object [][] getRowData() {
		Main.getDB().getFields().load();
		return Main.getDB().getFields().getContentTableData ();
	}

	private final MainTableModel tableModel = new MainTableModel();
	
	@Override
	public TableModel getModel() {
		return tableModel;
	}
	
	public TableRowSorter <TableModel> sorter;

	public MainTable() {
		// Properties.
		setModel(tableModel);
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
		
		// Key listener for little details.
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Main.getMainFrame().getSearchField().requestFocus();
				Main.getMainFrame().getSearchField().setText(Main.getMainFrame().getSearchField().getText() + e.getKeyChar());
			}
		});
		// Mouse listener for double click to vote.
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switch (e.getClickCount()) {
					case 2:
						Action.execute(Command.VOTE);
					break;
				}
			}
		});
	}
	
	private class MainTableModel extends AbstractTableModel {
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

}
