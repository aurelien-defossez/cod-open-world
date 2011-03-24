
package com;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CompressedDataInputStream extends DataInputStream {
	
	public CompressedDataInputStream(InputStream in) {
		super(in);
	}
	
	public int readVarInt() throws IOException {
		// Read first byte
		byte tmp = readByte();
		
		// One byte varint case
		if (tmp >= 0) {
			return decodeZigZag(tmp);
		}
		
		// Create int result value
		int result = tmp & 0x7f;
		
		// Two bytes varint case
		if ((tmp = readByte()) >= 0) {
			result |= tmp << 7;
		} else {
			result |= (tmp & 0x7f) << 7;
			
			// Three bytes varint case
			if ((tmp = readByte()) >= 0) {
				result |= tmp << 14;
			} else {
				result |= (tmp & 0x7f) << 14;
				
				// Four bytes varint case
				if ((tmp = readByte()) >= 0) {
					result |= tmp << 21;
				} else {
					result |= (tmp & 0x7f) << 21;
					result |= (tmp = readByte()) << 28;
					if (tmp < 0) {
						// Discard upper 32 bits.
						for (int i = 0; i < 5; i++) {
							if (readByte() >= 0) {
								return decodeZigZag(result);
							}
						}
						throw new IOException("Malformed varint");
					}
				}
			}
		}
		
		// Decode Zig-Zag value
		return decodeZigZag(result);
	}
	
	private int decodeZigZag(int value) {
		return (value >>> 1) ^ -(value & 1);
	}
}
