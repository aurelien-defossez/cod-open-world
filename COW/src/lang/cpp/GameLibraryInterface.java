
package lang.cpp;

import com.VariantType;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface GameLibraryInterface extends Library {
	public static class VariantStruct extends Structure {
		public byte type;
		public Pointer value;
		
		public VariantStruct() {
			// Do nothing
		}
		
		public VariantStruct(VariantType type, com.sun.jna.ptr.ByReference value) {
			this.type = type.getId();
			this.value = value.getPointer();
		}
		
		public static VariantStruct[] createArray(int nbStructs) {
			return (VariantStruct[]) (new VariantStruct().toArray(nbStructs));
		}
		
		public static VariantStruct[] toContiguous(VariantStruct[] variants) {
			VariantStruct[] contiguous = createArray(variants.length);
			
			for (int i = 0; i < variants.length; i++) {
				VariantStruct variant = variants[i];

				contiguous[i].type = variant.type;
				contiguous[i].value = variant.value;
			}
			
			return contiguous;
		}
		
		public static class ByValue extends VariantStruct implements
			Structure.ByValue {
			
			public ByValue() {
				// Do nothing
			}
			
			public ByValue(VariantType type, com.sun.jna.ptr.ByReference value) {
				super(type, value);
			}
		}
	}
	
	public void test(VariantStruct[] variants);
	
	public void init(int nbParameters, String[] parameters);
	
	public void addAi(short aiId, String aiName, String playerName);
	
	public void play();
	
	public void endGame();
	
	public void disqualifyAi(String aiName, String reason);
	
	public void performGameFunction(int functionId, int nbParameters,
		VariantStruct parameters[]);
}
