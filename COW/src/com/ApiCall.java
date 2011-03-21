/**
 * API Call - This class represents an API call, with a function id and its
 * parameters.
 */

package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import main.CowException;
import org.apache.log4j.Logger;
import com.pbuf.Call.FunctionMessage;

public class ApiCall {
	// -------------------------------------------------------------------------
	// Class attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The log4j logger.
	 */
	private static Logger logger = Logger.getLogger(ApiCall.class);
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The current function id.
	 */
	private short functionId;
	
	/**
	 * The current call parameters.
	 */
	private Variant[] parameters;
	
	/**
	 * The parameters counter.
	 */
	private int ctParameters;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Creates a new API call, with the given number of parameters.
	 * 
	 * @param functionId the function id.
	 * @param nbParameters the number of parameters.
	 */
	public ApiCall(short functionId, int nbParameters) {
		this.parameters = new Variant[nbParameters];
		this.functionId = functionId;
		this.ctParameters = 0;
	}
	
	/**
	 * Creates a new API call from the given parameters.
	 * 
	 * @param functionId the function Id.
	 * @param parameters the parameters.
	 */
	public ApiCall(short functionId, Variant[] parameters) {
		this.functionId = functionId;
		this.parameters = parameters;
		this.ctParameters = parameters.length;
	}
	
	/**
	 * Creates an API call from the protobuf function message.
	 * 
	 * @param functionMessage the function message.
	 */
	public ApiCall(FunctionMessage functionMessage) {
		this.functionId = (short) functionMessage.getFunctionId();
		int nbParameters = functionMessage.getParametersCount();
		this.parameters = new Variant[nbParameters];
		this.ctParameters = 0;
		
		// Deserialize parameters
		for (int i = 0; i < nbParameters; i++) {
			add(new Variant(functionMessage.getParameters(i)));
		}
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the function id.
	 * 
	 * @return the function id.
	 */
	public short getFunctionId() {
		return functionId;
	}
	
	/**
	 * Returns the call parameters.
	 * 
	 * @return the call parameters.
	 */
	public Variant[] getParameters() {
		return parameters;
	}
	
	/**
	 * Returns the call parameter at the specified position.
	 * 
	 * @param i the index of the element to return.
	 * @return the parameter at the specified index.
	 * @throws ArrayIndexOutOfBoundsException if the index is out of range.
	 */
	public Variant getParameter(int i) throws ArrayIndexOutOfBoundsException {
		return parameters[i];
	}
	
	/**
	 * Adds a parameter.
	 * 
	 * @param parameter the parameter.
	 */
	public void add(Variant parameter) {
		parameters[ctParameters++] = parameter;
	}
	
	/**
	 * Converts the API call object to a protobuf function message (optimized
	 * for transfer).
	 * 
	 * @return the protobuf function message.
	 */
	public FunctionMessage toFunctionMessage() {
		if (logger.isTraceEnabled())
			logger.trace("Create protobuf function message.");
		
		// Create Function PB builder
		FunctionMessage.Builder messageBuilder = FunctionMessage.newBuilder();
		
		// Set ID
		messageBuilder.setFunctionId(functionId);
		
		// Set parameters
		for (Variant parameter : parameters) {
			messageBuilder.addParameters(parameter.toVariantMessage());
		}
		
		return messageBuilder.build();
	}
	
	/**
	 * Serializes the call into a data output stream, without protobuf
	 * optimization.
	 * 
	 * @deprecated Prefer to use protocol buffer messages through
	 *             {@link #toFunctionMessage()}.
	 * @param out the data output stream.
	 * @throws IOException if an error occurs while writing data.
	 */
	@Deprecated
	public void serialize(DataOutputStream out) throws IOException {
		// Write function code
		out.writeShort(functionId);
		
		// Write number of parameters
		out.writeByte(parameters.length);
		
		// Write parameters
		for (Variant parameter : parameters) {
			if (logger.isTraceEnabled())
				logger.trace("API call parameter=" + parameter.getValue());
			
			parameter.serialize(out);
		}
	}
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	/**
	 * Deserializes a call from a data input stream.
	 * 
	 * @deprecated Prefer to use protocol buffer messages through
	 *             {@link #ApiCall(FunctionMessage)}.
	 * @param in the data input stream.
	 * @return the API call.
	 * @throws IOException if an error occurs while reading data.
	 */
	@Deprecated
	public static ApiCall deserialize(DataInputStream in) throws IOException,
			CowException {
		// Read function id and number of parameters
		short functionId = in.readShort();
		byte nbParameters = in.readByte();
		
		// Create API call
		ApiCall call = new ApiCall(functionId, nbParameters);
		
		// Read parameters
		for (int i = 0; i < nbParameters; i++) {
			call.add(Variant.deserialize(in));
		}
		
		return call;
	}
	
}
