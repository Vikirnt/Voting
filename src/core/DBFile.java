package core;

import java.io.*;

/**
 * The database.
 * 
 * @author admin
 *
 */
public class DBFile extends File {
	
	public DBFile (String filename) {
		super (System.getenv("APPDATA") + "/imp/" + filename);
	}

	/**
	 * A new set of fields.
	 */
	private Fields fields;

	/**
	 * This function could have been one single line, but it also creates a new
	 * file if the database does not exist.
	 * 
	 * @return true: file exists, false: new file created.
	 */
	public void initialiseFile() {

		try {
			new File (System.getenv("APPDATA") + "/imp/").mkdir ();
			this.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		fields = new Fields();

	}

	/**
	 * @return fields object.
	 */
	public Fields getFields() {
		return fields;
	}

	// -----

	/**
	 * Updates the database with new items.
	 */
	@SuppressWarnings("resource")
	public void save() {

		FileWriter fw = null;

		try {
			fw = new FileWriter(this);
			fw.write(fields.toString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Deletes all contents.
	 */
	public void clear() {

		this.delete();
		try {
			this.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static String getPassword () {
		return "cs15";
	}

}
