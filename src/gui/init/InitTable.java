package gui.init;

import gui.core.Action;
import gui.core.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.Main;

/**
 * The main content table.
 * 
 * @author admin
 *
 */

public class InitTable extends JTable implements ActionListener {

	private final String[] columnData = { "Name", "Surname", "Post", "Class" };

	public TableModel contentTableModel = new TableModel();
	
	public InitTable() {

		// Properties.
		setModel(contentTableModel);
		setToolTipText("Candidates.");
		setShowGrid(false);
		setName("Candidates.");
		setOpaque(true);
		setAutoCreateRowSorter(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(true);
		
		// Popup menu.
		final JPopupMenu popup = new JPopupMenu();
		
		JMenuItem deleteItem = new JMenuItem("Delete", new ImageIcon (Main.class.getResource("assets/cross-script.png")));
		deleteItem.setActionCommand(Command.DELETE);
		deleteItem.addActionListener(this);
		popup.add(deleteItem);
		
		// Mouse listener for popup menu.
		
		addMouseListener (new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
		        maybeShowPopup(e);
		    }
			@Override
		    public void mouseReleased(MouseEvent e) {
		        maybeShowPopup(e);
		    }

		    private void maybeShowPopup(MouseEvent e) {
		        if (e.isPopupTrigger() && getSelectedRow() != -1) {
		            popup.show(e.getComponent(),
		                       e.getX(), e.getY());
		        }
		    }
		});
		
		// KeyListener for delete hotkey.
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
				if (e.getKeyChar() == KeyEvent.VK_DELETE) {
					Action.execute(Command.DELETE);
				}
			}
		});

	}

	private Object [][] rowData() {
		return Main.getDB().getFields().getContentTableData ();
	}

	private class TableModel extends DefaultTableModel {

		@Override
		public int getRowCount() {
			return Main.getDB().getFields().getItemsCount();
		}

		@Override
		public int getColumnCount() {
			return columnData.length;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return columnData[columnIndex];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object [][] rowData = rowData();
			return rowData[rowIndex][columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Action.execute(e.getActionCommand());
	}

}
