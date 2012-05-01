
package lang.cpp.callback;

import lang.cpp.VariantStruct;
import com.sun.jna.Callback;

public interface MakeCallCallback extends Callback {
	public void invoke(
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
