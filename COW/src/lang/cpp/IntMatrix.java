
package lang.cpp;

import com.sun.jna.Structure;

public class IntMatrix extends Structure {
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
