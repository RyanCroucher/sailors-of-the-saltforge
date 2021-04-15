package main;

/**
 * Class to detail a transaction for adding to Ledger		
 * @author Steven Johnson sjo139
 *
 */
public class Transaction {

	private int quantity;
	
	private Item item;
	
	private Boolean isPurchase;
	
	private int price;
	
	private Island location;
	
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
		
		String purchaseString = new String("Bought: ");
		if (!isPurchase)
			purchaseString = ("Sold: ");
		
		String dayString = "days";
		if (((GameEnvironment.getHoursSinceStart() - timeOfTransaction)/24) == 1)
			dayString = "day";
		
		String transactionString;
		transactionString = purchaseString + quantity + " " + item.getName() + " for " + price + " " + Constants.NAME_CURRENCY 
				+  " each (Total: " +  (price * quantity) + ") at " + location.getIslandName() + ", " 
				+ ((GameEnvironment.getHoursSinceStart() - timeOfTransaction)/24) + dayString + " ago.";
					
		return transactionString;
		
	}
	
	
}
