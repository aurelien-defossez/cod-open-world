/**
 * Socket RPC Server - This class represents a RPC server communicating with a
 * RPC client through a socket.
 */

package com.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import main.CowException;
import org.apache.log4j.Logger;
import com.ApiCall;
import com.Variant;
import security.Watchdog;

public class SocketRpcServer implements RpcServer {
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
	private Logger logger = Logger.getLogger(RpcServer.class);

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

	/**
	 * The server socket, to connect the AI process.
	 */
	private ServerSocket serverSocket;

	/**
	 * The socket.
	 */
	private Socket socket;

	/**
	 * The socket reader.
	 */
	private CompressedDataInputStream in;

	/**
	 * The socket writer.
	 */
	private CompressedDataOutputStream out;

	/**
	 * The local port.
	 */
	private int port;

	/**
	 * True while the server is working.
	 */
	private boolean listening;

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
	public SocketRpcServer(ProxyAi ai, Watchdog watchdog) throws IOException {
		this.ai = ai;
		this.watchdog = watchdog;
		this.listening = true;

		// Create server socket
		serverSocket = new ServerSocket(0);
		port = serverSocket.getLocalPort();
	}

	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector<String> getProcessParameters() {
		Vector<String> parameters = new Vector<String>();
		parameters.add(RPC_SOCKET_TYPE);
		parameters.add("localhost");
		parameters.add(String.valueOf(port));

		return parameters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect() throws IOException {
		if (logger.isDebugEnabled())
			logger.debug("Waiting for socket connection...");

		// Connect with the AI process
		// TODO: Add timeout
		socket = serverSocket.accept();
		in = new CompressedDataInputStream(new BufferedInputStream(socket.getInputStream()));
		out = new CompressedDataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

		// Close server socket
		serverSocket.close();

		if (logger.isDebugEnabled())
			logger.debug("Connection accepted, waiting for ack...");

		// Wait for acknowledge
		waitForCommand();

		if (logger.isDebugEnabled())
			logger.debug("Remote AI connected on port " + socket.getPort()
					+ ".");
	}

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
			if (!listening) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopAi() {
		try {
			// Send stop AI command
			out.writeByte(RpcValues.CMD_AI_STOP);
			out.flush();

			// Read AI stream
			waitForCommand();

			// Close socket
			close();
		} catch (IOException e) {
			if (!listening) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		listening = true;

		try {
			// Close socket
			in.close();
			out.close();
			socket.close();
		} catch (IOException e1) {
			// Do nothing
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
	private void waitForCommand() throws IOException {
		byte command;

		// Start WatchDog
		// watchdog.start(ai);

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
		} while (listening && command != RpcValues.ACK);

		// Pause WatchDog
		watchdog.stop();
	}
}
