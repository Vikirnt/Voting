package gui.core;

import gui.init.InitForm;
import gui.init.InitFrame;
import gui.main.MainFrame;

import javax.swing.JOptionPane;
import javax.swing.JTable;

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
	 * @param command
	 *            - the command inputted.
	 */
	public static void execute(String command) {

		switch (command) {
		
		// add a vote.
			case Command.VOTE:
				int confirm = JOptionPane.showConfirmDialog(null, "Finalise your vote? You can vote only once.", "CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					DB.getFields().addVote(getPos(MainFrame.getContentTable()));
					MainFrame.getContentTable().setEnabled(false);
					saveDB();
					try {
						Thread.sleep(3 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					MainFrame.getContentTable().setEnabled(true);
					break;						
				}
			break;
			
		// save DB.
			case Command.SAVE:
				saveDB();
			break;
			
		// add an item.
			case Command.ADD:
				DB.getFields().addName(InitForm.getNameField().getText());
				DB.getFields().addSurname(InitForm.getSurnameField().getText());
				DB.getFields().addPost(InitForm.getPostField().getText());
				DB.getFields().addStdDiv(InitForm.getClassField().getText());
				DB.getFields().addVotecount(0);
				saveDB();
				clearFields();
			break;
			
		// remove an item.
			case Command.DELETE:
				removeItem(getPos(InitFrame.getContentTable()));
				saveDB();
			break;
			
		// cancel adding a new item.
			case Command.CLEAR:
				clearFields();
			break;
		
			default:
				System.err.println ("NOT CODED || ERROR: " + command);
			break;

		}
		
		try {
			MainFrame.getContentTable().repaint();
			MainFrame.getContentTable().updateUI();
			InitFrame.getContentTable().repaint();
			InitFrame.getContentTable().updateUI();
		} catch (NullPointerException e) {
			System.err.println("Known exception: " + e.getMessage());
		}
			
	}
	
	/**
	 * Clears all text fields in InitForm.
	 */
	private static void clearFields () {
		InitForm.getNameField().setText("");
		InitForm.getSurnameField().setText("");
		InitForm.getPostField().setText("");
		InitForm.getClassField().setText("");
		
		InitForm.getNameField().requestFocus();
	}

	/**
	 * Saves the fields to the database.
	 */
	private static void saveDB() {
		DB.save();
		DB.getFields().load();
	}

	/**
	 * Removes an item from the database.
	 */
	public static void removeItem(int itemPos) {
		try {
			DB.getFields().removeName(itemPos);
			DB.getFields().removeSurname(itemPos);
			DB.getFields().removePost(itemPos);
			DB.getFields().removeStdDiv(itemPos);
			DB.getFields().removeVote(itemPos);
			
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
	public static int getPos (JTable ref) {
		
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
