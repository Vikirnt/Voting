package core;

/**
 * Candidate datatype for binding data together.
 *
 * @version 1.0
 * @author vikirnt
 * @since October 2017
 */
public class Candidate {

    /**
     * String parameters for Candidate.
     */
    private String
            first_name,
            last_name,
            post,
            stddiv;
    /**
     * Integer parameters for Candidate.
     */
    private int
            votecount,
            rowid;

    /**
     * Constants for get ()
     */
    public static final int
            ROWID       = 0,
            FIRST_NAME  = 1,
            LAST_NAME   = 2,
            POST        = 3,
            STDDIV      = 4,
            VOTECOUNT   = 5;

    /**
     * @param first_name: First name of candidate.
     * @param last_name: Second name of candidate.
     * @param post: Post for which candidate is applying.
     * @param stddiv: Standard/ Class and Division of candidate.
     * Default votecount is 0.
     */
    public Candidate (String first_name, String last_name, String post, String stddiv) {
        this (first_name, last_name, post, stddiv, 0);
    }

    /**
     * @param first_name: First name of candidate.
     * @param last_name: Second name of candidate.
     * @param post: Post for which candidate is applying.
     * @param stddiv: Standard/ Class and Division of candidate.
     * @param votecount: Starting votecount. Default is 0.
     * @param rowid: rowid assigned to candidate in sql table.
     */
    public Candidate (String first_name, String last_name, String post, String stddiv, int votecount, int rowid) {
        this (first_name, last_name, post, stddiv, votecount);
        this.rowid = rowid;
    }

    /**
     * @param first_name: First name of candidate.
     * @param last_name: Second name of candidate.
     * @param post: Post for which candidate is applying.
     * @param stddiv: Standard/ Class and Division of candidate.
     * @param votecount: Starting votecount. Default is 0.
     */
    public Candidate (String first_name, String last_name, String post, String stddiv, int votecount) {
        this.first_name = first_name;
        this.last_name  = last_name;
        this.post       = post;
        this.stddiv     = stddiv;
        this.votecount  = votecount;
    }

    /**
     * @return Any of the fields.
     */
    public Object get (int key) {
        switch (key) {
            case ROWID:
                return rowid;
            case FIRST_NAME:
                return first_name;
            case LAST_NAME:
                return last_name;
            case POST:
                return post;
            case STDDIV:
                return stddiv;
            case VOTECOUNT:
                return votecount;
            default:
                return -1;
        }
    }

    /**
     * @return Info of candidate in one line.
     */
    @Override
    public String toString () {
        return String.format
        (
                "Candidate %s: %s %s, post: %s, stddiv: %s, votecount: %s",
                rowid, first_name, last_name, post, stddiv, votecount
        );
    }

    /**
     * @return Vote count of candidate.
     */
    public int    getVotecount  () {
        return votecount;
    }
    /**
     * @return First name of candidate.
     */
    public String getFirstName  () {
        return first_name;
    }
    /**
     * @return Last name of candidate.
     */
    public String getLastName   () {
        return last_name;
    }
    /**
     * @return Post for which candidate is applying.
     */
    public String getPost       () {
        return post;
    }
    /**
     * @return Standard/ Division of candidate.
     */
    public String getStddiv     () {
        return stddiv;
    }
    /**
     * @return row id of candidate in SQL db.
     */
    public int    getRowid      () {
        return rowid;
    }
}
