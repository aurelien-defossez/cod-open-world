
package com;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CompressedDataOutputStream extends DataOutputStream {
	
	public CompressedDataOutputStream(OutputStream out) {
		super(out);
	}
	
	public void writeVarInt(int value) throws IOException {
		// Zig-Zag encoding
		value = encodeZigZag(value);
		
		// Varint encoding
		while (true) {
			// Write final byte
			if ((value & ~0x7F) == 0) {
				writeByte(value);
				return;
			}
			// Write 7 bits and continue
			else {
				writeByte((value & 0x7F) | 0x80);
				value >>>= 7;
			}
		}
	}
	
	private int encodeZigZag(int value) {
		return (value << 1) ^ (value >> 31);
	}
}
