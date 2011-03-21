/**
 * Protobuf Utils - This class provides some util functions to easily read and
 * write protobuf messages through a stream.
 */

package com.pbuf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import com.google.protobuf.GeneratedMessage;

public class ProtobufUtils {
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	/**
	 * Writes the given message in the output stream.
	 * 
	 * @param message the message.
	 * @param out the output stream.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public static void writeTo(GeneratedMessage message, DataOutputStream out)
			throws IOException {
		// Send call size
		out.writeInt(message.getSerializedSize());
		
		// Write message
		message.writeTo(out);
		
		// Flush data
		out.flush();
	}
	
	/**
	 * Reads the message from the input stream in a form of a byte array.
	 * 
	 * @param in the input stream.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public static byte[] readFrom(DataInputStream in) throws IOException {
		// Read bytes
		int nbBytes = in.readInt();
		byte[] bytes = new byte[nbBytes];
		/* int nbBytesRead = */
		in.read(bytes);
		// TODO: Read nbBytes completely
		
		return bytes;
	}
}
