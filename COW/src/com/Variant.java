/**
 * Variant - This class represents a variable of a defined type, which can be a
 * boolean, an integer, a double, a string, or an array of one of these types.
 * It can also be a void variable, thus it represents the absence of a variable.
 */

package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import main.CowException;

public class Variant {
	// -------------------------------------------------------------------------
	// Constant
	// -------------------------------------------------------------------------
	
	/**
	 * Void type.
	 */
	public static final byte VOID = 0x01;
	
	/**
	 * Boolean type.
	 */
	public static final byte BOOLEAN = 0x02;
	
	/**
	 * Integer type.
	 */
	public static final byte INTEGER = 0x03;
	
	/**
	 * Double type.
	 */
	public static final byte DOUBLE = 0x04;
	
	/**
	 * String type.
	 */
	public static final byte STRING = 0x05;
	
	/**
	 * Boolean array type.
	 */
	public static final byte BOOLEAN_ARRAY = 0x06;
	
	/**
	 * Integer array type.
	 */
	public static final byte INTEGER_ARRAY = 0x07;
	
	/**
	 * Double array type.
	 */
	public static final byte DOUBLE_ARRAY = 0x08;
	
	/**
	 * String array type.
	 */
	public static final byte STRING_ARRAY = 0x09;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The variant type (one of the constant listed above).
	 */
	protected byte type;
	
	/**
	 * The variant value, as an object, being (depending on its type):<br/>
	 * - VOID: a null pointer;<br/>
	 * - BOOLEAN: a Boolean object;<br/>
	 * - INTEGER: an Integer object;<br/>
	 * - DOUBLE: a Double object;<br/>
	 * - STRING: a String object;<br/>
	 * - BOOLEAN_ARRAY: an array of primitive booleans;<br/>
	 * - INTEGER_ARRAY: an array of primitive ints;<br/>
	 * - DOUBLE_ARRAY: an array of primitive doubles;<br/>
	 * - STRING_ARRAY: an array of String objects.
	 */
	protected Object value;
	
	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------
	
	/**
	 * Creates a void variant.
	 */
	public Variant() {
		this.type = VOID;
		this.value = null;
	}
	
	/**
	 * Creates a boolean variant.
	 * 
	 * @param value the boolean value.
	 */
	public Variant(boolean value) {
		this.type = BOOLEAN;
		this.value = new Boolean(value);
	}
	
	/**
	 * Creates a boolean variant.
	 * 
	 * @param value the boolean value.
	 */
	public Variant(Boolean value) {
		this.type = BOOLEAN;
		this.value = value;
	}
	
	/**
	 * Creates an integer variant.
	 * 
	 * @param value the integer value.
	 */
	public Variant(int value) {
		this.type = INTEGER;
		this.value = new Integer(value);
	}
	
	/**
	 * Creates an integer variant.
	 * 
	 * @param value the integer value.
	 */
	public Variant(Integer value) {
		this.type = INTEGER;
		this.value = value;
	}
	
	/**
	 * Creates a double variant.
	 * 
	 * @param value the double value.
	 */
	public Variant(double value) {
		this.type = DOUBLE;
		this.value = new Double(value);
	}
	
	/**
	 * Creates a double variant.
	 * 
	 * @param value the double value.
	 */
	public Variant(Double value) {
		this.type = DOUBLE;
		this.value = value;
	}
	
	/**
	 * Creates a string variant.
	 * 
	 * @param value the string value.
	 */
	public Variant(String value) {
		this.type = STRING;
		this.value = value;
	}
	
	/**
	 * Creates an array of booleans variant.
	 * 
	 * @param value the array of booleans.
	 */
	public Variant(boolean[] value) {
		this.type = BOOLEAN_ARRAY;
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
		
		this.type = BOOLEAN_ARRAY;
		this.value = tempArray;
	}
	
	/**
	 * Creates an array of integers variant.
	 * 
	 * @param value the array of integers.
	 */
	public Variant(int[] value) {
		this.type = INTEGER_ARRAY;
		this.value = value;
	}
	
	/**
	 * Creates a multidimensional array of integers variant.
	 * 
	 * @param value the array of integers.
	 * @param dimensions the sizes of each dimension.
	 */
	public Variant(Object value, int[] dimensions) {
		//Get type
		String arrayClass = value.getClass().getSimpleName();
		arrayClass = arrayClass.substring(0, arrayClass.indexOf('['));
	
		//Assign type
		if(arrayClass.equals("boolean")) {
			this.type = BOOLEAN_ARRAY;
		} else if(arrayClass.equals("int")) {
			this.type = INTEGER_ARRAY;
		}
		
		//Assign value
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
		
		this.type = INTEGER_ARRAY;
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
		
		this.type = DOUBLE_ARRAY;
		this.value = tempArray;
	}
	
	/**
	 * Creates an array of strings variant.
	 * 
	 * @param value the array of strings.
	 */
	public Variant(String[] value) {
		this.type = STRING_ARRAY;
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
	public byte getType() {
		return type;
	}
	
	/**
	 * Returns the variant value.
	 * 
	 * @return the variant value, as an object, being (depending on its type):<br/>
	 *         - VOID: a null pointer;<br/>
	 *         - BOOLEAN: a Boolean object;<br/>
	 *         - INTEGER: an Integer object;<br/>
	 *         - DOUBLE: a Double object;<br/>
	 *         - STRING: a String object;<br/>
	 *         - BOOLEAN_ARRAY: an array of primitive booleans;<br/>
	 *         - INTEGER_ARRAY: an array of primitive ints;<br/>
	 *         - DOUBLE_ARRAY: an array of primitive doubles;<br/>
	 *         - STRING_ARRAY: an array of String objects.
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
	public void serialize(DataOutputStream out) throws IOException {
		// Write variant type
		out.writeByte(type);
		
		// Write variant value depending on its type
		switch (type) {
		case Variant.VOID:
			// Write nothing
			break;
		
		case Variant.BOOLEAN:
			out.writeBoolean((Boolean) value);
			break;
		
		case Variant.INTEGER:
			out.writeInt((Integer) value);
			break;
		
		case Variant.DOUBLE:
			out.writeDouble((Double) value);
			break;
		
		case Variant.STRING:
			out.writeUTF((String) value);
			break;
		
		case Variant.BOOLEAN_ARRAY:
			boolean[] booleanArray = (boolean[]) value;
			out.writeInt(booleanArray.length);
			for (boolean arrayValue : booleanArray) {
				out.writeBoolean(arrayValue);
			}
			break;
		
		case Variant.INTEGER_ARRAY:
			int[] integerArray = (int[]) value;
			out.writeInt(integerArray.length);
			for (int arrayValue : integerArray) {
				out.writeInt(arrayValue);
			}
			break;
		
		case Variant.DOUBLE_ARRAY:
			double[] doubleArray = (double[]) value;
			out.writeInt(doubleArray.length);
			for (double arrayValue : doubleArray) {
				out.writeDouble(arrayValue);
			}
			break;
		
		case Variant.STRING_ARRAY:
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
	public static Variant deserialize(DataInputStream in) throws IOException,
			CowException {
		// Read variant type
		byte type = in.readByte();
		
		// Read variant value depending on its type
		switch (type) {
		case Variant.VOID:
			return new Variant();
		case Variant.BOOLEAN:
			return new Variant(in.readBoolean());
		case Variant.INTEGER:
			return new Variant(in.readInt());
		case Variant.DOUBLE:
			return new Variant(in.readDouble());
		case Variant.STRING:
			return new Variant(in.readUTF());
			
		case Variant.BOOLEAN_ARRAY:
			int booleanArraySize = in.readInt();
			boolean[] booleanArray = new boolean[booleanArraySize];
			for (int i = 0; i < booleanArraySize; i++) {
				booleanArray[i] = in.readBoolean();
			}
			return new Variant(booleanArray);
			
		case Variant.INTEGER_ARRAY:
			int integerArraySize = in.readInt();
			int[] integerArray = new int[integerArraySize];
			for (int i = 0; i < integerArraySize; i++) {
				integerArray[i] = in.readInt();
			}
			return new Variant(integerArray);
			
		case Variant.DOUBLE_ARRAY:
			int doubleArraySize = in.readInt();
			double[] doubleArray = new double[doubleArraySize];
			for (int i = 0; i < doubleArraySize; i++) {
				doubleArray[i] = in.readDouble();
			}
			return new Variant(doubleArray);
			
		case Variant.STRING_ARRAY:
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
