
package lang.cpp;

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
		
		public static VariantUnion[] createArray(int nbStructs) {
			return (VariantUnion[]) (new VariantUnion().toArray(nbStructs));
		}
		
		public void setValue(boolean value) {
			setType(boolean.class);
			boolValue = value;
		}
		
		public void setValue(int value) {
			setType(int.class);
			intValue = value;
		}
		
		public void setValue(double value) {
			setType(double.class);
			doubleValue = value;
		}
		
		public void setValue(String value) {
			setType(String.class);
			stringValue = value;
		}
		
		public void setValue(int[] values) {
			setType(IntMatrix1.ByReference.class);
			intMatrix1 = new IntMatrix1.ByReference(values);
		}
		
		public void setValue(int[][] values) {
			setType(IntMatrix2.ByReference.class);
			intMatrix2 = new IntMatrix2.ByReference(values);
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
		}
	}
	
	public void init(int nbParameters, String[] parameters);
	
	public void addAi(short aiId, String aiName, String playerName);
	
	public void play();
	
	public void endGame();
	
	public void disqualifyAi(String aiName, String reason);
	
	public void performGameFunction(int functionId, int nbParameters,
		VariantUnion parameters[]);
}
