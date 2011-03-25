/**
 * Variant - This class represents a variable of a defined type, which can be a
 * boolean, an integer, a double, a string, or an array of one of these types.
 * It can also be a void variable, thus it represents the absence of a variable.
 */

package com;

import java.io.IOException;
import com.remote.CompressedDataInputStream;
import com.remote.CompressedDataOutputStream;
import main.CowException;

public class Variant {
	// -------------------------------------------------------------------------
	// Enumeration
	// -------------------------------------------------------------------------
	
	/**
	 * This enumeration represents the type of the variant.
	 */
	public enum VariantType {
		VOID, BOOL, INT, DOUBLE, STRING, BOOL_MATRIX1, BOOL_MATRIX2,
		BOOL_MATRIX3, INT_MATRIX1, INT_MATRIX2, INT_MATRIX3, DOUBLE_MATRIX1,
		DOUBLE_MATRIX2, DOUBLE_MATRIX3, STRING_MATRIX1, STRING_MATRIX2,
		STRING_MATRIX3
	}
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The variant type (one of the constant listed above).
	 */
	protected VariantType type;
	
	/**
	 * The variant value, as an object, being (depending on its type):<br/>
	 * - VOID: a null pointer;<br/>
	 * - BOOL: a Boolean object;<br/>
	 * - INT: an Integer object;<br/>
	 * - DOUBLE: a Double object;<br/>
	 * - STRING: a String object;<br/>
	 * - BOOL_MATRIX{n=1|2|3}: a nD matrix of primitive booleans;<br/>
	 * - INT_MATRIX{n=1|2|3}: a nD matrix of primitive ints;<br/>
	 * - DOUBLE_MATRIX{n=1|2|3}: a nD matrix of primitive doubles;<br/>
	 * - STRING_MATRIX{n=1|2|3}: a nD matrix of String objects.
	 */
	protected Object value;
	
	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------
	
	/**
	 * Creates a void variant.
	 */
	public Variant() {
		this.type = VariantType.VOID;
		this.value = null;
	}
	
	/**
	 * Creates a boolean variant.
	 * 
	 * @param value the boolean value.
	 */
	public Variant(boolean value) {
		this.type = VariantType.BOOL;
		this.value = new Boolean(value);
	}
	
	/**
	 * Creates a boolean variant.
	 * 
	 * @param value the boolean value.
	 */
	public Variant(Boolean value) {
		this.type = VariantType.BOOL;
		this.value = value;
	}
	
	/**
	 * Creates an integer variant.
	 * 
	 * @param value the integer value.
	 */
	public Variant(int value) {
		this.type = VariantType.INT;
		this.value = new Integer(value);
	}
	
	/**
	 * Creates an integer variant.
	 * 
	 * @param value the integer value.
	 */
	public Variant(Integer value) {
		this.type = VariantType.INT;
		this.value = value;
	}
	
	/**
	 * Creates a double variant.
	 * 
	 * @param value the double value.
	 */
	public Variant(double value) {
		this.type = VariantType.DOUBLE;
		this.value = new Double(value);
	}
	
	/**
	 * Creates a double variant.
	 * 
	 * @param value the double value.
	 */
	public Variant(Double value) {
		this.type = VariantType.DOUBLE;
		this.value = value;
	}
	
	/**
	 * Creates a string variant.
	 * 
	 * @param value the string value.
	 */
	public Variant(String value) {
		this.type = VariantType.STRING;
		this.value = value;
	}
	
	/**
	 * Creates a 1D matrix of booleans variant.
	 * 
	 * @param value the array of booleans.
	 */
	public Variant(boolean[] value) {
		this.type = VariantType.BOOL_MATRIX1;
		this.value = value;
	}
	
	/**
	 * Creates a 2D matrix of booleans variant.
	 * 
	 * @param value the array of booleans.
	 */
	public Variant(boolean[][] value) {
		this.type = VariantType.BOOL_MATRIX2;
		this.value = value;
	}
	
	/**
	 * Creates a 3D matrix of booleans variant.
	 * 
	 * @param value the array of booleans.
	 */
	public Variant(boolean[][][] value) {
		this.type = VariantType.BOOL_MATRIX3;
		this.value = value;
	}
	
	/**
	 * Creates a 1D matrix of integers variant.
	 * 
	 * @param value the array of integers.
	 */
	public Variant(int[] value) {
		this.type = VariantType.INT_MATRIX1;
		this.value = value;
	}
	
	/**
	 * Creates a 2D matrix of integers variant.
	 * 
	 * @param value the array of integers.
	 */
	public Variant(int[][] value) {
		this.type = VariantType.INT_MATRIX2;
		this.value = value;
	}
	
	/**
	 * Creates a 3D matrix of integers variant.
	 * 
	 * @param value the array of integers.
	 */
	public Variant(int[][][] value) {
		this.type = VariantType.INT_MATRIX3;
		this.value = value;
	}
	
	/**
	 * Creates a 1D matrix of doubles variant.
	 * 
	 * @param value the array of doubles.
	 */
	public Variant(double[] value) {
		this.type = VariantType.DOUBLE_MATRIX1;
		this.value = value;
	}
	
	/**
	 * Creates a 2D matrix of doubles variant.
	 * 
	 * @param value the array of doubles.
	 */
	public Variant(double[][] value) {
		this.type = VariantType.DOUBLE_MATRIX2;
		this.value = value;
	}
	
	/**
	 * Creates a 3D matrix of doubles variant.
	 * 
	 * @param value the array of doubles.
	 */
	public Variant(double[][][] value) {
		this.type = VariantType.DOUBLE_MATRIX3;
		this.value = value;
	}
	
	/**
	 * Creates a 1D matrix of strings variant.
	 * 
	 * @param value the array of strings.
	 */
	public Variant(String[] value) {
		this.type = VariantType.STRING_MATRIX1;
		this.value = value;
	}
	
	/**
	 * Creates a 2D matrix of strings variant.
	 * 
	 * @param value the array of strings.
	 */
	public Variant(String[][] value) {
		this.type = VariantType.STRING_MATRIX2;
		this.value = value;
	}
	
	/**
	 * Creates a 3D matrix of strings variant.
	 * 
	 * @param value the array of strings.
	 */
	public Variant(String[][][] value) {
		this.type = VariantType.STRING_MATRIX3;
		this.value = value;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the variant type.
	 * 
	 * @return the variant type, as one of the constants listed above.
	 */
	public VariantType getType() {
		return type;
	}
	
	/**
	 * Returns the variant value.
	 * 
	 * @return the variant value, as an object, being (depending on its type):<br/>
	 *         - VOID: a null pointer;<br/>
	 *         - BOOL: a Boolean object;<br/>
	 *         - INT: an Integer object;<br/>
	 *         - DOUBLE: a Double object;<br/>
	 *         - STRING: a String object;<br/>
	 *         - BOOL_MATRIX{n=1|2|3}: a nD matrix of primitive booleans;<br/>
	 *         - INT_MATRIX{n=1|2|3}: a nD matrix of primitive ints;<br/>
	 *         - DOUBLE_MATRIX{n=1|2|3}: a nD matrix of primitive doubles;<br/>
	 *         - STRING_MATRIX{n=1|2|3}: a nD matrix of String objects.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Serializes the variant in a data output stream.
	 * 
	 * @param out the data output stream.
	 * @throws IOException if an error occurs while writing the variant.
	 */
	public void serialize(CompressedDataOutputStream out) throws IOException {
		// Write variant type
		out.writeByte((byte) type.ordinal());
		
		// Write variant value depending on its type
		switch (type) {
		case VOID:
			// Write nothing
			break;
		
		case BOOL:
			out.writeBoolean((Boolean) value);
			break;
		
		case INT:
			out.writeInt((Integer) value);
			break;
		
		case DOUBLE:
			out.writeDouble((Double) value);
			break;
		
		case STRING:
			out.writeUTF((String) value);
			break;
		
		case BOOL_MATRIX1:
			out.writeBooleanMatrix((boolean[]) value);
			break;
		
		case BOOL_MATRIX2:
			out.writeBooleanMatrix((boolean[][]) value);
			break;
		
		case BOOL_MATRIX3:
			out.writeBooleanMatrix((boolean[][][]) value);
			break;
		
		case INT_MATRIX1:
			out.writeIntMatrix((int[]) value);
			break;
		
		case INT_MATRIX2:
			out.writeIntMatrix((int[][]) value);
			break;
		
		case INT_MATRIX3:
			out.writeIntMatrix((int[][][]) value);
			break;
		
		case DOUBLE_MATRIX1:
			out.writeDoubleMatrix((double[]) value);
			break;
		
		case DOUBLE_MATRIX2:
			out.writeDoubleMatrix((double[][]) value);
			break;
		
		case DOUBLE_MATRIX3:
			out.writeDoubleMatrix((double[][][]) value);
			break;
		
		case STRING_MATRIX1:
			out.writeStringMatrix((String[]) value);
			break;
		
		case STRING_MATRIX2:
			out.writeStringMatrix((String[][]) value);
			break;
		
		case STRING_MATRIX3:
			out.writeStringMatrix((String[][][]) value);
			break;
		}
	}
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	/**
	 * Deserializes the variant from a data input stream.
	 * 
	 * @param in the data input stream.
	 * @return the variant.
	 * @throws IOException if an error occurs while reading the stream.
	 * @throws CowException if the variant read is not valid.
	 */
	public static Variant deserialize(CompressedDataInputStream in)
			throws IOException, CowException {
		// Read variant type
		VariantType type = VariantType.values()[in.readByte()];
		
		// Read variant value depending on its type
		switch (type) {
		case VOID:
			return new Variant();
			
		case BOOL:
			return new Variant(in.readBoolean());
			
		case INT:
			return new Variant(in.readInt());
			
		case DOUBLE:
			return new Variant(in.readDouble());
			
		case STRING:
			return new Variant(in.readUTF());
			
		case BOOL_MATRIX1:
			return new Variant(in.readBooleanMatrix1());
			
		case BOOL_MATRIX2:
			return new Variant(in.readBooleanMatrix2());
			
		case BOOL_MATRIX3:
			return new Variant(in.readBooleanMatrix3());
			
		case INT_MATRIX1:
			return new Variant(in.readIntMatrix1());
			
		case INT_MATRIX2:
			return new Variant(in.readIntMatrix2());
			
		case INT_MATRIX3:
			return new Variant(in.readIntMatrix3());
			
		case DOUBLE_MATRIX1:
			return new Variant(in.readDoubleMatrix1());
			
		case DOUBLE_MATRIX2:
			return new Variant(in.readDoubleMatrix2());
			
		case DOUBLE_MATRIX3:
			return new Variant(in.readDoubleMatrix3());
			
		case STRING_MATRIX1:
			return new Variant(in.readStringMatrix1());
			
		case STRING_MATRIX2:
			return new Variant(in.readStringMatrix2());
			
		case STRING_MATRIX3:
			return new Variant(in.readStringMatrix3());
			
		default:
			throw new CowException("Unknown variant type (" + type + ")");
		}
	}
}
