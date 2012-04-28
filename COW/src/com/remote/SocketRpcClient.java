/**
 * Socket RPC Client - This class represents a RPC client communicating with a
 * RPC server through a socket.
 */

package com.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import main.CowException;

import org.apache.log4j.Logger;

public abstract class SocketRpcClient implements RpcClient {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private static Logger logger = Logger.getLogger(SocketRpcClient.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
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
	 * True while the socket is listening.
	 */
	private boolean listening;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs the Socket RPC client.
	 * 
	 * @param simulator the proxy simulator.
	 * @param address the host address.
	 * @param port the socket local port.
	 */
	public SocketRpcClient(String address, int port) {
		try {
			// Create socket and connect
			socket = new Socket(address, port);
			in = new CompressedDataInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new CompressedDataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			listening = true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void start() {
		logger.info("Remote process started.");
		
		try {
			// Send Ack
			ack();
			
			while (listening && waitForCommand()) {
				// Play while not ordered otherwise
			}
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
		}
		
		// Close communication objects and socket
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		logger.info("Remote AI stopped.");
	}
	
	/**
	 * Sends an ACK frame to the socket server.
	 * 
	 * @throws IOException if an error occurs while sending the ACK frame.
	 */
	public void ack() throws IOException {
		out.writeByte(RpcValues.ACK);
		out.flush();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		listening = false;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	/**
	 * Waits for a command on the socket reader, then executes this command.
	 * 
	 * @return false if the command received is "stop".
	 */
	private boolean waitForCommand() throws IOException {
		if (logger.isTraceEnabled())
			logger.trace("Wait for command...");
		
		return doCommand(in.readByte());
	}
	
	/**
	 * Executes the given command from the socket reader.
	 * 
	 * @return false if the command received is "stop".
	 */
	protected abstract boolean doCommand(byte command) throws CowException, IOException;
}
