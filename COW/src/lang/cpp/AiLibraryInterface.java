
package lang.cpp;

import lang.cpp.callback.AddParameterCallback;
import lang.cpp.callback.MakeCompleteReturnCallCallbackImpl;
import lang.cpp.callback.MakeReturnCallCallbackImpl;
import lang.cpp.callback.PrepareCallCallback;

import com.sun.jna.Library;

public interface AiLibraryInterface extends Library {
	public void registerCallbacks(PrepareCallCallback prepareCallcallback,
		AddParameterCallback setParameterCallback,
		MakeReturnCallCallbackImpl makeCallCallback,
		MakeCompleteReturnCallCallbackImpl makeCompleteReturnCallCallback);
	
	public abstract void performAiFunction(short functionId, int nbParameters,
		VariantStruct parameters[]);
	
	public abstract void stop();
}
