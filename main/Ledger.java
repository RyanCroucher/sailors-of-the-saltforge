package main;

import java.util.ArrayList;

/**
 * Keeps track of all historical transactions and offers some helper methods
 * @author Ryan Croucher rcr69
 *
 */
public class Ledger {
	
	/**
	 * A list of all historical transactions
	 */
	private static ArrayList<Transaction> transactions;
	
	/**
	 * Adds a new transaction to the ledger
	 * @param quantity number of items bought/sold. Must be positive
	 * @param item the item bought/sold
	 * @param isPurchase whether it's a purchase or a sale
	 * @param price the price paid/gained for each unit of the item. Must be non negative
	 * @param location the Island where the item was bought/sold
	 * @param timeOfTransaction the hour of the transaction since game started, must not exceed the current game duration
	 * @throws IllegalArgumentException
	 */
	public static void addTransaction(int quantity, Item item, Boolean isPurchase, int price, Island location, int timeOfTransaction) throws IllegalArgumentException {
		
		if (!validTransactionParameters(quantity, price, timeOfTransaction))
			throw new IllegalArgumentException("Transaction parameters are invalid");
		
		if (transactions == null)
			transactions = new ArrayList<Transaction>();
		
		transactions.add(new Transaction(quantity, item, isPurchase, price, location, timeOfTransaction));
	}
	
	/**
	 * Checks whether the parameters are within a valid range
	 * @param quantity number of items bought/sold. Must be positive
	 * @param price price the price paid/gained for each unit of the item. Must be non negative
	 * @param timeOfTransaction the hour of the transaction since game started, must not exceed the current game duration
	 * @return true if the parameters are all valid, else false
	 */
	private static boolean validTransactionParameters(int quantity, int price, int timeOfTransaction) {
		if (quantity < 1)
			return false;
		else if (price < 0)
			return false;
		else if (timeOfTransaction > GameEnvironment.getHoursSinceStart())
			return false;
		
		return true;
	}
	
	/**
	 * 
	 * @return all historical transactions
	 */
	public static ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	/**
	 * 
	 * @param numTransactions the maximum number of transactions to retrieve, most recent transactions first
	 * @return a subset of transactions up to numTransactions
	 */
	public static ArrayList<Transaction> getTransactions(int numTransactions) {
		return new ArrayList<Transaction>(transactions.subList(Math.max(0, transactions.size() - numTransactions), transactions.size()));
	}

}
