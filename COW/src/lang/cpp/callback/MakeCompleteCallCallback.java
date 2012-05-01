
package lang.cpp.callback;

import lang.cpp.VariantStruct;
import com.sun.jna.Callback;

public interface MakeCompleteCallCallback extends Callback {
	public int invoke(
		int functionId,
		int nbParameters,
		VariantStruct.ByValue parameter1,
		VariantStruct.ByValue parameter2,
		VariantStruct.ByValue parameter3,
		VariantStruct.ByValue parameter4,
		VariantStruct.ByValue parameter5,
		VariantStruct.ByValue parameter6,
		VariantStruct.ByValue parameter7,
		VariantStruct.ByValue parameter8);
}
