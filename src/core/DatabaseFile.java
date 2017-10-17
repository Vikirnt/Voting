package core;

import java.sql.*;

/**
 * Database created.
 * Driver used is SQLite.
 *
 * @version 2.0
 * @author vikirnt
 * @since October 2017
 */
public class DatabaseFile {

    /** Temporary hard-coded password. */
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

    // ----- OPERATIONS -----

    /**
     * @param cand: Candidate to add to db.
     */
    public void add (Candidate cand) {
        Query.ADD.execute (cand.getFirstName (), cand.getLastName (), cand.getPost (), cand.getStddiv (), cand.getVotecount ());
    }

    /**
     * @param rowid: rowid of candidate to remove.
     */
    public void sub (int rowid) {
        Query.SUB.execute (rowid);
    }

    /**
     * <b>Note</b>: rowid cannot be changed.
     * @param cand: New candidate data.
     */
    public void edit (Candidate cand) {
        Query.EDIT.execute (cand.getFirstName (), cand.getLastName (), cand.getPost (), cand.getStddiv (), cand.getVotecount (), cand.getRowid ());
    }

    /**
     * Deletes entire table and creates a fresh one.
     */
    public void cleanslate () {
        Query.DELETEDB.execute ();
        Query.CREATE.execute ();
    }

    /**
     * @param rowid: rowid of the candidate whose votecount should be increased.
     */
    public void vote (int rowid) {
        Query.VOTE.execute (rowid);
    }

    /**
     * @return data array for JTables.
     */
    public Candidate [] getCandidateArray () {
        Candidate [] temp = new Candidate [getCount ()];

        try {
            ResultSet rs = Query.SELECT.execute ();

            String first_name, last_name, post, stddiv;
            int rowid, votecount;
            int i = 0;

            while (rs.next ()) {
                first_name = rs.getString ("first_name");
                last_name = rs.getString ("last_name");
                post = rs.getString ("post");
                stddiv = rs.getString ("stddiv");
                votecount = rs.getInt ("votecount");
                rowid = rs.getInt ("rowid");

                temp [i] = new Candidate (first_name, last_name, post, stddiv, votecount, rowid);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace ();
        }

        return temp;
    }

    /** @return Specified specified candidate. */
    public Candidate getCandidate (int rowid) {
        try {
            ResultSet rs = Query.GET.execute (rowid);

            String first_name = rs.getString ("first_name");
            String last_name = rs.getString ("last_name");
            String post = rs.getString ("post");
            String stddiv = rs.getString ("stddiv");
            int votecount = rs.getInt ("votecount");

            return new Candidate (first_name, last_name, post, stddiv, votecount, rowid);
        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return null;
    }

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

    // -----

    // ----- QUERIES -----
    /*
        TODO: Rework this.
     */

    public enum Query {

        CREATE (
                "CREATE TABLE IF NOT EXISTS Candidates (" +
                "first_name varchar(30) NOT NULL, " +
                "last_name varchar(30) NOT NULL, " +
                "post varchar(100) NOT NULL, " +
                "stddiv varchar(10), " +
                "votecount int NOT NULL" +
                ");"
        ),
        SELECT (
                "SELECT *, rowid FROM Candidates;"
        ),
        ADD (
                "INSERT INTO Candidates VALUES " +
                "(?, ?, ?, ?, ?);",
                false
        ),
        SUB (
                "DELETE FROM Candidates WHERE rowid = ? ;",
                false
        ),
        EDIT (
                "UPDATE Candidates " +
                "SET first_name = ?, " +
                "last_name = ?, " +
                "post = ?, " +
                "stddiv = ?, " +
                "votecount = ? " +
                "WHERE rowid = ?" +
                ";",
                false
        ),
        DELETEDB (
                "DELETE FROM Candidates;",
                false
        ),
        VOTE (
                "UPDATE Candidates " +
                "SET votecount = votecount + 1 " +
                "WHERE rowid = ?" +
                ";",
                false
        ),
        COUNT (
                "SELECT COUNT(*) FROM Candidates;"
        ),
        GET (
                "SELECT * FROM Candidates WHERE rowid = ?;"
        );

        protected PreparedStatement prepstm;
        protected boolean results = true;
        protected String qry;

        Query (String qry) {
            this.qry = qry;
            try {
                prepstm = conn.prepareStatement (qry);
            } catch (SQLException e) {
                e.printStackTrace ();
            }
        }

        Query (String qry, boolean results) {
            this (qry);
            this.results = results;
        }

        public PreparedStatement getPrepstm () {
            return prepstm;
        }

        public ResultSet execute (Object ... params) {
            ResultSet rs = null;
            try {
                for (int i = 0; i < params.length; i++)
                    prepstm.setObject (i + 1, params [i]);
                if (results)
                    rs = prepstm.executeQuery ();
                else
                    prepstm.executeUpdate ();
            } catch (Exception e) {
                e.printStackTrace ();
            }
            return rs;
        }
    }

}

