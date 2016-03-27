/**
 * @(#)Checkout.java
 * The main class for ToolRental, which takes a tool code, the date of Checkout, number of days to rent said tool, and a discount percentage.  
 * It then uses this information to generate instances of Tool and RentalAgreement. All required parameters may be passed as parameters to runPOS() 
 *
 * Arguments passed on the command line should be passed in the following order: toolcode, days rented, discount.
 *
 * This class is responsible for accepting and validating user input for variables, and then using those variables to generate and return the
 * applicable RentalAgreement.
 *
 * @author Iain St. John
 * @version 1.00 2016/3/20
 */
 
import java.lang.Exception;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;



public class Checkout {
	//Static variables for a Tool Checkout
	private String toolCode; //A Tool's unique identifier 
	private int rentalDayCount; //The number of days a customer rents the Tool.
	private int discount; //The discount percentage to apply to the checkout of the specified tool.
	private Date checkoutDate; //The date on which this Checkout is performed
	
	static final String KNOWN_TOOL_CODES = "LADW CHNS JAKR JAKD";
	
	//default, empty constructor
	public Checkout() {
	}
	
	//Define RentalDayCountException locally. CheckoutDiscountException was too, initially, but was moved to it's own separate file, since it needed to be defined within the test class.
	static class RentalDayCountException extends Exception {
		public RentalDayCountException(String message) {
			super(message);
		}
	}
	
    /**
     * @param args the command line arguments. Possible legal arguments:
     * @param args[0]  Tool code of the desired tool.
     * @param args[1]  The checkout date formated mm/dd/yy
     * @param args[2]  Number of days to rent the tool.
     * @param args[3]  The discount percentage to apply to the transaction.
     * 
     */
    public RentalAgreement runPOS(String[] args) throws CheckoutDiscountException {
    	//give initial values to checkout variables
	   	toolCode = null;
	   	String dateStr = null;
    	rentalDayCount = -1;
    	discount = -1;
    	
		//Begin parsing command line arguments, if any are present. Only expecting one, the tool code.
		if(args.length == 4) {
			if(KNOWN_TOOL_CODES.contains(args[0])) {
				toolCode = args[0];
			}
			else {
				System.err.println("Argument is not a tool code! Ignoring.");
			}
			
			//Parse rental day count
			try  {
				rentalDayCount = Integer.parseInt(args[2]);
				//Ensure that the number entered is greater than 1
				try {
					checkRentalDayCount();
				}
				catch(RentalDayCountException rdce) {
					System.err.println("\n" + rdce.getMessage());
					rentalDayCount = -1;
				}
			}
			catch(NumberFormatException e) {
				System.err.println(e.getMessage());
			}
	
			//Parse discount percentage	
			discount = Integer.parseInt(args[3]);
			checkDiscount();
			
			
			//Parse the date string
			dateStr = args[1];
			String parts[] = dateStr.split("/");
			int mon = Integer.parseInt(parts[0]);
			int day = Integer.parseInt(parts[1]);
			int yr = Integer.parseInt(parts[2]);
			Calendar c = Calendar.getInstance();
			mon--; //Decrement month, calendars store them as 0 - 11
			yr+=2000; //year is 20##, not just ##.
			c.set(Calendar.DATE,day);
			c.set(Calendar.MONTH,mon);
			c.set(Calendar.YEAR,yr);
			checkoutDate = c.getTime();
		}
		else {
			Scanner in = new Scanner(System.in);
			String input = "";
			//if there was not a valid tool code on the command line, it will still be null
			while(toolCode == null) {
				System.out.print("Please enter tool code now>>>");
				input = in.next();
				if(KNOWN_TOOL_CODES.contains(input.trim())){
					toolCode = input;
				}
				else {
					System.out.println("\nUnrecognized tool code! Please try again.");
				}
			}
			
			//Reaching this point means that a valid tool code has been acquired. Next, get rentalDayCount
			while(rentalDayCount == -1) {
				System.out.print("For how many days do you want to rent " + toolCode +"? >>>");
				input = in.next();
				//Ensure that the user entered an integer
				try  {
					rentalDayCount = Integer.parseInt(input.trim());
					//Ensure that the number entered is greater than 1
					try {
						checkRentalDayCount();
					}
					catch(RentalDayCountException rdce) {
						System.out.println("\n" + rdce.getMessage());
						rentalDayCount = -1;
						continue;
					}
				}
				catch(NumberFormatException e) {
					System.out.println(e.getMessage());
					continue;
				}
			}
		
			//Finally, acquire a discount
			while(discount == -1) {
				System.out.print("Enter the discount to apply to this transaction as a raw integer between 0 and 100. >>>");
				input = in.next();
				//Ensure an integer was entered
				try {
					discount = Integer.parseInt(input.trim());
					try {
						checkDiscount();
					}
					catch(CheckoutDiscountException cde) {
						System.out.println("\n" + cde.getMessage());
						discount = -1;
						continue;
					}
				}
				catch(NumberFormatException ex) {
					System.out.println(ex.getMessage());
					continue;
				}
			}
			
			while(dateStr == null) {
				System.out.println("Enter the date of the Checkout as mm/dd/yy >>>");
				dateStr = in.next();
				//Parse the date string
				int mon, day, yr;
				try {
					String parts[] = dateStr.split("/");
					mon = Integer.parseInt(parts[0]);
					day = Integer.parseInt(parts[1]);
					yr = Integer.parseInt(parts[2]);
				}
				catch(Exception exc) {
					System.out.println(exc.getMessage());
					continue;
				}
				
				Calendar c = Calendar.getInstance();
				mon--; //months from 0 to 11
				yr += 2000;
				c.set(yr,mon,day);
				checkoutDate = c.getTime();
			}
			in.close();
		}//End of the else code block
		return new RentalAgreement(toolCode, checkoutDate, rentalDayCount, discount);
    }
    
    /* The next two private helper methods check the logical contraints on rentalDayCount and discount. A customer cannot rent any given
     * tool for any less than 1 day, and a discount cannot be lower than 0 percent or higher than 100 percent. If the variable of 
     * the respective function is valid, it will simply check the truth value of the if statement, skip over the if block, and exit the 
     * function unceremoniously. If the variable is invalid, however, a correct instance of the local Exceptions defined above will be thrown
     * and a error message will be printed. Then, the variable will be restored to its initial value set at the beginning of the main function
     * (-1) so that the while loops for validation keep executing. */
    
    private void checkRentalDayCount() throws RentalDayCountException {
    	if(rentalDayCount < 1) {
    		throw new RentalDayCountException("Invalid rental day count. Unable to rent a tool for any less than 1 day.");
    	}
    }
    
    private void checkDiscount() throws CheckoutDiscountException {
    	if(discount < 0 || discount > 100) {
    		throw new CheckoutDiscountException("Percentages are only in the range of 0 to 100 (0 to 1.0)" +
    				"The minimum possible discount is 0 (full price), and the maximum is 100 (free)");
    	}
    }
}
