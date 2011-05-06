
package lang.cpp;

import com.Variant;
import com.sun.jna.Library;
import com.sun.jna.Structure;
import com.sun.jna.Union;

public interface GameLibraryInterface extends Library {
	public static class VariantUnion extends Union {
		public boolean boolValue;
		public int intValue;
		public double doubleValue;
		public String stringValue;
		
		public IntMatrix1.ByReference intMatrix1;
		public IntMatrix2.ByReference intMatrix2;
		
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
				setType(IntMatrix1.ByReference.class);
				this.intMatrix1 =
					new IntMatrix1.ByReference((int[]) variant.getValue());
				break;
			
			case INT_MATRIX2:
				setType(IntMatrix2.ByReference.class);
				this.intMatrix2 =
					new IntMatrix2.ByReference((int[][]) variant.getValue());
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
	}
	
	public static class VariantStruct extends Structure {
		public byte type;
		public VariantUnion values;
		
		public VariantStruct() {
			values = new VariantUnion();
		}
		
		public void setValue(Variant variant) {
			type = variant.getType().getId();
			values.setValue(variant);
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
	}
	
	public static class IntMatrix1 extends Structure {
		public int length;
		public int[] values;
		
		public static class ByReference extends IntMatrix1 implements
			Structure.ByReference {
			
			public ByReference(int[] values) {
				this.length = values.length;
				this.values = values;
			}
			
			public int[] getMatrix() {
				return values;
			}
		}
	}
	
	public static class IntMatrix2 extends Structure {
		public int length;
		public int length2;
		public int[] values;
		
		public static class ByReference extends IntMatrix2 implements
			Structure.ByReference {
			
			public ByReference(int[][] values) {
				this.length = values.length;
				this.length2 = values[0].length;
				
				int[] contiguous = new int[length * length2];
				for (int i = 0; i < length; i++) {
					for (int j = 0; j < length2; j++) {
						contiguous[i * length2 + j] = values[i][j];
					}
				}
				
				this.values = contiguous;
			}
			
			public int[][] getMatrix() {
				int[][] matrix = new int[length][length2];
				
				for (int i = 0; i < length; i++) {
					for (int j = 0; j < length2; j++) {
						matrix[i][j] = values[i * length2 + j];
					}
				}
				
				return matrix;
			}
		}
	}
	
	public void init(int nbParameters, String[] parameters);
	
	public void addAi(short aiId, String aiName, String playerName);
	
	public void play();
	
	public void endGame();
	
	public void disqualifyAi(String aiName, String reason);
	
	public VariantStruct.ByValue performGameFunction(int functionId,
		int nbParameters, VariantStruct parameters[]);
}
