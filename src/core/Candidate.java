package core;

public class Candidate {

    private String first_name, last_name, post, stddiv;
    private int votecount;
    private int rowid;

    public final int FIRST_NAME = 0, LAST_NAME = 1, POST = 2, STDDIV = 3, VOTECOUNT = 4, ROWID = 5;

    public Candidate (String first_name, String last_name, String post, String stddiv, int rowid) {
        this (first_name, last_name, post, stddiv, rowid, 0);
    }

    public Candidate (String first_name, String last_name, String post, String stddiv, int rowid, int votecount) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.post = post;
        this.stddiv = stddiv;
        this.rowid = rowid;
        this.votecount = votecount;
    }

    public Object get (int key) {
        switch (key) {
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
                return "This is an error!";
        }
    }

    public int getRowid () {
        return rowid;
    }

    public int getVotecount () {
        return votecount;
    }

    public String getFirst_name () {
        return first_name;
    }

    public String getLast_name () {
        return last_name;
    }

    public String getPost () {
        return post;
    }

    public String getStddiv () {
        return stddiv;
    }
}
