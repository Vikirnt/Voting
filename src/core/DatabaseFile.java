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

    /** JDBC connection object. */
    private static Connection conn;

    /** Constructor gets the connection and sets ResultSet pointer. */
    public DatabaseFile (String path) {
        /* Location of DB. */
        try {
            String loc = "jdbc:sqlite:" + path + "/Candidates.db";
            conn = DriverManager.getConnection (loc);

            Query.CREATE.getPrepstm ().execute ();
            Query.SELECT.getPrepstm ().execute ();
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

        try (ResultSet temprs = Query.COUNT.prepstm.executeQuery ()){
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

    /**
     * @return data array for JTables.
     */
    public Candidate [] getTableContentArray () {
        Candidate [] temp = new Candidate [getCount ()];

        try {
//            Statement st = conn.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = Query.SELECT.execute ();

            System.out.println ("RESULT SET CREATED: " + rs.getString (1));

            String first_name, last_name, post, stddiv;
            int rowid, votecount;
            int i = 0;
            while (rs.next ()) {
                first_name = rs.getString ("first_name");
                last_name = rs.getString ("last_name");
                post = rs.getString ("post");
                stddiv = rs.getString ("class");
                rowid = rs.getInt ("rowid");
                votecount = rs.getInt ("votecount");
                temp [i] = new Candidate (first_name, last_name, post, stddiv, rowid, votecount);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace ();
        }

        return temp;
    }

    /** @return Specified cell value. */
    public String get (String col, int rowid) {
        String val = null;
        try {
            ResultSet rs = Query.GET.execute (rowid);
            val = rs.getString (col);
        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return val;
    }



    // -----

    // INTERACTIONS

    public enum Query {

        CREATE (
                "CREATE TABLE IF NOT EXISTS Candidates (" +
                "first_name varchar(30) NOT NULL, " +
                "last_name varchar(30) NOT NULL, " +
                "post varchar(100) NOT NULL, " +
                "class varchar(10), " +
                "votecount int NOT NULL" +
                ");"
        ),
        SELECT (
                "SELECT *, rowid FROM Candidates;"
        ),
        ADD (
                "INSERT INTO Candidates VALUES " +
                "('?', '?', '?', '?', ?);"
        ),
        SUB (
                "DELETE FROM Candidates WHERE rowid = ? ;"
        ),
        EDIT (
                "UPDATE Candidates " +
                "SET first_name = ?, " +
                "last_name = ?, " +
                "post = ?, " +
                "class = ? " +
                "WHERE rowid = ?" +
                ";"
        ),
        DELETEDB (
                "DELETE FROM Candidates;"
        ),
        VOTE (
                "UPDATE Candidates " +
                "SET votecount = votecount + 1 " +
                "WHERE rowid = ?" +
                ";"
        ),
        COUNT (
                "SELECT COUNT(*) FROM Candidates;"
        ),
        GET (
                "SELECT * FROM Candidates WHERE rowid = ?;"
        );

        protected PreparedStatement prepstm;
        protected  String qry;

        Query (String qry) {
            this.qry = qry;
            try {
                prepstm = conn.prepareStatement (qry);
            } catch (SQLException e) {
                e.printStackTrace ();
            }
        }

        public PreparedStatement getPrepstm () {
            return prepstm;
        }
//        public String getQry () {
//            return qry;
//        }

        public ResultSet execute (Object ... params) {
            ResultSet rs;
            try {
                for (int i = 0; i < params.length; i++)
                    prepstm.setObject (i + 1, params[i]);
                return prepstm.executeQuery ();
            } catch (Exception e) {
                e.printStackTrace ();
            }
            return null;
        }
    }

}

