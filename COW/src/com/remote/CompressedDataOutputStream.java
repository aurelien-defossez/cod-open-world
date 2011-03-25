
package com.remote;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CompressedDataOutputStream extends DataOutputStream {
	
	public CompressedDataOutputStream(OutputStream out) {
		super(out);
	}
	
	public void writeIntMatrix(int[] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		
		// Write every value
		for (int value : matrix) {
			writeVarint(value);
		}
	}
	
	public void writeIntMatrix(int[][] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		writeUnsignedVarint(matrix[0].length);
		
		// Write every value
		for (int[] submatrix : matrix) {
			for (int value : submatrix) {
				writeVarint(value);
			}
		}
	}
	
	public void writeIntMatrix(int[][][] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		writeUnsignedVarint(matrix[0].length);
		writeUnsignedVarint(matrix[0][0].length);
		
		// Write every value
		for (int[][] submatrix : matrix) {
			for (int[] subsubmatrix : submatrix) {
				for (int value : subsubmatrix) {
					writeVarint(value);
				}
			}
		}
	}
	
	public void writeVarint(int value) throws IOException {
		// Encore value with Zig-Zag encoding
		writeUnsignedVarint(encodeZigZag(value));
	}
	
	public void writeUnsignedVarint(int value) throws IOException {
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
