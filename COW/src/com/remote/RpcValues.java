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
	public final static byte ACK = -1;
	
	/**
	 * Frame: Error.
	 */
	public final static byte ERROR = -2;
	
	/**
	 * COW to AI commands
	 */
	public enum CowToAi {
		Execute,
		Stop,
		ApiCallResult
	}
	
	/**
	 * Ai to COW commands
	 */
	public enum AiToCow {
		CallGameFunction
	}
	
	/**
	 * COW to game commands
	 */
	public enum CowToGame {
		InitGame,
		Play,
		PerformGameApiCall,
		AiTimedOut,
		EndGame,
		AiAck
	}
	
	/**
	 * Game to COW commands
	 */
	public enum GameToCow {
		CallViewFunction,
		CallAiFunction,
		SetScore,
		IncrementScore,
		SetFrame,
		SetTimeout,
		SetColor,
		StopAi,
		ThrowException,
		ApiCallResult
	}
}
