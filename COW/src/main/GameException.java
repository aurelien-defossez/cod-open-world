/**
 * Game Exception - This class represents a fatal exception sent by the game
 * engine.
 */

package main;

public class GameException extends RuntimeException {
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
	 * Constructs a game exception with the specific message.
	 * 
	 * @param message the exception message.
	 */
	public GameException(String message) {
		super(message);
	}
}
