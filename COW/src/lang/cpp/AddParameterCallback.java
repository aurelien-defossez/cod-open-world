package lang.cpp;

import com.sun.jna.Callback;

public interface AddParameterCallback extends Callback {
	public void invoke(VariantStruct.ByValue parameter);
}
