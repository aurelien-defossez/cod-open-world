
package lang.cpp;

import com.Variant;
import com.VariantType;
import com.sun.jna.Structure;

public class VariantStruct extends Structure {
	public byte type;
	public int length1;
	public int length2;
	public int length3;
	public VariantUnion.ByValue values;
	
	public VariantStruct() {
		values = new VariantUnion.ByValue();
	}
	
	public void setValue(Variant variant) {
		VariantType vType = variant.getType();
		type = vType.getId();
		values.setValue(variant);
		
		// TODO: Other matrices
		if (vType == VariantType.INT_MATRIX1) {
			length1 = ((int[]) variant.getValue()).length;
		} else if (vType == VariantType.INT_MATRIX2) {
			int[][] matrix = (int[][]) variant.getValue();
			length1 = matrix.length;
			length2 = matrix[0].length;
		}
	}
	
	public int[] getIntMatrix1() {
		return values.returnIntMatrix.getIntArray(0, length1);
	}
	
	public int[][] getIntMatrix2() {
		int[] rawMatrix = values.returnIntMatrix.getIntArray(0,
			length1 * length2);
		int[][] matrix = new int[length1][length2];
		
		for (int i = 0; i < length1; i++) {
			for (int j = 0; j < length2; j++) {
				matrix[i][j] = rawMatrix[i * length2 + j];
			}
		}
		
		return matrix;
	}
	
	public static VariantStruct[] createArray(int nbStructs) {
		return (VariantStruct[]) (new VariantStruct().toArray(nbStructs));
	}
	
	public static VariantStruct[] createArray(Variant[] variants) {
		VariantStruct[] array = createArray(variants.length);
		
		for (int i = 0; i < array.length; i++) {
			array[i].setValue(variants[i]);
		}
		
		return array;
	}
	
	public static class ByValue extends VariantStruct implements
		Structure.ByValue {
		// Empty class
	}
	
	public static class ByReference extends VariantStruct implements
		Structure.ByReference {
		// Empty class
	}
}
