package gui.init;

import gui.core.Action;
import gui.core.Command;
import gui.main.Main;

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
		
		JMenuItem editItem = new JMenuItem("Edit", new ImageIcon (Main.class.getResource("assets/pencil-small.png")));
		editItem.setActionCommand(Command.EDIT);
		editItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getInitFrame().getFormPanel().changeFormState(InitFormPanel.EDIT);
			}
		});
		popup.add(editItem);
		
		// Mouse listener for popup menu.
		addMouseListener (new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
		        super.mousePressed(e);
				maybeShowPopup(e);
		    }
			@Override
		    public void mouseReleased(MouseEvent e) {
		        super.mouseReleased(e);
				maybeShowPopup(e);
		    }
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && getSelectedRow() != -1) {
					Main.getInitFrame().getFormPanel().changeFormState(InitFormPanel.EDIT);
				}
			}

		    private void maybeShowPopup(MouseEvent e) {
		        if (e.isPopupTrigger() && getSelectedRow() != -1) {
		            popup.show(e.getComponent(), e.getX(), e.getY());
		        }
		    }
		});
		// Key listener for little details and hotkey for delete.
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_DELETE) {
					Action.execute(Command.DELETE);
				} else {
					Main.getInitFrame().getFormPanel().getNameField().requestFocus();
					Main.getInitFrame().getFormPanel().getNameField().setText(Main.getInitFrame().getFormPanel().getNameField().getText() + e.getKeyChar());
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
