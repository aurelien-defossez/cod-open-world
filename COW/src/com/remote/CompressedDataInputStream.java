/**
 * Compressed Data Input Stream - This class reads matrices for booleans,
 * integers, doubles and strings, with a specific compression for boolean
 * matrices (8 booleans per byte) and integers matrices (integers on 1 to 5
 * bytes, depending on their value).
 */

package com.remote;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import main.CowException;
import com.Variant;
import com.Variant.VariantType;

public class CompressedDataInputStream extends DataInputStream {
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Initializes the compressed data input reader.
	 * 
	 * @param in the input stream to connect this reader.
	 */
	public CompressedDataInputStream(InputStream in) {
		super(in);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Uncompresses and reads the 1D boolean matrix from the stream.
	 * 
	 * @return the boolean matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public boolean[] readBooleanMatrix1() throws IOException {
		int index = 8;
		byte boolByte = 0x00;
		int size = readUnsignedVarint();
		boolean[] matrix = new boolean[size];
		
		for (int i = 0; i < size; i++) {
			if (index == 8) {
				boolByte = readByte();
				index = 0;
			}
			
			matrix[i] = decodeVarbool(boolByte, index++);
		}
		
		return matrix;
	}
	
	/**
	 * Uncompresses and reads the 2D boolean matrix from the stream.
	 * 
	 * @return the boolean matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public boolean[][] readBooleanMatrix2() throws IOException {
		int index = 8;
		byte boolByte = 0x00;
		int size1 = readUnsignedVarint();
		int size2 = readUnsignedVarint();
		boolean[][] matrix = new boolean[size1][size2];
		
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				if (index == 8) {
					boolByte = readByte();
					index = 0;
				}
				
				matrix[i][j] = decodeVarbool(boolByte, index++);
			}
		}
		
		return matrix;
	}
	
	/**
	 * Uncompresses and reads the 3D boolean matrix from the stream.
	 * 
	 * @return the boolean matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public boolean[][][] readBooleanMatrix3() throws IOException {
		int index = 8;
		byte boolByte = 0x00;
		int size1 = readUnsignedVarint();
		int size2 = readUnsignedVarint();
		int size3 = readUnsignedVarint();
		boolean[][][] matrix = new boolean[size1][size2][size3];
		
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				for (int k = 0; k < size3; k++) {
					if (index == 8) {
						boolByte = readByte();
						index = 0;
					}
					
					matrix[i][j][k] = decodeVarbool(boolByte, index++);
				}
			}
		}
		
		return matrix;
	}
	
	/**
	 * Uncompresses and reads the 1D integer matrix from the stream.
	 * 
	 * @return the integer matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public int[] readIntMatrix1() throws IOException {
		int size = readUnsignedVarint();
		int[] matrix = new int[size];
		
		for (int i = 0; i < size; i++) {
			matrix[i] = readVarint();
		}
		
		return matrix;
	}
	
	/**
	 * Uncompresses and reads the 2D integer matrix from the stream.
	 * 
	 * @return the integer matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public int[][] readIntMatrix2() throws IOException {
		int size1 = readUnsignedVarint();
		int size2 = readUnsignedVarint();
		int[][] matrix = new int[size1][size2];
		
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				matrix[i][j] = readVarint();
			}
		}
		
		return matrix;
	}
	
	/**
	 * Uncompresses and reads the 3D integer matrix from the stream.
	 * 
	 * @return the integer matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public int[][][] readIntMatrix3() throws IOException {
		int size1 = readUnsignedVarint();
		int size2 = readUnsignedVarint();
		int size3 = readUnsignedVarint();
		int[][][] matrix = new int[size1][size2][size3];
		
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				for (int k = 0; k < size3; k++) {
					matrix[i][j][k] = readVarint();
				}
			}
		}
		
		return matrix;
	}
	
	/**
	 * Reads the 1D double matrix from the stream.
	 * 
	 * @return the double matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public double[] readDoubleMatrix1() throws IOException {
		int size1 = readUnsignedVarint();
		double[] matrix = new double[size1];
		
		for (int i = 0; i < size1; i++) {
			matrix[i] = readDouble();
		}
		
		return matrix;
	}
	
	/**
	 * Reads the 2D double matrix from the stream.
	 * 
	 * @return the double matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public double[][] readDoubleMatrix2() throws IOException {
		int size1 = readUnsignedVarint();
		int size2 = readUnsignedVarint();
		double[][] matrix = new double[size1][size2];
		
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				matrix[i][j] = readDouble();
			}
		}
		
		return matrix;
	}
	
	/**
	 * Reads the 3D double matrix from the stream.
	 * 
	 * @return the double matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public double[][][] readDoubleMatrix3() throws IOException {
		int size1 = readUnsignedVarint();
		int size2 = readUnsignedVarint();
		int size3 = readUnsignedVarint();
		double[][][] matrix = new double[size1][size2][size3];
		
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				for (int k = 0; k < size3; k++) {
					matrix[i][j][k] = readDouble();
				}
			}
		}
		
		return matrix;
	}
	
	/**
	 * Reads the 1D string matrix from the stream.
	 * 
	 * @return the string matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public String[] readStringMatrix1() throws IOException {
		int size1 = readUnsignedVarint();
		String[] matrix = new String[size1];
		
		for (int i = 0; i < size1; i++) {
			matrix[i] = readUTF();
		}
		
		return matrix;
	}
	
	/**
	 * Reads the 2D string matrix from the stream.
	 * 
	 * @return the string matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public String[][] readStringMatrix2() throws IOException {
		int size1 = readUnsignedVarint();
		int size2 = readUnsignedVarint();
		String[][] matrix = new String[size1][size2];
		
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				matrix[i][j] = readUTF();
			}
		}
		
		return matrix;
	}
	
	/**
	 * Reads the 3D string matrix from the stream.
	 * 
	 * @return the string matrix.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public String[][][] readStringMatrix3() throws IOException {
		int size1 = readUnsignedVarint();
		int size2 = readUnsignedVarint();
		int size3 = readUnsignedVarint();
		String[][][] matrix = new String[size1][size2][size3];
		
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				for (int k = 0; k < size3; k++) {
					matrix[i][j][k] = readUTF();
				}
			}
		}
		
		return matrix;
	}
	
	/**
	 * Uncompresses the next unsigned varint from the stream.
	 * 
	 * @return the int value of the varint.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
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
	
	/**
	 * Uncompresses the next signed varint from the stream.
	 * 
	 * @return the int value of the varint.
	 * @throws IOException if an error occurs while reading from the stream.
	 */
	public int readVarint() throws IOException {
		return decodeZigZag(readUnsignedVarint());
	}
	
	/**
	 * Reads the variant from the stream.
	 * 
	 * @return the variant read.
	 * @throws IOException if an error occurs while reading from the stream.
	 * @throws CowException if the type read is not valid.
	 */
	public Variant readVariant() throws IOException {
		// Read variant type
		VariantType type = VariantType.values()[readByte()];
		
		// Read variant value depending on its type
		return readVariantValue(type);
	}

	/**
	 * Reads the variant value from the stream.
	 * 
	 * @param type the variant type.
	 * @return the variant read.
	 * @throws IOException if an error occurs while reading from the stream.
	 * @throws CowException if the type read is not valid.
	 */
	public Variant readVariantValue(VariantType type) throws IOException {
		switch (type) {
		case VOID:
			return new Variant();
			
		case BOOL:
			return new Variant(readBoolean());
			
		case INT:
			return new Variant(readVarint());
			
		case DOUBLE:
			return new Variant(readDouble());
			
		case STRING:
			return new Variant(readUTF());
			
		case BOOL_MATRIX1:
			return new Variant(readBooleanMatrix1());
			
		case BOOL_MATRIX2:
			return new Variant(readBooleanMatrix2());
			
		case BOOL_MATRIX3:
			return new Variant(readBooleanMatrix3());
			
		case INT_MATRIX1:
			return new Variant(readIntMatrix1());
			
		case INT_MATRIX2:
			return new Variant(readIntMatrix2());
			
		case INT_MATRIX3:
			return new Variant(readIntMatrix3());
			
		case DOUBLE_MATRIX1:
			return new Variant(readDoubleMatrix1());
			
		case DOUBLE_MATRIX2:
			return new Variant(readDoubleMatrix2());
			
		case DOUBLE_MATRIX3:
			return new Variant(readDoubleMatrix3());
			
		case STRING_MATRIX1:
			return new Variant(readStringMatrix1());
			
		case STRING_MATRIX2:
			return new Variant(readStringMatrix2());
			
		case STRING_MATRIX3:
			return new Variant(readStringMatrix3());
			
		default:
			throw new CowException("Unknown variant type (" + type + ")");
		}
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	/**
	 * Decodes the signed value encoded with the Zig-Zag encoding.
	 * 
	 * @param value the encoded value.
	 * @return the decoded value.
	 */
	private int decodeZigZag(int value) {
		return (value >>> 1) ^ -(value & 1);
	}
	
	/**
	 * Decodes the boolean from the given byte and index in this byte.
	 * 
	 * @param boolByte the byte where the boolean is stored.
	 * @param index the index in this byte.
	 * @return the boolean value.
	 */
	private boolean decodeVarbool(byte boolByte, int index) {
		return ((boolByte & (0x01 << index)) != 0);
	}
}
