package core;

import java.util.*;
import java.io.*;

/**
 * A collection of core fields of an homework.
 * 
 * @author admin
 *
 */
public class Fields {

	/**
	 * <String> fields.
	 */
	private ArrayList<String> name, surname, post, stddiv;

	/**
	 * <Integer> fields.
	 */
	private ArrayList<Integer> votecount;
	
	/**
	 * Reference
	 */
	private final String NAME = "name = ", SURNAME = "surname = ", POST = "post = ", STDDIV = "stddiv = ", VOTECOUNT = "votecount = ";


	/**
	 * Default constructor which initialises the lists.
	 */
	public Fields() {

		// Creating objects.
		name 		= 	new ArrayList<String>();
		surname 	=	new ArrayList<String>();
		post 		= 	new ArrayList<String>();
		stddiv 		= 	new ArrayList<String>();
		votecount 	= 	new ArrayList<Integer>();

		load();

	}


	/**
	 * Load everything from DB.
	 */
	public void load() {

		// A line in the db.
		String line = "";

		// Clear all lists.
		clear();

		// If the DB is not empty...
		if (!noItems()) {

			// Creating Scanner object.
			Scanner fileScanner = null;
			try {
				fileScanner = new Scanner(DB.getFile());
			} catch (FileNotFoundException e) {
				System.err.println("WHAT");
				e.printStackTrace();
			}

			// loops through each item and adds its fields to the lists.
			while (fileScanner.hasNextLine()) {

				line = fileScanner.nextLine();

				if (line.startsWith(NAME)) {

					name.add(line.substring(NAME.length ()).trim());

				} else if (line.startsWith(SURNAME)) {

					surname.add(line.substring(SURNAME.length ()).trim());

				} else if (line.startsWith(POST)) {

					post.add(line.substring(POST.length ()).trim());

				} else if (line.startsWith(STDDIV)) {

					stddiv.add(line.substring(STDDIV.length ()).trim());

				} else if (line.startsWith(VOTECOUNT)) {

					votecount.add(Integer.parseInt(line.substring(VOTECOUNT.length ()).trim ()));

				}

			} // loop ends.

			fileScanner.close();

		} // if ends.

	}

	/**
	 * Clear teh lists.
	 */
	public void clear() {

		name		.clear();
		surname		.clear();
		post		.clear();
		stddiv		.clear();
		votecount	.clear();

	}

	/**
	 * Check if database is empty.
	 * 
	 * @return true- DB empty. false- DB isn't empty.
	 */
	public boolean noItems() {

		if (DB.getFile().length() == 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @return number of items.
	 */
	public int getItemsCount() {

		return name.size();

	}
	
	// Interactions.
	
	public ArrayList <String> getName () {
		return name;
	}
	public ArrayList <String> getSurname () {
		return surname;
	}
	public ArrayList <String> getPost () {
		return post;
	}
	public ArrayList <String> getStdDiv () {
		return stddiv;
	}
	public ArrayList <Integer> getVoteCount () {
		return votecount;
	}
	
	public void addName (String addition) {
		name.add (addition);
	}
	public void addSurname (String addition) {
		surname.add (addition);
	}
	public void addPost (String addition) {
		post.add (addition);
	}
	public void addStdDiv (String addition) {
		stddiv.add (addition);
	}
	public void addVotecount (int addition) {
		votecount.add (addition);
	}
	public void addVote (int index) {
		System.out.println ("index = " + index);
		int temp = votecount.get (index);
		votecount.set (index, temp + 1);
	}
	
	public void removeName (int itemPos) {
		name.remove (itemPos);
	}
	public void removeSurname (int removal) {
		surname.remove (removal);
	}
	public void removePost (int removal) {
		post.remove (removal);
	}
	public void removeStdDiv (int removal) {
		stddiv.remove (removal);
	}
	public void removeVote (int index) {
		int temp = votecount.get (index);
		votecount.set (index, temp - 1);
	}

	// -----

	/**
	 * Returns all fields in output-able format.
	 */
	@Override
	public String toString() {

		String all = "";

		for (int i = 0; i < getItemsCount(); i++) {
			all = all
					+ NAME + name.get(i)
					+ System.getProperty("line.separator")
					+ SURNAME + surname.get(i)
					+ System.getProperty("line.separator")
					+ POST + post.get(i)
					+ System.getProperty("line.separator")
					+ STDDIV + stddiv.get(i)
					+ System.getProperty("line.separator")
					+ VOTECOUNT + votecount.get(i)
					+ System.getProperty("line.separator");
					
			all += "<::>" + System.getProperty("line.separator");
			
		}

		return all;

	}

	/**
	 * @return data for table.
	 */
	public Object [][] tableData () {

		Object [][] temp = new Object [DB.getFields().getItemsCount()][4];
		for (int i = 0; i < DB.getFields().getItemsCount(); i++) {

			temp[i][0] = name.get(i);
			temp[i][1] = surname.get(i);
			temp[i][2] = post.get(i);
			temp[i][3] = stddiv.get(i);

		}
		return temp;

	}

}