/**
 * @(#)Tool.java
 * Written for the Programming Demonstration, which requests an example point-of-sale application for a tool rental store.
 * Tool is the base class for the application, and represents the objects that customers will rent. Each Tool consists of a:
 * Type -- what kind of tool it is.(ladder, chainsaw, or jack hammer in this implementation).
 * Brand -- the name of the manufacturing company which produced this tool.
 * Code -- A four character code, unique to each individual tool.
 * Daily Rate -- The amount the tool rental store charges per day to rent a particular tool.
 * Exempt Days -- Some tools are not charged on specific days during their rental period.
 *
 * @author Iain St. John
 * @version 1.00 2016/3/20
 */


public class Tool {
	//Tool Attributes
	private String code;
	private String brand;
	private String type;
	private double dailyCharge;
	private String daysExempt;
	
  /**
    *Default, empty Tool constructor method which initializes all String properties to null and the double property to 0.00.
    */	
    public Tool() {
    	code = null;
    	brand = null;
    	type = null;
    	dailyCharge = 0.00;
    	daysExempt = null;
    }
   /**
    *Tool constructor which takes a tool code.
    *
    *@param toolCode
    */ 
    public Tool(String toolCode) {
    	code = toolCode;
    	//Helper function fills in the rest of the info.
    	this.determineInfoFromCode(toolCode);
    }
    
    //"Setter" methods
   /**
    *Set this tool instance's code to the code String specified.
    *
    *@param toolCode -- the code you wish to set this tool's code to.
    */ 
    public void setCode(String toolCode) {
    	code = toolCode;
    }
    
    //"Getter" methods
    public String getType() {
    	return type;
    }
    
    public String getCode() {
    	return code;
    }
    
    public String getBrand() {
    	return brand;
    }
    
    public double getDailyCharge() {
    	return dailyCharge;
    }
    
    public String getDaysExempt() {
    	return daysExempt;
    }
    
   /**
    *Private helper method which determines this Tool's correct brand, type, dailyCharge, and daysExempt from a given tool code.
    *
    *@param toolCode  the tool code passed to this tool instance's constructor method.
    *@return void     determines the correct information for the tool based on the given code, and sets the corresponding properties accordingly.
    */ 
    private void determineInfoFromCode(String toolCode) {
    	if(toolCode.equals("LADW")) {
    		type = "Ladder";
    		brand = "Werner";
    		dailyCharge = 1.99;
    		daysExempt = null;
    		
    	}
    	else if(toolCode.equals("CHNS")) {
    		type = "Chainsaw";
    		brand = "Stihl";
    		dailyCharge = 1.49;
    		daysExempt = "weekends";
    	}
    	else if(toolCode.equals("JAKR")) {
    		type = "Jackhammer";
    		brand = "Rigid";
    		dailyCharge = 2.99;
    		daysExempt = "weekends and Holidays.";
    	}
    	else if(toolCode.equals("JAKD")) {
    		type = "Jackhammer";
    		brand = "DeWalt";
    		dailyCharge = 2.99;
    		daysExempt = "weekends and Holidays.";
    	}
    	else {
    		System.out.println("ERROR! Tool code not recognized.");
    		return;
    	}
    }
    
    //Returns a string representation of a Tool for output
    public String toString() {
    	String feeStr = String.format("$%.3d");
    	String s = (brand + " " + type + "(" + code + ") Rental fee per day: " + feeStr);
    	if(daysExempt == null) {
    		s += ".";
    	}
    	else {
    		s += (", except on " + daysExempt +".");
    	}
    	return s;
    } 
}