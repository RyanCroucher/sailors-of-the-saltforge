package exceptions;

/**
 * The exception used when the player doesnt have enough money to do something.
 * @author Ryan Croucher rcr69
 *
 */
public class InsufficientGoldException extends Exception {

	public InsufficientGoldException(String errorMessage) {
		super(errorMessage);
	}

}
