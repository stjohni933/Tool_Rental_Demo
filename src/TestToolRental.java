/**
 * @(#)TestToolRental.java
 * Creates test cases to check the accuracy of the tool, checkout, and rentalagreement implementations.
 *
 * @author Iain St. John
 * @version 1.00 2016/3/24
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.lang.Exception;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

public class TestToolRental {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
    @Test
    public void test1() throws Exception{
    	System.out.print("Running Test 1...\n");
    	String args[] = {"LADW","09/03/15","5","10"};
    	String testResults[] = {"09/08/15","$1.99","5","$9.95","10%","$1.00","$8.95"};
    	RentalAgreement result;
		Checkout test = new Checkout();
		result = test.runPOS(args);
		System.out.print("Checking due date: ");
		assertEquals(testResults[0],result.getCorrect(0));
		System.out.print("passed.\n Checking tool daily rate: ");
		assertEquals(testResults[1],result.getCorrect(1));
		System.out.print("passed.\n Checking charge days: ");
		assertEquals(testResults[2],result.getCorrect(2));
		System.out.print("passed.\n Checking pre-discount charge: ");
		assertEquals(testResults[3],result.getCorrect(3));
		System.out.print("passed.\n Checking discount percentage: ");
		assertEquals(testResults[4],result.getCorrect(4));
		System.out.print("passed.\n Checking discount amount: ");
		assertEquals(testResults[5],result.getCorrect(5));
		System.out.print("passed.\n Checking final charge: ");
		assertEquals(testResults[6],result.getCorrect(6));
		System.out.print("passed.\n Test 1 passed.\n\n");	
    }
    
    @Test
    public void test2() throws Exception {
    	System.out.print("Running Test 2...\n");
    	RentalAgreement result;
    	Checkout test = new Checkout();
    	String testResults[] = {"07/07/15","$1.49","3","$4.47","25%","$1.12","$3.35"};
    	String args[] = {"CHNS","07/02/15","5","25"};
    	result = test.runPOS(args);
    	System.out.print("Checking due date: ");
		assertEquals(testResults[0],result.getCorrect(0));
		System.out.print("passed.\n Checking tool daily rate: ");
		assertEquals(testResults[1],result.getCorrect(1));
		System.out.print("passed.\n Checking charge days: ");
		assertEquals(testResults[2],result.getCorrect(2));
		System.out.print("passed.\n Checking pre-discount charge: ");
		assertEquals(testResults[3],result.getCorrect(3));
		System.out.print("passed.\n Checking discount percentage: ");
		assertEquals(testResults[4],result.getCorrect(4));
		System.out.print("passed.\n Checking discount amount: ");
		assertEquals(testResults[5],result.getCorrect(5));
		System.out.print("passed.\n Checking final charge: ");
		assertEquals(testResults[6],result.getCorrect(6));
		System.out.print("passed.\n Test 2 passed.\n\n");
    }
    
    @Test
    public void test3() throws Exception {
    	System.out.print("Running Test 3...\n");
    	RentalAgreement result;
    	Checkout test = new Checkout();
    	String args[] = {"JAKD","09/03/15","6","0"};
    	String testResults[] = {"09/09/15","$2.99","3","$8.97","0%","$0.00","$8.97"};
    	result = test.runPOS(args);
    	System.out.print("Checking due date: ");
		assertEquals(testResults[0],result.getCorrect(0));
		System.out.print("passed.\n Checking tool daily rate: ");
		assertEquals(testResults[1],result.getCorrect(1));
		System.out.print("passed.\n Checking charge days: ");
		assertEquals(testResults[2],result.getCorrect(2));
		System.out.print("passed.\n Checking pre-discount charge: ");
		assertEquals(testResults[3],result.getCorrect(3));
		System.out.print("passed.\n Checking discount percentage: ");
		assertEquals(testResults[4],result.getCorrect(4));
		System.out.print("passed.\n Checking discount amount: ");
		assertEquals(testResults[5],result.getCorrect(5));
		System.out.print("passed.\n Checking final charge: ");
		assertEquals(testResults[6],result.getCorrect(6));
		System.out.print("passed.\n Test 3 passed.\n\n");
    		
    }
    
    @Test
    public void test4() throws Exception {
    	System.out.print("Running Test 4...\n");
    	RentalAgreement result;
    	Checkout test = new Checkout();
    	String args[] = {"JAKR","07/02/20","4","50"};
    	String testResults[] = {"07/06/20","$2.99","1","$2.99","50%","$1.50","$1.49"};
    	result = test.runPOS(args);
    	System.out.print("Checking due date: ");
		assertEquals(testResults[0],result.getCorrect(0));
		System.out.print("passed.\n Checking tool daily rate: ");
		assertEquals(testResults[1],result.getCorrect(1));
		System.out.print("passed.\n Checking charge days: ");
		assertEquals(testResults[2],result.getCorrect(2));
		System.out.print("passed.\n Checking pre-discount charge: ");
		assertEquals(testResults[3],result.getCorrect(3));
		System.out.print("passed.\n Checking discount percentage: ");
		assertEquals(testResults[4],result.getCorrect(4));
		System.out.print("passed.\n Checking discount amount: ");
		assertEquals(testResults[5],result.getCorrect(5));
		System.out.print("passed.\n Checking final charge: ");
		assertEquals(testResults[6],result.getCorrect(6));
		System.out.print("passed.\n Test 4 passed.\n\n");
    }
    
    @Test
    public void test5() throws Exception {
    	System.out.print("Running Test 5 (Exception Test)...\n");
    	RentalAgreement result;
    	Checkout test = new Checkout();
    	String failArgs[] = {"JAKR","09/03/15","5","101"};
    	System.out.print("Expecting Checkout Discount Exception:\n ");
    	exception.expect(CheckoutDiscountException.class);
    	test.runPOS(failArgs);	
    }
    
    
}