package gui.init;

import gui.core.Action;
import gui.core.Command;
import gui.main.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;

/**
 * The main content table.
 * 
 * @author admin
 *
 */
public class InitTable extends JTable implements ActionListener {
	
	/** Column names */
	private final String[] columnData = { "Name", "Surname", "Post", "Class" };

	/** Table filter. */
	private TableRowSorter <TableModel> sorter;
	
	InitTable () {
		// Properties.
		/* Table model. */
		TableModel contentTableModel = new MyTableModel ();
		setModel (contentTableModel);
		setToolTipText ("Candidates.");
		setShowGrid (false);
		setName ("Candidates.");
		setOpaque (true);
		setAutoCreateRowSorter (true);
		setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
		getTableHeader ().setReorderingAllowed (true);
		
		// Filter.
		sorter = new TableRowSorter<> (getModel ());
		setRowSorter (sorter);
		
		// Popup menu.
		final JPopupMenu popup = new JPopupMenu ();
		
		JMenuItem deleteItem = new JMenuItem ("Delete", new ImageIcon (Main.class.getResource ("assets/cross-script.png")));
		deleteItem.setActionCommand (Command.DELETE.name ());
		deleteItem.addActionListener (this);
		popup.add (deleteItem);
		
		JMenuItem editItem = new JMenuItem ("Edit", new ImageIcon (Main.class.getResource ("assets/pencil-small.png")));
		editItem.setActionCommand (Command.EDIT.name ());
		editItem.addActionListener (e -> Main.getInitFrame ().getFormPanel ().changeFormState (InitFormPanel.EDIT));
		popup.add (editItem);
		
		// Mouse listener for popup menu.
		addMouseListener (new MouseAdapter () {
			@Override
			public void mousePressed (MouseEvent e) {
		        super.mousePressed (e);
				maybeShowPopup (e);
		    }
			@Override
		    public void mouseReleased (MouseEvent e) {
		        super.mouseReleased (e);
				maybeShowPopup (e);
		    }
			@Override
			public void mouseClicked (MouseEvent e) {
				super.mouseClicked (e);
				if (e.getClickCount () == 2 && e.getButton () == MouseEvent.BUTTON1 && getSelectedRow () != -1) {
					Main.getInitFrame ().getFormPanel ().changeFormState (InitFormPanel.EDIT);
				}
			}

		    private void maybeShowPopup (MouseEvent e) {
		        if (e.isPopupTrigger () && getSelectedRow () != -1) {
		            popup.show (e.getComponent (), e.getX (), e.getY ());
		        }
		    }
		});
		// Key listener for little details and hotkey for delete.
		addKeyListener (new KeyAdapter () {
			@Override
			public void keyTyped (KeyEvent e) {
				if (e.getKeyChar () == KeyEvent.VK_DELETE) {
					Action.execute (Command.DELETE);
				} else if (Character.isLetter (e.getKeyChar ()) || Character.isDigit (e.getKeyChar ())) {
					Main.getInitFrame ().getFormPanel ().getNameField ().requestFocusInWindow ();
					Main.getInitFrame ().getFormPanel ().getNameField ().setText (Main.getInitFrame ().getFormPanel ().getNameField ().getText () + e.getKeyChar ());
				}
			}
		});
	}
	
	/** Table model class. */
	private class MyTableModel extends DefaultTableModel {
		@Override
		public String getColumnName (int columnIndex) {
			return columnData[columnIndex];
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
		public Object getValueAt (int rowIndex, int columnIndex) {
			return Main.getDB ().getTableContentArray () [rowIndex][columnIndex];
		}
		@Override
		public boolean isCellEditable (int rowIndex, int columnIndex) {
			return false;
		}
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		Action.execute (Command.valueOf (e.getActionCommand ()));
	}
	
	/** Changes the sorter according to the regex. Case insensitive. */
	void setSorter (String reg) {
		sorter.setRowFilter (RowFilter.regexFilter ( (" (?i)" + reg).trim ()));
	}

}
