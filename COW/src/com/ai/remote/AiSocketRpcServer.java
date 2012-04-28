/**
 * Socket RPC Server - This class represents a RPC server communicating with a
 * RPC client through a socket.
 */

package com.ai.remote;

import java.io.IOException;

import main.CowException;

import org.apache.log4j.Logger;

import security.Watchdog;

import com.ApiCall;
import com.Variant;
import com.remote.RpcValues;
import com.remote.SocketRpcServer;

public class AiSocketRpcServer extends SocketRpcServer implements AiRpcServer {
	// -------------------------------------------------------------------------
	// Constant
	// -------------------------------------------------------------------------

	/**
	 * The RPC type, as a string.
	 */
	public static final String RPC_SOCKET_TYPE = "socket";

	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------

	/**
	 * The log4j logger.
	 */
	private Logger logger = Logger.getLogger(AiRpcServer.class);

	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------

	/**
	 * The Proxy AI.
	 */
	private ProxyAi ai;

	/**
	 * The security watchdog.
	 */
	private Watchdog watchdog;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	/**
	 * Initializes the Socket RPC server.
	 * 
	 * @param ai
	 *            the proxy AI.
	 * @param watchdog
	 *            the security watchdog.
	 * @throws IOException
	 *             if an error occurs while creating the server socket.
	 */
	public AiSocketRpcServer(ProxyAi ai, Watchdog watchdog) throws IOException {
		super();
		
		this.ai = ai;
		this.watchdog = watchdog;
	}

	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performAiFunction(ApiCall call) {
		try {
			// Send execute AI command
			out.writeByte(RpcValues.CMD_AI_EXE);
			call.serialize(out);
			out.flush();

			// Read AI stream
			waitForCommand();
		} catch (IOException e) {
			if (!isListening()) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		try {
			// Send stop AI command
			out.writeByte(RpcValues.CMD_AI_STOP);
			out.flush();

			// Read AI stream
			waitForCommand();

			// Close socket
			close();
		} catch (IOException e) {
			if (!isListening()) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------

	/**
	 * Waits for a command on the socket reader, then executes this command.
	 * 
	 * @throws IOException
	 *             if an error occurs while communicating with the RPC client.
	 */
	@Override
	protected void waitForCommand() throws IOException {
		byte command;

		// Start WatchDog
		watchdog.start(ai);

		if (logger.isDebugEnabled())
			logger.debug("Wait for command...");

		do {
			// Read command
			command = (byte) in.read();
			
			if (logger.isDebugEnabled())
				logger.debug("Command read: "
						+ RpcValues.getConstantName(command));

			switch (command) {
			case RpcValues.CMD_GAME_CALL_API:
				ApiCall call = ApiCall.deserialize(in);

				if (logger.isTraceEnabled()) {
					logger.trace("API call: function=" + call.getFunctionId()
							+ ", " + "nbParameters="
							+ call.getParameters().length);
					for (Variant parameter : call.getParameters()) {
						logger.trace("API call parameter="
								+ parameter.getValue());
					}
				}

				// Send API call
				Variant returnVariant = ai.callGameFunction(call);

				if (logger.isTraceEnabled())
					logger.trace("API call return=" + returnVariant);

				// Send return value
				out.writeByte(RpcValues.CALL_API_RESULT);
				returnVariant.serialize(out);
				out.flush();
				break;

			case RpcValues.ERROR:
				// TODO: Handle error message
				throw new CowException("AI connection error: TODO");

			}
		} while (isListening() && command != RpcValues.ACK);

		// Pause WatchDog
		watchdog.stop();
	}
}
