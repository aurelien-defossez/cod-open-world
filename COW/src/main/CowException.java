/**
 * Cow Exception - This class represents a fatal exception for this project.
 */

package main;

public class CowException extends RuntimeException {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1;
	
	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs a Cow exception with the specific message.
	 * 
	 * @param message the exception message.
	 */
	public CowException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a Cow exception with the specific message concatenated to the
	 * given exception message.
	 * 
	 * @param message the exception message.
	 * @param originalMessage the message of the previously thrown exception.
	 */
	public CowException(String message, String originalMessage) {
		this(message + " (error=" + originalMessage + ")");
	}
	
	/**
	 * Constructs a Cow exception with the specific message concatenated to the
	 * given exception message.
	 * 
	 * @param message the exception message.
	 * @param exception the previously thrown exception.
	 */
	public CowException(String message, Exception exception) {
		this(message, exception.getMessage());
	}
	
	/**
	 * Constructs a Cow exception from the previously thrown exception.
	 * 
	 * @param exception the previously thrown exception.
	 */
	public CowException(Exception exception) {
		super(exception);
	}
}
