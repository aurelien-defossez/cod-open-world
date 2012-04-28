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

public abstract class SocketRpcServer implements RpcServer {
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
	private Logger logger = Logger.getLogger(SocketRpcServer.class);

	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------

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
	protected CompressedDataInputStream in;

	/**
	 * The socket writer.
	 */
	protected CompressedDataOutputStream out;

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
	 * @param watchdog
	 *            the security watchdog.
	 * @throws IOException
	 *             if an error occurs while creating the server socket.
	 */
	public SocketRpcServer() throws IOException {
		this.listening = true;

		// Create server socket
		serverSocket = new ServerSocket(0);
		port = serverSocket.getLocalPort();
	}

	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------

	public Vector<String> getProcessParameters() {
		Vector<String> parameters = new Vector<String>();
		parameters.add(RPC_SOCKET_TYPE);
		parameters.add("localhost");
		parameters.add(String.valueOf(port));

		return parameters;
	}

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

	public void close() {
		listening = false;

		try {
			// Close socket
			in.close();
			out.close();
			socket.close();
		} catch (IOException e1) {
			// Do nothing
		}
	}
	
	public boolean isListening() {
		return listening;
	}

	
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	/**
	 * Executes the given command from the socket reader.
	 * 
	 * @return false if the command received is "stop".
	 */
	protected abstract void doCommand(byte command) throws CowException, IOException;

	// -------------------------------------------------------------------------
	// Protected methods
	// -------------------------------------------------------------------------
	/**
	 * Waits for a command on the socket reader, then executes this command.
	 * 
	 * @throws IOException if an error occurs while communicating with the RPC client.
	 */
	protected void waitForCommand() throws IOException {
		boolean ack;
		
		if (logger.isDebugEnabled())
			logger.debug("Wait for command...");
		
		do {
			// Read command
			byte command = (byte) in.read();
			ack = (command == RpcValues.ACK);
			
			if (!ack) {
				doCommand(command);
			}
		} while (isListening() && !ack);
	}
}
