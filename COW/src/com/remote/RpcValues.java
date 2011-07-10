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
	 * Frame: Acknowledge.
	 */
	public final static byte ACK = 5;
	
	/**
	 * Frame: Error.
	 */
	public final static byte ERROR = 6;
	
	/**
	 * API call result value.
	 */
	public final static byte CALL_API_RESULT = 7;
	
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
