/**
 * @(#)TestRunner.java
 * Runs all included junit tests.
 *
 * @author Iain St. John
 * @version 1.00 2016/3/24
 */
 
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(TestToolRental.class);
      for (Failure failure : result.getFailures()) {
      	 System.out.print("Test failed: ");
         System.out.println(failure.toString());
      }
      System.out.println("All Tests passed.");
   }
}