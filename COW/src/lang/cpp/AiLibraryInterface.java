
package lang.cpp;

import com.sun.jna.Library;

public interface AiLibraryInterface extends Library {
	public void registerCallbacks(PrepareCallCallback prepareCallcallback,
		AddParameterCallback setParameterCallback,
		MakeCallCallback makeCallCallback);
	
	public abstract void performAiFunction(short functionId, int nbParameters,
		VariantStruct parameters[]);
	
	public abstract void stop();
}
