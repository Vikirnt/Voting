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

    /** Temporary hard-coded password for school use only. */
    public static final String password = "cs15";

    // ----- PREPARED STATEMENTS -----

    /**
     * Prepared SQL statements.
     */
    private PreparedStatement
            create_stm,
            select_stm,
            add_stm,
            sub_stm,
            edit_stm,
            cleanslate_stm,
            vote_stm,
            count_stm,
            get_stm;

    /** Constructor gets the connection and sets ResultSet pointer. */
    public DatabaseFile (String path) {
        try {
            /* JDBC connection object. */
            Connection conn = DriverManager.getConnection ("jdbc:sqlite:" + path + "/Candidates.db");

            create_stm = conn.prepareStatement    (Query.CREATE);
            create_stm.executeUpdate ();

            select_stm = conn.prepareStatement    (Query.SELECT);
            add_stm = conn.prepareStatement       (Query.ADD);
            sub_stm = conn.prepareStatement       (Query.SUB);
            edit_stm = conn.prepareStatement      (Query.EDIT);
            cleanslate_stm = conn.prepareStatement(Query.CLEANSLATE);
            vote_stm = conn.prepareStatement      (Query.VOTE);
            count_stm = conn.prepareStatement     (Query.COUNT);
            get_stm = conn.prepareStatement       (Query.GET);

        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    // ----- OPERATIONS -----

    /**
     * @param cand: Candidate to add to db.
     */
    public void add (Candidate cand) {
        try {
            add_stm.setString (1, cand.getFirstName ());
            add_stm.setString (2, cand.getLastName ());
            add_stm.setString (3, cand.getPost ());
            add_stm.setString (4, cand.getStddiv ());
            add_stm.setInt    (5, cand.getVotecount ());
            add_stm.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    /**
     * @param rowid: rowid of candidate to remove.
     */
    public void sub (int rowid) {
        try {
            sub_stm.setInt (1, rowid);
            sub_stm.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    /**
     * <b>Note</b>: rowid cannot be changed.
     * @param cand: New candidate data.
     */
    public void edit (Candidate cand) {
        try {
            edit_stm.setString (1, cand.getFirstName ());
            edit_stm.setString (2, cand.getLastName ());
            edit_stm.setString (3, cand.getPost ());
            edit_stm.setString (4, cand.getStddiv ());
            edit_stm.setInt    (5, cand.getVotecount ());
            edit_stm.setInt    (6, cand.getRowid ());
            edit_stm.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    /**
     * Deletes entire table and creates a fresh one.
     */
    public void cleanslate () {
        try {
            cleanslate_stm.executeUpdate ();
            create_stm.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    /**
     * @param rowid: rowid of the candidate whose votecount should be increased.
     */
    public void vote (int rowid) {
        try {
            vote_stm.setInt (1, rowid);
            vote_stm.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    /**
     * @return data array for JTables.
     */
    public Candidate [] getCandidatesArray () {
        Candidate [] temp = new Candidate [getCount ()];

        try (ResultSet rs = select_stm.executeQuery ()) {

            String first_name, last_name, post, stddiv;
            int rowid, votecount;
            int i = 0;

            while (rs.next ()) {
                first_name = rs.getString ("first_name");
                last_name = rs.getString  ("last_name");
                post = rs.getString       ("post");
                stddiv = rs.getString     ("stddiv");
                votecount = rs.getInt     ("votecount");
                rowid = rs.getInt         ("rowid");

                temp [i] = new Candidate (first_name, last_name, post, stddiv, votecount, rowid);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace ();
        }

        return temp;
    }

    /**
     * @return Specified specified candidate.
     */
    public Candidate getCandidate (int rowid) {
        try {
            get_stm.setInt (1, rowid);
            ResultSet rs = get_stm.executeQuery ();

            String first_name = rs.getString ("first_name");
            String last_name = rs.getString  ("last_name");
            String post = rs.getString       ("post");
            String stddiv = rs.getString     ("stddiv");
            int votecount = rs.getInt        ("votecount");

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

        try (ResultSet rs = count_stm.executeQuery ()){
            cnt = rs.getInt (1);
        } catch (SQLException e) {
            e.printStackTrace ();
        }

        return cnt;
    }

}

