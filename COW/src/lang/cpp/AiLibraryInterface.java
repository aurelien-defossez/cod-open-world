
package lang.cpp;

import lang.cpp.callback.MakeReturnCallCallbackImpl;
import com.sun.jna.Library;

public interface AiLibraryInterface extends Library {
	public void registerCallbacks(MakeReturnCallCallbackImpl makeCallCallback);
	
	public abstract void performAiFunction(short functionId, int nbParameters,
		VariantStruct parameters[]);
	
	public abstract void stop();
}
