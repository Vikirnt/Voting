import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Basic JUnit tests runner.
 *
 * @version 1.0
 * @author vikirnt
 * @since October 2017
 */
class TestRunner {

    public static void main (String [] args) {
        Result result = JUnitCore.runClasses (DatabaseFileTest.class);

        for (Failure failure : result.getFailures ())
            System.out.println (failure.toString ());

        System.out.println ("Successful = " + result.wasSuccessful ());
    }

}
