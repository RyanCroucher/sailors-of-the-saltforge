package exceptions;

/**
 * The exception thrown when there exists too few of an item in an inventory for something
 * @author Ryan Croucher rcr69
 *
 */
public class InsufficientItemQuantityException extends Exception {

	public InsufficientItemQuantityException(String errorMessage) {
		super(errorMessage);
	}

}
