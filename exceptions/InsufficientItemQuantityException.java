package exceptions;

/**
 * The exception thrown when there exists too few of an item in an inventory for something
 * @author Ryan Croucher rcr69
 *
 */
public class InsufficientItemQuantityException extends Exception {

	/**
	 * Constructs a new InsufficientItemQuantityException
	 * @param errorMessage the error details
	 */
	public InsufficientItemQuantityException(String errorMessage) {
		super(errorMessage);
	}

}
