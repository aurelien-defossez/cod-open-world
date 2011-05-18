/**
 * API Call - This class represents an API call, with a function id and its
 * parameters.
 */

package com;

import java.io.IOException;
import main.CowException;
import org.apache.log4j.Logger;
import com.remote.CompressedDataInputStream;
import com.remote.CompressedDataOutputStream;

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
	 * Serializes the call into a data output stream, without protobuf
	 * optimization.
	 * 
	 * @param out the data output stream.
	 * @throws IOException if an error occurs while writing data.
	 */
	public void serialize(CompressedDataOutputStream out) throws IOException {
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
	
	/**
	 * Returns the string presentation of the API call.
	 * 
	 * @return the string presentation.
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ApiCall(function #"+functionId+"(");
		
		for (int i = 0; i < parameters.length; i++) {
			sb.append(parameters[i]);
			
			if(i < parameters.length - 1) {
				sb.append(", ");
			}
		}
		
		sb.append("))");
		
		return sb.toString();
	}
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	/**
	 * Deserializes a call from a data input stream.
	 * 
	 * @param in the data input stream.
	 * @return the API call.
	 * @throws IOException if an error occurs while reading data.
	 */
	public static ApiCall deserialize(CompressedDataInputStream in)
		throws IOException, CowException {
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
