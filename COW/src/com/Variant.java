/**
 * Variant - This class represents a variable of a defined type, which can be a
 * boolean, an integer, a double, a string, or an array of one of these types.
 * It can also be a void variable, thus it represents the absence of a variable.
 */

package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import com.pbuf.Call.VariantMessage;
import com.pbuf.Call.VariantMessage.VariantType;
import main.CowException;

public class Variant {
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
	 * Creates a variant from the protobuf variant message.
	 * 
	 * @param variantMessage the variant message.
	 */
	public Variant(VariantMessage variantMessage) {
		this.type = variantMessage.getType();
		
		switch (type) {
		case VOID:
			this.value = null;
			break;
		
		case BOOL:
			this.value = new Boolean(variantMessage.getBoolValue());
			break;
		
		case INT:
			this.value = new Integer(variantMessage.getIntValue());
			break;
		
		case DOUBLE:
			this.value = new Double(variantMessage.getDoubleValue());
			break;
		
		case INT_MATRIX:
			int intMatrixSize = variantMessage.getIntMatrixCount();
			int[] intMatrix = new int[intMatrixSize];
			for (int i = 0; i < intMatrixSize; i++) {
				intMatrix[i] = variantMessage.getIntMatrix(i);
			}
			this.value = intMatrix;
			break;
		
		default:
			throw new CowException("Unknown variant type ("
					+ variantMessage.getType() + ")");
		}
	}
	
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
		this.type = VariantType.BOOL_MATRIX;
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
		
		this.type = VariantType.BOOL_MATRIX;
		this.value = tempArray;
	}
	
	/**
	 * Creates an array of integers variant.
	 * 
	 * @param value the array of integers.
	 */
	public Variant(int[] value) {
		this.type = VariantType.INT_MATRIX;
		this.value = value;
	}
	
	/**
	 * Creates a multidimensional array of integers variant.
	 * 
	 * @param value the array of integers.
	 * @param dimensions the sizes of each dimension.
	 */
	public Variant(Object value, int[] dimensions) {
		// Get type
		String arrayClass = value.getClass().getSimpleName();
		arrayClass = arrayClass.substring(0, arrayClass.indexOf('['));
		
		// Assign type
		if (arrayClass.equals("boolean")) {
			this.type = VariantType.BOOL_MATRIX;
		} else if (arrayClass.equals("int")) {
			this.type = VariantType.INT_MATRIX;
		}
		
		// Assign value
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
		
		this.type = VariantType.INT_MATRIX;
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
		
		this.type = VariantType.DOUBLE_MATRIX;
		this.value = tempArray;
	}
	
	/**
	 * Creates an array of strings variant.
	 * 
	 * @param value the array of strings.
	 */
	public Variant(String[] value) {
		this.type = VariantType.STRING_MATRIX;
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
	 * Converts the variant into a protobuf variant message (optimized for
	 * transfer).
	 * 
	 * @return the protobuf variant message.
	 */
	public VariantMessage toVariantMessage() {
		// Create Variant PB builder
		VariantMessage.Builder messageBuilder = VariantMessage.newBuilder();
		
		// Set variant type
		messageBuilder.setType(type);
		
		switch (type) {
		case VOID:
			// Write nothing
			break;
		
		case BOOL:
			messageBuilder.setBoolValue((Boolean) value);
			break;
		
		case INT:
			messageBuilder.setIntValue((Integer) value);
			break;
		
		case DOUBLE:
			messageBuilder.setDoubleValue((Double) value);
			break;
		
		case STRING:
			messageBuilder.setStringValue((String) value);
			break;
		
		case INT_MATRIX:
			messageBuilder.addCardinalities(((int[]) value).length);
			for (int arrayValue : (int[]) value) {
				messageBuilder.addIntMatrix(arrayValue);
			}
			break;
		}
		
		return messageBuilder.build();
	}
	
	/**
	 * Serializes the variant in a data output stream.
	 * 
	 * @deprecated Prefer to use protocol buffer messages through
	 *             {@link #toFunctionMessage()}.
	 * @param out the data output stream.
	 * @throws IOException if an error occurs while writing the variant.
	 */
	@Deprecated
	public void serialize(DataOutputStream out) throws IOException {
		// Write variant type
		out.writeByte(type.getNumber());
		
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
		
		case BOOL_MATRIX:
			boolean[] booleanArray = (boolean[]) value;
			out.writeInt(booleanArray.length);
			for (boolean arrayValue : booleanArray) {
				out.writeBoolean(arrayValue);
			}
			break;
		
		case INT_MATRIX:
			int[] integerArray = (int[]) value;
			out.writeInt(integerArray.length);
			for (int arrayValue : integerArray) {
				out.writeInt(arrayValue);
			}
			break;
		
		case DOUBLE_MATRIX:
			double[] doubleArray = (double[]) value;
			out.writeInt(doubleArray.length);
			for (double arrayValue : doubleArray) {
				out.writeDouble(arrayValue);
			}
			break;
		
		case STRING_MATRIX:
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
	 * @deprecated Prefer to use protocol buffer messages through
	 *             {@link #Variant(VariantMessage)}.
	 * @param in the data input stream.
	 * @return the variant.
	 * @throws IOException if an error occurs while reading the stream.
	 * @throws CowException if the variant read is not valid.
	 */
	@Deprecated
	public static Variant deserialize(DataInputStream in) throws IOException,
			CowException {
		// Read variant type
		VariantType type = VariantType.valueOf(in.readByte());
		
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
			
		case BOOL_MATRIX:
			int booleanArraySize = in.readInt();
			boolean[] booleanArray = new boolean[booleanArraySize];
			for (int i = 0; i < booleanArraySize; i++) {
				booleanArray[i] = in.readBoolean();
			}
			return new Variant(booleanArray);
			
		case INT_MATRIX:
			int integerArraySize = in.readInt();
			int[] integerArray = new int[integerArraySize];
			for (int i = 0; i < integerArraySize; i++) {
				integerArray[i] = in.readInt();
			}
			return new Variant(integerArray);
			
		case DOUBLE_MATRIX:
			int doubleArraySize = in.readInt();
			double[] doubleArray = new double[doubleArraySize];
			for (int i = 0; i < doubleArraySize; i++) {
				doubleArray[i] = in.readDouble();
			}
			return new Variant(doubleArray);
			
		case STRING_MATRIX:
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
