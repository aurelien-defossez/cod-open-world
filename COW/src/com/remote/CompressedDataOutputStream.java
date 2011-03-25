/**
 * Compressed Data Output Stream - This class writes matrices for booleans,
 * integers, doubles and strings, with a specific compression for boolean
 * matrices (8 booleans per byte) and integers matrices (integers on 1 to 5
 * bytes, depending on their value).
 */

package com.remote;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CompressedDataOutputStream extends DataOutputStream {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The boolean counter, when compressing booleans.
	 */
	private int ctBool;
	
	/**
	 * The buffering byte, when compressing booleans.
	 */
	private byte boolByte;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the compressed data output stream.
	 * 
	 * @param out the output stream to connect this writer.
	 */
	public CompressedDataOutputStream(OutputStream out) {
		super(out);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Writes the boolean 1D matrix, and compresses it so 8 booleans are in a
	 * single byte.
	 * 
	 * @param matrix the boolean matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeBooleanMatrix(boolean[] matrix) throws IOException {
		// Initialize varbool writing
		initializeVarboolWriting();
		
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		
		// Write values
		for (boolean value : matrix) {
			bufferizeVarbool(value);
		}
		
		// Write remaining booleans
		writeVarboolRemainder();
	}
	
	/**
	 * Writes the boolean 2D matrix, and compresses it so 8 booleans are in a
	 * single byte.
	 * 
	 * @param matrix the boolean matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeBooleanMatrix(boolean[][] matrix) throws IOException {
		// Initialize varbool writing
		initializeVarboolWriting();
		
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		writeUnsignedVarint(matrix[0].length);
		
		// Write values
		for (boolean[] submatrix : matrix) {
			for (boolean value : submatrix) {
				bufferizeVarbool(value);
			}
		}
		
		// Write remaining booleans
		writeVarboolRemainder();
	}
	
	/**
	 * Writes the boolean 3D matrix, and compresses it so 8 booleans are in a
	 * single byte.
	 * 
	 * @param matrix the boolean matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeBooleanMatrix(boolean[][][] matrix) throws IOException {
		// Initialize varbool writing
		initializeVarboolWriting();
		
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		writeUnsignedVarint(matrix[0].length);
		writeUnsignedVarint(matrix[0][0].length);
		
		// Write values
		for (boolean[][] submatrix : matrix) {
			for (boolean[] subsubmatrix : submatrix) {
				for (boolean value : subsubmatrix) {
					bufferizeVarbool(value);
				}
			}
		}
		
		// Write remaining booleans
		writeVarboolRemainder();
	}
	
	/**
	 * Writes the integer 1D matrix, and compresses it so every integer takes 1
	 * to 5 bytes, depending on their size.
	 * 
	 * @param matrix the integer matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeIntMatrix(int[] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		
		// Write values
		for (int value : matrix) {
			writeVarint(value);
		}
	}
	
	/**
	 * Writes the integer 2D matrix, and compresses it so every integer takes 1
	 * to 5 bytes, depending on their size.
	 * 
	 * @param matrix the integer matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeIntMatrix(int[][] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		writeUnsignedVarint(matrix[0].length);
		
		// Write values
		for (int[] submatrix : matrix) {
			for (int value : submatrix) {
				writeVarint(value);
			}
		}
	}
	
	/**
	 * Writes the integer 3D matrix, and compresses it so every integer takes 1
	 * to 5 bytes, depending on their size.
	 * 
	 * @param matrix the integer matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeIntMatrix(int[][][] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		writeUnsignedVarint(matrix[0].length);
		writeUnsignedVarint(matrix[0][0].length);
		
		// Write values
		for (int[][] submatrix : matrix) {
			for (int[] subsubmatrix : submatrix) {
				for (int value : subsubmatrix) {
					writeVarint(value);
				}
			}
		}
	}
	
	/**
	 * Writes the 1D double matrix.
	 * 
	 * @param matrix the double matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeDoubleMatrix(double[] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		
		// Write values
		for (double value : matrix) {
			writeDouble(value);
		}
	}
	
	/**
	 * Writes the 2D double matrix.
	 * 
	 * @param matrix the double matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeDoubleMatrix(double[][] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		writeUnsignedVarint(matrix[0].length);
		
		// Write values
		for (double[] submatrix : matrix) {
			for (double value : submatrix) {
				writeDouble(value);
			}
		}
	}
	
	/**
	 * Writes the 3D double matrix.
	 * 
	 * @param matrix the double matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeDoubleMatrix(double[][][] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		writeUnsignedVarint(matrix[0].length);
		writeUnsignedVarint(matrix[0][0].length);
		
		// Write values
		for (double[][] submatrix : matrix) {
			for (double[] subsubmatrix : submatrix) {
				for (double value : subsubmatrix) {
					writeDouble(value);
				}
			}
		}
	}
	
	/**
	 * Writes the 1D string matrix.
	 * 
	 * @param matrix the string matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeStringMatrix(String[] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		
		// Write values
		for (String value : matrix) {
			writeUTF(value);
		}
	}
	
	/**
	 * Writes the 2D string matrix.
	 * 
	 * @param matrix the string matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeStringMatrix(String[][] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		writeUnsignedVarint(matrix[0].length);
		
		// Write values
		for (String[] submatrix : matrix) {
			for (String value : submatrix) {
				writeUTF(value);
			}
		}
	}
	
	/**
	 * Writes the 3D string matrix.
	 * 
	 * @param matrix the string matrix.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeStringMatrix(String[][][] matrix) throws IOException {
		// Write matrix size
		writeUnsignedVarint(matrix.length);
		writeUnsignedVarint(matrix[0].length);
		writeUnsignedVarint(matrix[0][0].length);
		
		// Write values
		for (String[][] submatrix : matrix) {
			for (String[] subsubmatrix : submatrix) {
				for (String value : subsubmatrix) {
					writeUTF(value);
				}
			}
		}
	}
	
	/**
	 * Writes the signed integer with the varint algorithm, so it takes 1 to 5
	 * bytes, depending on its size. This signed integer is signed by the
	 * Zig-Zag encoding, so negative numbers are not interpreted as big varints.
	 * 
	 * @param value the integer value.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	public void writeVarint(int value) throws IOException {
		// Encode value with Zig-Zag encoding
		writeUnsignedVarint(encodeZigZag(value));
	}
	
	/**
	 * Writes the unsigned integer with the varint algorithm, so it takes 1 to 5
	 * bytes, depending on its size.
	 * 
	 * @param value the integer value.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
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
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	/**
	 * Encodes the given number with the Zig-Zag encoding, so negative numbers
	 * are not interpreted as big varints.
	 * 
	 * @param value the value to encode.
	 * @return the encoded value.
	 */
	private int encodeZigZag(int value) {
		return (value << 1) ^ (value >> 31);
	}
	
	/**
	 * Initializes the class instance attributes in order to write a varbool
	 * matrix.
	 */
	private void initializeVarboolWriting() {
		ctBool = 0;
		boolByte = 0x00;
	}
	
	/**
	 * Bufferizes a boolean into the current byte and writes it if it is full.
	 * This function must be called between a call to initializeVarboolWriting
	 * and a call to writeVarboolRemainder.
	 * 
	 * @param value the boolean to bufferize.
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	private void bufferizeVarbool(boolean value) throws IOException {
		// Write boolean in byte
		if (value) {
			boolByte |= (0x01 << ctBool);
		}
		
		// Write booleans
		if (ctBool++ == 8) {
			writeByte(boolByte);
			initializeVarboolWriting();
		}
	}
	
	/**
	 * Writes the remaining booleans into the stream. After this function is
	 * called, no other bufferizeVarbool calls shall be made.
	 * 
	 * @throws IOException if an error occurs while writing into the stream.
	 */
	private void writeVarboolRemainder() throws IOException {
		if (ctBool > 0) {
			writeByte(boolByte);
		}
	}
}
