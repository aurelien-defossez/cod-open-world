
package lang.cpp;

import com.Variant;
import com.VariantType;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Union;

public interface GameLibraryInterface extends Library {
	public static class VariantUnion extends Union {
		public boolean boolValue;
		public int intValue;
		public double doubleValue;
		public String stringValue;
		
		public IntMatrix.ByReference intMatrix;
		public Pointer returnIntMatrix;
		
		public void setValue(Variant variant) {
			switch (variant.getType()) {
			case VOID:
				setType(int.class);
				this.intValue = 0;
				break;
			
			case BOOL:
				setType(boolean.class);
				this.boolValue = (Boolean) variant.getValue();
				break;
			
			case INT:
				setType(int.class);
				this.intValue = (Integer) variant.getValue();
				break;
			
			case DOUBLE:
				setType(double.class);
				this.doubleValue = (Double) variant.getValue();
				break;
			
			case STRING:
				setType(String.class);
				this.stringValue = (String) variant.getValue();
				break;
			
			case BOOL_MATRIX1:
				// TODO
				break;
			
			case BOOL_MATRIX2:
				// TODO
				break;
			
			case BOOL_MATRIX3:
				// TODO
				break;
			
			case INT_MATRIX1:
				setType(IntMatrix.ByReference.class);
				this.intMatrix =
					new IntMatrix.ByReference((int[]) variant.getValue());
				break;
			
			case INT_MATRIX2:
				setType(IntMatrix.ByReference.class);
				this.intMatrix =
					new IntMatrix.ByReference((int[][]) variant.getValue());
				break;
			
			case INT_MATRIX3:
				// TODO
				break;
			
			case DOUBLE_MATRIX1:
				// TODO
				break;
			
			case DOUBLE_MATRIX2:
				// TODO
				break;
			
			case DOUBLE_MATRIX3:
				// TODO
				break;
			
			case STRING_MATRIX1:
				// TODO
				break;
			
			case STRING_MATRIX2:
				// TODO
				break;
			
			case STRING_MATRIX3:
				// TODO
				break;
			}
		}
		
		public static class ByValue extends VariantUnion implements
			Union.ByValue {
			// Empty class
		}
	}
	
	public static class VariantStruct extends Structure {
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
				int[][] matrix = (int[][])variant.getValue();
				length1 = matrix.length;
				length2 = matrix[0].length;
			}
		}
		
		public int[] getIntMatrix1() {
			return values.returnIntMatrix.getIntArray(0, length1);
		}
		
		public int[][] getIntMatrix2() {
			int[] rawMatrix =
				values.returnIntMatrix.getIntArray(0, length1 * length2);
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
	
	public static class IntMatrix extends Structure {
		public int[] values;
		
		public static class ByReference extends IntMatrix implements
			Structure.ByReference {
			
			public ByReference(int[] values) {
				this.values = values;
			}
			
			public ByReference(int[][] values) {
				int length = values.length;
				int length2 = values[0].length;
				
				this.values = new int[length * length2];
				for (int i = 0; i < length; i++) {
					for (int j = 0; j < length2; j++) {
						this.values[i * length2 + j] = values[i][j];
					}
				}
			}
			
			public int[] getMatrix() {
				return values;
			}
		}
	}

	public void registerCallbacks(PrepareCallCallback prepareCallcallback,
		AddParameterCallback setParameterCallback,
		MakeCallCallback makeCallCallback);
	
	public void init(int nbParameters, String[] parameters);
	
	public void addAi(short aiId, String aiName, String playerName);
	
	public void play();
	
	public void endGame();
	
	public void disqualifyAi(String aiName, String reason);
	
	public VariantStruct.ByValue performGameFunction(int functionId,
		int nbParameters, VariantStruct parameters[]);
}
