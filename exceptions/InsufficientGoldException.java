package exceptions;

/**
 * The exception used when the player doesnt have enough money to do something.
 * @author Ryan Croucher rcr69
 *
 */
public class InsufficientGoldException extends Exception {

	/**
	 * Constructs a new InsufficientGoldException
	 * @param errorMessage the error details
	 */
	public InsufficientGoldException(String errorMessage) {
		super(errorMessage);
	}

}
