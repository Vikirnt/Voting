package core;

import gui.main.Main;

import java.sql.*;

/**
 * Database created.
 * Driver used is SQLite.
 *
 * @version 2
 * @author vikirnt
 * @since 6 October 2017
 *
 */
public class DatabaseFile {

    /** Temperory hard-coded password. */
    public static final String password = "cs15";

    /** Location of DB. */
    private String loc;
    /** Table name. */
    private String table = "Candidates";
    /** JDBC connection object. */
    private Connection conn;
    /** JDBS statement to execute queries. */
    private Statement stm;
    /** ResultSet for the entire object. */
    private ResultSet rs;

    /** Constructor gets the connection and sets ResultSet pointer. */
    public DatabaseFile (String path) {
        loc = "jdbc:sqlite:" + path + "/Candidates.db";
        try {
            conn = DriverManager.getConnection (loc);
            stm = conn.createStatement ();
            Main.log ("Database connected at location: " + path);

            String initqry =
                    "SELECT * FROM "+table+";";
            rs = stm.executeQuery (initqry);
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    /**
     * Dangerous! Clears entire table.
     */
    public void clear () {
        String qry =
                "DELETE * FROM "+table+";";
        try {
            stm.execute (qry);
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    /**
     * @return length of table/ number of rows.
     */
    public int getCount () {
        int cnt = 0;
        String qry = "SELECT COUNT(*) FROM "+table+";";
        try (ResultSet temprs = stm.executeQuery (qry)){
            cnt = temprs.getInt (1);
        } catch (SQLException e) {
            e.printStackTrace ();
        }

        return cnt;
    }



    // -----

    /**
     * @return data array for JTables.
     */
    public Object [][] getTableContentArray () {

        Object [][] temp = new Object [getCount ()][4];

        try {
            rs.first ();
            int i = 0;
            while (rs.next ()) {
                temp [i][0] = rs.getObject ("first_name");
                temp [i][1] = rs.getObject ("last_name");
                temp [i][2] = rs.getObject ("post");
                temp [i][3] = rs.getObject ("stddiv");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace ();
        }

        return temp;

    }

}
