/**
 * Variant - This class represents a variable of a defined type, which can be a
 * boolean, an integer, a double, a string, or an array of one of these types.
 * It can also be a void variable, thus it represents the absence of a variable.
 */

package com;

import java.io.IOException;
import com.remote.CompressedDataInputStream;
import com.remote.CompressedDataOutputStream;
import lang.cpp.VariantStruct;
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
	
	/**
	 * Creates a variant from a C++ variant structure.
	 * 
	 * @param variant The C++ variant structure.
	 */
	public Variant(VariantStruct variant) {
		this.type = VariantType.valueOf(variant.type);
		
		switch (type) {
		case VOID:
			break;
		
		case BOOL:
			this.value = variant.values.boolValue;
			break;
		
		case INT:
			this.value = new Integer(variant.values.intValue);
			break;
		
		case DOUBLE:
			this.value = variant.values.doubleValue;
			break;
		
		case STRING:
			this.value = variant.values.stringValue;
			break;
		
		case BOOL_MATRIX1:
			// this.value = variant.values.;
			break;
		
		case BOOL_MATRIX2:
			// this.value = variant.values.;
			break;
		
		case BOOL_MATRIX3:
			// this.value = variant.values.;
			break;
		
		case INT_MATRIX1:
			this.value = variant.getIntMatrix1();
			break;
		
		case INT_MATRIX2:
			this.value = variant.getIntMatrix2();
			break;
		
		case INT_MATRIX3:
			// this.value = variant.values.;
			break;
		
		case DOUBLE_MATRIX1:
			// this.value = variant.values.;
			break;
		
		case DOUBLE_MATRIX2:
			// this.value = variant.values.;
			break;
		
		case DOUBLE_MATRIX3:
			// this.value = variant.values.;
			break;
		
		case STRING_MATRIX1:
			// this.value = variant.values.;
			break;
		
		case STRING_MATRIX2:
			// this.value = variant.values.;
			break;
		
		case STRING_MATRIX3:
			// this.value = variant.values.;
			break;
		}
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
	 * Returns the variant boolean value.
	 * 
	 * @return the variant boolean value.
	 */
	public Boolean getBoolValue() {
		return (Boolean) value;
	}
	
	/**
	 * Returns the variant integer value.
	 * 
	 * @return the variant integer value.
	 */
	public Integer getIntValue() {
		return (Integer) value;
	}
	
	/**
	 * Returns the variant double value.
	 * 
	 * @return the variant double value.
	 */
	public Double getDoubleValue() {
		return (Double) value;
	}
	
	/**
	 * Returns the variant string value.
	 * 
	 * @return the variant string value.
	 */
	public String getStringValue() {
		return (String) value;
	}
	
	/**
	 * Returns the variant 1D boolean matrix.
	 * 
	 * @return the variant 1D boolean matrix.
	 */
	public boolean[] getBoolMatrix1Value() {
		return (boolean[]) value;
	}
	
	/**
	 * Returns the variant 2D boolean matrix.
	 * 
	 * @return the variant 2D boolean matrix.
	 */
	public boolean[][] getBoolMatrix2Value() {
		return (boolean[][]) value;
	}
	
	/**
	 * Returns the variant 3D boolean matrix.
	 * 
	 * @return the variant 3D boolean matrix.
	 */
	public boolean[][][] getBoolMatrix3Value() {
		return (boolean[][][]) value;
	}
	
	/**
	 * Returns the variant 1D integer matrix.
	 * 
	 * @return the variant 1D integer matrix.
	 */
	public int[] getIntMatrix1Value() {
		return (int[]) value;
	}
	
	/**
	 * Returns the variant 2D integer matrix.
	 * 
	 * @return the variant 2D integer matrix.
	 */
	public int[][] getIntMatrix2Value() {
		return (int[][]) value;
	}
	
	/**
	 * Returns the variant 3D integer matrix.
	 * 
	 * @return the variant 3D integer matrix.
	 */
	public int[][][] getIntMatrix3Value() {
		return (int[][][]) value;
	}
	
	/**
	 * Returns the variant 1D double matrix.
	 * 
	 * @return the variant 1D double matrix.
	 */
	public double[] getDoubleMatrix1Value() {
		return (double[]) value;
	}
	
	/**
	 * Returns the variant 2D double matrix.
	 * 
	 * @return the variant 2D double matrix.
	 */
	public double[][] getDoubleMatrix2Value() {
		return (double[][]) value;
	}
	
	/**
	 * Returns the variant 3D double matrix.
	 * 
	 * @return the variant 3D double matrix.
	 */
	public double[][][] getDoubleMatrix3Value() {
		return (double[][][]) value;
	}
	
	/**
	 * Returns the variant 1D string matrix.
	 * 
	 * @return the variant 1D string matrix.
	 */
	public String[] getStringMatrix1Value() {
		return (String[]) value;
	}
	
	/**
	 * Returns the variant 2D string matrix.
	 * 
	 * @return the variant 2D string matrix.
	 */
	public String[][] getStringMatrix2Value() {
		return (String[][]) value;
	}
	
	/**
	 * Returns the variant 3D string matrix.
	 * 
	 * @return the variant 3D string matrix.
	 */
	public String[][][] getStringMatrix3Value() {
		return (String[][][]) value;
	}
	
	/**
	 * Returns the matrix cardinalities.
	 * 
	 * @return an array of 1 to 3 cardinalities, depending on the matrix size.
	 */
	public int[] getMatrixCardinalities() {
		switch (type) {
		case BOOL_MATRIX1:
			boolean[] boolMatrix1 = (boolean[]) value;
			return new int[] { boolMatrix1.length };
			
		case BOOL_MATRIX2:
			boolean[][] boolMatrix2 = (boolean[][]) value;
			return new int[] { boolMatrix2.length,
				(boolMatrix2.length == 0) ? 0 : boolMatrix2[0].length };
			
		case BOOL_MATRIX3:
			boolean[][][] boolMatrix3 = (boolean[][][]) value;
			return new int[] {
				boolMatrix3.length,
				(boolMatrix3.length == 0) ? 0 : boolMatrix3[0].length,
				(boolMatrix3.length == 0 || boolMatrix3[0].length == 0) ? 0
					: boolMatrix3[0][0].length };
			
		case INT_MATRIX1:
			int[] intMatrix1 = (int[]) value;
			return new int[] { intMatrix1.length };
			
		case INT_MATRIX2:
			int[][] intMatrix2 = (int[][]) value;
			return new int[] { intMatrix2.length,
				(intMatrix2.length == 0) ? 0 : intMatrix2[0].length };
			
		case INT_MATRIX3:
			int[][][] intMatrix3 = (int[][][]) value;
			return new int[] { intMatrix3.length,
				(intMatrix3.length == 0) ? 0 : intMatrix3[0].length,
				(intMatrix3.length == 0 || intMatrix3[0].length == 0) ? 0
					: intMatrix3[0][0].length };
			
		case DOUBLE_MATRIX1:
			double[] doubleMatrix1 = (double[]) value;
			return new int[] { doubleMatrix1.length };
			
		case DOUBLE_MATRIX2:
			double[][] doubleMatrix2 = (double[][]) value;
			return new int[] { doubleMatrix2.length,
				(doubleMatrix2.length == 0) ? 0 : doubleMatrix2[0].length };
			
		case DOUBLE_MATRIX3:
			double[][][] doubleMatrix3 = (double[][][]) value;
			return new int[] { doubleMatrix3.length,
				(doubleMatrix3.length == 0) ? 0 : doubleMatrix3[0].length,
				(doubleMatrix3.length == 0 || doubleMatrix3[0].length == 0) ? 0
					: doubleMatrix3[0][0].length };
			
		case STRING_MATRIX1:
			String[] stringMatrix1 = (String[]) value;
			return new int[] { stringMatrix1.length };
			
		case STRING_MATRIX2:
			String[][] stringMatrix2 = (String[][]) value;
			return new int[] { stringMatrix2.length,
				(stringMatrix2.length == 0) ? 0 : stringMatrix2[0].length };
			
		case STRING_MATRIX3:
			String[][][] stringMatrix3 = (String[][][]) value;
			return new int[] { stringMatrix3.length,
				(stringMatrix3.length == 0) ? 0 : stringMatrix3[0].length,
				(stringMatrix3.length == 0 || stringMatrix3[0].length == 0) ? 0
					: stringMatrix3[0][0].length };
			
		default:
			return new int[] { 0 };
		}
	}
	
	/**
	 * Serializes the variant in a data output stream.
	 * 
	 * @param out the data output stream.
	 * @throws IOException if an error occurs while writing the variant.
	 */
	public void serialize(CompressedDataOutputStream out) throws IOException {
		out.writeVariant(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String strValue;
		
		switch (type) {
		case BOOL:
		case INT:
		case DOUBLE:
		case STRING:
			strValue = "=" + value;
			break;
		
		case BOOL_MATRIX1:
		case INT_MATRIX1:
		case DOUBLE_MATRIX1:
		case STRING_MATRIX1:
			int[] card1 = getMatrixCardinalities();
			strValue = "[" + card1[0] + "]";
			break;
		
		case BOOL_MATRIX2:
		case INT_MATRIX2:
		case DOUBLE_MATRIX2:
		case STRING_MATRIX2:
			int[] card2 = getMatrixCardinalities();
			strValue = "[" + card2[0] + "][" + card2[1] + "]";
			break;
		
		case BOOL_MATRIX3:
		case INT_MATRIX3:
		case DOUBLE_MATRIX3:
		case STRING_MATRIX3:
			int[] card3 = getMatrixCardinalities();
			strValue = "[" + card3[0] + "][" + card3[1] + "][" + card3[2] + "]";
			break;
		
		case VOID:
		default:
			strValue = "";
			break;
		}
		
		return "Variant(" + type.name() + strValue + ")";
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
		return in.readVariant();
	}
}
