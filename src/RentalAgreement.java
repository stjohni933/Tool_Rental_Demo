/**
 * @(#)RentalAgreement.java
 * This class generates and holds the resulting "contract" from the applicable information in Checkout.
 *
 * @author Iain St. John
 * @version 1.00 2016/3/20
 */

import java.util.Date;
import java.util.Calendar;
import java.lang.Math;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RentalAgreement {
	//Private variables for a RentalAgreement instance
	//The following variables are passed from Checkout, and are used to derive other data.
	private String toolCode;
	private int daysRented;
	private Date checkoutDate;
	private int discountPerc;
	
	//The rest of the variables are calculated or otherwise derived
	//Tool variables
	private Tool rentedTool;
	private String toolBrand;
	private String toolType;
	private double dailyToolCharge;
	
	//Date/time variables
	private Date dueDate;
	private int chargeableDays;
	
	//Currency variables
	private double baseCharge;
	private double discountAmnt;
	private double finalCharge;
    
   /**RentalAgreement constructor that takes the 4 necessary variables from Checkout, and calls all this instance's
    * private helper methods to generate the rest of the information contained within.
    *
    *@param code -- the desired tool's code.
    *@param date -- a Date object containing the date of checkout.
    *@param days -- the number of days for which this tool will be rented.
    *@param disc -- the discount percentage to apply to the charge of this rental.
    */
    public RentalAgreement(String code, Date date, int days, int disc) {
    	//Save the arguments passed into this constructor
    	toolCode = code;
    	//Use checkout date and the number of days for the rental to determine due date
    	checkoutDate = (Date) date.clone();
    	daysRented = days;
    	discountPerc = disc;
    	
    	//Call all the helper methods below to fill in the rest of the data of this instance
    	setToolVars();
    	determineDueDate();
    	determineCharge();
    }
    
    //Returns a String representation of a RentalAgreement
    public String toString() {
    	finalCharge = Math.floor(finalCharge * 100)/100;
    	String toolStr = String.format("%s %s (%s) rented on %tD.",toolBrand,toolType,toolCode,checkoutDate);
    	String dateStr = String.format("Rental Period: %tD -- %tD (%d)",checkoutDate,dueDate,daysRented);
    	String moneyStr = String.format("Initial charge: $%.2f. Discount Applied: %d%% (Amount: $%.2f)",baseCharge,discountPerc,discountAmnt);
    	String feeStr = String.format("Final amount due at return: $%.2f",finalCharge);
    	return (String.format("%s%n%s%n%s%n%s",toolStr,dateStr,moneyStr,feeStr));
    }
    
    /*Private helper function that creates a Tool from the toolCode given by Checkout, and gets the rest of the resulting information
     *from the Tool Object created.
     */
    private void setToolVars() {
    	rentedTool = new Tool(toolCode);
    	toolBrand = rentedTool.getBrand();
    	toolType = rentedTool.getType();
    	dailyToolCharge = rentedTool.getDailyCharge();
    }
    
   /**Private helper function that derives when the rented tool is due using the checkoutDate and the number of days it's rented.
    */ 
    private void determineDueDate() {
    	Calendar c = Calendar.getInstance();
    	c.setTime(checkoutDate);
    	//add the number of days rented
    	c.add(Calendar.DATE, daysRented);
    	//copy the resulting date into due date
    	dueDate = c.getTime();
    }
    
  /**This helper method will perform the brunt of the work in this class. It first determines the number of chargeable days in the range
    *of the day after checkout to the due date (inclusive). Then, it calculates the baseCharge using the number of days charged and the
    *daily rate of the rented tool. Next, it calculates the dollar amount of the applied discount, and subtracts that from the base charge
    *to yield the final charge of this rental.
    */  
    private void determineCharge() {
    	Calendar c = Calendar.getInstance(); //working calendar, beginning at checkout date
    	Calendar end = Calendar.getInstance(); //end condition calendar at the due date
    	c.setTime(checkoutDate);
    	end.setTime(dueDate);
    	end.add(Calendar.DATE,1); //extend by one, so last rental day isn't missed
    	String excluded = rentedTool.getDaysExempt();
    	//What days are exempt from this tool's rental charge?
    	//If the excluded days String is still null, the tool has no days exempt from charge, so every day is chargeable
    	if(excluded == null) {
    		chargeableDays = daysRented;
    	}
    	//If it isn't null, then weekends are definitely exempt, and Holdidays might be. Find all days without charge
    	else {
    		chargeableDays = daysRented;
    		c.add(Calendar.DATE,1); //skip past rental day, as it is never charged
    		int dow = c.get(Calendar.DAY_OF_WEEK);
    		//Loop through the rental period range until we reach the checkout date
    		while(!c.equals(end)) {
    			//Get the integer values for the day in the month and week, and the month for the current calendar day
    			int day = c.get(Calendar.DATE);
    			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    			int month = c.get(Calendar.MONTH);
    			int year = c.get(Calendar.YEAR);
    		   /*Two Holidays in the context of this problem are Independence Day and Labor Day
    			*Independence Day is July 4th. Labor Day is the first monday in September.
    			*/
    			if(excluded.contains("Holidays")) {
    				if(dayOfWeek == 2 && month == 8) {
    					 if(this.isFirstWeekday(day, dayOfWeek, month, year)){
    					 	chargeableDays--;
    					 }
    				}
    				//Independence falls on a weekday
    				if(day == 4 && month == 6 && (dayOfWeek != 1 && dayOfWeek != 7)) {
    					chargeableDays--;
    				}
    				//It fell on a Sunday
    				if(dayOfWeek == 2 && ((day - 1) == 4)) {
    					chargeableDays--;
    				}
    				//It fell on a Saturday
    				if(dayOfWeek == 6 && ((day + 1) == 4)) {
    					chargeableDays--;
    				}
    			}
    			//Weekends are excluded either way.
    			if(dayOfWeek == 7) {
    					chargeableDays--;
    			}
    			if(dayOfWeek == 1) {
    					chargeableDays--;
    			}
    			//go to the next day for the next iteration
    			c.add(Calendar.DATE,1);
    		}
    	}
    	//chargeableDays will now have a value that acurately reflects the days in this rental period where a charge is necessary
    	
    	//Calculate the baseCharge
    	baseCharge = chargeableDays * dailyToolCharge;
    	
    	//Calculate discount amount
    	discountAmnt = ((((double) discountPerc/100)) * baseCharge);
    	//Now, the final charge. Ensure this is rounded down
    	finalCharge = baseCharge - discountAmnt;

    }
   /**Private helper function that determines whether a given date is the first occurence of a weekday within the specified month.
    *
    *@param dayOfMonth  The date.
    *@param dayOfWeek   The day of the week of dayOfMonth.
    *@param moth    The month we're checking within.
    *@param year    The year we're checking in.
    *@return boolean   True if there are no given weekdays before, false if there are not.
    */ 
    private boolean isFirstWeekday(int dayOfMonth, int dayOfWeek, int month, int year) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month, dayOfMonth);
    	//go back one week, to the last time we were at this weekday
    	cal.add(Calendar.DATE,-7);
    	int m = cal.get(Calendar.MONTH);
    	//if just one instance of this weekday was found in this month, then dayOfMonth isn't the first
    	if(m == month) {
    		return false;
    	}
    	//If we went back to the last same weekday, and it's a different month, then dayOfMonth must be the first in the month
    	else {
    		return true;
    	}
    }
    
   /**Added to support iterative based calling of the below get methods. The index of the expected variable value in the testResults array is passed here, and
    * the correct "get...()" method is called and returned. 
    * @param index  The index of the desired variable to return within an array
    */
    public String getCorrect(int index) {
    	switch(index) {
    		case 0:
    			return this.getDueDateString();
    		case 1:
    			return this.getDailyRateString();
    		case 2:
    			return this.getChargeDaysString();
    		case 3:
    			return this.getBaseChargeString();
    		case 4:
    			return this.getDiscountString();
    		case 5:
    			return this.getDiscountAmntString();
    		case 6:
    			return this.getFinalChargeString();
    		
    	}	
    	return null;
    }
    
    //Below are all the public "getter" methods for a Rental Agreement's variables. Used in JUnit testing
    public String getChargeDaysString() {
    	return String.format("%d",chargeableDays); //I'm returning a String here to help facilitate the testing loop in validTest() method in the TestToolRental class.
    }
    
    public String getDueDateString() {
    	DateFormat simple = new SimpleDateFormat("MM/dd/yy");
    	return simple.format(dueDate).toString();
    }
    
    public String getDailyRateString() {
    	return String.format("$%.2f",dailyToolCharge);
    }
    
    public String getBaseChargeString() {
    	return String.format("$%.2f",baseCharge);
    }
    
    public String getDiscountString() {
    	return String.format("%d%%",discountPerc);
    }
    
    public String getDiscountAmntString() {
    	return String.format("$%.2f",discountAmnt);
    }
    
    public String getFinalChargeString() {
    	finalCharge = Math.floor(finalCharge * 100) / 100;
    	return String.format("$%.2f",finalCharge);
    }
    
    
    
   	
    
}