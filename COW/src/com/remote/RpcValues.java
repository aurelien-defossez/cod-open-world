/**
 * RPC Values - This class represents the universal frame codes in order to
 * communicate between a RPC client and a RPC server.
 */

package com.remote;

public abstract class RpcValues {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	/**
	 * Frame: Acknowledge.
	 */
	public final static byte ACK = 1;
	
	/**
	 * Frame: Error.
	 */
	public final static byte ERROR = -1;
	
	/**
	 * Command: Execute AI.
	 */
	public final static byte CMD_AI_EXE = 2;
	
	/**
	 * Command: Stop AI.
	 */
	public final static byte CMD_AI_STOP = 3;
	
	/**
	 * Command: Makes a game API call.
	 */
	public final static byte CMD_GAME_CALL_API = 4;
	
	/**
	 * API call result value.
	 */
	public final static byte CALL_API_RESULT = 5;
	
	/**
	 * Command: Makes a view API call.
	 */
	public final static byte CMD_VIEW_CALL_API = 10;
	
	/**
	 * Command: Set the score
	 */
	public final static byte CMD_SET_SCORE = 11;
	
	/**
	 * Command: Increment the score
	 */
	public final static byte CMD_INCREMENT_SCORE = 12;
	
	/**
	 * Command: Set a frame
	 */
	public final static byte CMD_SET_FRAME = 13;
	
	/**
	 * Command: Set the timeout value
	 */
	public final static byte CMD_SET_TIMEOUT = 14;
	
	/**
	 * Command: Stop an AI
	 */
	public final static byte CMD_STOP_AI = 15;
	
	/**
	 * Command: Init the game
	 */
	public final static byte CMD_INIT_GAME = 30;
	
	/**
	 * Command: Play
	 */
	public final static byte CMD_PLAY = 31;
	
	/**
	 * Command: Perform a game function
	 */
	public final static byte CMD_PERFORM_GAME_API = 32;
	
	/**
	 * Command: An AI has timed out
	 */
	public final static byte CMD_AI_TIMED_OUT = 33;
	
	/**
	 * Command: End the game
	 */
	public final static byte CMD_END_GAME = 34;
	
	// -------------------------------------------------------------------------
	// Public static methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the name corresponding to the constant.
	 * 
	 * @param constant the constant as an byte.
	 * @return the constant name as a string.
	 */
	public static String getConstantName(byte constant) {
		switch (constant) {
		case CMD_GAME_CALL_API:
			return "CMD_GAME_CALL_API";
		case CALL_API_RESULT:
			return "CALL_API_RESULT";
		case ACK:
			return "ACK";
		case CMD_AI_EXE:
			return "CMD_AI_EXE";
		case CMD_AI_STOP:
			return "CMD_AI_STOP";
		case ERROR:
			return "ERROR";
		default:
			return "Unknown constant (" + constant + ")";
		}
	}
}
