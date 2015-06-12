package gui.core;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import main.Main;

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
			
		// save Main.getDB().
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
	 * Adds an item to Main.getDB().
	 */
	private static void addItem() {
		Main.getDB().getFields().addName
			(Main.getInitFrame().getFormPane().getNameField().getText());
		Main.getDB().getFields().addSurname
			(Main.getInitFrame().getFormPane().getSurnameField().getText());
		Main.getDB().getFields().addPost
			(Main.getInitFrame().getFormPane().getPostField().getText());
		Main.getDB().getFields().addStdDiv
			(Main.getInitFrame().getFormPane().getClassField().getText());
		Main.getDB().getFields().addVotecount
			(0);
		clearFields();
	}
	
	/**
	 * Edit an item in Main.getDB().
	 */
	private static void editItem() {

		Main.getDB().getFields().getName().set
			(getPos(Main.getInitFrame().getContentTable()), 	Main.getInitFrame().getFormPane().getNameField().getText());
		Main.getDB().getFields().getSurname().set
			(getPos(Main.getInitFrame().getContentTable()),		Main.getInitFrame().getFormPane().getSurnameField().getText());
		Main.getDB().getFields().getPost().set
			(getPos(Main.getInitFrame().getContentTable()),		Main.getInitFrame().getFormPane().getPostField().getText());
		Main.getDB().getFields().getStdDiv().set
			(getPos(Main.getInitFrame().getContentTable()),		Main.getInitFrame().getFormPane().getClassField().getText());
		clearFields();
	}
	
	/**
	 * Adds a vote.
	 */
	private static void doVote() {
		int confirm = JOptionPane.showConfirmDialog(null, "Finalise your vote? You can vote only once.", "CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			Main.getDB().getFields().addVote(getPos(Main.getMainFrame().getContentTable()));
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
		Main.getDB().save();
	}

	/**
	 * Removes an item from the database.
	 */
	private static void removeItem(int itemPos) {
		try {
			Main.getDB().getFields().removeName	(itemPos);
			Main.getDB().getFields().removeSurname(itemPos);
			Main.getDB().getFields().removePost	(itemPos);
			Main.getDB().getFields().removeStdDiv	(itemPos);
			Main.getDB().getFields().removeVote	(itemPos);
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
			for (int i = 0; i < Main.getDB().getFields().getItemsCount(); i++) {
				if (Main.getDB().getFields().getName().get(i).equals(name)) {
					namepos = i;
				}
				if (Main.getDB().getFields().getSurname().get(i).equals(surname)) {
					surnamepos = i;
				}
				if (Main.getDB().getFields().getPost().get(i).equals(post)) {
					postpos = i;
				}
				if (Main.getDB().getFields().getStdDiv().get(i).equals(stddiv)) {
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
