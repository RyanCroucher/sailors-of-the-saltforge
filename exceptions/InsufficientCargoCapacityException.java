package exceptions;

/**
 * The exception used when the player doesnt have enough cargo space to do something.
 * @author Ryan Croucher rcr69
 *
 */
public class InsufficientCargoCapacityException extends Exception {

	public InsufficientCargoCapacityException(String errorMessage) {
		super(errorMessage);
	}

}
