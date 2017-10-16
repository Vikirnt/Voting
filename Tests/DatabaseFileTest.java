import core.DatabaseFile;
import org.junit.Test;

public class DatabaseFileTest {

    DatabaseFile db = new DatabaseFile ("/Users/vikirnt/Documents/Programming");

    @Test
    public void getCount () {
        System.out.println (db.getCount ());
    }

    @Test
    public void get () {
        System.out.println (db.get ("first_name", 1));
    }

}