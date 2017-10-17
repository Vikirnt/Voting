package gui.core;

import core.Candidate;
import gui.init.InitFormPanel;
import gui.main.Main;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * A collection of *important* actions.
 *
 * @version 3.0
 * @author vikirnt
 */
public class Action {
	
	/**
	 * Checks for a command and functions accordingly.
	 *
     * @param command: the command inputted.
     */
	public static void execute (Command command) {

		switch (command) {
			
			case VOTE:
				int confirm = JOptionPane.showConfirmDialog (Main.getMainFrame (), "Finalise your vote? You can vote only once.", "CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					Main.getDB ().vote ( getPos (Main.getMainFrame ().getContentTable ()) );

					try {
						Main.getMainFrame ().setResizable (false);
						Thread.sleep (2500);
						Main.getMainFrame ().setResizable (true);
					} catch (Exception e) {
						System.err.println ("WE HAVE AN ITERRUPTION!");
					}
				}
				Main.getMainFrame ().getSearchField ().setText ("");
				Main.getMainFrame ().getContentTable ().setSorter ("");
			break;
				
			case ADD:
				addItem ();
				execute (Command.CLEAR);
			break;

			case EDIT:
				editItem ( getPos (Main.getInitFrame ().getContentTable ()) );
				execute (Command.CLEAR);
			break;
			
			case DELETE:
				Main.getDB ().sub ( getPos (Main.getInitFrame ().getContentTable ()) );
				execute (Command.CLEAR);
			break;
			
			case CLEAR:
				Main.getInitFrame ().getFormPanel ().clearFields ();
				Main.getInitFrame ().getFormPanel ().getNameField ().requestFocusInWindow ();
				Main.getInitFrame ().getFormPanel ().changeFormState (InitFormPanel.ADD);
			break;
			
			case CLEANSLATE:
				if (
						JOptionPane.showConfirmDialog
								(Main.getInitFrame (), "Do you want to clear the DB? It will be extremely painful.","CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION)
								== JOptionPane.YES_OPTION
					)
					Main.getDB ().cleanslate ();
			break;
		
			default:
				System.err.println ("NOT CODED || ERROR: " + command);
			break;

		}
		
		try {
			 ( (AbstractTableModel) Main.getMainFrame ().getContentTable ().getModel ()).fireTableDataChanged ();
			 ( (AbstractTableModel) Main.getInitFrame ().getContentTable ().getModel ()).fireTableDataChanged ();
		} catch (NullPointerException e) {
			System.err.println ("Known exception: " + e.getMessage ()); // <--- What's this? Please someone ask 2015 vikirnt...
		}
	}

	/**
	 * Adds an item to Main.getDB ().
	 */
	private static void addItem () {
		String
			first_name = Main.getInitFrame ().getFormPanel ().getNameField ().getText (),
			last_name = Main.getInitFrame ().getFormPanel ().getSurnameField ().getText (),
			post = Main.getInitFrame ().getFormPanel ().getPostField ().getText (),
			stddiv = Main.getInitFrame ().getFormPanel ().getStdDivField ().getText ();

		Main.getDB ().add (new Candidate (first_name, last_name, post, stddiv));
	}
	
	/**
	 * Edit an item in Main.getDB ().
	 */
	private static void editItem (int rowid) {
		String
			first_name = Main.getInitFrame ().getFormPanel ().getNameField ().getText (),
			last_name = Main.getInitFrame ().getFormPanel ().getSurnameField ().getText (),
			post = Main.getInitFrame ().getFormPanel ().getPostField ().getText (),
			stddiv = Main.getInitFrame ().getFormPanel ().getStdDivField ().getText ();

		Main.getDB ().edit (new Candidate (first_name, last_name, post, stddiv, 0, rowid));
	}
	
	/**
	 * Gets the rowid of the selected item in the table.
	 *
	 * @return rowid.
	 */
	public static int getPos (JTable ref) {
		// View index.
		int row = ref.getSelectedRow ();
		// Sorted index.
		int sot = ref.getRowSorter ().convertRowIndexToModel (row);
		return (int) ref.getModel ().getValueAt (sot, 0);
	}

}
