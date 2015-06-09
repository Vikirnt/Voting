package gui.core;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import main.Main;
import core.DB;

/**
 * A collection of common actions.
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
	public static void execute(String command) {
		
		switch (command) {
		
		// add a vote.
			case Command.VOTE:
				doVote ();
				saveDB ();
			break;
			
		// save DB.
			case Command.SAVE:
				saveDB ();
			break;
			
		// add an item.
			case Command.ADD:
				addItem ();
				saveDB ();
			break;
			
		// remove an item.
			case Command.DELETE:
				removeItem (getPos(Main.getInitFrame().getContentTable()));
				saveDB ();
			break;
			
		// cancel adding a new item.
			case Command.CLEAR:
				clearFields ();
			break;
		
			default:
				System.err.println ("NOT CODED || ERROR: " + command);
			break;

		}
		
		try {
			((AbstractTableModel) Main.getMainFrame().getContentTable().getModel()).fireTableDataChanged ();
			((AbstractTableModel) Main.getInitFrame().getContentTable().getModel()).fireTableDataChanged ();
		} catch (NullPointerException e) {
			System.err.println("Known exception: " + e.getMessage());
		}
			
	}
	
	/**
	 * Adds an item to DB.
	 */
	private static void addItem() {
		DB.getFields().addName(Main.getInitFrame().getFormPane().getNameField().getText());
		DB.getFields().addSurname(Main.getInitFrame().getFormPane().getSurnameField().getText());
		DB.getFields().addPost(Main.getInitFrame().getFormPane().getPostField().getText());
		DB.getFields().addStdDiv(Main.getInitFrame().getFormPane().getClassField().getText());
		DB.getFields().addVotecount(0);
		clearFields();
	}
	
	/**
	 * Adds a vote.
	 */
	private static void doVote() {
		int confirm = JOptionPane.showConfirmDialog(null, "Finalise your vote? You can vote only once.", "CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			DB.getFields().addVote(getPos(Main.getMainFrame().getContentTable()));
			try {
				Main.getMainFrame().setResizable(false);
				Thread.sleep(3200);
				Main.getMainFrame().setResizable(true);
				Main.getMainFrame().getSearchField ().setText("");
			} catch (Exception e) {
				System.err.println("WE HAVE AN INTERRUPTION!");
			}					
		}
	}

	/**
	 * Clears all text fields in InitForm.
	 */
	private static void clearFields () {
		Main.getInitFrame().getFormPane().getNameField().setText("");
		Main.getInitFrame().getFormPane().getSurnameField().setText("");
		Main.getInitFrame().getFormPane().getPostField().setText("");
		Main.getInitFrame().getFormPane().getClassField().setText("");
		
		Main.getInitFrame().getFormPane().getNameField().requestFocus();
	}

	/**
	 * Saves the fields to the database.
	 */
	private static void saveDB() {
		DB.save();
	}

	/**
	 * Removes an item from the database.
	 */
	private static void removeItem(int itemPos) {
		try {
			DB.getFields().removeName	(itemPos);
			DB.getFields().removeSurname(itemPos);
			DB.getFields().removePost	(itemPos);
			DB.getFields().removeStdDiv	(itemPos);
			DB.getFields().removeVote	(itemPos);
		} catch (NullPointerException e) {
			System.err.println("\nRemoval canceled.\n");
		}

	}
	
	/**
	 * Gets the position of the selected item in the table.
	 * Reason: Filtering fucks up indexes.
	 * 
	 * @return
	 */
	private static int getPos (JTable ref) {
		
		int namepos = -1, surnamepos = -2, postpos = -3, stddivpos = -4;
		
		String 	name	=	(String) ref.getValueAt (ref.getSelectedRow(), 0),
				surname = 	(String) ref.getValueAt (ref.getSelectedRow(), 1),
				post	=	(String) ref.getValueAt (ref.getSelectedRow(), 2),
				stddiv	=	(String) ref.getValueAt (ref.getSelectedRow(), 3);
		
		aaa: while (namepos != surnamepos) {
			for (int i = 0; i < DB.getFields().getItemsCount(); i++) {
				if (DB.getFields().getName().get(i).equals(name)) {
					namepos = i;
				}
				if (DB.getFields().getSurname().get(i).equals(surname)) {
					surnamepos = i;
				}
				if (DB.getFields().getPost().get(i).equals(post)) {
					postpos = i;
				}
				if (DB.getFields().getStdDiv().get(i).equals(stddiv)) {
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
