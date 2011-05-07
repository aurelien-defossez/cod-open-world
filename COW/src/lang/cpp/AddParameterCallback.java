package lang.cpp;

import lang.cpp.GameLibraryInterface.VariantStruct;
import com.sun.jna.Callback;

public interface AddParameterCallback extends Callback {
	public void invoke(VariantStruct.ByValue parameter);
}
