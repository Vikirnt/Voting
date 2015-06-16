package core;

import gui.main.Main;

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
	 * Load everything from Main.getDB().
	 */
	public void load() {

		// A line in the db.
		String line = "";

		// Clear all lists.
		clear();

		// If the DB is not empty...
		if(!noItems()) {

			// Creating Scanner object.
			Scanner fileScanner = null;
			try {
				fileScanner = new Scanner(Main.getDB());
			} catch(FileNotFoundException e) {
				System.err.println("WHAT");
				e.printStackTrace();
			}

			// loops through each item and adds its fields to the lists.
			while(fileScanner.hasNextLine()) {

				line = fileScanner.nextLine();
				String content;
				
				if(line.startsWith(NAME)) {
					
					content = line.substring(NAME.length()).trim();
					content = Character.toUpperCase(content.charAt(0)) + content.substring(1);
					name.add(content);

				} else if(line.startsWith(SURNAME)) {
					
					content = line.substring(SURNAME.length()).trim();
					content = Character.toUpperCase(content.charAt(0)) + content.substring(1);
					surname.add(content);

				} else if(line.startsWith(POST)) {
					
					content = line.substring(POST.length()).trim();
					post.add(content);

				} else if(line.startsWith(STDDIV)) {
					
					content = line.substring(STDDIV.length()).trim();
					stddiv.add(content);

				} else if(line.startsWith(VOTECOUNT)) {
					
					content = line.substring(VOTECOUNT.length()).trim();
					votecount.add(Integer.parseInt(content));

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

		if(Main.getDB().length() == 0) {
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
	
	public ArrayList <String> getName() {
		return name;
	}
	public ArrayList <String> getSurname() {
		return surname;
	}
	public ArrayList <String> getPost() {
		return post;
	}
	public ArrayList <String> getStdDiv() {
		return stddiv;
	}
	public ArrayList <Integer> getVoteCount() {
		return votecount;
	}
	
	public void addName(String addition) {
		name.add(addition);
	}
	public void addSurname(String addition) {
		surname.add(addition);
	}
	public void addPost(String addition) {
		post.add(addition);
	}
	public void addStdDiv(String addition) {
		stddiv.add(addition);
	}
	public void addVotecount(int addition) {
		votecount.add(addition);
	}
	public void addVote(int index) {
		int temp = votecount.get(index);
		votecount.set(index, temp + 1);
	}
	
	public void removeName(int itemPos) {
		name.remove(itemPos);
	}
	public void removeSurname(int removal) {
		surname.remove(removal);
	}
	public void removePost(int removal) {
		post.remove(removal);
	}
	public void removeStdDiv(int removal) {
		stddiv.remove(removal);
	}
	public void removeVote(int index) {
		int temp = votecount.get(index);
		votecount.set(index, temp - 1);
	}

	// -----

	/**
	 * Returns all fields in output-able format.
	 */
	@Override
	public String toString() {

		String all = "";

		for(int i = 0; i < getItemsCount(); i++) {
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
	
	// -----

	/**
	 * @return data for normal table.
	 */
	public Object [][] getContentTableData() {

		Object [][] temp = new Object [Main.getDB().getFields().getItemsCount()][4];
		for(int i = 0; i < Main.getDB().getFields().getItemsCount(); i++) {

			temp[i][0] = name.get(i);
			temp[i][1] = surname.get(i);
			temp[i][2] = post.get(i);
			temp[i][3] = stddiv.get(i);

		}
		return temp;

	}
	
	/**
	 * @return data for results table.
	 */
	public Object [][] getVoteBasedData() {

		Object [][] temp = new Object [Main.getDB().getFields().getItemsCount()][5];
		for(int i = 0; i < Main.getDB().getFields().getItemsCount(); i++) {

			temp[i][0] = name.get(i);
			temp[i][1] = surname.get(i);
			temp[i][2] = post.get(i);
			temp[i][3] = stddiv.get(i);
			temp[i][4] = votecount.get(i);

		}
		return temp;

	}

}