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
import com.ApiCall;
import com.Variant;

public class SocketRpcClient implements RpcClient {
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
	 * The proxy simulator.
	 */
	private ProxySimulator simulator;
	
	/**
	 * The socket reader.
	 */
	private CompressedDataInputStream in;
	
	/**
	 * The socket writer.
	 */
	private CompressedDataOutputStream out;
	
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
	public SocketRpcClient(ProxySimulator simulator, String address, int port) {
		try {
			this.simulator = simulator;
			
			if (logger.isDebugEnabled())
				logger.debug("Connecting to framework at " + address + ":"
					+ port + "...");
			
			// Create socket and connect
			socket = new Socket(address, port);
			in = new CompressedDataInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new CompressedDataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			listening = true;
			
			if (logger.isDebugEnabled())
				logger.debug("Remote AI connected to port " + port);
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Variant callGameFunction(ApiCall call) {
		// Read number of parameters
		int nbParameters = call.getParameters().length;
		
		logger.trace("API call: function=" + call.getFunctionId() + ", "
			+ "nbParameters=" + nbParameters);
		
		try {
			// Write call API command
			out.writeByte(RpcValues.CMD_GAME_CALL_API);
			
			// Serialize and send call
			call.serialize(out);
			out.flush();
			
			// Read return command
			byte command;
			do {
				command = in.readByte();
				
				// Execute callback command
				if (command != RpcValues.CALL_API_RESULT) {
					doCommand(command);
				}
			} while (command != RpcValues.CALL_API_RESULT);
			
			// Read return variant
			Variant returnVariant = Variant.deserialize(in);
			
			if (logger.isTraceEnabled())
				logger.trace("API call return=" + returnVariant);
			
			return returnVariant;
		} catch (IOException e) {
			logger.fatal(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		logger.info("Remote AI started.");
		
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
	private boolean doCommand(byte command) throws CowException, IOException {
		if (logger.isTraceEnabled())
			logger.trace("Command: " + RpcValues.getConstantName(command));
		
		switch (command) {
		// Executes an AI function
		case RpcValues.CMD_AI_EXE:
			ApiCall call = ApiCall.deserialize(in);
			simulator.callAiFunction(call);
			ack();
			break;
		
		// Stops the game
		case RpcValues.CMD_AI_STOP:
			simulator.stopAi();
			ack();
			break;
		}
		
		return (command != RpcValues.CMD_AI_STOP);
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
	public void close() {
		listening = false;
	}
}
