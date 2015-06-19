package core;

import java.io.*;

/**
 * The database.
 * 
 * @author admin
 *
 */
public class DBFile extends File {
	
	/** A new set of fields. */
	private Fields fields;
	
	/**
	 * Main constructor.
	 */
	public DBFile (String loc) {
		super (loc + "/cont.dat");
		try {
			new File (loc).mkdir ();
			this.createNewFile ();
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	/**
	 * Initialise fields.
	 */
	public void initFields () {
		fields = new Fields ();
	}
	/**
	 * @return fields object.
	 */
	public Fields getFields () {
		return fields;
	}

	// -----

	/**
	 * Updates the database with new items.
	 */
	@SuppressWarnings ("resource")
	public void save () {
		FileWriter fw = null;

		try {
			fw = new FileWriter (this, false); // No appending!
			fw.write (fields.toString ()); // Writes new data every time it saves.
			fw.flush ();
			fw.close ();
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	/**
	 * Deletes all contents.
	 */
	public void clear () {
		this.delete (); // delete
		try {
			this.createNewFile (); // create :p
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}
	
	/** Temperory password placeholder. */
	public static String getPassword () {
		return "cs15";
	}

}
