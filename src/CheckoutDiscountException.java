/**
 * @(#)TestRunner.java
 * Custom exception class for when Checkout receives a discount greater than 100, or less than 0.
 *
 * @author Iain St. John
 * @version 1.00 2016/3/24
 */
 
import java.lang.Exception; 
 
public class CheckoutDiscountException extends Exception {
	public CheckoutDiscountException(String message) {
		super(message);
	}
}
