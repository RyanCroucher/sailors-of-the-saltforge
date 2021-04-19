package main;

/**
 * Class to detail a transaction for adding to Ledger		
 * @author Steven Johnson sjo139
 *
 */
public class Transaction {

	/**
	 * Amount of an item transacted
	 */
	private int quantity;
	
	/**
	 * Item being transacted
	 */
	private Item item;
	
	/**
	 * Boolean to see if item was bought or sold
	 */
	private Boolean isPurchase;
	
	/**
	 * Price of the item transacted
	 */
	private int price;
	
	/**
	 * Island that the transaction happened
	 */
	private Island location;
	
	/**
	 * Time when the transaction happened
	 */
	private int timeOfTransaction;
	
	/**
	 * Constructs a Transaction object
	 * @param quantity Number of items bought/sold in a transaction
	 * @param item Item bought/sold in a transaction
	 * @param isPurchase Was the item bought or sold
	 * @param price How much was the item that was bought/sold
	 * @param location Where was the item bought/sold
	 * @param timeOfTransaction When was the item bought/sold
	 */
	public Transaction(int quantity, Item item, Boolean isPurchase, int price, Island location, int timeOfTransaction) {
		
		this.quantity = quantity;
		this.item = item;
		this.isPurchase = isPurchase;
		this.price = price;
		this.location = location;
		this.timeOfTransaction = timeOfTransaction;
		
	}

	/**
	 * Gets Quantity
	 * @return quantity of item
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Gets Item
	 * @return The Item transacted
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Was the item bought or sold
	 * @return True or False
	 */
	public Boolean getIsPurchase() {
		return isPurchase;
	}

	/**
	 * Gets price of the item
	 * @return price of the item transacted
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Gets location of transaction
	 * @return Location of transaction
	 */
	public Island getLocation() {
		return location;
	}

	/**
	 * Get time of transaction
	 * @return How many days ago an item was transacted
	 */
	public int getTimeOfTransaction() {
		return timeOfTransaction;
	}
	
	/**
	 * Creates a string detailing the transaction
	 * @return A string detailing the transaction
	 */
	public String toString() {
		
		String purchaseString = new String("Bought ");
		if (!isPurchase)
			purchaseString = ("Sold ");
		
		String dayString = "days";
		if (((GameEnvironment.getHoursSinceStart() - timeOfTransaction)/24) == 1)
			dayString = "day";
		
		String transactionString;
		transactionString = purchaseString + quantity + " " + item.getName() + " for " + price + " " + Constants.NAME_CURRENCY 
				+  " each (Total: " +  (price * quantity) + ") at " + location.getIslandName() + ", " 
				+ ((GameEnvironment.getHoursSinceStart() - timeOfTransaction)/24) + " " + dayString + " ago.";
					
		return transactionString;
		
	}
	
	
}
