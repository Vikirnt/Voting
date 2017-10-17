import core.Candidate;
import core.DatabaseFile;
import org.junit.Ignore;

/**
 * JUnit tests for DatabaseFile.java
 *
 * @version 1.0
 * @author vikirnt
 * @since October 2017
 */
public class DatabaseFileTest {

    /**
     * Database object.
     */
    private DatabaseFile db = new DatabaseFile ("/Users/vikirnt/Programming");

    @Ignore
    public void getCount () {
        System.out.println (db.getCount ());
    }

    @Ignore
    public void get () {
        System.out.println (db.getCandidate (2).toString ());
    }

    @Ignore
    public void vote () {
        db.vote (1);
    }

    @Ignore
    public void add () {
        Candidate x = new Candidate ("Vikrant", "G", "PM", "10B", 6);
        db.add (x);
    }

    @Ignore
    public void getTable () {
        Candidate [] x = db.getCandidatesArray ();
        for (Candidate i : x)
            System.out.println (i.toString ());
    }

    @Ignore
    public void edit () {
        db.edit (new Candidate ("Viki", "Gajria", "KKK", "5C", 0, 2));
    }

}