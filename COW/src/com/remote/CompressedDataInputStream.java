
package com.remote;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CompressedDataInputStream extends DataInputStream {
	
	public CompressedDataInputStream(InputStream in) {
		super(in);
	}
	
	public int[] readIntMatrix1() throws IOException {
		int size = readUnsignedVarint();
		int[] matrix = new int[size];
		
		for(int i = 0; i < size; i++) {
			matrix[i] = readVarint();
		}
		
		return matrix;
	}

	public int[][] readIntMatrix2() throws IOException {
		int size1 = readUnsignedVarint();
		int size2 = readUnsignedVarint();
		int[][] matrix = new int[size1][size2];

		for(int i = 0; i < size1; i++) {
			for(int j = 0; j < size2; j++) {
				matrix[i][j] = readVarint();
			}
		}
		
		return matrix;
	}

	public int[][][] readIntMatrix3() throws IOException {
		int size1 = readUnsignedVarint();
		int size2 = readUnsignedVarint();
		int size3 = readUnsignedVarint();
		int[][][] matrix = new int[size1][size2][size3];

		for(int i = 0; i < size1; i++) {
			for(int j = 0; j < size2; j++) {
				for(int k = 0; k < size3; k++) {
					matrix[i][j][k] = readVarint();
				}
			}
		}
		
		return matrix;
	}
	
	public int readUnsignedVarint() throws IOException {
		// Read first byte
		byte tmp = readByte();
		
		// One byte varint case
		if (tmp >= 0) {
			return tmp;
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
								return result;
							}
						}
						throw new IOException("Malformed varint");
					}
				}
			}
		}
		
		// Decode Zig-Zag value
		return result;
	}
	
	public int readVarint() throws IOException {
		return decodeZigZag(readUnsignedVarint());
	}
	
	private int decodeZigZag(int value) {
		return (value >>> 1) ^ -(value & 1);
	}
}
