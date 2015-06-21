package gui.core;

import gui.init.InitFormPanel;
import gui.main.Main;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * A collection of *important* actions.
 * 
 * @author admin
 *
 */
public class Action {
	
	/**
	 * Checks for a command and functions accordingly.
	 * 
	 * @param command - the command inputted.
	 */
	public static void execute (String command) {
		
		switch (command) {
		
		// save Main.getDB ().
			case Command.SAVE:
				saveDB ();
			break;
			
		// add a vote.
			case Command.VOTE:
				doVote ();
				saveDB ();
				Main.getMainFrame ().getSearchField ().setText ("");
				Main.getMainFrame ().getContentTable ().setSorter ("");
			break;
				
		// add an item.
			case Command.ADD:
				addItem ();
				saveDB ();
				execute (Command.CLEAR);
			break;
			
		// edits an item info.
			case Command.EDIT:
				editItem (getPos (Main.getInitFrame ().getContentTable ()));
				saveDB ();
				execute (Command.CLEAR);
			break;
			
		// remove an item.
			case Command.DELETE:
				removeItem (getPos (Main.getInitFrame ().getContentTable ()));
				saveDB ();
				execute (Command.CLEAR);
			break;
			
		// clears all fields in editing frame.
			case Command.CLEAR:
				Main.getInitFrame ().getFormPanel ().clearFields ();
				Main.getInitFrame ().getFormPanel ().getNameField ().requestFocusInWindow ();
				Main.getInitFrame ().getFormPanel ().changeFormState (InitFormPanel.ADD);
			break;
			
		// clears database O.O
			case Command.CLEANSLATE:
				int ans = JOptionPane.showConfirmDialog (Main.getInitFrame (), "Do you want to clear the DB? It will be extremely painful.", "CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION);
				if (ans == JOptionPane.YES_OPTION)
					cleanslateDB ();
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
	 * Clears entire DB.
	 */
	private static void cleanslateDB () {
		Main.getDB ().clear ();
		Main.getDB ().getFields ().clear ();
		Main.getDB ().save ();
	}

	/**
	 * Adds an item to Main.getDB ().
	 */
	private static void addItem () {
		Main.getDB ().getFields ().addName
			 (Main.getInitFrame ().getFormPanel ().getNameField ().getText ());
		Main.getDB ().getFields ().addSurname
			 (Main.getInitFrame ().getFormPanel ().getSurnameField ().getText ());
		Main.getDB ().getFields ().addPost
			 (Main.getInitFrame ().getFormPanel ().getPostField ().getText ());
		Main.getDB ().getFields ().addStdDiv
			 (Main.getInitFrame ().getFormPanel ().getStdDivField ().getText ());
		Main.getDB ().getFields ().addVotecount
			 (0);
	}
	
	/**
	 * Edit an item in Main.getDB ().
	 */
	private static void editItem (int pos) {
		Main.getDB ().getFields ().getName ().set
			 (pos, 	Main.getInitFrame ().getFormPanel ().getNameField ().getText ());
		Main.getDB ().getFields ().getSurname ().set
			 (pos,		Main.getInitFrame ().getFormPanel ().getSurnameField ().getText ());
		Main.getDB ().getFields ().getPost ().set
			 (pos,		Main.getInitFrame ().getFormPanel ().getPostField ().getText ());
		Main.getDB ().getFields ().getStdDiv ().set
			 (pos,		Main.getInitFrame ().getFormPanel ().getStdDivField ().getText ());
	}
	
	/**
	 * Adds a vote.
	 */
	private static void doVote () {
		int confirm = JOptionPane.showConfirmDialog (Main.getMainFrame (), "Finalise your vote? You can vote only once.", "CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			Main.getDB ().getFields ().addVote (getPos (Main.getMainFrame ().getContentTable ()));
			try {
				Main.getMainFrame ().setResizable (false);
				Thread.sleep (2500);
				Main.getMainFrame ().setResizable (true);
			} catch (Exception e) {
				System.err.println ("WE HAVE AN ITERRUPTION!");
			}					
		}
	}

	/**
	 * Saves the fields to the database.
	 */
	private static void saveDB () {
		Main.getDB ().save ();
	}

	/**
	 * Removes an item from the database.
	 */
	private static void removeItem (int itemPos) {
		try {
			Main.getDB ().getFields ().removeName	 (itemPos);
			Main.getDB ().getFields ().removeSurname (itemPos);
			Main.getDB ().getFields ().removePost	 (itemPos);
			Main.getDB ().getFields ().removeStdDiv	 (itemPos);
			Main.getDB ().getFields ().removeVote	 (itemPos);
		} catch (NullPointerException e) {
			System.err.println ("\nRemoval canceled.\n");
		}

	}
	
	/**
	 * Gets the position of the selected item in the table.
	 * Reason: Filtering fucks up indexes.
	 * 
	 * @return filtered index.
	 */
	public static int getPos (JTable ref) {
		
		int namepos = -1, surnamepos = -2, postpos = -3, stddivpos = -4;
		
		String 	name	=	 (String) ref.getValueAt (ref.getSelectedRow (), 0),
				surname = 	 (String) ref.getValueAt (ref.getSelectedRow (), 1),
				post	=	 (String) ref.getValueAt (ref.getSelectedRow (), 2),
				stddiv	=	 (String) ref.getValueAt (ref.getSelectedRow (), 3);
		
		aaa: while (namepos != surnamepos) {
			for (int i = 0; i < Main.getDB ().getFields ().getItemsCount (); i++) {
				if (Main.getDB ().getFields ().getName ().get (i).equals (name)) {
					namepos = i;
				}
				if (Main.getDB ().getFields ().getSurname ().get (i).equals (surname)) {
					surnamepos = i;
				}
				if (Main.getDB ().getFields ().getPost ().get (i).equals (post)) {
					postpos = i;
				}
				if (Main.getDB ().getFields ().getStdDiv ().get (i).equals (stddiv)) {
					stddivpos = i;
				}
				if (namepos == surnamepos && surnamepos == postpos && postpos == stddivpos && stddivpos == namepos) { // Inefficient but gets the job done.
					break aaa;
				}
			}
		}
		
		return namepos;
		
	}

}
