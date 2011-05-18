package lang.cpp.callback;

import lang.cpp.VariantStruct;
import lang.cpp.VariantStruct.ByValue;
import com.sun.jna.Callback;

public interface AddParameterCallback extends Callback {
	public void invoke(VariantStruct.ByValue parameter);
}
