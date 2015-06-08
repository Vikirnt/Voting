package core;

import java.io.*;

/**
 * The database.
 * 
 * @author admin
 *
 */
public class DB {

	/**
	 * The file object for database. Stored in /src/
	 */
	private static File dbFile = new File(
			"C:/Users/admin/AppData/Roaming/imp/cont.dat"
	);

	/**
	 * A new set of fields.
	 */
	private static Fields fields = new Fields();

	/**
	 * This function could have been one single line, but it also creates a new
	 * file if the database does not exist.
	 * 
	 * @return true: file exists, false: new file created.
	 */
	public static boolean checkExistance() {

		boolean temp = true;

		try {
			new File ("C:/Users/admin/AppData/Roaming/imp/").mkdir ();
			if (dbFile.createNewFile()) {
				temp = false;
			} else {
				temp = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return temp;

	}

	/**
	 * @return the DB file object reference.
	 */
	public static File getFile() {
		return dbFile;
	}
	/**
	 * @return fields object.
	 */
	public static Fields getFields() {
		return fields;
	}

	// -----

	/**
	 * Updates the database with new items.
	 */
	@SuppressWarnings("resource")
	public static void save() {

		FileWriter fw = null;

		try {
			fw = new FileWriter(dbFile);
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
	public static void clear() {

		dbFile.delete();
		try {
			dbFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static String getPassword () {
		return "cs15";
	}

}
