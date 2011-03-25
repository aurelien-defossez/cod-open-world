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
	 * - BOOL_MATRIX: an array of primitive booleans;<br/>
	 * - INT_MATRIX: an array of primitive ints;<br/>
	 * - DOUBLE_MATRIX: an array of primitive doubles;<br/>
	 * - STRING_MATRIX: an array of String objects.
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
	 * Creates an array of booleans variant.
	 * 
	 * @param value the array of booleans.
	 */
	public Variant(boolean[] value) {
		this.type = VariantType.BOOL_MATRIX1;
		this.value = value;
	}
	
	/**
	 * Creates an array of booleans variant.
	 * 
	 * @param value the array of booleans.
	 */
	public Variant(Boolean[] value) {
		int length = value.length;
		boolean[] tempArray = new boolean[length];
		for (int i = 0; i < length; i++) {
			tempArray[i] = value[i].booleanValue();
		}
		
		this.type = VariantType.BOOL_MATRIX1;
		this.value = tempArray;
	}
	
	/**
	 * Creates an array of integers variant.
	 * 
	 * @param value the array of integers.
	 */
	public Variant(int[] value) {
		this.type = VariantType.INT_MATRIX1;
		this.value = value;
	}
	
	/**
	 * Creates an array of integers variant.
	 * 
	 * @param value the array of integers.
	 */
	public Variant(Integer[] value) {
		int length = value.length;
		int[] tempArray = new int[length];
		for (int i = 0; i < length; i++) {
			tempArray[i] = value[i].intValue();
		}
		
		this.type = VariantType.INT_MATRIX1;
		this.value = tempArray;
	}
	
	/**
	 * Creates an array of doubles variant.
	 * 
	 * @param value the array of doubles.
	 */
	public Variant(double[] value) {
		this.value = value;
	}
	
	/**
	 * Creates an array of doubles variant.
	 * 
	 * @param value the array of doubles.
	 */
	public Variant(Double[] value) {
		int length = value.length;
		double[] tempArray = new double[length];
		for (int i = 0; i < length; i++) {
			tempArray[i] = value[i].doubleValue();
		}
		
		this.type = VariantType.DOUBLE_MATRIX1;
		this.value = tempArray;
	}
	
	/**
	 * Creates an array of strings variant.
	 * 
	 * @param value the array of strings.
	 */
	public Variant(String[] value) {
		this.type = VariantType.STRING_MATRIX1;
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
	 *         - BOOL_MATRIX: an array of primitive booleans;<br/>
	 *         - INT_MATRIX: an array of primitive ints;<br/>
	 *         - DOUBLE_MATRIX: an array of primitive doubles;<br/>
	 *         - STRING_MATRIX: an array of String objects.
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
			boolean[] booleanArray = (boolean[]) value;
			out.writeInt(booleanArray.length);
			for (boolean arrayValue : booleanArray) {
				out.writeBoolean(arrayValue);
			}
			break;
		
		case INT_MATRIX1:
			int[] integerArray = (int[]) value;
			out.writeInt(integerArray.length);
			for (int arrayValue : integerArray) {
				out.writeInt(arrayValue);
			}
			break;
		
		case DOUBLE_MATRIX1:
			double[] doubleArray = (double[]) value;
			out.writeInt(doubleArray.length);
			for (double arrayValue : doubleArray) {
				out.writeDouble(arrayValue);
			}
			break;
		
		case STRING_MATRIX1:
			String[] stringArray = (String[]) value;
			out.writeInt(stringArray.length);
			for (String arrayValue : stringArray) {
				out.writeUTF(arrayValue);
			}
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
			int booleanArraySize = in.readInt();
			boolean[] booleanArray = new boolean[booleanArraySize];
			for (int i = 0; i < booleanArraySize; i++) {
				booleanArray[i] = in.readBoolean();
			}
			return new Variant(booleanArray);
			
		case INT_MATRIX1:
			int integerArraySize = in.readInt();
			int[] integerArray = new int[integerArraySize];
			for (int i = 0; i < integerArraySize; i++) {
				integerArray[i] = in.readInt();
			}
			return new Variant(integerArray);
			
		case DOUBLE_MATRIX1:
			int doubleArraySize = in.readInt();
			double[] doubleArray = new double[doubleArraySize];
			for (int i = 0; i < doubleArraySize; i++) {
				doubleArray[i] = in.readDouble();
			}
			return new Variant(doubleArray);
			
		case STRING_MATRIX1:
			int stringArraySize = in.readInt();
			String[] stringArray = new String[stringArraySize];
			for (int i = 0; i < stringArraySize; i++) {
				stringArray[i] = in.readUTF();
			}
			return new Variant(stringArray);
			
		default:
			throw new CowException("Unknown variant type (" + type + ")");
		}
	}
}
