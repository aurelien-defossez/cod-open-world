/**
 * Bash Exception - This class represents a bash error that happens when a bash command fails.
 */

package main;

public class BashException extends Exception {
	
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4528068612273533110L;
	
	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs a bash exception with the specific message.
	 * 
	 * @param message the exception message.
	 */
	public BashException(String message) {
		super(message);
	}
}
