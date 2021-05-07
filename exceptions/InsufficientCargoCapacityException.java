package exceptions;

/**
 * The exception used when the player doesnt have enough cargo space to do something.
 * @author Ryan Croucher rcr69
 *
 */
public class InsufficientCargoCapacityException extends Exception {

	/**
	 * Constructs a new InsufficientCargoCapacityException
	 * @param errorMessage the error details
	 */
	public InsufficientCargoCapacityException(String errorMessage) {
		super(errorMessage);
	}

}
