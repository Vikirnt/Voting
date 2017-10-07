package core;

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

    /** Table name. */
    private static final String table = "Candidates";
    /** JDBC connection object. */
    private static Connection conn;
    /** JDBS statement to execute queries. */
    private Statement stm;
    /** ResultSet for the entire object. */
    private ResultSet rs;

    /** Constructor gets the connection and sets ResultSet pointer. */
    public DatabaseFile (String path) {
        /* Location of DB. */
        String loc = "jdbc:sqlite:" + path + "/Candidates.db";
        try {
            conn = DriverManager.getConnection (loc);
            stm = conn.createStatement ();

            Query.CREATE.getStm ().execute ();
            String initqry =
                    "SELECT *, rowid FROM "+table+";";
            rs = stm.executeQuery (initqry);
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    // -----

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

    /**
     * @return Connection object.
     */
    static Connection getConn () {
        return conn;
    }

    // -----

    /**
     * @return data array for JTables.
     */
    public Object [][] getTableContentArray () {

        Object [][] temp = new Object [getCount ()][5];

        try {
            rs.first ();
            int i = 0;
            while (rs.next ()) {
                temp [i][0] = rs.getObject ("first_name");
                temp [i][1] = rs.getObject ("last_name");
                temp [i][2] = rs.getObject ("post");
                temp [i][3] = rs.getObject ("class");
                temp [i][4] = rs.getObject ("rowid");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace ();
        }

        return temp;

    }

    /** @return Index of a student. */
    public int getID (String ... params) {
        String qry = "SELECT *, rowid FROM "+table+" WHERE first_name = ?, last_name = ?, post = ?, class = ?;";
        int id = 0;

        try (PreparedStatement ps = conn.prepareStatement (qry)) {
            for (int i = 0; i < params.length; i++)
                ps.setObject (i+1, params [i]);
            id = stm.executeQuery (qry).getInt ("rowid");
        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return  id;
    }

    /** @return Specified cell value. */
    public String get (String col, int row) {
        String qry = "SELECT * FROM "+table+" WHERE rowid = " + row;
        String val = null;

        try (ResultSet rs = stm.executeQuery (qry)) {
            val = rs.getString (col);
        } catch (SQLException e) {
            e.printStackTrace ();
        }

        return val;
    }



    // -----

    // INTERACTIONS

    public void execute (Query q, Object... params) {
        try {
            for (int i = 0; i < params.length; i++)
                q.getStm ().setObject (i+1, params[i]);

            q.getStm ().execute ();
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    public enum Query {

        CREATE (
                String.format (
                        "CREATE TABLE IF NOT EXISTS %s (" +
                        "first_name varchar(30) NOT NULL," +
                        "last_name varchar(30) NOT NULL," +
                        "post varchar(100) NOT NULL," +
                        "class varchar(10)," +
                        "votecount int NOT NULL" +
                        ");",
                        table)
        ),
        ADD (
                String.format (
                        "INSERT INTO %s VALUES " +
                        "(?, ?, ?, ?, ?);",
                        table)
        ),
        SUB (
                String.format (
                        "DELETE FROM %s WHERE rowid = ? ;",
                        table)
        ),
        EDIT (
                String.format (
                        "UPDATE %s " +
                        "SET first_name = ?, " +
                        "last_name = ?, " +
                        "post = ?, " +
                        "class = ? " +
                        "WHERE rowid = ?" +
                        ";",
                        table)
        ),
        DELETEDB (
                String.format (
                        "DELETE FROM %s;",
                        table)
        ),
        VOTE (
                String.format (
                        "UPDATE %s " +
                        "SET votecount = votecount + 1 " +
                        "WHERE rowid = ?" +
                        ";",
                        table)
        ),
        DEVOTE (
                String.format (
                        "UPDATE %s " +
                                "SET votecount = votecount - 1 " +
                                "WHERE rowid = ?" +
                                ";",
                        table)
        );

        protected PreparedStatement stm;

        Query (String qry) {
            try {
                stm = getConn ().prepareStatement (qry);
            } catch (SQLException e) {
                e.printStackTrace ();
            }
        }

        public PreparedStatement getStm () {
            return stm;
        }
    }

}

