package gui.core;

import core.DatabaseFile;
import gui.init.InitFormPanel;
import gui.main.Main;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * A collection of *important* actions.
 *
 * @version 2.0
 * @author vikirnt
 *
 */
public class Action {
	
	/**
	 * Checks for a command and functions accordingly.
	 *
     * @param command - the command inputted.
     */
	public static void execute (Command command) {

		switch (command) {
			
		// add a vote.
			case VOTE:
				int confirm = JOptionPane.showConfirmDialog (Main.getMainFrame (), "Finalise your vote? You can vote only once.", "CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					DatabaseFile.Query.VOTE.execute ( getPos (Main.getMainFrame ().getContentTable ()) );

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
				
		// add an item.
			case ADD:
				addItem ();
				execute (Command.CLEAR);
			break;
			
		// edits an item info.
			case EDIT:
				DatabaseFile.Query.EDIT.execute ( getPos (Main.getInitFrame ().getContentTable ()) );
				execute (Command.CLEAR);
			break;
			
		// remove an item.
			case DELETE:
				DatabaseFile.Query.SUB.execute ( getPos (Main.getInitFrame ().getContentTable ()) );
				execute (Command.CLEAR);
			break;
			
		// clears all fields in editing frame.
			case CLEAR:
				Main.getInitFrame ().getFormPanel ().clearFields ();
				Main.getInitFrame ().getFormPanel ().getNameField ().requestFocusInWindow ();
				Main.getInitFrame ().getFormPanel ().changeFormState (InitFormPanel.ADD);
			break;
			
		// clears database O.O
			case CLEANSLATE:
				if (
						JOptionPane.showConfirmDialog
								(Main.getInitFrame (), "Do you want to clear the DB? It will be extremely painful.","CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION)
								== JOptionPane.YES_OPTION
					)
					DatabaseFile.Query.DELETEDB.execute ();
			break;
		
			default:
				System.err.println ("NOT CODED || ERROR: " + command);
			break;

		}
		
		try {
			 ( (AbstractTableModel) Main.getMainFrame ().getContentTable ().getModel ()).fireTableDataChanged ();
			 ( (AbstractTableModel) Main.getInitFrame ().getContentTable ().getModel ()).fireTableDataChanged ();
		} catch (NullPointerException e) {
			System.err.println ("Known exception: " + e.getMessage ());
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

		DatabaseFile.Query.ADD.execute (first_name, last_name, post, stddiv, 0 );
	}
	
	/**
	 * Edit an item in Main.getDB ().
	 */
	private static void editItem (int pos) {
		String
			first_name = Main.getInitFrame ().getFormPanel ().getNameField ().getText (),
			last_name = Main.getInitFrame ().getFormPanel ().getSurnameField ().getText (),
			post = Main.getInitFrame ().getFormPanel ().getPostField ().getText (),
			stddiv = Main.getInitFrame ().getFormPanel ().getStdDivField ().getText ();

		DatabaseFile.Query.EDIT.execute ( first_name, last_name, post, stddiv );
	}
	
	/**
	 * Gets the position of the selected item in the table.
	 * Reason: Filtering fucks up indexes.
	 *
	 * TODO: Use rowid
	 * 
	 * @return filtered index.
	 */
	public static int getPos (JTable ref) {

		// TODO: Fix this
		int pos=0;
		
		String 	name	=	 (String) ref.getValueAt (ref.getSelectedRow (), 0),
				surname = 	 (String) ref.getValueAt (ref.getSelectedRow (), 1),
				post	=	 (String) ref.getValueAt (ref.getSelectedRow (), 2),
				stddiv	=	 (String) ref.getValueAt (ref.getSelectedRow (), 3);
		
		//pos = Main.getDB ().getID (name, surname, post, stddiv);
		
		return pos + 1;
		
	}

}
